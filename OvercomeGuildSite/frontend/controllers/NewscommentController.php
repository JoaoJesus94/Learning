<?php

namespace frontend\controllers;

use common\models\Newscomment;
use common\models\Userprofile;
use Yii;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

/**
 * NewscommentController implements the CRUD actions for Newscomment model.
 */
class NewscommentController extends Controller
{
    /**
     * {@inheritdoc}
     */
    public function behaviors()
    {
        return [
            'access' => [
                'class' => AccessControl::className(),
                'only' => ['index', 'create', 'update', 'delete'],
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
     * Creates a new Newscomment model.
     * If creation is successful, the browser will be redirected to the 'view' page.
     * @return mixed
     */
    public function actionCreate($new_id)
    {
        $model = new Newscomment();

        $userProfile = Userprofile::findOne(['user_id' => Yii::$app->user->getId()]);

        $model->date = date("Y-m-d H:i:s");
        $model->User_id = $userProfile->id;
        $model->News_id = $new_id;

        if ($model->load(Yii::$app->request->post()) && $model->save()) {
            return $this->redirect(['news/']);
        }

        return $this->render('create', [
            'model' => $model,
        ]);
    }

    /**
     * Updates an existing Newscomment model.
     * If update is successful, the browser will be redirected to the 'view' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionUpdate($id)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('editOwnNewComment', ['userNewComment' => $model])) {
            if ($model->load(Yii::$app->request->post()) && $model->save()) {
                return $this->redirect(['/news']);
            }

            return $this->render('update', [
                'model' => $model,
            ]);
        } else {
            Yii::$app->session->setFlash('error', 'That comment wasn\'t created by you!');
            return $this->redirect(['/news']);
        }
    }

    /**
     * Deletes an existing Newscomment model.
     * If deletion is successful, the browser will be redirected to the 'index' page.
     * @param integer $id
     * @return mixed
     * @throws NotFoundHttpException if the model cannot be found
     */
    public function actionDelete($id)
    {
        $model = $this->findModel($id);
        if (Yii::$app->user->can('deleteOwnNewComment', ['userNewComment' => $model])) {
            $model->delete();
            return $this->redirect(['/news']);
        } else {
            Yii::$app->session->setFlash('error', 'That comment wasn\'t created by you!');
            return $this->redirect(['/news']);
        }
    }

    /**
     * Finds the Newscomment model based on its primary key value.
     * If the model is not found, a 404 HTTP exception will be thrown.
     * @param integer $id
     * @return Newscomment the loaded model
     * @throws NotFoundHttpException if the model cannot be found
     */
    protected function findModel($id)
    {
        if (($model = Newscomment::findOne($id)) !== null) {
            return $model;
        }

        throw new NotFoundHttpException('The requested page does not exist.');
    }
}
