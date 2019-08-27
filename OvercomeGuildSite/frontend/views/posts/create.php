<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model common\models\Posts */

$this->title = 'Create Posts';
$this->params['breadcrumbs'][] = ['label' => 'Posts', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="posts-create center-tables">

    <h1 style='color:white' ><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
        'idTopic' => $idTopic,
    ]) ?>

</div>
