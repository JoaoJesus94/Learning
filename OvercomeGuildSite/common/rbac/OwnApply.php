<?php

namespace common\rbac;

use yii\rbac\Rule;
use common\models\Apply;

class OwnApply extends Rule
{
    public $name = 'isOwnApply';

    public function execute($user, $item, $params)
    {
        return isset($params['userApply']) ? $params['userApply']->user_id == $user : false;
    }
}