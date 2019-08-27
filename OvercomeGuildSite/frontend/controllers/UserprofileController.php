<?php

namespace frontend\controllers;

use common\models\Apply;
use Yii;
use common\models\Userprofile;
use common\models\UserprofileSearch;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * UserprofileController implements the CRUD actions for Userprofile model.
 */
class UserprofileController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'view', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['index'],
                        'allow' => true,
                        'roles' => ['@']
                    ],
                    [
                        'actions' => ['view', 'update', 'delete'],
                        'allow' => true,
                        'roles' => ['member']
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

    /**
     * Lists all Userprofile models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new UserprofileSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'dataProvider' => $dataProvider,
            'searchModel' => $searchModel]);
    }

    /**
     * Displays a single Userprofile model.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        $userProfileModel = $this->findModel($id);
        $applyModel = Apply::findOne(['user_id' => $userProfileModel->user_id]);
        $charactersModel = new \yii\data\ArrayDataProvider([
            'allModels' => $userProfileModel->getCharacters()->all()]);

        return $this->render('view', [
            'userProfileModel' => $userProfileModel,
            'charactersModel' => $charactersModel,
            'applyModel' => $applyModel,
        ]);
    }


    /**
     * Updates an existing Userprofile model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);
        $applyModel = Apply::findOne(['user_id' => $model->user_id]);
        if (Yii::$app->user->can('editOwnProfile', ['userProfile' => $model])) {
            if (($model->load(Yii::$app->request->post()) && $model->save()) && ($applyModel->load(Yii::$app->request->post()) && $applyModel->save())) {
                return $this->redirect(['view', 'id' => $model->id]);
            }

            return $this->render('update', [
                'model' => $model,
                'applyModel' => $applyModel,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'Unable to edit that profile');
            return $this->redirect(['/']);
        }
    }

    /**
     * Finds the Userprofile model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Userprofile the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Userprofile::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }

}
