<?php

namespace common\models;

use Yii;
use yii\helpers\ArrayHelper;

/**
 * This is the model class for table "topic".
 *
 * @property int $id
 * @property string $topicName
 * @property string $topicDescription
 * @property int $User_id
 * @property int $Topic_id
 *
 * @property Posts[] $posts
 * @property Topic $topic
 * @property Topic[] $topics
 * @property Userprofile $user
 */
class Topic extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'topic';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [

            //[['topicName'], 'required'],
            //[['User_id', 'Topic_id'], 'integer'],
            [['topicName'], 'string', 'max' => 20],
            [['topicDescription'], 'string', 'max' => 50],
            [['Topic_id'], 'exist', 'skipOnError' => true, 'targetClass' => Topic::className(), 'targetAttribute' => ['Topic_id' => 'id']],
            [['User_id'], 'exist', 'skipOnError' => true, 'targetClass' => Userprofile::className(), 'targetAttribute' => ['User_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'topicName' => 'Topic Name',
            'topicDescription' => 'Topic Description',
            'User_id' => 'User ID',
            'Topic_id' => 'Topic ID',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getPosts()
    {
        return $this->hasMany(Posts::className(), ['Topic_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getTopic()
    {
        return $this->hasOne(Topic::className(), ['id' => 'Topic_id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getTopics()
    {
        return $this->hasMany(Topic::className(), ['Topic_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(Userprofile::className(), ['id' => 'User_id']);
    }

    public function getUserName($profileID)
    {
        $profileName = Userprofile::findOne(['id' => $profileID]);
        return $profileName->displayName;
    }
}
