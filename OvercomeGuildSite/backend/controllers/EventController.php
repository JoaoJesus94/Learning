<?php
namespace backend\controllers;
use common\models\Userprofilehasevent;
use Yii;
use common\models\Event;
use common\models\EventSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;

/**
 * EventController implements the CRUD actions for Event model.
 */
class EventController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'create', 'view', 'update'],
                'rules' => [
                    [
                        'actions' => ['index', 'create', 'view', 'update'],
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
     * Lists all Event models.
     * @return mixed
     */
    public function actionIndex()
    {
        $events = [];
        $lists = Event::find()->all();
        foreach ($lists as $list){
            $event = new \yii2fullcalendar\models\Event();
            $event->id = $list->id;
            $event->title = $list->eventName;
            $event->description = $list->description;
            $event->start = $list->date;
            $event->nonstandard = $list->category;
            $events[] = $event;
        }


        return $this->render('index', [
            'events'=> $events,
        ]);
    }
    /**
     * Displays a single Event model.
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
     * Creates a new Event model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate()
    {
        $model = new Event();
        //$model->date = $date;
        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['event/']);
        }else{
            return $this->render('create', [
                'model' => $model,
            ]);
        }
    }

    /**
     * Updates an existing Event model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['event/']);
        } else {
            return $this->render('update', [
                'model' => $model,
            ]);
        }
    }


    /**
     * Deletes an existing Event model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $event = $this->findModel($id);
        $eventData = Userprofilehasevent::find(['event_id' => $id])->all();
        if(!empty($eventData)){
            foreach ($eventData as $data){
                $data->delete();
            }
        }
        $event->delete();
        return $this->redirect(['event/']);
    }

    /**
     * Finds the Event model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Event the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Event::findOne($id)) !== null) {
            return $model;

        }
        throw new NotFoundHttpException('The requested page does not exist.');
    }


    /*public function actionJsoncalendar($start=NULL,$_=NULL){

        \Yii::$app->response->format = \yii\web\Response::FORMAT_JSON;

        $id = \Yii::$app->request->get('id');

        $times = Event::findAll(['id' => $id]);
        $events = array();

        foreach ($times AS $time){
            //Testing
            $Event = new \yii2fullcalendar\models\Event();
            $Event->id = $time->id;
            $Event->title = $time->eventName;
            $Event->start = date('Y-m-d\TH:i:s\Z',strtotime($time->date));
            $events[] = $Event;
        }

        return $events;
    }*/

}