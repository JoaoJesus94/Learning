<?php namespace frontend\tests\acceptance;
use frontend\tests\AcceptanceTester;

class AddCharacterCest
{
    public function _before(AcceptanceTester $I)
    {
    }

    // tests
    public function tryToTest(AcceptanceTester $I)
    {
        $I->amOnPage('/site/login');
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Cytrine', 'LoginForm[password]' => '123456']);
        $I->wait(3);
        $I->click('Profile');
        $I->click('Add Character');
        $I->fillField('#character-charname', 'Foo bar Character');
        $I->fillField('#character-level', 120);
        $I->selectOption('#dropRaces', 'Gnome');
        $I->wait(2);
        $I->selectOption('#dropClasses','Mage');
        $I->wait(2);
        $I->selectOption('#character-mainspec', 'Frost');
        $I->wait(2);
        $I->fillField('#character-main', 1);
        $I->wait(3);
        $I->click('Save');
        $I->wait(3);
        $I->see('Foo bar Character');
        $I->wait(3);
    }
}
