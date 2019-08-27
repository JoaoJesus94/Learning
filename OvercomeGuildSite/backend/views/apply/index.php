<?php

use kartik\grid\GridView;
use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $searchModel common\models\ApplySearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Applies';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="apply-index center-tables">


    <?= Html::tag('h1', 'Applies', ['class' => 'text-center color-dimgrey']) ?>
    <br>


    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'options' => ['style' => 'color:#6E6E6E'],
        'headerRowOptions' => ['style' => 'color:white'],
        'columns' => [
            [
                'attribute' => 'name',
                'width' => '20%',
            ],
            [
                'attribute' => 'age',
                'width' => '20%',
            ],
            [
                'attribute' => 'status',
                'width' => '20%',
            ],
            [
                'attribute' => 'mainSpec',
                'width' => '20%',
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{view}',
                'buttons' => [
                    'view' => function ($url, $model) {
                        return Html::a(
                            '<span>View ' . $model->name . '</span>',
                            ['apply/view/', 'id' => $model->id],
                            [
                                'data-method' => 'post'
                            ]
                        );
                    },
                ]
            ]
        ]
    ]); ?>

</div>
