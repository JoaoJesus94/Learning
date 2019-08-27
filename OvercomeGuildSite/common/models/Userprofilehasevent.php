<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "userprofile_has_event".
 *
 * @property int $userprofile_id
 * @property int $event_id
 * @property string $attending
 * @property string $role
 *
 * @property Event $event
 * @property Userprofile $userprofile
 */
class Userprofilehasevent extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'userprofile_has_event';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['userprofile_id', 'event_id', 'attending'], 'required'],
            [['userprofile_id', 'event_id'], 'integer'],
            [['attending', 'role'], 'string', 'max' => 45],
            [['userprofile_id', 'event_id'], 'unique', 'targetAttribute' => ['userprofile_id', 'event_id']],
            [['event_id'], 'exist', 'skipOnError' => true, 'targetClass' => Event::className(), 'targetAttribute' => ['event_id' => 'id']],
            [['userprofile_id'], 'exist', 'skipOnError' => true, 'targetClass' => Userprofile::className(), 'targetAttribute' => ['userprofile_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'userprofile_id' => 'Userprofile ID',
            'event_id' => 'Event ID',
            'attending' => 'Attending',
            'role' => 'Role',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEvent()
    {
        return $this->hasOne(Event::className(), ['id' => 'event_id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUserprofile()
    {
        return $this->hasOne(Userprofile::className(), ['id' => 'userprofile_id']);
    }
}
