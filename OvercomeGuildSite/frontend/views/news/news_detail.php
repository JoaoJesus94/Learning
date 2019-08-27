<?php

use common\models\Userprofile;
use kartik\grid\GridView;
use yii\data\ArrayDataProvider;
use yii\helpers\Html;
use common\models\Newscomment;
use yii\helpers\Url;
use yii\widgets\ActiveForm;

?>

<?php

$comments = new ArrayDataProvider([
    'allModels' => $model->newscomments,
]);

?>


<div class="center-tables">
    <br>
    <div style="border-style: solid;border-width: 2px;border-color: black">
        <p><?= $model->content ?></p>
    </div>



    <div class="center-tables">
        <?= Html::tag('h1', 'Comments', ['class' => 'text-center color-dimgrey']) ?>
        <br>
        <?= GridView::widget([
            'dataProvider' => $comments,
            'summary' => false,
            'striped' => false,
            'resizableColumns' => false,
            'showHeader' => false,
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
                    'width' => '20%',
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
                                ['newscomment/update/', 'id' => $commentModel->id],
                                [
                                    'data-method' => 'post'
                                ]
                            );
                        },
                        'delete' => function ($url, $commentModel) use ($model) {
                            return Html::a(
                                '<span class="glyphicon glyphicon-trash"></span>',
                                ['newscomment/delete', 'id' => $commentModel->id],
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
            <?php $modelComment = new Newscomment() ?>

            <?php $form = ActiveForm::begin(['method' => 'post', 'action' => Url::to(['newscomment/create', 'new_id' => $model->id])]); ?>

            <?= $form->field($modelComment, 'content')->textarea(['maxlength' => true, 'rows' => '6'])->label('Comment') ?>

            <div class="form-group">
                <?= Html::submitButton('Send', ['class' => 'btn btn-success', 'id' => 'btnsave']) ?>
            </div>
            <?php ActiveForm::end(); ?>

        </div>
    </div>

</div>