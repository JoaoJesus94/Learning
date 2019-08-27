<?php

use kartik\grid\GridView;
use yii\helpers\Html;

?>

<?php
$this->title = 'Guild Management';
?>

<div class="center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'options' => ['class' => 'color-dimgrey'],
        'headerRowOptions' => ['style' => 'color:white'],
        'columns' => [
            [
                'width' => '35%',
                'attribute' => 'displayName',
            ],
            [
                'width' => '35%',
                'attribute' => 'rank',
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{update}',
                'header' => 'Promote/Demote',
                'buttons' => [
                    'update' => function ($url, $model) {

                    },
                ]
            ],
        ]
    ]) ?>

</div>

