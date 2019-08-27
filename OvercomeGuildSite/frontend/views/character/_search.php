<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\CharacterSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="character-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'charName') ?>

    <?= $form->field($model, 'level') ?>

    <?= $form->field($model, 'class') ?>

    <?= $form->field($model, 'race') ?>

    <?php // echo $form->field($model, 'mainSpec') ?>

    <?php // echo $form->field($model, 'offSpec') ?>

    <?php // echo $form->field($model, 'User_id') ?>

    <?php // echo $form->field($model, 'main') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-default']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
