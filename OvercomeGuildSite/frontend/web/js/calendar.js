$('.deleteEventBtn').click(function () {
    var id = $('#inputID').val();
    alert(id);
});

$('.updateEventBtn').click(function () {

    var id = $('#inputID').val();

        $.post('index.php?r=event/update',{'id':id}, function(id, eventName, date, description, category){

            $.ajax({
                type: "POST",
                url: "Event/Update",
                data: "id="+id,
                success: function (result) {
                    window.alert("Success!"+result); // should show "Success! id = [sent id value]"                          },

                    },
                error: function() {
                        window.alert("Error!");
                },}
            );


    });





    alert(id);
});