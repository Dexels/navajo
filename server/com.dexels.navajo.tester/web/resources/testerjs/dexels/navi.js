var init = true;

window.onpopstate = function(event) {
    init = false;
    updateContent(event.state.content, event.state.newclick);
};

$(document).ready(function() {
    $("#nav-mobile").html($("#nav-main").html());
    $("#nav-main li").click(function() {
        navigationClick($(this));
        return false;
    });
});

function updateContent(data, liid) {
    $('#lihome').parent().children().removeClass("open");

    if (typeof data == 'undefined') {
        $("#content").load("content/index.html");
        $('#lihome').addClass("open");
    } else {
        $('#' + liid).addClass("open");
        $('#content').html(data);
    }
}

function postScriptLoad() {
    if (init) {
        console.log('Init');
        initPage();
    } else {
        console.log('Reloading page');
        reloadPage();
    }

}

function preScriptLoad() {

    if (window.history.length < 2) {
        // Make initial entry
        var dataObj = {
            content : $("#content").html(),
            newclick : $('li[class="open"]').attr('id')
        };
        history.pushState(dataObj, 'Dexels Monitor', window.location.pathname + window.location.search);
    }
}

function navigationClick($elem) {
    var href = $elem.find("a").attr("href");
    var id = $elem.attr('id');
    var title = "Dexels Monitor: " + $elem.text();

    $.get("content/" + href, function(data) {
        init = true;
        updateContent(data, id);
        var dataObj = {
            "newclick" : id,
            "content" : data
        };
        history.pushState(dataObj, title, href);

    });
}

function replaceState() {
    var href = window.location.pathname;
    var id = $('li[class="open"]').attr('id');
    var title = "Dexels Monitor: replaced";
    var data = $('#content').html();

    var dataObj = {
        "newclick" : id,
        "content" : data
    };
    history.replaceState(dataObj, title, href);
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) === 0) {
            return c.substring(name.length,c.length);
        }
    }
    return "";
}