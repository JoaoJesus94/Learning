<?php
/**
 * Created by PhpStorm.
 * User: Rogério
 * Date: 22/12/2018
 * Time: 02:54
 */

namespace app\modules\v1\controllers;


use common\models\Userprofile;
use yii\filters\auth\CompositeAuth;
use yii\filters\auth\HttpBasicAuth;
use yii\filters\auth\HttpBearerAuth;
use yii\filters\auth\QueryParamAuth;
use yii\rest\ActiveController;
use yii\web\User;

class UserprofileController extends ActiveController
{
    public $modelClass = 'common\models\Userprofile';

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

    public function actionProfile($userid){

        $profile = Userprofile::findOne(['user_id' => $userid]);
        return $profile;
    }
}