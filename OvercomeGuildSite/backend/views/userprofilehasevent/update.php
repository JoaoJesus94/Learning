<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Userprofilehasevent */

$this->title = 'Update Userprofile Has Event: ' . $model->userprofile_id;
$this->params['breadcrumbs'][] = ['label' => 'Userprofile Has Events', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->userprofile_id, 'url' => ['view', 'userprofile_id' => $model->userprofile_id, 'event_id' => $model->event_id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="userprofile-has-event-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
