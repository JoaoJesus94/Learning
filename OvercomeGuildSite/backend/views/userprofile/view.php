<?php

use kartik\detail\DetailView;
use kartik\grid\GridView;
use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Userprofile */

$this->title = $userProfileModel->displayName . " Profile";
$this->params['breadcrumbs'][] = ['label' => 'Userprofiles', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
    <div class="userprofile-view">

    <div class="userprofile-view center-tables">
        <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
        <br>

        <?= DetailView::widget([
            'model' => $userProfileModel->user,
            'options' => ['class' => 'detailViewProfile color-dimgrey'],
            'condensed' => true,
            'striped' => false,
            'hAlign' => DetailView::ALIGN_CENTER,
            'vAlign' => DetailView::ALIGN_CENTER,
            'mode' => DetailView::MODE_VIEW,
            'labelColOptions' => ['style' => 'color:white', 'width' => '20%'],
            'attributes' => [
                'username',
                'email',
                [
                    'label' => 'Name',
                    'value' => function($model) use ($applyModel){
                        return $applyModel->name;
                    }
                ],
                [
                    'label' => 'Age',
                    'value' => function($model) use ($applyModel){
                        return $applyModel->age;
                    }
                ],
                'created_at:date',
            ],
        ]) ?>

        <?= DetailView::widget([
            'model' => $userProfileModel,
            'options' => ['class' => 'detailViewProfile color-dimgrey'],
            'condensed' => true,
            'striped' => false,
            'hAlign' => DetailView::ALIGN_CENTER,
            'vAlign' => DetailView::ALIGN_CENTER,
            'mode' => DetailView::MODE_VIEW,
            'labelColOptions' => ['style' => 'color:white', 'width' => '20%'],
            'attributes' => [
                'displayName',
                [
                    'label' => 'Guild Rank',
                    'attribute' => 'rank'
                ],
                [
                    'label' => 'Permission',
                    'attribute' => 'role'
                ],
            ],
        ]) ?>

        <?= Html::a('Edit Profile', ['update', 'id' => $userProfileModel->id], ['class' => 'btn btn-primary', 'style' => 'float:right']) ?>

        <br>
        <?= Html::tag('h3', 'Characters', ['style' => 'color:white']) ?>

        <?php if ($charactersModel->getTotalCount() != 0) {
            echo GridView::widget([
                'dataProvider' => $charactersModel,
                'summary' => false,
                'striped' => false,
                'options' => ['class' => 'color-dimgrey'],
                'headerRowOptions' => ['style' => 'color:white'],
                'columns' => [
                    [
                        'header' => 'Character',
                        'attribute' => 'charName',
                    ],
                    [
                        'header' => 'Level',
                        'attribute' => 'level',
                    ],
                    [
                        'header' => 'Class',
                        'attribute' => 'class',
                    ],
                    [
                        'header' => 'Race',
                        'attribute' => 'race',
                    ],
                    [
                        'header' => 'Main Spec',
                        'attribute' => 'mainSpec',
                    ],
                    [
                        'header' => 'Off Spec',
                        'attribute' => 'offSpec',
                    ],
                    [
                        'class' => 'yii\grid\ActionColumn',
                        'template' => '{update}, {delete}',
                        'buttons' => [
                            'update' => function ($url, $model) {
                                return Html::a(
                                    '<span class="glyphicon glyphicon-pencil"></span>',
                                    ['character/update/', 'id' => $model->id, 'userprofile_id' => $model->User_id],
                                    [
                                        'data-method' => 'post'
                                    ]
                                );
                            },
                            'delete' => function ($url, $model) {
                                return Html::a(
                                    '<span class="glyphicon glyphicon-trash"></span>',
                                    ['character/delete', 'id' => $model->id, 'userprofile_id' => $model->User_id],
                                    [
                                        'data-method' => 'post',
                                        'data-confirm' => 'Are you sure you want to delete this character?',
                                    ]
                                );
                            },
                        ]
                    ],
                ]
            ]);
        } else {
            if (Yii::$app->user->getId() == $userProfileModel->user_id) {
                echo Html::tag('h3', 'You have no characters!');
            } else {
                echo Html::tag('h3', 'This user has no characters!');
            }

        } ?>
        <p>
            <?php if (Yii::$app->user->getId() == $userProfileModel->user_id) {
                echo Html::a('Add Character', ['character/create'], ['class' => 'btn btn-success', 'style' => 'float:right']);
            } ?>
        </p>
    </div>
<?php