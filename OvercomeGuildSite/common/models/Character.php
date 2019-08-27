<?php

namespace common\models;

use Yii;

/**
 * This is the model class for table "character".
 *
 * @property int $id
 * @property string $charName
 * @property int $level
 * @property string $class
 * @property string $race
 * @property string $mainSpec
 * @property string $offSpec
 * @property int $User_id
 * @property int $main
 *
 * @property Userprofile $user
 * @property CharacterHasEvent[] $characterHasEvents
 * @property Event[] $events
 */
class Character extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'character';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['charName', 'level', 'class', 'race', 'mainSpec'], 'required'],
            [['level', 'User_id', 'main'], 'integer'],
            [['charName', 'class', 'race', 'mainSpec', 'offSpec'], 'string', 'max' => 20],
            [['charName'], 'unique'],
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
            'charName' => 'Char Name',
            'level' => 'Level',
            'class' => 'Class',
            'race' => 'Race',
            'mainSpec' => 'Main Spec',
            'offSpec' => 'Off Spec',
            'User_id' => 'User ID',
            'main' => 'Main',
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
    public function getCharacterHasEvents()
    {
        return $this->hasMany(CharacterHasEvent::className(), ['Character_id' => 'id']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEvents()
    {
        return $this->hasMany(Event::className(), ['id' => 'Event_id'])->viaTable('character_has_event', ['Character_id' => 'id']);
    }
}
