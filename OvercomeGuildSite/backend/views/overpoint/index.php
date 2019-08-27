<?php

use yii\helpers\Html;
use kartik\grid\GridView;
use \common\models\Userprofile;

/* @var $this yii\web\View */
/* @var $searchModel common\models\OverpointSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Overpoints';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="overpoint-index center-tables">

    <?= Html::tag('h1', 'Overpoints', ['class' => 'text-center color-dimgrey']) ?>
    <br>
    <br>

    <?= Html::a('Manage Points By Event', ['overpoint/managebyevent'], ['class' => 'btn btn-primary']) ?>
    <!--Html::a('Decay 50%', ['decay'], ['class' => 'btn btn-danger', 'style' => 'float:right']) -->
    <?= Html::a('Reset', ['resetpoints'], ['class' => 'btn btn-danger', 'style' => 'float:right']) ?>
    <br>
    <br>
    <br>

    <?php
    $models = $dataProvider->getModels();
    ?>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'resizableColumns' => false,
        'options' => ['style' => 'color:#6E6E6E'],
        'headerRowOptions' => ['style' => 'color:white'],
        'columns' => [
            [
                'attribute' => 'userprofile_id',
                'value' => function ($model, $key, $index, $widget) {
                    $displayName =  Userprofile::findOne(['id' => $model->userprofile_id]);
                    return $displayName['displayName'];
                },
            ],
            ['attribute' => 'op'],
            ['attribute' => 'up'],
            ['attribute' => 'priority'],
            ['attribute' => 'attendance'],
            [
                'class' => 'kartik\grid\ExpandRowColumn',
                'expandOneOnly' => true,
                'header' => 'Manage',
                'value' => function ($model, $key, $index, $column) {
                    return GridView::ROW_COLLAPSED;
                },
                'detail' => function ($model, $key, $index, $column){
                    return Yii::$app->controller->renderPartial('point_player_detail', ['points' => $model, 'eventId' => null]);
                },
            ],
        ]
    ]); ?>

</div>
