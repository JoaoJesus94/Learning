var sn = $("#navbar");
var pos = sn.position();
$(window).scroll(function () {
    "use strict";
    var windowPos = $(window).scrollTop();
    if (windowPos >= pos.top) {
        sn.addClass("mynav-fixed-left");
    } else {
        sn.removeClass("mynav-fixed-left");
    }
});