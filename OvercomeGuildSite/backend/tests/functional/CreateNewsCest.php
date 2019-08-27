<?php namespace backend\tests\functional;
use backend\tests\FunctionalTester;

class CreateNewsCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function createNewsTest(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Admin', 'LoginForm[password]' => '123456']);
        $I->see('News');
        $I->click('News');
        $I->see('Create New');
        $I->click('Create New');
        $I->see('Tittle');
        $I->see('Content');
        $I->fillField('#news-tittle', 'Welcome');
        $I->fillField('#news-content', 'Hello World');
        $I->click('Save');
        $I->see('Welcome');
        $I->see('Created by:');
    }
}
