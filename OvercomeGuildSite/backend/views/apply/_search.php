<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\ApplySearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="apply-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'status') ?>

    <?= $form->field($model, 'name') ?>

    <?= $form->field($model, 'age') ?>

    <?= $form->field($model, 'mainSpec') ?>

    <?php // echo $form->field($model, 'offSpec') ?>

    <?php // echo $form->field($model, 'class') ?>

    <?php // echo $form->field($model, 'race') ?>

    <?php // echo $form->field($model, 'armory') ?>

    <?php // echo $form->field($model, 'wowHeroes') ?>

    <?php // echo $form->field($model, 'logs') ?>

    <?php // echo $form->field($model, 'uiScreen') ?>

    <?php // echo $form->field($model, 'experience') ?>

    <?php // echo $form->field($model, 'availableTime') ?>

    <?php // echo $form->field($model, 'objective') ?>

    <?php // echo $form->field($model, 'knownPeople') ?>

    <?php // echo $form->field($model, 'user_id') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-default']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
