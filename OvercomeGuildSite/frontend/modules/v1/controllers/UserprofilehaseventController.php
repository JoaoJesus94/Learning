<?php
/**
 * Created by PhpStorm.
 * User: Rogerio
 * Date: 06/01/2019
 * Time: 22:59
 */

namespace app\modules\v1\controllers;

use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class UserprofilehaseventController extends ActiveController
{
    public $modelClass = "common\models\Userprofilehasevent";

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

    public function actionEvents($idevent){

        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT userprofile_id, event_id, displayName, attending, userprofile_has_event.role
                    FROM event INNER JOIN userprofile_has_event ON event.id = userprofile_has_event.event_id
                    INNER JOIN userprofile ON  userprofile_has_event.userprofile_id = userprofile.id
                    WHERE userprofile_has_event.event_id = $idevent
                    ORDER BY userprofile_has_event.userprofile_id ASC");

        return $command->queryAll();
    }

    public function actionGoing($idevent){

        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT userprofile_id, event_id, displayName, attending, userprofile_has_event.role
                    FROM event INNER JOIN userprofile_has_event ON event.id = userprofile_has_event.event_id
                    INNER JOIN userprofile ON  userprofile_has_event.userprofile_id = userprofile.id
                    WHERE userprofile_has_event.event_id = $idevent AND userprofile_has_event.attending = 'Going'
                    ORDER BY userprofile_has_event.userprofile_id ASC");

        return $command->queryAll();
    }
}