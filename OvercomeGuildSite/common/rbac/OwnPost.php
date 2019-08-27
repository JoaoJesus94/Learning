<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnPost extends Rule
{
    public $name = 'isOwnPost';

    public function execute($user, $item, $params)
    {
        $userProfile = Userprofile::findOne(['user_id' => $user]);
        return isset($params['userPost']) ? $params['userPost']->User_id == $userProfile->id : false;
    }
}