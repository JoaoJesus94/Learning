<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "news".
 *
 * @property int $id
 * @property string $content
 * @property string $tittle
 * @property int $User_id
 *
 * @property Userprofile $user
 * @property Newscomment[] $newscomments
 */
class News extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'news';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['content', 'tittle'], 'required'],
            [['User_id'], 'integer'],
            [['content'], 'string', 'max' => 5000],
            [['tittle'], 'string', 'max' => 30],
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
            'tittle' => 'Tittle',
            'User_id' => 'User ID',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUser()
    {
        return $this->hasOne(Userprofile::className(), ['id' => 'User_id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getNewscomments()
    {
        return $this->hasMany(Newscomment::className(), ['News_id' => 'id']);
    }

    public function getUserName($profileID)
    {
        $profileName = Userprofile::findOne(['id' => $profileID]);
        return $profileName->displayName;
    }
}
