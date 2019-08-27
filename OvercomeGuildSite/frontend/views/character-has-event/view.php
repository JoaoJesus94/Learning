<?php

use yii\helpers\Html;
use yii\widgets\DetailView;

/* @var $this yii\web\View */
/* @var $model common\models\CharacterHasEvent */

$this->title = $model->Character_id;
$this->params['breadcrumbs'][] = ['label' => 'Character Has Events', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
\yii\web\YiiAsset::register($this);
?>
<div class="character-has-event-view">

    <h1><?= Html::encode($this->title) ?></h1>

    <p>
        <?= Html::a('Update', ['update', 'Character_id' => $model->Character_id, 'Event_id' => $model->Event_id], ['class' => 'btn btn-primary']) ?>
        <?= Html::a('Delete', ['delete', 'Character_id' => $model->Character_id, 'Event_id' => $model->Event_id], [
            'class' => 'btn btn-danger',
            'data' => [
                'confirm' => 'Are you sure you want to delete this item?',
                'method' => 'post',
            ],
        ]) ?>
    </p>

    <?= DetailView::widget([
        'model' => $model,
        'attributes' => [
            'Character_id',
            'Event_id',
            'attending',
            'role',
        ],
    ]) ?>

</div>
