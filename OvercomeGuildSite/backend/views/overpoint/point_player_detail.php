<?php

use yii\helpers\Html;

?>

<div class="overpoint-view center-tables">

    <?= Html::tag('h4', " Manage Player Points", ['class' => 'text-center color-dimgrey']) ?>
    <br>

    <?= Html::a('Give Gear', ['givegear', 'pointsId' => $points->idoverpoint, 'eventId' => $eventId], ['class' => 'btn btn-primary', 'onclick' => 'refreshPage()']) ?>
    <?= Html::a('Donated Gear', ['donatedgear', 'pointsId' => $points->idoverpoint, 'eventId' => $eventId], ['class' => 'btn btn-success', 'style' => 'float:right']) ?>

</div>
