<?php namespace frontend\tests\acceptance;

use frontend\tests\AcceptanceTester;

class CreateApplyCest
{
    public function _before(AcceptanceTester $I)
    {
    }

    // tests
    public function CreateApplyTest(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->wait(2);
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Foo bar', 'LoginForm[password]' => '123456']);
        $I->wait(5);
        $I->see('Foo bar');
        $I->click('Apply');
        $I->fillField('#apply-name', 'Foo bar');
        $I->fillField('#apply-age', 23);
        $I->fillField('#apply-mainspec', 'Frost');
        $I->fillField('#apply-class', 'Mage');
        $I->fillField('#apply-race', 'Gnome');
        $I->wait(3);
        $I->click('Save');
        $I->wait(5);
        $I->click('View Apply');
        $I->see('Foo bar');
        $I->see(23);
        $I->see('Frost');
        $I->see('Mage');
        $I->see('Gnome');
        $I->wait(5);
    }
}
