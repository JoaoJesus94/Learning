<?php

use common\models\Posts;
use common\models\Topic;
use common\models\Userprofile;
use kartik\grid\GridView;
use kartik\icons\Icon;
use yii\data\ArrayDataProvider;
use yii\helpers\Html;


Icon::map($this, Icon::FAS);
?>


<?php
$subtopics = Topic::findAll(['Topic_id' => $subtopic]);
$dataProvider = new ArrayDataProvider([
    'allModels' => $subtopics,
]);
?>
<div>
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'options' => ['style' => 'color:#6E6E6E; font-size:15px; font-weight:none;'],
        'headerRowOptions' => ['style' => 'color:black'],
        'columns' => [
            [
                'header' => 'Subtopic',
                'attribute' => 'topicName',
                'width' => '55%',
                'content' => function ($model) {
                    return Icon::show('folder-open', ['framework' => Icon::FAR]) . $model->topicName;
                },
            ],
            [
                'attribute' => 'User_id',
                'width' => '20%',
                'header' => 'Created by',
                'value' => function ($model, $key, $index, $widget) {
                    $displayName = Userprofile::findOne(['id' => $model->User_id])->displayName;
                    return $displayName;
                },
            ],
            [
                'width' => '15%',
                'hAlign' => 'center',
                'header' => 'Threads',
                'value' => function ($model, $key, $index, $widget) {
                    return count(Posts::findAll(['Topic_id' => $model->id]));
                }
            ],
            [
                'class' => '\kartik\grid\ActionColumn',
                'width' => '10%',
                'buttons' => [
                    'view' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-eye-open"></span>',
                            ['topic/' . $model->id]
                        );
                    },
                    'update' => function ($url, $model) {
                        return Html::a('<span></span><span class="glyphicon glyphicon-pencil"></span>',
                            ['topic/update/' . $model->id]
                        );
                    },
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
            ]
        ]
    ]); ?>
</div>

