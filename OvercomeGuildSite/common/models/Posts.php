<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "posts".
 *
 * @property int $id
 * @property string $date
 * @property string $content
 * @property string $title
 * @property int $User_id
 * @property int $Topic_id
 *
 * @property Postcomment[] $postcomments
 * @property Topic $topic
 * @property Userprofile $user
 */
class Posts extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'posts';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['date', 'content', 'title',], 'required'],
            [['date'], 'safe'],
            [['User_id', 'Topic_id'], 'integer'],
            [['content'], 'string', 'max' => 1000],
            [['title'], 'string', 'max' => 30],
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
            'content' => 'Content',
            'title' => 'Title',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getPostcomments()
    {
        return $this->hasMany(Postcomment::className(), ['Posts_id' => 'id']);
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
    public function getUser()
    {
        return $this->hasOne(Userprofile::className(), ['id' => 'User_id']);
    }
}
