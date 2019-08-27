<?php namespace backend\tests\functional;

use backend\tests\FunctionalTester;

class AcceptApplyCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function acceptApply(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Admin', 'LoginForm[password]' => '123456']);
        $I->see('Admin');
        $I->see('Logout');

        $I->amOnPage('/apply');
        $I->see('Applies');
        $I->see('View');
        $I->see('OPEN');
        $I->click('View');
        $I->see('Accept');
        $I->see('Refuse');
        $I->click('Accept');
        $I->seeLink('Applies');
        $I->click('Applies');
        $I->click('View');
        $I->see('ACCEPTED');
        $I->dontSee('OPEN');
    }
}
