<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnNewComment extends Rule
{
    public $name = 'isOwnNewComment';

    public function execute($user, $item, $params)
    {
        $userProfile = Userprofile::findOne(['user_id' => $user]);
        return isset($params['userNewComment']) ? $params['userNewComment']->User_id == $userProfile->id : false;
    }
}