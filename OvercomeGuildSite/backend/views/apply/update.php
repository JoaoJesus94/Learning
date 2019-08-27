<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = 'Update Apply: ' . $model->name;
$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->name, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="apply-update">

    <p>
        <?= Html::a('Voltar', ['index'], ['class' => 'btn btn-success']) ?>
    </p>

    <h1 style='color:white' ><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
