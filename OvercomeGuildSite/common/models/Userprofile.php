<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "userprofile".
 *
 * @property int $id
 * @property string $displayName
 * @property string $rank
 * @property string $role
 * @property int $user_id
 *
 * @property Character[] $characters
 * @property Logs[] $logs
 * @property News[] $news
 * @property Newscomment[] $newscomments
 * @property Overpoint[] $overpoints
 * @property Postcomment[] $postcomments
 * @property Posts[] $posts
 * @property Topic[] $topics
 * @property User $user
 */
class Userprofile extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'userprofile';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['displayName', 'rank', 'role'], 'required'],
            [['user_id'], 'integer'],
            [['displayName', 'rank', 'role'], 'string', 'max' => 20],
            [['user_id'], 'exist', 'skipOnError' => true, 'targetClass' => User::className(), 'targetAttribute' => ['user_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'displayName' => 'Display Name',
            'rank' => 'Rank',
            'role' => 'Role',
            'user_id' => 'User ID',
        ];
    }

    public function getUserProfileByUserId($user_id){
        return $this->findOne(['user_id' => $user_id]);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getCharacters()
    {
        return $this->hasMany(Character::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getLogs()
    {
        return $this->hasMany(Logs::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getNews()
    {
        return $this->hasMany(News::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getNewscomments()
    {
        return $this->hasMany(Newscomment::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getOverpoints()
    {
        return $this->hasMany(Overpoint::className(), ['userprofile_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getPostcomments()
    {
        return $this->hasMany(Postcomment::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getPosts()
    {
        return $this->hasMany(Posts::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getTopics()
    {
        return $this->hasMany(Topic::className(), ['User_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(User::className(), ['id' => 'user_id']);
    }

    public function getId(){
        return $this->getPrimaryKey();
    }
}
