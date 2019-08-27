<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\OverpointSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="overpoint-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'idoverpoint') ?>

    <?= $form->field($model, 'op') ?>

    <?= $form->field($model, 'up') ?>

    <?= $form->field($model, 'priority') ?>

    <?= $form->field($model, 'attendance') ?>

    <?php // echo $form->field($model, 'userprofile_id') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-default']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
