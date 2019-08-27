<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:44
 */

namespace app\modules\v1\controllers;


use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class PostcommentController extends ActiveController
{
    public $modelClass = 'common\models\Postcomment';

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

    public function actionPostcomment($postid)
    {
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT postcomment.id, content, displayName
                    FROM postcomment INNER JOIN userprofile ON postcomment.User_id = userprofile.id
                    WHERE postcomment.Posts_id = $postid
                    ORDER BY postcomment.id ASC");

        return $command->queryAll();
    }
}