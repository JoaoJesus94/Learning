<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;
use kartik\depdrop\DepDrop;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $model common\models\Character */
/* @var $form yii\widgets\ActiveForm */

?>


<div class="character-form center-tables">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'charName')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'level')->textInput() ?>

    <?= $form->field($model, 'race')->dropDownList($races, ['id' => 'dropRaces', 'prompt' => '--> Select Race <--']) ?>

    <?= $form->field($model, 'class')->widget(DepDrop::classname(), [
        'options' => ['id' => 'dropClasses'],
        'pluginOptions' => [
            'depends' => ['dropRaces'],
            'placeholder' => '--> Select Class <--',
            'url' => Url::to(['classes'])
        ],

    ]) ?>

    <?= $form->field($model, 'mainSpec')->widget(DepDrop::classname(), [
        'pluginOptions' => [
            'depends' => ['dropClasses'],
            'placeholder' => '--> Select MainSpec <--',
            'url' => Url::to(['specializations'])
        ]
    ]) ?>

    <?= $form->field($model, 'offSpec')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'main')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
