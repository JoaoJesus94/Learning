<?php

namespace common\models;

use Yii;
use yii\helpers\ArrayHelper;

/**
 * This is the model class for table "overpoint".
 *
 * @property int $idoverpoint
 * @property int $op
 * @property int $up
 * @property int $priority
 * @property int $attendance
 * @property int $userprofile_id
 *
 * @property Userprofile $userprofile
 */
class Overpoint extends \yii\db\ActiveRecord
{
    /**
     * {@inheritdoc}
     */
    public static function tableName()
    {
        return 'overpoint';
    }

    /**
     * {@inheritdoc}
     */
    public function rules()
    {
        return [
            [['op', 'up', 'priority', 'attendance', 'userprofile_id'], 'integer'],
            [['userprofile_id'], 'exist', 'skipOnError' => true, 'targetClass' => Userprofile::className(), 'targetAttribute' => ['userprofile_id' => 'id']],
        ];
    }

    /**
     * {@inheritdoc}
     */
    public function attributeLabels()
    {
        return [
            'idoverpoint' => 'Idoverpoint',
            'op' => 'Op',
            'up' => 'Up',
            'priority' => 'Priority',
            'attendance' => 'Attendance',
            'userprofile_id' => 'Userprofile ID',
        ];
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getUserprofile()
    {
        return $this->hasOne(Userprofile::className(), ['id' => 'userprofile_id']);
    }


    public function calculatePriority()
    {
        $this->priority = $this->op / $this->up;
    }
}
