<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model common\models\Postcomment */

$this->title = 'Create Postcomment';
$this->params['breadcrumbs'][] = ['label' => 'Postcomments', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="postcomment-create center-tables">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
