<?php namespace common\tests;

use common\models\Apply;
use common\models\Character;
use common\models\Event;
use common\models\News;
use common\models\Newscomment;
use common\models\Overpoint;
use common\models\Postcomment;
use common\models\Posts;
use common\models\Topic;
use common\models\User;
use common\models\Userprofile;
use common\models\Userprofilehasevent;
use Yii;

class SavingDataToDatabaseTest extends \Codeception\Test\Unit
{
    /**
     * @var \common\tests\UnitTester
     */
    protected $tester;
    private $user;
    private $userProfile;
    private $apply;
    private $character;
    private $overpoint;
    private $event;
    private $userprofileHasEvent;
    private $topic;
    private $post;
    private $postComment;
    private $news;
    private $newsComment;


    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testSavingUser()
    {
        $auth = Yii::$app->authManager;
        $adminRole = $auth->getRole('admin');
        $this->user = new User();
        $this->user->username = 'Cytrine';
        $this->user->setPassword('123456');
        $this->user->email = 'cytrine@hotmail.com';
        $this->user->generateAuthKey();
        $this->assertTrue($this->user->save());
        $this->tester->seeInDatabase('user', ['username' => 'Cytrine']);
        try {
            $auth->assign($adminRole, $this->user->getId());
        } catch (\Exception $e) {
        }
    }

    public function testSavingApply()
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

    public function testSavingUserprofile()
    {
        $this->userProfile = new Userprofile();
        $this->user = User::findOne(['username' => 'Cytrine']);
        $this->userProfile->displayName = 'Cytrine';
        $this->userProfile->role = 'Admin';
        $this->userProfile->rank = 'Guild Master';
        $this->userProfile->user_id = $this->user->getId();
        $this->assertTrue($this->userProfile->save());
        $this->tester->seeInDatabase('userprofile', ['displayName' => 'Cytrine',
            'user_id' => $this->user->getId()]);
    }

    public function testSavingCharacter()
    {
        $this->character = new Character();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->character->charName = 'Cytrine';
        $this->character->level = 120;
        $this->character->class = 'Mage';
        $this->character->race = 'Gnome';
        $this->character->mainSpec = 'Frost';
        $this->character->User_id = $this->userProfile->getId();
        $this->character->main = "1";
        $this->tester->assertTrue($this->character->save());
        $this->tester->seeInDatabase('character', ['charName' => 'Cytrine', 'level' => 120, 'class' => 'Mage']);
    }

    public function testSavingOverpoint()
    {
        $this->overpoint = new Overpoint();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->overpoint->op = 600;
        $this->overpoint->up = 2;
        $this->overpoint->calculatePriority();
        $this->overpoint->attendance = 5;
        $this->overpoint->userprofile_id = $this->userProfile->getId();
        $this->tester->assertTrue($this->overpoint->save());
        $this->tester->seeInDatabase('overpoint', ['priority' => 300,
            'userprofile_id' => $this->userProfile->getId()]);
    }

    public function testSavingEvent()
    {
        $this->event = new Event();
        $this->event->eventName = 'Raid';
        $this->event->date = '2019-01-10';
        $this->event->category = 'PVE';
        $this->tester->assertTrue($this->event->save());
        $this->tester->seeInDatabase('event', ['eventName' => 'Raid', 'date' => '2019-01-10']);
    }

    public function testSavingUserprofileHasEvent()
    {
        $this->userprofileHasEvent = new Userprofilehasevent();
        $this->event = Event::findOne(['eventName' => 'Raid', 'date' => '2019-01-10']);
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->userprofileHasEvent->event_id = $this->event->getPrimaryKey();
        $this->userprofileHasEvent->userprofile_id = $this->userProfile->getPrimaryKey();
        $this->userprofileHasEvent->attending = 'Going';
        $this->userprofileHasEvent->role = 'Damage';
        $this->tester->assertTrue($this->userprofileHasEvent->save());
        $this->tester->seeInDatabase('userprofile_has_event', ['event_id' => $this->event->getPrimaryKey(),
            'userprofile_id' => $this->userProfile->getPrimaryKey()]);
    }

    public function testSavingTopic()
    {
        $this->topic = new Topic();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->topic->topicName = 'Welcome';
        $this->topic->User_id = $this->userProfile->getPrimaryKey();
        $this->tester->assertTrue($this->topic->save());
        $this->tester->seeInDatabase('topic', ['topicName' => 'Welcome',
            'User_id' => $this->userProfile->getPrimaryKey()]);
    }

    public function testSavingPosts()
    {
        $this->post = new Posts();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->topic = Topic::findOne(['User_id' => $this->userProfile->getPrimaryKey()]);
        $this->post->date = '2019-01-10';
        $this->post->content = 'Hello World!';
        $this->post->title = 'Hello';
        $this->post->User_id = $this->userProfile->getPrimaryKey();
        $this->post->Topic_id = $this->topic->getPrimaryKey();
        $this->tester->assertTrue($this->post->save());
        $this->tester->seeInDatabase('posts', ['date' => '2019-01-10', 'title' => 'Hello',
            'content' => 'Hello World!']);
    }

    public function testSavingPostComment()
    {
        $this->postComment = new Postcomment();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->post = Posts::findOne(['User_id' => $this->userProfile->getPrimaryKey()]);
        $this->postComment->content = 'Goodbye World';
        $this->postComment->date = '2019-01-10';
        $this->postComment->Posts_id = $this->post->getPrimaryKey();
        $this->postComment->User_id = $this->userProfile->getPrimaryKey();
        $this->tester->assertTrue($this->postComment->save());
        $this->tester->seeInDatabase('postcomment', ['content' => 'Goodbye World',
            'User_id' => $this->userProfile->getPrimaryKey()]);
    }

    public function testSavingNews()
    {
        $this->news = new News();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->news->content = 'Goodbye World!';
        $this->news->tittle = 'Hello World!';
        $this->news->User_id = $this->userProfile->getPrimaryKey();
        $this->tester->assertTrue($this->news->save());
        $this->tester->seeInDatabase('news', ['content' => 'Goodbye World!',
            'User_id' => $this->userProfile->getPrimaryKey()]);
    }

    public function testSavingNewsComment()
    {
        $this->newsComment = new Newscomment();
        $this->userProfile = Userprofile::findOne(['displayName' => 'Cytrine', 'rank' => 'Guild Master']);
        $this->news = News::findOne(['User_id' => $this->userProfile->getPrimaryKey()]);
        $this->newsComment->content = 'Hello!';
        $this->newsComment->date = '2019-01-10';
        $this->newsComment->News_id = $this->news->getPrimaryKey();
        $this->newsComment->User_id = $this->userProfile->getPrimaryKey();
        $this->tester->assertTrue($this->newsComment->save());
        $this->tester->seeInDatabase('newscomment', ['content' => 'Hello!',
            'News_id' => $this->news->getPrimaryKey(), 'User_id' => $this->userProfile->getPrimaryKey()]);

    }


    public function testDeleteFromDatabase()
    {
        $this->user = User::findOne(['username' => 'Cytrine']);
        $this->userProfile = Userprofile::findOne(['user_id' => $this->user->getId()]);
        $this->apply = Apply::findOne(['user_id' => $this->user->getId()]);
        $this->event = Event::findOne(['eventName' => 'Raid', 'date' => '2019-01-10']);
        $this->overpoint = Overpoint::findOne(['userprofile_id' => $this->userProfile->getId()]);
        $this->character = Character::findOne(['User_id' => $this->userProfile->getId()]);
        $this->userprofileHasEvent = Userprofilehasevent::findOne(['event_id' => $this->event->getPrimaryKey(),
            'userprofile_id' => $this->userProfile->getPrimaryKey()]);
        $this->topic = Topic::findOne(['User_id' => $this->userProfile->getPrimaryKey()]);
        $this->post = Posts::findOne(['User_id' => $this->userProfile->getPrimaryKey(),
            'Topic_id' => $this->topic->getPrimaryKey()]);
        $this->postComment = Postcomment::findOne(['User_id' => $this->userProfile->getPrimaryKey(),
            'Posts_id' => $this->post->getPrimaryKey(), 'date' => '2019-01-10']);
        $this->news = News::findOne(['User_id' => $this->userProfile->getPrimaryKey()]);
        $this->newsComment = Newscomment::findOne(['News_id' => $this->news->getPrimaryKey(),
            'User_id' => $this->userProfile->getPrimaryKey()]);


        $this->newsComment->delete();
        $this->news->delete();
        $this->postComment->delete();
        $this->post->delete();
        $this->topic->delete();
        $this->userprofileHasEvent->delete();
        $this->character->delete();
        $this->overpoint->delete();
        $this->event->delete();
        $this->apply->delete();
        $this->userProfile->delete();
        $this->user->delete();


        $this->tester->dontSeeInDatabase('newscomment', ['content' => 'Hello!',
            'News_id' => $this->news->getPrimaryKey(), 'User_id' => $this->userProfile->getPrimaryKey()]);
        $this->tester->dontSeeInDatabase('news', ['User_id' => $this->userProfile->getPrimaryKey(),
            'tittle' => 'Hello World!']);
        $this->tester->dontSeeInDatabase('postcomment', ['content' => 'Goodbye World',
            'User_id' => $this->userProfile->getPrimaryKey()]);
        $this->tester->dontSeeInDatabase('posts', ['date' => '2019-01-10', 'title' => 'Hello',
            'content' => 'Hello World!']);
        $this->tester->dontSeeInDatabase('user', ['username' => 'Cytrine']);
        $this->tester->dontSeeInDatabase('apply', ['status' => 'OPEN', 'age' => 24, 'name' => 'Joao Jesus']);
        $this->tester->dontSeeInDatabase('userprofile', ['displayName' => 'Cytrine',
            'user_id' => $this->user->getId()]);
        $this->tester->dontSeeInDatabase('character', ['charName' => 'Cytrine', 'level' => 120,
            'class' => 'Mage']);
        $this->tester->dontSeeInDatabase('overpoint', ['op' => 600, 'up' => 2, 'priority' => 300,
            'attendance' => 5]);
        $this->tester->dontSeeInDatabase('event', ['eventName' => 'Raid', 'date' => '2019-01-10']);
        $this->tester->dontSeeInDatabase('userprofile_has_event', ['event_id' => $this->event->getPrimaryKey(),
            'userprofile_id' => $this->userProfile->getPrimaryKey()]);
        $this->tester->dontSeeInDatabase('topic', ['topicName' => 'Welcome',
            'User_id' => $this->userProfile->getPrimaryKey()]);
    }
}