<?php

namespace backend\controllers;

use common\models\Event;
use common\models\Userprofile;
use common\models\Userprofilehasevent;
use Yii;
use common\models\Overpoint;
use common\models\OverpointSearch;
use yii\data\ArrayDataProvider;
use yii\db\Query;
use yii\filters\AccessControl;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;

/**
 * OverpointController implements the CRUD actions for Overpoint model.
 */
class OverpointController extends Controller
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

    /*public function actionDecay()
    {
        $points = Overpoint::find()->all();
        foreach ($points as $item) {
            if ($item->op >= 200) {
                $item->op /= 2;
            }
            if($item->up > 2){
                $item->up /= 2;
            }
            if ($item->up != 0) {
                $item->calculatePriority();
            }else{
                $item->priority = $item->op;
            }
            $item->save();
        }
        return $this->redirect(['overpoint/']);
    }*/

    public function actionResetpoints()
    {
        $points = Overpoint::find()->all();
        foreach ($points as $item) {
            $item->op = 100;
            $item->up = 0;
            $item->attendance = 0;
            $item->priority = $item->op;
            $item->save();
        }
        return $this->redirect(['overpoint/']);
    }

    public function actionManagebyevent($id = null)
    {
        $events = Event::find()->all();
        if ($id == null) {
            $userPoints = null;
            return $this->render('managebyevent', [
                'eventsModel' => $events,
                'userPoints' => $userPoints,
                'id' => $id,
            ]);
        } else {

            $event = Event::findOne(['id' => $id]);
            $conn = Yii::$app->getDb();
            $comm = $conn->createCommand("SELECT * FROM userprofile_has_event WHERE event_id = '$id' AND (attending = 'Going' OR attending = 'Maybe' OR attending = 'Inserted')");
            $result = $comm->queryAll();

            $points = array();
            foreach ($result as $item) {
                array_push($points, Overpoint::findOne(['userprofile_id' => $item['userprofile_id']]));
            }

            $provider = new ArrayDataProvider([
                'allModels' => $points,
                'sort' => [
                    'defaultOrder' => ['priority' => SORT_DESC],
                    'attributes' => ['op', 'priority', 'up', 'attendance'],
                ],
            ]);


            if ($provider->getTotalCount() != 0) {
                return $this->render('managebyevent', [
                    'eventsModel' => $events,
                    'userPoints' => $provider,
                    'id' => $id
                ]);
            } else {
                $provider = null;
                return $this->render('managebyevent', [
                    'eventsModel' => $events,
                    'userPoints' => $provider,
                    'id' => $id
                ]);
            }
        }
    }

    public function actionGivegear($pointsId, $eventId = null)
    {
        $points = Overpoint::findOne(['idoverpoint' => $pointsId]);
        $points->up += 2;
        if ($points->up != 0) {
            $points->calculatePriority();
        }
        $points->save();
        if ($eventId == null) {
            return $this->redirect(['overpoint/']);
        }
        return $this->redirect(['managebyevent', 'id' => $eventId]);
    }

    public function actionDonatedgear($pointsId, $eventId = null)
    {
        $points = Overpoint::findOne(['idoverpoint' => $pointsId]);
        $points->op += 60;
        if ($points->up != 0) {
            $points->calculatePriority();
        } else {
            $points->priority = $points->op;
        }
        $points->save();
        if ($eventId == null) {
            return $this->redirect(['overpoint/']);
        }
        return $this->redirect(['managebyevent', 'id' => $eventId]);
    }

    public function actionStartraid($id)
    {
        $event = Event::findOne(['id' => $id]);

        $userprofiles = $event->getUserprofiles()->all();
        foreach ($userprofiles as $userprofile) {
            $points = $userprofile->getOverpoints()->one();
            $points->op += 200;
            if ($points->up != 0) {
                $points->calculatePriority();
            } else {
                $points->priority = $points->op;
            }
            $points->save();
        }
        return $this->redirect(['managebyevent', 'id' => $id]);
    }

    public function actionEndraid($id)
    {
        $event = Event::findOne(['id' => $id]);
        $userprofiles = $event->getUserprofiles()->all();
        foreach ($userprofiles as $userprofile) {
            $points = $userprofile->getOverpoints()->one();
            $points->op += 100;
            $points->attendance += 1;
            if ($points->up != 0) {
                $points->calculatePriority();
            } else {
                $points->priority = $points->op;
            }
            $points->save();
        }
        return $this->redirect(['overpoint/', 'id' => $id]);
    }

    /**
     * Lists all Overpoint models.
     * @return mixed
     */
    public function actionIndex()
    {
        $searchModel = new OverpointSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Displays a single Overpoint model.
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
     * Creates a new Overpoint model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $model = new Overpoint();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->idoverpoint]);
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Overpoint model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['view', 'id' => $model->idoverpoint]);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing Overpoint model.
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

    /**
     * Finds the Overpoint model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Overpoint the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Overpoint::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
