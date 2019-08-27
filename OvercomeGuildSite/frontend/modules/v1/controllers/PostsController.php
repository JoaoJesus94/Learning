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

class PostsController extends ActiveController
{
    public $modelClass = 'common\models\Posts';

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

    }

    public function actionPosts($topicid)
    {
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT posts.id, displayName, title, content, posts.User_id
                    FROM posts INNER JOIN userprofile ON posts.User_id = userprofile.id
                    WHERE posts.Topic_id = $topicid
                    ORDER BY posts.id DESC");

        return $command->queryAll();
    }

    public function actionDel($idpost){

        $command =  Yii::$app->db->createCommand();
        $command->delete('postcomment', ['Posts_id' => $idpost])->execute();
        $command->delete('posts', ['id' => $idpost])->execute();
    }
}


















