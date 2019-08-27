$(function () {
    $('#modelButton').click(function () {
        $('.modal').modal('show')
            .find('#modelContent')
            .load($(this).attr('value'));
    });

    $(document).on('click', '.fc-day', function () {
        var date = $(this).attr('data-date');
        $.get('index.php?r=calendar/create', {'date': date}, function (data) {
            $('.modal').modal('show')
                .find('#modelContent')
                .html(data);
        })
    });

});