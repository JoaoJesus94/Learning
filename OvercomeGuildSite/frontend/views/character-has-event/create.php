<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\CharacterHasEvent */

$this->title = 'Create Character Has Event';
$this->params['breadcrumbs'][] = ['label' => 'Character Has Events', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="character-has-event-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
