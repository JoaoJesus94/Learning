<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Newscomment */

$this->title = 'Update Comment';
$this->params['breadcrumbs'][] = ['label' => 'Newscomments', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="newscomment-update center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
