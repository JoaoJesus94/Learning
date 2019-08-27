<?php

namespace backend\tests\functional;

use backend\tests\FunctionalTester;
use common\fixtures\UserFixture;
use yii\rbac\Assignment;

/**
 * Class LoginCest
 */
class LoginCest
{
    /**
     * @param FunctionalTester $I
     */
    public function loginUser(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->see('Login');
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Admin', 'LoginForm[password]' => '123456']);
        $I->see('Admin');
        $I->dontSee('Login');
        $I->see('Logout');
    }
}
