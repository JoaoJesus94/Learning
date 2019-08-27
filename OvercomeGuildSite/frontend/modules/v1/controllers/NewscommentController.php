<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:45
 */

namespace app\modules\v1\controllers;


use common\models\News;
use common\models\Newscomment;
use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class NewscommentController extends ActiveController
{
    public $modelClass = 'common\models\Newscomment';

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

    public function actionNewscomments($idnew)
    {
        $connection = Yii::$app->getDb();

        $command = $connection->createCommand("
                    SELECT newscomment.id, content, displayName
                    FROM newscomment INNER JOIN userprofile ON newscomment.User_id = userprofile.id
                    WHERE newscomment.News_id = $idnew
                    ORDER BY newscomment.id ASC");

        return $command->queryAll();
    }
}