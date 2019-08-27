<?php

namespace backend\controllers;

use common\models\Topic;
use common\models\Userprofile;
use Yii;
use yii\data\ArrayDataProvider;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

/**
 * TopicController implements the CRUD actions for Topic model.
 */
class TopicController extends Controller
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
     * Displays a single Topic model.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        $model = $this->findModel($id);
        $dataProvider = new ArrayDataProvider([
            'allModels' => $model->posts,
        ]);

        return $this->render('view', [
            'model' => $model,
            'dataProvider' => $dataProvider,
        ]);
    }

    /**
     * Creates a new Topic model.
     * If creation is successful, the browser will be redirected to the 'index' page.
     * @return mixed
     */

    public function actionCreate()
    {
        $model = new Topic();
        $model->User_id = Userprofile::findOne(['user_id' => Yii::$app->user->getId()])->getPrimaryKey();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['forum/']);
        }

        $topics = Topic::find()->all();
        $topicArray = [];
        foreach ($topics as $topic) {
            if ($topic->Topic_id == null) {
                $topicArray['Create Sub Into'][$topic['id']] = $topic['topicName'];
            }
        }

        return $this->render('create', [
            'topicArray' => $topicArray,
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Topic model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['/forum']);
        }

        return $this->render('update', [
            'model' => $model,
        ]);
    }

    /**
     * Deletes an existing Topic model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $model = $this->findModel($id);
        if ($model->Topic_id == null) {
            foreach ($model->topics as $topic) {
                foreach ($topic->posts as $posts) {
                    foreach ($posts->postcomments as $comments) {
                        $comments->delete();
                    }
                    $posts->delete();
                }
                $topic->delete();
            }
            $model->delete();
        } else {
            foreach ($model->posts as $posts) {
                foreach ($posts->postcomments as $comments) {
                    $comments->delete();
                }
                $posts->delete();
            }
            $model->delete();
        }

        return $this->redirect(['forum/']);
    }

    /**
     * Finds the Topic model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Topic the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Topic::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
