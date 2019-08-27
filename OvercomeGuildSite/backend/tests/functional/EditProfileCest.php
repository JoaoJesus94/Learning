<?php namespace backend\tests\functional;
use backend\tests\FunctionalTester;

class EditProfileCest
{
    public function _before(FunctionalTester $I)
    {
    }

    // tests
    public function addCharacterTest(FunctionalTester $I)
    {
        $I->amOnPage('/site/login');
        $I->submitForm('#login-form', ['LoginForm[username]' => 'Admin', 'LoginForm[password]' => '123456']);
        $I->click('Profile');
        $I->see('Admin');
        $I->see('Edit Profile');
        $I->click('Edit Profile');
        $I->see('Update Userprofile');
        $I->fillField('#userprofile-displayname', 'MasterAdmin');
        $I->fillField('#userprofile-rank','GuildMaster');
        $I->fillField('#userprofile-role','Damage');
        $I->click('Save');
        $I->see('MasterAdmin');
        $I->see('Damage');
    }
}
