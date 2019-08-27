<?php namespace backend\tests\acceptance;
use backend\tests\AcceptanceTester;

class acceptApplyCest
{
    public function _before(AcceptanceTester $I)
    {
    }

    // tests
    public function tryToTest(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->wait(2);
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Admin', 'LoginForm[password]' => '123456']);
        $I->wait(5);
        $I->click('Applies');
        $I->see('OPEN');
        $I->click('View Foo bar');
        $I->wait(2);
        $I->click('Accept');
        $I->wait(2);
        $I->click('Applies');
        $I->click('View Foo bar');
        $I->see('ACCEPTED');
        $I->wait(5);
    }
}
