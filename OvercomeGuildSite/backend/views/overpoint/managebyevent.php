<?php

use common\models\Userprofile;
use kartik\grid\GridView;
use yii\helpers\Html;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $model common\models\Overpoint */

$this->title = "Manage OverPoints";
$this->params['breadcrumbs'][] = ['label' => 'Overpoints', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;

if ($userPoints != null) {
    $allUserIdArray = array();
    $allUserInEventIdArray = array();
    foreach (Userprofile::find()->all() as $player) {
        array_push($allUserIdArray, $player->id);
    }
    foreach ($userPoints->getModels() as $playerAlready) {
        array_push($allUserInEventIdArray, $playerAlready->userprofile_id);
    }
}

?>

<script>
    function selectEvent() {
        var id = $('#eventSelect').val();

        if (id == "") {
            url = "<?= Url::toRoute(['overpoint/managebyevent'])?>";
        } else {
            url = "<?= Url::toRoute(['overpoint/managebyevent'])?>" + "?id=" + id;
        }

        document.getElementById('eventForm').action = url;

        window.location.href = url;
        location.reload(true);
    }

    function addPlayerToEvent() {

        var id = $('#inputID').val();
        url = "<?= Url::toRoute(['userprofilehasevent/add'])?>";

        document.getElementById('addForm').action = url;

        window.location.href = url;
        location.reload(true);

    }

</script>

<div class="overpoint-view center-tables">

    <?= Html::tag('h1', $this->title . " By Event", ['class' => 'text-center color-dimgrey']) ?>
    <br>
    <br>
    <br>

    <div class="center-elements">
        <form id="eventForm" method="post" onsubmit="selectEvent()">
            <span class="color-dimgrey">Choose Event:</span>
            <select id="eventSelect">
                <option value="" selected>Choose One</option>
                <?php
                foreach ($eventsModel as $event) { ?>
                    <?php $date = (new DateTime($event->date))->format('d / M / Y') ?>
                    <option value="<?= $event['id'] ?>"><?= $date ?></option>
                    <?php
                } ?>
            </select>
            <button class="btn btn-primary" type="submit">Go</button>
        </form>
    </div>

    <br>
    <br>
    <br>

    <div id="result">
        <?php
        if ($id != null) {
            if ($userPoints != null) {
                ?>
                <div class="col-md-8">
                    <?= GridView::widget([
                        'dataProvider' => $userPoints,
                        'summary' => false,
                        'striped' => false,
                        'resizableColumns' => false,
                        'options' => ['style' => 'color:dimgrey'],
                        'headerRowOptions' => ['style' => 'color:white'],
                        'columns' => [
                            [
                                'attribute' => 'userprofile_id',
                                'header' => 'User',
                                'vAlign' => GridView::ALIGN_MIDDLE,
                                'width' => '30%',
                                'value' => function ($model) {
                                    $userProfile = Userprofile::findOne(['id' => $model->userprofile_id]);
                                    return $userProfile->displayName;
                                }
                            ],
                            [
                                'attribute' => 'op',
                                'vAlign' => GridView::ALIGN_MIDDLE
                            ],
                            [
                                'attribute' => 'up',
                                'vAlign' => GridView::ALIGN_MIDDLE
                            ],
                            [
                                'attribute' => 'priority',
                                'vAlign' => GridView::ALIGN_MIDDLE
                            ],
                            [
                                'attribute' => 'attendance',
                                'vAlign' => GridView::ALIGN_MIDDLE
                            ],
                            [
                                'class' => 'kartik\grid\ActionColumn',
                                'template' => '{delete}',
                                'header' => '',
                                'vAlign' => GridView::ALIGN_MIDDLE,
                                'buttons' => [
                                    'delete' => function ($url, $model) use ($id) {
                                        return Html::a('<span class="glyphicon glyphicon-minus-sign"></span>', ['userprofilehasevent/delete',
                                            'userprofile_id' => $model->userprofile_id, 'event_id' => $id],
                                            [
                                                'data' => [
                                                    'confirm' => 'Are you sure you want to remove this player from the event?',
                                                    'method' => 'post'
                                                ]
                                            ]
                                        );
                                    },
                                ]
                            ],
                            [
                                'class' => 'kartik\grid\ExpandRowColumn',
                                'allowBatchToggle' => false,
                                'width' => '10%',
                                'header' => '',
                                'value' => function ($model, $key, $index, $column) {
                                    return GridView::ROW_COLLAPSED;
                                },
                                'detail' => function ($model, $key, $index, $column) use ($id) {
                                    return Yii::$app->controller->renderPartial('point_player_detail', ['points' => $model, 'eventId' => $id]);
                                },
                            ],
                        ],
                    ]); ?>
                </div>
                <div class="col-md-4">
                    <?= Html::a('Raid Start', ['startraid', 'id' => $id], ['class' => 'btn btn-success']) ?>
                    <?= Html::a('Raid End', ['endraid', 'id' => $id], ['class' => 'btn btn-danger', 'style' => 'float:right']) ?>
                </div>
                <br>
                <br>
                <br>
                <br>
                <br>
                <div class="center-elements"
                     style="border: white 1px solid; padding: 10px; background-color: white; border-radius: 15px">
                    <form id="addForm" method="post" onsubmit="addPlayerToEvent()">
                        <h4 style="font-weight: bold">Add Players to Event</h4>
                        <input type="hidden" name="eventId" value="<?= $id ?>">
                        <select name="userId">
                            <option value="">Choose Player</option>
                            <?php
                            $playerNotInEventId = array_diff($allUserIdArray, $allUserInEventIdArray);
                            foreach ($playerNotInEventId as $userId) {
                                $user = Userprofile::findOne(['id' => $userId]) ?>
                                <option name="userId" value="<?= $userId ?>"><?= $user->displayName ?></option>
                            <?php } ?>
                        </select>
                        <button class="btn btn-primary glyphicon glyphicon-plus-sign" type="submit">Add</button>
                    </form>
                </div>
                <?php
            } else {
                Yii::$app->session->setFlash('error', 'No players to show at the selected event');
            }
        }
        ?>

    </div>
</div>