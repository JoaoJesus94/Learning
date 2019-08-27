<?php

use common\models\Userprofile;
use frontend\assets\AppAsset;
use kartik\grid\GridView;
use kartik\icons\Icon;
use yii\helpers\Html;

AppAsset::register($this);
Icon::map($this, Icon::FAS);

$this->title = 'Forum';
$this->params['breadcrumbs'][] = $this->title;

?>

<div class="forum-index center-tables">
    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= Html::a('Create Main/Sub Topic', ['topic/create'], ['class' => 'btn btn-success', 'style' => 'float:right']) ?>
    <br>
    <br>
    <br>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'showHeader' => false,
        'options' => ['style' => 'color:#6E6E6E; font-size:20px;'],
        'headerRowOptions' => ['style' => 'color:white; font-size:15px'],
        'columns' => [
            [
                'vAlign' => 'middle',
                'width' => '60%',
                'header' => 'Topic',
                'attribute' => 'topicName',
                'content' => function ($model) {
                    return Icon::show('folder-open', ['framework' => Icon::FAS]) . $model->topicName;
                },
            ],
            [
                'vAlign' => 'middle',
                'header' => 'Created by',
                'width' => '30%',
                'attribute' => 'User_id',
                'value' => function ($model, $key, $index, $widget) {
                    $displayName = Userprofile::findOne(['id' => $model->User_id])->displayName;
                    return 'Created by: ' . $displayName;
                },
            ],
            [
                'class' => '\kartik\grid\ActionColumn',
                'width' => '10%',
                'template' => '{delete}',
                'buttons' => [
                    'delete' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-trash"></span>',
                            ['topic/delete/' . $model->id],
                            [
                                'data-method' => 'post',
                                'data-confirm' => 'Are you sure you want to delete this topic?',
                            ]
                        );
                    },
                ]

            ],
            [
                'class' => 'kartik\grid\ExpandRowColumn',
                'width' => '10%',
                'vAlign' => 'middle',
                'allowBatchToggle' => false,
                'header' => 'Expand',
                'value' => function ($model, $key, $index, $column) {
                    return GridView::ROW_EXPANDED;
                },
                'detail' => function ($model, $key, $index, $column) {
                    return Yii::$app->controller->renderPartial('subTopic_detail', ['subtopic' => $model->id]);
                },
            ],
        ]
    ]); ?>
</div>
