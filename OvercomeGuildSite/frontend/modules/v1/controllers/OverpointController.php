<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:54
 */

namespace app\modules\v1\controllers;


use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class OverpointController extends ActiveController
{
    public $modelClass = 'common\models\Overpoint';

    public function behaviors()
    {
        $behaviors = parent::behaviors();
        $behaviors['authenticator'] = [
            'class' => CompositeAuth::className(),
            'authMethods' => [
                HttpBasicAuth::className(),
                HttpBearerAuth::className(),
                QueryParamAuth::className(),
            ],
        ];
        return $behaviors;
    }

    public function checkAccess($action, $model = null, $params = [])
    {
        if($action === 'create' || $action === 'delete' || $action === 'update'){
            if(!(\Yii::$app->user->can('admin'))){
                throw new \yii\web\ForbiddenHttpException("Apenas administradores");
            }
        }
    }

    public function actionOverpoint()
    {
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
            SELECT displayName, op, up, priority, attendance
            FROM overpoint INNER JOIN userprofile on overpoint.userprofile_id = userprofile.id
            ORDER BY priority DESC");

        return $command->queryAll();
    }
}

























