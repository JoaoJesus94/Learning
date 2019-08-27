<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Logs */

$this->title = 'Update Logs: ' . $model->idLogs;
$this->params['breadcrumbs'][] = ['label' => 'Logs', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->idLogs, 'url' => ['view', 'id' => $model->idLogs]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="logs-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
