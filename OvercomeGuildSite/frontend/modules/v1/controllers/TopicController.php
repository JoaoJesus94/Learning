<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:43
 */

namespace app\modules\v1\controllers;


use common\models\Posts;
use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class TopicController extends ActiveController
{
    public $modelClass = 'common\models\Topic';

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

    public function actionTopic()
    {
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT topic.id, displayName, topicName, topicDescription , topic.User_id
                    FROM topic INNER JOIN userprofile ON topic.User_id = userprofile.id
                    WHERE topic.Topic_id IS NOT NULL
                    ORDER BY topic.id DESC");

        return $command->queryAll();
    }

    public function actionSubject(){
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT topic.id, topicName
                    FROM topic
                    WHERE Topic_id IS NULL
                    ORDER BY topic.id DESC");
        return $command->queryAll();
    }

    public function actionDel($idtopic){

        $idPost = Posts::findOne(['Topic_id' => $idtopic])['id'];

        $command =  Yii::$app->db->createCommand();
        $command->delete('postcomment', ['Posts_id' => $idPost])->execute();
        $command->delete('posts', ['Topic_id' => $idtopic])->execute();
        $command->delete('topic', ['id' => $idtopic])->execute();
    }
}