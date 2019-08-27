<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = 'Create Apply';
$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="apply-create center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
