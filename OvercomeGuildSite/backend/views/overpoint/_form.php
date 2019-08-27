<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\Overpoint */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="overpoint-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'op')->textInput() ?>

    <?= $form->field($model, 'up')->textInput() ?>

    <?= $form->field($model, 'priority')->textInput() ?>

    <?= $form->field($model, 'attendance')->textInput() ?>

    <?= $form->field($model, 'userprofile_id')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
