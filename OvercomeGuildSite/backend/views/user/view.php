<?php

use yii\helpers\Html;
use backend\assets\AppAsset;
use kartik\detail\DetailView;

AppAsset::register($this);

/* @var $this yii\web\View */
/* @var $model common\models\User */

$this->params['breadcrumbs'][] = ['label' => 'Users', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="user-view">

    <h1><?= Html::encode($this->title) ?></h1>


    <?= DetailView::widget([
        'model' => $model,
        'condensed' => true,
        'hover' => true,
        'striped' => false,
        'hAlign' => DetailView::ALIGN_CENTER,
        'vAlign' => DetailView::ALIGN_CENTER,
        'mode' => DetailView::MODE_VIEW,
        'attributes' => [
            'username',
            'email:email',
            'created_at',
            'updated_at',
        ],
    ])?>

</div>
