<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\Userprofile */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="userprofile-form center-tables">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'displayName')->textInput(['maxlength' => true]) ?>

    <?= $form->field($applyModel, 'name')->textInput(['maxlength' => true]) ?>

    <?= $form->field($applyModel, 'age')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
