<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model common\models\Userprofile */

$this->title = 'Update Userprofile: ' . $model->displayName;
$this->params['breadcrumbs'][] = ['label' => 'Userprofiles', 'url' => ['index']];
$this->params['breadcrumbs'][] = ['label' => $model->id, 'url' => ['view', 'id' => $model->id]];
$this->params['breadcrumbs'][] = 'Update';
?>
<div class="userprofile-update">

    <?= Html::tag('h1', $this->title, ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= $this->render('_form', [
        'model' => $model,
        'applyModel' => $applyModel,
    ]) ?>

</div>
