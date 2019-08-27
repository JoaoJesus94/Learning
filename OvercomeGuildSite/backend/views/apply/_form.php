<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\Apply */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="apply-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'name')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'age')->textInput() ?>

    <?= $form->field($model, 'mainSpec')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'offSpec')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'class')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'race')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'armory')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'wowHeroes')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'logs')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'uiScreen')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'experience')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'availableTime')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'objective')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'knownPeople')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
