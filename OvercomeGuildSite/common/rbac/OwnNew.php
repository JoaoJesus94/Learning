<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnNew extends Rule
{
    public $name = 'isOwnNew';

    public function execute($user, $item, $params)
    {
        $userProfile = Userprofile::findOne(['user_id' => $user]);
        return isset($params['userNew']) ? $params['userNew']->User_id == $userProfile->id : false;
    }
}