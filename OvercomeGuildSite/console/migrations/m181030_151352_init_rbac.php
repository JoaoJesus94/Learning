<?php

use common\models\Apply;
use common\models\Overpoint;
use common\models\User;
use common\models\Userprofile;
use yii\db\Migration;

/**
 * Class m181030_151352_init_rbac
 */
class m181030_151352_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {

    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        echo "m181030_151352_init_rbac cannot be reverted.\n";

        return false;
    }


    // Use up()/down() to run migration code without a transaction.
    public function up()
    {
        $auth = Yii::$app->authManager;
        $admin = $auth->createRole('admin');
        $auth->add($admin);
        $member = $auth->createRole('member');
        $auth->add($member);
        $auth->addChild($admin, $member);
        $guest = $auth->createRole('guest');
        $auth->add($guest);
        $auth->addChild($admin, $guest);

        //Admin Access backend
        $accessBackend = $auth->createPermission('accessBackend');
        $accessBackend->description = 'Access Backend';
        $auth->add($accessBackend);
        $auth->addChild($admin, $accessBackend);


        //-------->PROFILE<--------
        $rule = new common\rbac\OwnProfile;
        $auth->add($rule);

        //Edit Profile Rule
        $editProfile = $auth->createPermission('editProfile');
        $editProfile->description = 'Edit Profile';
        $auth->add($editProfile);
        $auth->addChild($admin, $editProfile);

        //Edit ownProfile Rule
        $editOwnProfile = $auth->createPermission('editOwnProfile');
        $editOwnProfile->description = 'Update Own Profile';
        $editOwnProfile->ruleName = $rule->name;
        $auth->add($editOwnProfile);

        $auth->addChild($editOwnProfile, $editProfile);
        $auth->addChild($member, $editOwnProfile);


        //-------->APPLY<--------
        $rule = new common\rbac\OwnApply;
        $auth->add($rule);

        //Edit ownApply Rule
        $editOwnApply = $auth->createPermission('editOwnApply');
        $editOwnApply->description = 'Edit Own Apply';
        $editOwnApply->ruleName = $rule->name;
        $auth->add($editOwnApply);
        $auth->addChild($guest, $editOwnApply);

        //Delete ownApply Rule
        $deleteOwnApply = $auth->createPermission('deleteOwnApply');
        $deleteOwnApply->description = 'Delete Own Apply';
        $deleteOwnApply->ruleName = $rule->name;
        $auth->add($deleteOwnApply);
        $auth->addChild($guest, $deleteOwnApply);


        //-------->CHARACTER<--------
        $rule = new common\rbac\OwnCharacter;
        $auth->add($rule);


        //Edit Character Rule
        $editCharacter = $auth->createPermission('editCharacter');
        $editCharacter->description = 'Edit Character';
        $auth->add($editCharacter);
        $auth->addChild($admin, $editCharacter);

        //Delete Character Rule
        $deleteCharacter = $auth->createPermission('deleteCharacter');
        $deleteCharacter->description = 'Delete Character';
        $auth->add($deleteCharacter);
        $auth->addChild($admin, $deleteCharacter);


        //Edit ownCharacter Rule
        $editOwnCharacter = $auth->createPermission('editOwnCharacter');
        $editOwnCharacter->description = 'Edit Own Character';
        $editOwnCharacter->ruleName = $rule->name;
        $auth->add($editOwnCharacter);
        $auth->addChild($editOwnCharacter, $editCharacter);
        $auth->addChild($member, $editOwnCharacter);

        //Delete ownCharacter Rule
        $deleteOwnCharacter = $auth->createPermission('deleteOwnCharacter');
        $deleteOwnCharacter->description = 'Delete Own Character';
        $deleteOwnCharacter->ruleName = $rule->name;
        $auth->add($deleteOwnCharacter);
        $auth->addChild($deleteOwnCharacter, $deleteCharacter);
        $auth->addChild($member, $deleteOwnCharacter);


        //-------->TOPIC<--------

        //Edit Topic Rule
        $editTopic = $auth->createPermission('editTopic');
        $editTopic->description = 'Edit Topic';
        $auth->add($editTopic);
        $auth->addChild($admin, $editTopic);

        //Delete Topic Rule
        $deletTopic = $auth->createPermission('deleteTopic');
        $deletTopic->description = 'Delete Topic';
        $auth->add($deletTopic);
        $auth->addChild($admin, $deletTopic);


        //-------->POSTS<--------
        $rule = new common\rbac\OwnPost;
        $auth->add($rule);

        //Edit Post Rule
        $editPost = $auth->createPermission('editPost');
        $editPost->description = 'Edit Post';
        $auth->add($editPost);
        $auth->addChild($admin, $editPost);

        //Delete Post Rule
        $deletePost = $auth->createPermission('deletePost');
        $deletePost->description = 'Delete Post';
        $auth->add($deletePost);
        $auth->addChild($admin, $deletePost);

        //Edit ownPost Rule
        $editOwnPost = $auth->createPermission('editOwnPost');
        $editOwnPost->description = 'Edit Own Post';
        $editOwnPost->ruleName = $rule->name;
        $auth->add($editOwnPost);
        $auth->addChild($editOwnPost, $editPost);
        $auth->addChild($member, $editOwnPost);

        //Delete ownPost Rule
        $deleteOwnPost = $auth->createPermission('deleteOwnPost');
        $deleteOwnPost->description = 'Delete Own Post';
        $deleteOwnPost->ruleName = $rule->name;
        $auth->add($deleteOwnPost);
        $auth->addChild($deleteOwnPost, $deletePost);
        $auth->addChild($member, $deleteOwnPost);


        //-------->POSTCOMMENT<--------
        $rule = new common\rbac\OwnPostComment;
        $auth->add($rule);

        //Edit ownPostComment Rule
        $editOwnPostComment = $auth->createPermission('editOwnPostComment');
        $editOwnPostComment->description = 'Edit Own Post Comment';
        $editOwnPostComment->ruleName = $rule->name;
        $auth->add($editOwnPostComment);
        $auth->addChild($member, $editOwnPostComment);

        //Delete ownPostComment Rule
        $deleteOwnPostComment = $auth->createPermission('deleteOwnPostComment');
        $deleteOwnPostComment->description = 'Delete Own Post Comment';
        $deleteOwnPostComment->ruleName = $rule->name;
        $auth->add($deleteOwnPostComment);
        $auth->addChild($member, $deleteOwnPostComment);

        //-------->NEWS<--------
        $rule = new common\rbac\OwnNew;
        $auth->add($rule);

        //Edit New Rule
        $editNew = $auth->createPermission('editNew');
        $editNew->description = 'Edit New';
        $auth->add($editNew);
        $auth->addChild($admin, $editNew);

        //Delete New Rule
        $deleteNew = $auth->createPermission('deleteNew');
        $deleteNew->description = 'Delete News';
        $auth->add($deleteNew);
        $auth->addChild($admin, $deleteNew);

        //Edit ownNew Rule
        $editOwnNew = $auth->createPermission('editOwnNew');
        $editOwnNew->description = 'Edit Own New';
        $editOwnNew->ruleName = $rule->name;
        $auth->add($editOwnNew);
        $auth->addChild($editOwnNew, $editNew);
        $auth->addChild($member, $editOwnNew);

        //Delete ownNew Rule
        $deleteOwnNew = $auth->createPermission('deleteOwnNew');
        $deleteOwnNew->description = 'Delete Own New';
        $deleteOwnNew->ruleName = $rule->name;
        $auth->add($deleteOwnNew);
        $auth->addChild($deleteOwnNew, $deleteNew);
        $auth->addChild($member, $deleteOwnNew);


        //-------->NEWOMMENT<--------
        $rule = new common\rbac\OwnNewComment;
        $auth->add($rule);

        //Edit ownNewComment Rule
        $editOwnNewComment = $auth->createPermission('editOwnNewComment');
        $editOwnNewComment->description = 'Edit Own New Comment';
        $editOwnNewComment->ruleName = $rule->name;
        $auth->add($editOwnNewComment);
        $auth->addChild($member, $editOwnNewComment);

        //Delete ownNewComment Rule
        $deleteOwnNewComment = $auth->createPermission('deleteOwnNewComment');
        $deleteOwnNewComment->description = 'Delete Own Post Comment';
        $deleteOwnNewComment->ruleName = $rule->name;
        $auth->add($deleteOwnNewComment);
        $auth->addChild($member, $deleteOwnNewComment);


        $user = new User();
        $user->username = 'Admin';
        $user->email = 'admin@hotmail.com';
        $user->setPassword('123456');
        $user->generateAuthKey();
        $user->save();

        $adminRole = $auth->getRole('admin');
        $auth->assign($adminRole, $user->getId());

        $apply = new Apply();
        $apply->status = 'ACCEPTED';
        $apply->name = 'Admin';
        $apply->age = 0;
        $apply->mainSpec = 'Admin';
        $apply->class = 'Admin';
        $apply->race = 'Admin';
        $apply->user_id = $user->id;
        $apply->save();

        $userProfile = new Userprofile();
        $userProfile->displayName = $user->username;
        $userProfile->rank = 'Guild Master';
        $userProfile->role = 'Admin';
        $userProfile->user_id = $user->id;
        $userProfile->save();

        $points = new Overpoint();
        $points->userprofile_id = $userProfile->id;
        $points->op = 100;
        $points->up = 0;
        $points->priority = $points->op;
        $points->attendance = 0;
        $points->save();
    }

    public function down()
    {
        $auth = Yii::$app->authManager;

        $auth->removeAll();
    }

}
