<?php

use kartik\grid\GridView;
use yii\helpers\Html;

?>

<?php
$this->title = 'Management';
$auth = Yii::$app->authManager;
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
                'width' => '25%',
                'attribute' => 'username',
            ],
            [
                'width' => '30%',
                'attribute' => 'email',
            ],
            [
                'width' => '20%',
                'header' => 'Permission',
                'attribute' => 'id',
                'value' => function ($model) use ($auth) {
                    $role = $auth->getRolesByUser($model->id);
                    return array_shift($role)->name;
                }
            ],
            [
                'class' => 'yii\grid\ActionColumn',
                'template' => '{update}',
                'header' => 'Promote/Demote',
                'buttons' => [
                    'update' => function ($url, $model) use ($auth) {
                        $role = $auth->getRolesByUser($model->id);
                        if (array_shift($role)->name == "admin") {
                            return Html::a('<span class="glyphicon glyphicon-wrench"> Demote To Member</span>',
                                ['site/demote/', 'userId' => $model->id]);
                        } else {
                            return Html::a('<span class="glyphicon glyphicon-wrench"> Promote To Admin</span>',
                                ['site/promote/', 'userId' => $model->id]);
                        }
                    },
                ]
            ],
        ]
    ]) ?>

</div>

