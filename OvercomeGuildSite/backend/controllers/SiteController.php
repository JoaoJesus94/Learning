<?php

namespace backend\controllers;

use common\models\User;
use common\models\Userprofile;
use Yii;
use yii\data\ArrayDataProvider;
use yii\web\Controller;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use common\models\LoginForm;

/**
 * Site controller
 */
class SiteController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['logout', 'signup', 'index'],
                'rules' => [
                    [
                        'actions' => ['signup'],
                        'allow' => true,
                        'roles' => ['?'],
                    ],
                    [
                        'actions' => ['logout', 'index'],
                        'allow' => true,
                        'roles' => ['@'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'logout' => ['post'],
                ],
            ],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function actions()
    {
        return [
            'error' => [
                'class' => 'yii\web\ErrorAction',
            ],
        ];
    }

    /**
     * Displays homepage.
     *
     * @return string
     */
    public function actionIndex()
    {
        return $this->render('index');
    }

    /**
     * Login action.
     *
     * @return string
     */
    public function actionLogin()
    {
        if (!Yii::$app->user->isGuest) {
            return $this->goHome();
        }

        $model = new LoginForm();
        if ($model->load(Yii::$app->request->post()) && $model->login()) {
            if (Yii::$app->user->can('accessBackend')) {
                return $this->goBack();
            } else {
                Yii::$app->user->logout();
                Yii::$app->session->setFlash('error', 'You are not a Admin!');
                return $this->goHome();
            }
        } else {
            $model->password = '';

            return $this->render('login', [
                'model' => $model,
            ]);
        }
    }

    /**
     * Logout action.
     *
     * @return string
     */
    public function actionLogout()
    {
        Yii::$app->user->logout();

        return $this->goHome();
    }

    public function actionManagement(){
        $dataProvider = new ArrayDataProvider([
            'allModels' => User::find()->all(),
        ]);;

        return $this->render('management', [
            'dataProvider' => $dataProvider,
        ]);
    }

    public function actionDemote($userId){
        if(Yii::$app->user->getId() == $userId){
            Yii::$app->session->setFlash('error', 'Can\'t demote yourself');
            return $this->redirect(['management']);
        }
        $auth = Yii::$app->authManager;
        $adminRole = $auth->getRole('admin');
        $auth->revoke($adminRole, $userId);
        $memberRole = $auth->getRole('member');
        $auth->assign($memberRole, $userId);

        return $this->redirect(['management']);
    }

    public function actionPromote($userId){
        $auth = Yii::$app->authManager;
        $memberRole = $auth->getRole('member');
        $auth->revoke($memberRole, $userId);
        $adminRole = $auth->getRole('admin');
        $auth->assign($adminRole, $userId);

        $this->redirect(['management']);
    }

    public function actionGuildmanagement(){
        $dataProvider = new ArrayDataProvider([
            'allModels' => Userprofile::find()->all()
        ]);

        return $this->render('guild_management', [
            'dataProvider' => $dataProvider,
        ]);
    }
}
