<?php

namespace backend\controllers;

use common\models\Userprofile;
use Yii;
use common\models\Userprofilehasevent;
use common\models\UserprofilehaseventSearch;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * UserprofilehaseventController implements the CRUD actions for Userprofilehasevent model.
 */
class UserprofilehaseventController extends Controller
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

    /**
     * Lists all Userprofilehasevent models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new UserprofilehaseventSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Userprofilehasevent model.
     * @param integer $userprofile_id
     * @param integer $event_id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($userprofile_id, $event_id)
    {
        return $this->render('view', [
            'model' => $this->findModel($userprofile_id, $event_id),
        ]);
    }

    /**
     * Creates a new Userprofilehasevent model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $eventId = $_POST["eventId"];
        $eventRole = $_POST["eventRole"];
        $eventOption = $_POST["eventOption"];
        $model = $this->findModel(Userprofile::findOne(['user_id' => Yii::$app->user->id])->id, $eventId);
        if ($model != null) {
            $model->role = $eventRole;
            $model->attending = $eventOption;
            $model->save();
        } else {
            $model = new Userprofilehasevent();
            $model->role = $eventRole;
            $model->userprofile_id = Userprofile::findOne(['user_id' => Yii::$app->user->id])->id;
            $model->attending = $eventOption;
            $model->event_id = $eventId;
            $model->save();
        }

        return $this->redirect(['event/']);
    }

    public function actionAdd(){
        $eventId = $_POST['eventId'];
        $userId = $_POST['userId'];
        if ($userId == ''){
            return $this->redirect(['overpoint/managebyevent', 'id' => $eventId]);
        }else{
            $model = $this->findModel($userId, $eventId);
            if($model != null){
                $model->role = 'Inserted';
                $model->attending = 'Inserted';
                $model->save();
            }else{
                $model = new Userprofilehasevent();
                $model->role = 'Inserted';
                $model->userprofile_id = $userId;
                $model->attending = 'Inserted';
                $model->event_id = $eventId;
                $model->save();
            }

        }
        return $this->redirect(['overpoint/managebyevent', 'id' => $eventId]);
    }


    /**
     * Updates an existing Userprofilehasevent model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $userprofile_id
     * @param integer $event_id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($userprofile_id, $event_id)
    {
        $model = $this->findModel($userprofile_id, $event_id);

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['view', 'userprofile_id' => $model->userprofile_id, 'event_id' => $model->event_id]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing Userprofilehasevent model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $userprofile_id
     * @param integer $event_id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($userprofile_id, $event_id)
    {
        $this->findModel($userprofile_id, $event_id)->delete();

        return $this->redirect(['overpoint/managebyevent', 'id' => $event_id]);
    }

    /**
     * Finds the Userprofilehasevent model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $userprofile_id
     * @param integer $event_id
     * @return Userprofilehasevent the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($userprofile_id, $event_id)
    {
        if (($model = Userprofilehasevent::findOne(['userprofile_id' => $userprofile_id, 'event_id' => $event_id])) !== null) {
            return $model;
        }

        return null;
    }
}
