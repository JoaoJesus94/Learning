<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Overpoint */

$this->title = 'Update Overpoint: ' . $model->idoverpoint;
$this->params['breadcrumbs'][] = ['label' => 'Overpoints', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->idoverpoint, 'url' => ['view', 'id' => $model->idoverpoint]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="overpoint-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
