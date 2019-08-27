<?php
namespace frontend\controllers;
use Yii;
use common\models\Event;
use common\models\EventSearch;
use yii\web\Controller;
use yii\web\NotFoundHttpException;
use yii\filters\VerbFilter;
use yii\filters\AccessControl;
use yii\web\View;
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
                'only' => ['index', 'view'],
                'rules' => [
                    [
                        'actions' => ['index'],
                        'allow' => true,
                        'roles' => ['member'],
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
            //$event->end = $list->endTime;
            $event->nonstandard = $list->category;
            $events[] = $event;
        }
        $searchModel = new EventSearch();
        $dataProvider = $searchModel->search(Yii::$app->request->queryParams);

        return $this->render('index', [
            'events'=> $events,
            'searchModel' => $searchModel,
            'dataProvider' => $dataProvider,
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


    public function actionJsoncalendar($start=NULL,$_=NULL){

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
    }

}