<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Userprofilehasevent */

$this->title = 'Create Userprofile Has Event';
$this->params['breadcrumbs'][] = ['label' => 'Userprofile Has Events', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="userprofile-has-event-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
