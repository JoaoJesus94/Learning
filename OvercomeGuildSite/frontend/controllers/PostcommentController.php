<?php

namespace frontend\controllers;

use common\models\Postcomment;
use common\models\Userprofile;
use Yii;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

/**
 * PostcommentController implements the CRUD actions for Postcomment model.
 */
class PostcommentController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['create', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['create', 'update', 'delete'],
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
     * Creates a new Postcomment model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate($post_id)
    {
        $model = new Postcomment();

        $userProfile = Userprofile::findOne(['user_id' => Yii::$app->user->getId()]);

        $model->date = date("Y-m-d H:i:s");
        $model->Posts_id = $post_id;
        $model->User_id = $userProfile->getId();

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['posts/' . $post_id]);
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Postcomment model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id, $post_id)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('editOwnPostComment', ['userPostComment' => $model])) {
            if ($model->load(Yii::$app->request->post()) && $model->save()) {
                return $this->redirect(['posts/' . $post_id]);
            }

            return $this->render('update', [
                'model' => $model,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'That comment wasn\'t created by you!');
            return $this->redirect(['/posts/' . $post_id]);
        }
    }

    /**
     * Deletes an existing Postcomment model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id, $post_id)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('deleteOwnPostComment', ['userPostComment' => $model])) {
            $model->delete();
            return $this->redirect(['posts/' . $post_id]);
        } else {
            Yii::$app->session->setFlash('error', 'That comment wasn\'t created by you!');
            return $this->redirect(['/posts/' . $post_id]);
        }
    }

    /**
     * Finds the Postcomment model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Postcomment the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Postcomment::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
