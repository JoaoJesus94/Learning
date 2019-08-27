<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Postcomment */

$this->title = 'Update comment';
$this->params['breadcrumbs'][] = ['label' => 'Postcomments', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="postcomment-update center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
