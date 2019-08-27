<?php

use kartik\detail\DetailView;
use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Apply */

$this->title = $model->name;
$this->params['breadcrumbs'][] = ['label' => 'Applies', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="apply-view center-tables">

    <?= Html::tag('h1', $model->name, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <p>
        <?php if ($model->status == "OPEN") {
            echo Html::a('Accept', ['accept', 'id' => $model->id], [
                'class' => 'btn btn-success',
                'data' => [
                    'confirm' => 'Are you sure you want to accept this apply?',
                    'method' => 'post'
                ]
            ]);
            echo Html::a('Refuse', ['refuse', 'id' => $model->id], [
                'class' => 'btn btn-danger',
                'data' => [
                    'confirm' => 'Are you sure you want to refuse this apply?',
                    'method' => 'post'
                ]
            ]);
        }
        ?>
    </p>


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
            'status',
            'name',
            'age',
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
            'knownPeople',
        ],
    ]) ?>

</div>
