<?php

use common\models\Postcomment;
use common\models\Userprofile;
use kartik\detail\DetailView;
use kartik\form\ActiveForm;
use kartik\grid\GridView;
use yii\data\ArrayDataProvider;
use yii\helpers\Html;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $model common\models\Posts */

$this->title = $model->title;
$this->params['breadcrumbs'][] = ['label' => 'Posts', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>


<div class="posts-view center-tables">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= DetailView::widget([
        'model' => $model,
        'mode' => DetailView::MODE_VIEW,
        'options' => ['class' => 'color-dimgrey'],
        'condensed' => true,
        'striped' => false,
        'hAlign' => DetailView::ALIGN_CENTER,
        'vAlign' => DetailView::ALIGN_CENTER,
        'labelColOptions' => ['style' => 'color:white', 'width' => '15%'],
        'attributes' => [
            [
                'columns' => [
                    [
                        'attribute' => 'title',
                        'valueColOptions' => ['style' => 'width:42.5%']
                    ],
                    [
                        'attribute' => 'date',
                        'format' => 'date',
                    ]
                ],
            ],
            [
                'columns' => [
                    [
                        'label' => 'Author',
                        'value' => $model->user->displayName,
                        'valueColOptions' => ['style' => 'width:42.5%']
                    ],
                    [
                        'attribute' => 'date',
                        'label' => 'Time',
                        'format' => 'time',
                    ]
                ],
            ],
            [
                'attribute' => 'content',
                'format' => 'raw',
                'options' => ['rows' => 4]
            ],
        ],
    ]) ?>
    <?php

    $postComments = Postcomment::findAll(['Posts_id' => $model->id]);
    $dataProvider = new ArrayDataProvider([
        'allModels' => $postComments,
    ]);
    ?>

    <br>

    <div class="center-tables">
        <br>
        <h2 class="color-dimgrey">Comments</h2>


        <br>

        <?= GridView::widget([
            'dataProvider' => $dataProvider,
            'summary' => false,
            'striped' => false,
            'resizableColumns' => false,
            'options' => ['style' => 'color:#6E6E6E; font-size:15px;'],
            'headerRowOptions' => ['style' => 'color:white; font-size:15px'],
            'columns' => [
                [
                    'vAlign' => 'middle',
                    'header' => 'Author',
                    'width' => '15%',
                    'attribute' => 'User_id',
                    'value' => function ($commentModel, $key, $index, $widget) {
                        $displayName = Userprofile::findOne(['id' => $commentModel->User_id])->displayName;
                        return $displayName;
                    },
                ],
                [
                    'attribute' => 'content',
                    'vAlign' => 'middle',
                ],
                [
                    'header' => 'Date',
                    'vAlign' => 'middle',
                    'hAlign' => 'center',
                    'width' => '15%',
                    'attribute' => 'date',
                    'value' => function ($commentModel, $key, $index, $widget) {
                        $date = new DateTime($commentModel->date);
                        if (date_format($date, 'd-m-Y') == date('d-m-Y')) {
                            return date_format($date, 'H:m');
                        } else {
                            return date_format($date, 'd-m-Y');
                        }
                    },
                ],
                [
                    'class' => '\kartik\grid\ActionColumn',
                    'width' => '10%',
                    'template' => '{update} {delete}',
                    'buttons' => [
                        'update' => function ($url, $commentModel) use ($model) {
                            return Html::a(
                                '<span class="glyphicon glyphicon-pencil"></span>',
                                ['postcomment/update/', 'id' => $commentModel->id, 'post_id' => $model->id],
                                [
                                    'data-method' => 'post'
                                ]
                            );
                        },
                        'delete' => function ($url, $commentModel) use ($model) {
                            return Html::a(
                                '<span class="glyphicon glyphicon-trash"></span>',
                                ['postcomment/delete', 'id' => $commentModel->id, 'post_id' => $model->id],
                                [
                                    'data-method' => 'post',
                                    'data-confirm' => 'Are you sure you want to delete this comment?',
                                ]
                            );
                        },
                    ]
                ],
            ],
        ]); ?>

        <div class="newscomment-form">
            <?php $modelComment = new Postcomment() ?>

            <?php $form = ActiveForm::begin(['method' => 'post', 'action' => Url::to(['postcomment/create', 'post_id' => $model->id])]); ?>

            <?= $form->field($modelComment, 'content')->textarea(['maxlength' => true, 'rows' => '6'])->label('Comment') ?>

            <div class="form-group">
                <?= Html::submitButton('Send', ['class' => 'btn btn-success', 'id' => 'btnsave']) ?>
            </div>
            <?php ActiveForm::end(); ?>

        </div>

    </div>

</div>
