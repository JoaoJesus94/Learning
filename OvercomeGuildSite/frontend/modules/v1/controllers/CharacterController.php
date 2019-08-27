<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:53
 */

namespace app\modules\v1\controllers;


use common\models\Character;
use common\models\Userprofile;
use Yii;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;

class CharacterController extends ActiveController
{
    public $modelClass = 'common\models\Character';

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

    public function actionChar($userid){
        $character = Character::findAll(['User_id' => $userid]);
        return $character;
    }
}