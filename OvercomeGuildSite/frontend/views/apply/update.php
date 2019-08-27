<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = 'Update Apply: ' . $model->name;
//$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->name, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="apply-update center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>

    <br>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
