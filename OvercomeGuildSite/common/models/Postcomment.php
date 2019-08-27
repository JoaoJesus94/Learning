<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "postcomment".
 *
 * @property int $id
 * @property string $content
 * @property string $date
 * @property int $Posts_id
 * @property int $User_id
 *
 * @property Posts $posts
 * @property Userprofile $user
 */
class Postcomment extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'postcomment';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['content', 'date', 'Posts_id'], 'required'],
            [['date'], 'safe'],
            [['Posts_id', 'User_id'], 'integer'],
            [['content'], 'string', 'max' => 500],
            [['Posts_id'], 'exist', 'skipOnError' => true, 'targetClass' => Posts::className(), 'targetAttribute' => ['Posts_id' => 'id']],
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
            'date' => 'Date',
            'Posts_id' => 'Posts ID',
            'User_id' => 'User ID',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getPosts()
    {
        return $this->hasOne(Posts::className(), ['id' => 'Posts_id']);
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
