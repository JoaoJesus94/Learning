<?php

use common\models\Userprofile;
use frontend\assets\AppAsset;
use kartik\grid\GridView;
use kartik\icons\Icon;
use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $searchModel common\models\NewsSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

AppAsset::register($this);
Icon::map($this, Icon::FAS);

$this->title = 'News';
$this->params['breadcrumbs'][] = $this->title;

?>

<div class="news-index center-tables">
    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= Html::a('Create New', ['news/create'], ['class' => 'btn btn-success', 'style' => 'float:right']) ?>
    <br>
    <br>
    <br>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'options' => ['style' => 'color:#6E6E6E; font-size:18px;'],
        'headerRowOptions' => ['style' => 'color:white; font-size:15px'],
        'columns' => [
            [
                'vAlign' => 'middle',
                'width' => '50%',
                'header' => 'Tittle',
                'attribute' => 'tittle',
                'content' => function ($model) {
                    return Icon::show('newspaper', ['framework' => Icon::FAS]) . $model->tittle;
                },
            ],
            [
                'vAlign' => 'middle',
                'header' => 'Created by',
                'width' => '20%',
                'attribute' => 'User_id',
                'value' => function ($model, $key, $index, $widget) {
                    $displayName = Userprofile::findOne(['id' => $model->User_id])->displayName;
                    return $displayName;
                },
            ],
            [
                'class' => '\kartik\grid\ActionColumn',
                'width' => '10%',
                'vAlign' => 'middle',
                'template' => '{update} {delete}',
                'buttons' => [
                    'update' => function ($url, $model) {
                        return Html::a(
                            '<span class="glyphicon glyphicon-pencil"></span>',
                            ['news/update/', 'id' => $model->id, 'post_id' => $model->id],
                            [
                                'data-method' => 'post'
                            ]
                        );
                    },
                    'delete' => function ($url, $model) {
                        return Html::a(
                            '<span class="glyphicon glyphicon-trash"></span>',
                            ['news/delete', 'id' => $model->id, 'post_id' => $model->id],
                            [
                                'data-method' => 'post',
                                'data-confirm' => 'Are you sure you want to delete this comment?',
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
                    return GridView::ROW_COLLAPSED;
                },
                'detail' => function ($model, $key, $index, $column) {
                    return Yii::$app->controller->renderPartial('news_detail', ['model' => $model]);
                },
            ],

        ]
    ]); ?>

</div>

