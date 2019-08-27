<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\CharacterHasEvent */

$this->title = 'Update Character Has Event: ' . $model->Character_id;
$this->params['breadcrumbs'][] = ['label' => 'Character Has Events', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->Character_id, 'url' => ['view', 'Character_id' => $model->Character_id, 'Event_id' => $model->Event_id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="character-has-event-update">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
