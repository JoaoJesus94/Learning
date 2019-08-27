<?php

namespace frontend\controllers;

use common\models\Posts;
use common\models\Userprofile;
use Yii;
use yii\data\ArrayDataProvider;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

/**
 * PostsController implements the CRUD actions for Posts model.
 */
class PostsController extends Controller
{

    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {


        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['create', 'view', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['create', 'view', 'update', 'delete'],
                        'allow' => true,
                        'roles' => ['member'],
                    ],
                ],
            ],
            'verbs' => [
                'class' => VerbFilter::className(),
                'actions' => [
                    //'delete' => ['POST']
                ],
            ],
        ];
    }

    /**
     * Displays a single Posts model.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionView($id)
    {
        $model = $this->findModel($id);
        $commentDataProvider = new ArrayDataProvider([
            'allModels' => $model->postcomments,
        ]);

        return $this->render('view', [
            'commentDataProvider' => $commentDataProvider,
            'model' => $model
        ]);
    }

    /**
     * Creates a new Posts model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate($idTopic)
    {
        $model = new Posts();

        $userprofile = Userprofile::findOne(['user_id' => Yii::$app->user->getId()]);

        $model->User_id = $userprofile->getPrimaryKey();
        $model->date = date("Y-m-d H:i:s");
        $model->Topic_id = $idTopic;

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['topic/' . $idTopic]);
        }

        return $this->render('create', [
            'model' => $model,
            'idTopic' => $idTopic,
        ]);
    }

    /**
     * Updates an existing Posts model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id, $topicId)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('editOwnPost', ['userPost' => $model])) {
            if ($model->load(Yii::$app->request->post()) && $model->save()) {
                return $this->redirect(['view', 'id' => $model->id]);
            }

            return $this->render('update', [
                'model' => $model,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'That post wasn\'t created by you!');
            return $this->redirect(['/topic/' . $topicId]);
        }

    }

    /**
     * Deletes an existing Posts model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id, $topicId)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('deleteOwnPost', ['userPost' => $model])) {
            $topicId = $model->topic->id;
            foreach ($model->postcomments as $comment) {
                $comment->delete();
            }

            $model->delete();
            return $this->redirect(['topic/' . $topicId]);
        } else {
            Yii::$app->session->setFlash('error', 'That post wasn\'t created by you!');
            return $this->redirect(['/topic/' . $topicId]);
        }
    }

    /**
     * Finds the Posts model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Posts the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Posts::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
