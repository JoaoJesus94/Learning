<?php

namespace common\models;

use Yii;
use yii\helpers\ArrayHelper;

/**
 * This is the model class for table "apply".
 *
 * @property int $id
 * @property string $status
 * @property string $name
 * @property int $age
 * @property string $mainSpec
 * @property string $offSpec
 * @property string $class
 * @property string $race
 * @property string $armory
 * @property string $wowHeroes
 * @property string $logs
 * @property string $uiScreen
 * @property string $experience
 * @property string $availableTime
 * @property string $objective
 * @property string $knownPeople
 * @property int $user_id
 *
 * @property User $user
 */
class Apply extends \yii\db\ActiveRecord
{
    public static function tableName()
    {
        return 'apply';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['name', 'age', 'mainSpec', 'class', 'race'], 'required'],
            [['age', 'user_id'], 'integer'],
            [['status', 'mainSpec', 'offSpec', 'class', 'race'], 'string', 'max' => 20],
            [['name', 'knownPeople'], 'string', 'max' => 50],
            [['armory', 'wowHeroes', 'logs', 'uiScreen', 'objective'], 'string', 'max' => 100],
            [['experience', 'availableTime'], 'string', 'max' => 200],
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
            'status' => 'Status',
            'name' => 'Name',
            'age' => 'Age',
            'mainSpec' => 'Main Spec',
            'offSpec' => 'Off Spec',
            'class' => 'Class',
            'race' => 'Race',
            'armory' => 'Armory',
            'wowHeroes' => 'Wow Heroes',
            'logs' => 'Logs',
            'uiScreen' => 'Ui Screen',
            'experience' => 'Experience',
            'availableTime' => 'Available Time',
            'objective' => 'Objective',
            'knownPeople' => 'Known People',
            'user_id' => 'User ID',
        ];
    }

    public function getUser()
    {
        return $this->hasOne(User::className(), ['id' => 'user_id']);
    }

    public function getApplyByUserId($id)
    {
        return $this->findOne(['user_id' => $id]);
    }
}
