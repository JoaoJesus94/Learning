<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnCharacter extends Rule
{
    public $name = 'isOwnCharacter';

    public function execute($user, $item, $params)
    {
        $userProfile = Userprofile::findOne(['user_id' => $user]);
        return isset($params['userCharacter']) ? $params['userCharacter']->User_id == $userProfile->id : false;
    }
}