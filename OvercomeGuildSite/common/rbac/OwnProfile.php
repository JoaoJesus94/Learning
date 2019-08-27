<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Userprofile;

class OwnProfile extends Rule
{
    public $name = 'isOwnProfile';

    public function execute($user, $item, $params)
    {
        return isset($params['userProfile']) ? $params['userProfile']->user_id == $user : false;
    }
}