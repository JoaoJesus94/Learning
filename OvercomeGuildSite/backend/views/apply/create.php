<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = 'Create Apply';
$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="apply-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
