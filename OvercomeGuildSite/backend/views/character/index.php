<?php

use yii\helpers\Html;
use kartik\grid\GridView;
use \common\models\Userprofile;

/* @var $this yii\web\View */
/* @var $searchModel common\models\CharacterSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Characters';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="character-index center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>


    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'summary' => false,
        'striped' => false,
        'options' => ['class' => 'color-dimgrey'],
        'headerRowOptions' => ['style' => 'color:white'],
        'columns' => [
            'charName',
            'level',
            'class',
            'race',
            [
                'header' => 'Main Spec',
                'attribute' => 'mainSpec',
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{view}',
                'header' => 'User Profile',
                'buttons' => [
                    'view' => function ($url, $model) {
                        $userProfile = Userprofile::findOne($model->User_id);
                        return Html::a('<span>' . $userProfile->displayName . ' </span><span class="glyphicon glyphicon-eye-open"></span>', ['userprofile/' . $model->User_id]
                        );
                    },
                ]
            ],
        ]
    ]); ?>
</div>
