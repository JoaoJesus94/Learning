<?php

namespace app\modules\v1\controllers;

use common\models\User;
use common\models\Userprofile;
use Yii;
use yii\rest\ActiveController;

class UserController extends ActiveController
{
    public $modelClass = 'common\models\User';

    public function actions()
    {
        $actions = parent::actions();

        unset($actions['delete'], $actions['index'], $actions['view'], $actions['options'], $actions['create']);

        return $actions;
    }

    public function actionLogin()
    {
        $post = Yii::$app->request->post();
        $model = User::findOne(["username" => $post["username"]]);
        if (empty($model)) {
            return -1;
        }
        if ($model->validatePassword($post["password"])) {
            return ['id' => $model['id']] + ['auth_key' => $model['auth_key']] + ['email' => $model['email']];
        } else {
            return -2;
        }
    }

    public function actionRoster($userid){

        $modelUser = User::findOne(['id' => (Userprofile::findOne(['id' => $userid]))['user_id']]);
        $modelProfile = Userprofile::findOne(['id' => $userid]);

        $obj = new \stdClass();
        $obj->username = $modelUser['username'];
        $obj->email = $modelUser['email'];
        $obj->created_at = Yii::$app->formatter->asDate($modelUser['created_at']) ;
        $obj->displayName = $modelProfile['displayName'];
        $obj->rank = $modelProfile['rank'];
        $obj->role = $modelProfile['role'];

        return $obj;
    }
}
