<?php

use yii\helpers\Html;
use kartik\detail\DetailView;

/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = "Apply: " . $model->name;
$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="apply-view center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <p style="float: right">
        <?= Html::a('Update', ['update', 'id' => $model->id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'id' => $model->id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <br>
    <br>
    <br>

    <?= DetailView::widget([
        'model' => $model,
        'options' => ['class' => 'detailViewProfile color-dimgrey'],
        'condensed' => true,
        'striped' => false,
        'hAlign' => DetailView::ALIGN_CENTER,
        'vAlign' => DetailView::ALIGN_CENTER,
        'mode' => DetailView::MODE_VIEW,
        'labelColOptions' => ['style' => 'color:white', 'width' => '20%'],
        'attributes' => [
            'name',
            'age',
            'status',
            'mainSpec',
            'offSpec',
            'class',
            'race',
            'armory',
            'wowHeroes',
            'logs',
            'uiScreen',
            'experience',
            'availableTime',
            'objective',
            'knownPeople'
        ],
    ]) ?>

</div>
