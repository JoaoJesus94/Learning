<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model common\models\Topic */
/* @var $form yii\widgets\ActiveForm */


?>

<div class="topic-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'topicName')->textInput(['maxlength' => true])->label('Topic / SubTopic Name')?>

    <?= $form->field($model, 'topicDescription')->textInput(['maxlength' => true])->label('Topic / SubTopic Description')?>

    <?= $form->field($model, 'Topic_id')->dropDownList($topicArray,['prompt' => 'Create Main Topic'])->label('Create Main Topic / Sub Topic')?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
