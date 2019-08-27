<?php

namespace frontend\controllers;

use common\models\Apply;
use Yii;
use yii\filters\AccessControl;
use yii\filters\VerbFilter;
use yii\web\Controller;
use yii\web\NotFoundHttpException;

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
                'only' => ['create', 'view', 'update', 'delete'],
                'rules' => [
                    [
                        'actions' => ['create', 'view', 'update', 'delete'],
                        'allow' => false,
                        'roles' => ['member'],
                    ],
                    [
                        'actions' => ['create', 'view', 'update', 'delete'],
                        'allow' => true,
                        'roles' => ['@'],
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
        if(Yii::$app->user->can('editOwnApply', ['userApply' => $model])){
            if ($model->load(Yii::$app->request->post()) && $model->save()) {
                if($model->status == 'REFUSED'){
                    $model->status = 'OPEN';
                    $model->save();
                    return $this->redirect(['/']);
                }
                return $this->redirect(['/']);
            }
            return $this->render('update', [
                'model' => $model,
            ]);
        }else{
            Yii::$app->session->setFlash('error', 'You are not allowed!');
            return $this->redirect(['/']);
        }
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
        $model = $this->findModel($id);
        if (Yii::$app->user->can('deleteOwnApply', ['userApply' => $model])){
            $model->delete();
            return $this->redirect(['/']);
        }else{
            Yii::$app->session->setFlash('error', 'You are not allowed to delete that apply!');
            return $this->redirect(['/']);
        }

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
