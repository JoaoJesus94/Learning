<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnPostComment extends Rule
{
    public $name = 'isOwnPostComment';

    public function execute($user, $item, $params)
    {
        $userProfile = Userprofile::findOne(['user_id' => $user]);
        return isset($params['userPostComment']) ? $params['userPostComment']->User_id == $userProfile->id : false;
    }
}