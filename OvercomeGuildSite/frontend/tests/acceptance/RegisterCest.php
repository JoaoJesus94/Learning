<?php namespace frontend\tests\acceptance;
use frontend\tests\AcceptanceTester;

class RegisterCest
{
    public function _before(AcceptanceTester $I)
    {
    }

    // tests
    public function RegisterMember(AcceptanceTester $I)
    {
        $I->amOnPage('/');
        $I->click('Register');
        $I->wait(3);
        $I->fillField('#signupform-username', 'Foo bar');
        $I->fillField('#signupform-email', 'foobar@hotmail.com');
        $I->fillField('#signupform-password', '123456');
        $I->click('Signup');
        $I->wait(3);
        $I->see('Guest');
        $I->see('Foo bar');
        $I->wait(5);
    }
}
