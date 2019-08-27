<?php

namespace backend\controllers;

use common\models\User;
use common\models\Userprofile;
use phpDocumentor\Reflection\DocBlock\Tags\Throws;
use Yii;
use common\models\Apply;
use common\models\ApplySearch;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use common\models\Overpoint;

/**
 * ApplyController implements the CRUD actions for Apply model.
 */
class ApplyController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'create', 'view', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['index', 'create', 'view', 'update', 'delete'],
                        'allow' => true,
                        'roles' => ['admin'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    'delete' => ['POST'],
                ],
            ],
        ];
    }

    public function actions()
    {
        return [
            'error' => [
                'class' => 'yii\web\ErrorAction',
            ],
        ];
    }

    /**
     * Lists all Apply models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new ApplySearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Apply model.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        return $this->render('view', [
            'model' => $this->findModel($id),
        ]);
    }

    /**
     * Creates a new Apply model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $model = new Apply();
        $exists = $model->applyExist(Yii::$app->user->getId());

        if($exists){
            $this->redirect(['/']);
        }

        $model->status = "OPEN";
        $model->user_id = Yii::$app->user->getId();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['/']);
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Apply model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing Apply model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $this->findModel($id)->delete();

        return $this->redirect(['index']);
    }

    public function actionRefuse($id){
        $apply = $this->findModel($id);

        $apply->status = 'REFUSED';
        $apply->save();
        return $this->redirect(['/']);
    }

    public function actionAccept($id){
        $apply = $this->findModel($id);

        $userProfile = new Userprofile();
        $userProfile->displayName = $apply->user->username;
        $userProfile->rank = 'Member';
        $userProfile->user_id = $apply->user->id;
        $userProfile->role = $apply->mainSpec;
        if($userProfile->validate() && $userProfile->save()){
            $points = new Overpoint();
            $points->userprofile_id = $userProfile->id;
            $points->op = 100;
            $points->up = 0;
            $points->priority = $points->op;
            $points->attendance = 0;
            if(!$points->validate() && $points->save()) {
                Yii::$app->session->setFlash('error', 'Something went wrong while creating overpoints!');
            }
        }else{
            Yii::$app->session->setFlash('error', 'Something went wrong while creating userProfile!');
            return $this->redirect(['apply/'.$apply->id]);
        }
        $points->save();
        $apply->status = 'ACCEPTED';
        $apply->save();

        $auth = Yii::$app->authManager;
        $memberRole = $auth->getRole('member');
        $auth->assign($memberRole, $userProfile->user->id);
        return $this->redirect(['/']);
    }

    /**
     * Finds the Apply model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Apply the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Apply::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
