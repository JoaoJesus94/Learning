<?php namespace frontend\tests\acceptance;
use frontend\tests\AcceptanceTester;

class LoginCest
{
    public function _before(AcceptanceTester $I)
    {
    }

    // tests
    public function LoginTest(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->wait(2);
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Foo bar', 'LoginForm[password]' => '123456']);
        $I->wait(3);
        $I->see('Guest');
        $I->see('Foo bar');
        $I->wait(5);
    }
}