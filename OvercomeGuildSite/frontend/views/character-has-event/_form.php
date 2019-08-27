<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\CharacterHasEvent */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="character-has-event-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'Character_id')->textInput() ?>

    <?= $form->field($model, 'Event_id')->textInput() ?>

    <?= $form->field($model, 'attending')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'role')->textInput(['maxlength' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
