<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "event".
 *
 * @property int $id
 * @property string $eventName
 * @property string $date
 * @property string $category
 * @property string $description
 *
 * @property Userprofilehasevent[] $UserProfileHasEvents
 * @property Character[] $characters
 */
class Event extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'event';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['eventName', 'date', 'category'], 'required'],
            [['date', 'date'], 'safe'],
            [['eventName'], 'string', 'max' => 30],
            [['category'], 'string', 'max' => 10],
            [['description'], 'string', 'max' => 200],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'eventName' => 'Event Name',
            'date' => 'Event Date',
            'category' => 'Category',
            'description' => 'Description',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUserProfileHasEvent()
    {
        return $this->hasMany(Userprofilehasevent::className(), ['Event_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUserprofiles()
    {
        return $this->hasMany(Userprofile::className(), ['id' => 'userprofile_id'])->viaTable('userprofile_has_event', ['event_id' => 'id']);
    }
}
