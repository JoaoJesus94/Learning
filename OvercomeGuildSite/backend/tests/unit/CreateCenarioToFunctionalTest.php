<?php namespace backend\tests;

use common\models\Apply;
use common\models\Overpoint;
use common\models\User;
use common\models\Userprofile;
use Yii;
use yii\rbac\Assignment;

class CreateCenarioToFunctionalTest extends \Codeception\Test\Unit
{
    /**
     * @var \backend\tests\UnitTester
     */
    protected $tester;
    private $user;
    private $userProfile;
    private $overpoint;
    private $apply;

    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testCreateAdminUser()
    {
        $auth = Yii::$app->authManager;
        $adminRole = $auth->getRole('admin');
        $this->user = new User();
        $this->user->username = 'Admin';
        $this->user->setPassword('123456');
        $this->user->email = 'admin@hotmail.com';
        $this->user->generateAuthKey();
        $this->assertTrue($this->user->save());
        $this->tester->seeInDatabase('user', ['username' => 'Admin']);
        $auth->assign($adminRole, $this->user->getId());
    }

    public function testCreateUserProfileToAdmin()
    {
        $this->userProfile = new Userprofile();
        $this->user = User::findOne(['username' => 'Admin']);
        $this->userProfile->displayName = 'Admin';
        $this->userProfile->role = 'Admin';
        $this->userProfile->rank = 'Guild Master';
        $this->userProfile->user_id = $this->user->getId();
        $this->assertTrue($this->userProfile->save());
        $this->tester->seeInDatabase('userprofile', ['displayName' => 'Admin',
            'user_id' => $this->user->getId()]);
    }

    public function testCreateOverpointToAdmin()
    {
        $this->overpoint = new Overpoint();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Admin', 'rank' => 'Guild Master']);
        $this->overpoint->op = 600;
        $this->overpoint->up = 2;
        $this->overpoint->calculatePriority();
        $this->overpoint->attendance = 5;
        $this->overpoint->userprofile_id = $this->userProfile->getId();
        $this->tester->assertTrue($this->overpoint->save());
        $this->tester->seeInDatabase('overpoint', ['priority' => 300,
            'userprofile_id' => $this->userProfile->getId()]);
    }

    public function testCreateMemberUser()
    {
        $this->user = new User();
        $this->user->username = 'Cytrine';
        $this->user->setPassword('123456');
        $this->user->email = 'cytrine@hotmail.com';
        $this->user->generateAuthKey();
        $this->assertTrue($this->user->save());
        $this->tester->seeInDatabase('user', ['username' => 'Cytrine']);
    }

    public function testCreateApplyToMember()
    {
        $this->apply = new Apply();
        $this->user = User::findOne(['username' => 'Cytrine']);
        $this->apply->status = 'OPEN';
        $this->apply->name = 'João Jesus';
        $this->apply->age = 24;
        $this->apply->mainSpec = 'Frost';
        $this->apply->class = 'Mage';
        $this->apply->race = 'Gnome';
        $this->apply->user_id = $this->user->getId();
        $this->assertTrue($this->apply->save());
        $this->tester->seeInDatabase('apply', ['status' => 'OPEN', 'age' => 24, 'name' => 'Joao Jesus']);
    }
}