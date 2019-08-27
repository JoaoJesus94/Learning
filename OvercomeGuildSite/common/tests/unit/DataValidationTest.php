<?php namespace common\tests;

use common\models\Event;
use common\models\User;
use common\models\Userprofile;

class DataValidationTest extends \Codeception\Test\Unit
{
    /**
     * @var \common\tests\UnitTester
     */
    protected $tester;

    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testUserDataValidation()
    {
        $user = new User();

        $user->username = null;
        $this->tester->assertFalse($user->validate(['username']));

        $user->username = 'Cytrine';
        $this->tester->assertTrue($user->validate(['username']));

        $user->email = null;
        $this->tester->assertFalse($user->validate(['email']));

        $user->email = 'cytrine@hotmail.com';
        $this->tester->assertTrue($user->validate(['email']));
    }

    public function testUserprofileValidation()
    {
        $userprofile = new Userprofile();

        $userprofile->displayName = null;
        $this->tester->assertFalse($userprofile->validate(['displayName']));

        $userprofile->displayName = 12;
        $this->tester->assertFalse($userprofile->validate(['displayName']));

        $userprofile->displayName = 'asdjejdkeodoepeosiesj';
        $this->tester->assertFalse($userprofile->validate(['displayName']));

        $userprofile->displayName = 'Cytrine';
        $this->tester->assertTrue($userprofile->validate(['displayName']));

        $userprofile->rank = null;
        $this->tester->assertFalse($userprofile->validate(['rank']));

        $userprofile->rank = 12;
        $this->tester->assertFalse($userprofile->validate(['rank']));

        $userprofile->rank = 'asdjejdkeodoepeosiesj';
        $this->tester->assertFalse($userprofile->validate(['rank']));

        $userprofile->rank = 'Guild Master';
        $this->tester->assertTrue($userprofile->validate(['rank']));

        $userprofile->role = null;
        $this->tester->assertFalse($userprofile->validate(['role']));

        $userprofile->role = 12;
        $this->tester->assertFalse($userprofile->validate(['role']));

        $userprofile->role = 'asdjejdkeodoepeosiesj';
        $this->tester->assertFalse($userprofile->validate(['role']));

        $userprofile->role = 'Admin';
        $this->tester->assertTrue($userprofile->validate(['role']));
    }

    public function testEventValidation(){
        $event = new Event();

        $event->eventName = null;
        $this->tester->assertFalse($event->validate(['eventName']));

        $event->eventName = 'asdasdasdasdasdasdrtyrtyrtyrtyy';
        $this->tester->assertFalse($event->validate(['eventName']));

        $event->eventName = 12;
        $this->tester->assertFalse($event->validate(['eventName']));

        $event->eventName = 'Raid';
        $this->tester->assertTrue($event->validate(['eventName']));

        $event->category = null;
        $this->tester->assertFalse($event->validate(['category']));

        $event->category = 'asdasdasdasdasdasdrtyrtyrtyrtyy';
        $this->tester->assertFalse($event->validate(['category']));

        $event->category = 12;
        $this->tester->assertFalse($event->validate(['category']));

        $event->category = 'PVE';
        $this->tester->assertTrue($event->validate(['category']));

        $event->date = null;
        $this->tester->assertFalse($event->validate(['date']));

        $event->date = '2019-01-18';
        $this->tester->assertTrue($event->validate(['date']));
    }
}