<?php

use frontend\assets\AppAsset;
use yii\bootstrap\Modal;
use yii\helpers\Url;

/* @var $this yii\web\View */
/* @var $searchModel common\models\EventSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Events';
$this->params['breadcrumbs'][] = $this->title;
$this->registerJsFile('@web/js/calendar.js', ['depends' => [yii\web\JQueryAsset::className()]]);
AppAsset::register($this);

?>

<script>

    function goEvent() {
        var id = $('#inputID').val();

        url = "<?= Url::toRoute(['userprofilehasevent/create'])?>";

        document.getElementById("eventId").value = id;
        document.getElementById("formAttend").action = url;

        window.location.href = url;
        location.reload(true);
    }

</script>

<div class="event-index center-tables">

    <br>


    <?= \yii2fullcalendar\yii2fullcalendar::widget(array(
        'events' => $events,
        'header' => [
            'left' => 'prev next today',
            'center' => 'title',
            'right' => false,
        ],
        'id' => 'event',
        'clientOptions' => [
            'editable' => false,
            'draggable' => false,
            'selectable' => true,
            'timeFormat' => 'H(:mm)',

        ],

        'eventClick' => "function(calEvent, jsEvent, view) {

                $(this).css('border-color', 'red');
                               
                $.get('index.php?r=calendar/event',{'id':calEvent.id}, function(id, eventName, date, description, category){
                                       
                data = $.fullCalendar.formatDate( calEvent.start, 'DD-MM-YY');
                tempo = $.fullCalendar.formatDate( calEvent.start, 'HH:mm');
                
               document.getElementById('inputID').setAttribute('value',calEvent.id);
               $('#pEventName' ).html(calEvent.title);
               $('#pDate' ).html( 'Date: ' + data );
               $('#pTime' ).html( 'Time: ' + tempo );
               $('#pCategory' ).html('Category: ' + calEvent.nonstandard); 
               $('#pDescription' ).html('Description: ' + calEvent.description );
                              
                    $('.modal').modal('show')
                    .find('#modelContent')
                    .html()
                    ;            
                })

        }",

    ));
    ?>


</div>


<div class="modal">


    <?php
    Modal::begin([
        'header' => '<h4 class="center-elements event_title" id="pEventName"></h4>',
        'id' => 'model',
        'size' => 'model-lg',

    ]);

    ?>


    <div id='modelContent' class="event_content">

        <!--<p id='pid'></p>-->
        <form id="form1" method="post" style="border-bottom: 1px solid #e5e5e5">
            <input type="hidden" id='inputID'>
            <p id='pDate'></p>
            <p id='pTime'></p>
            <p id='pCategory'></p>
            <p id='pDescription'></p>

        </form>
        <br>

        <form id="formAttend" method="post" style="border-bottom: 1px solid #e5e5e5">

            <input id="eventId" type="hidden" name="eventId">
            <span>Role:
            <select name="eventRole">
                <option value="Tank">Tank</option>
                <option value="Healer">Healer</option>
                <option value="Dps">DPS</option>
            </select></span>

            <br>
            <br>

            <button class="btn btn-success" type="submit" value="Going" name="eventOption" onclick="goEvent()">Going
            </button>
            <button class="btn btn-primary" type="submit" value="Maybe" name="eventOption" onclick="goEvent()">Maybe
            </button>
            <button class="btn btn-danger" type="submit" value="Not Going" name="eventOption" onclick="goEvent()">Not
                Going
            </button>
            <br>
            <br>

        </form>

    </div>


    <?php Modal::end(); ?>

</div>

