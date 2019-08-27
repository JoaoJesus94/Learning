<?php

use common\models\Userprofile;
use kartik\grid\GridView;
use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Topic */

$this->title = $model->topicName . ' Posts';
$this->params['breadcrumbs'][] = ['label' => 'Topics', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>

<div class="topic-view center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= Html::a('Create Post', ['posts/create/', 'idTopic' => $model->id], ['class' => 'btn btn-success', 'style' => 'float:right']) ?>
    <br>
    <br>
    <br>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'options' => ['style' => 'color:#6E6E6E;'],
        'headerRowOptions' => ['style' => 'color:white; font-size:18px'],
        'columns' => [
            [
                'attribute' => 'title',
                'width' => '50%',
                'vAlign' => 'middle',
            ],
            [
                'header' => 'Created by',
                'hAlign' => 'center',
                'vAlign' => 'middle',
                'width' => '15%',
                'attribute' => 'User_id',
                'value' => function ($model, $key, $index, $widget) {
                    $displayName = Userprofile::findOne(['id' => $model->User_id])->displayName;
                    return $displayName;
                },
            ],
            [
                'header' => 'Replies',
                'hAlign' => 'center',
                'vAlign' => 'middle',
                'width' => '10%',
                'value' => function ($model, $key, $index, $widget) {
                    return count($model->postcomments);
                },
            ],
            [
                'attribute' => 'date',
                'width' => '15%',
                'hAlign' => 'center',
                'value' => function ($model, $key, $index, $widget) {
                    $date = new DateTime($model->date);
                    return $date->format('d-m-Y');
                },
            ],
            [
                'class' => '\kartik\grid\ActionColumn',
                'width' => '10%',
                'buttons' => [
                    'view' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-eye-open"></span>',
                            ['posts/' . $model->id]
                        );
                    },
                    'update' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-pencil"></span>',
                            ['posts/update/' . $model->id]
                        );
                    },
                    'delete' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-trash"></span>',
                            ['posts/delete/' . $model->id,],
                            [
                                'data-method' => 'post',
                                'data-confirm' => 'Are you sure you want to delete this post?',
                            ]
                        );
                    },
                ]
            ],
        ]
    ]); ?>

</div>
