<?php

use yii\helpers\Html;
use kartik\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel common\models\UserprofileSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Roster';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="userprofile-index center-tables">

    <?= Html::tag('h1', 'Roster', ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'options' => ['style' => 'color:dimgrey'],
        'headerRowOptions' => ['style' => 'color:white'],
        'columns' => [
            'displayName',
            'rank',
            'role',
            [
                'class' => 'yii\grid\ActionColumn',
                'header' => 'View profile',
                'template' => '{view}'
            ],
        ],
    ]); ?>
</div>