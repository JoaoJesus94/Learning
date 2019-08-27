<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "newscomment".
 *
 * @property int $id
 * @property string $content
 * @property string $date
 * @property int $News_id
 * @property int $User_id
 *
 * @property News $news
 * @property Userprofile $user
 */
class Newscomment extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'newscomment';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['content', 'date', 'News_id'], 'required'],
            [['date'], 'safe'],
            [['News_id', 'User_id'], 'integer'],
            [['content'], 'string', 'max' => 500],
            [['News_id'], 'exist', 'skipOnError' => true, 'targetClass' => News::className(), 'targetAttribute' => ['News_id' => 'id']],
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
            'News_id' => 'News ID',
            'User_id' => 'User ID',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getNews()
    {
        return $this->hasOne(News::className(), ['id' => 'News_id']);
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
