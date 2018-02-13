var modal;

var pretty_max_source_length = 80000;


function setupLoginDialog() {
    // instantiate new modal
    modal= new tingle.modal({
        footer: true,
        stickyFooter: false,
        closeMethods: ['overlay', 'button', 'escape'],
        closeLabel: "Close",
        onOpen: function() {

        },
        onClose: function() {
            //console.log('modal closed');
        },
        beforeClose: function() {
            if ($('#bearertoken').val()) {
                sessionStorage.token = $('#bearertoken').val();
            }
            return true; // close the modal
        }
    });

    // set content
    modal.setContent($('#setauth').html());

}

$(document).ready(function() {
    setupLoginDialog();
    if (localStorage.clientid != null) {
        $('#clientid').val(localStorage.clientid);
    }
    
    
    /* Event handling */
    $(document).on('click', 'a', function(event) {
        event.preventDefault();
        $(this).next().find('.JSON').children('pre').addClass("prettyprint");
        prettyPrint();
        $(this).next().filter(".entityDescription").slideToggle();
    });
    
    
    $(document).on('click', '.outputFormatJSON', function(event) {
        $("div.JSON").slideDown(); 
        $("div.XML").slideUp(); 
    });

    $(document).on('click', '.outputFormatXML', function(event) {
        $("div.JSON").slideUp(); 
        $("div.XML").slideDown(); 
        
    });
    
    /* Bearer flow button. Close dialog and set token */
    $(document).on('click', '#bearerflowbutton', function() {
        modal.close();
    });
     

    /* Clicking on the oauth authorize button should initiate the oauth flow
     * For this we redirect the user to the oauth login page
     */
    $(document).on('click', '#oauthflowbutton', function() {
        
        var redirect = document.location;
        var clientid = $('#clientid').val();
        var scopes = $('#oauthscopes').val(); // TODO: scopes empty
        
        localStorage.clientid = clientid;
        
        var url;
        if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
            url = "http://localhost:9090/oauth";
        } else if (location.hostname.includes("test")) {
            url = "https://authtest.sportlink.com/oauth";
        } else {
            url = "https://auth.sportlink.com/oauth";
        }
        url += "?redirect_uri="+redirect;
        url += "&response_type=token";
        url += "&client_id="+clientid;
        if (scopes) {
            url += "&scope="+scopes;
        }
        url += "&state=123abcdef";
        url += "&login_page_type=full";
        window.location = url;
    });
    
    
    
    $(document).on('click', '#authbutton', function() {
        modal.open();
    });
    
    /* Going to perform an entity call */
    $(document).on('click', '.callentitybutton', function() {
        var myRequest =  $(this).closest('.requestbody');
        var myOp =  $(this).closest('.operation');
        if (sessionStorage.getItem("token") === null || !sessionStorage.getItem("token") ) {
            modal.open();
            return;
        }
        var method = $(this).attr('method');
        myOp.find('.entityresponsebody').children().remove();
        myOp.find('.shell-body').text('');
        var url = window.location.origin + "/entity/"+ myOp.find('.url').text();
       
        if (method === "GET" || method === "DELETE") {
            // prepare URL
            var missingRequired = false;
            myRequest.find('.requestTable tbody tr').each(function() {
                var name = $(this).find('.propname').text();
                var value = $(this).find('input').val();
                if (value !== '') {
                    url += "&" + name + "=" + value;
                    $(this).find('input').removeClass('missinginput');
                } else {
                    // Check if this was required
                    if ($(this).find('.propname').hasClass('required')) {
                        missingRequired = true;
                        $(this).find('input').addClass('missinginput');
                    }
                }
                
            });
            if (missingRequired) {
                return;
            }
            url = url.replace('&', '?'); // replace first & with ?
            addSpinner();
            // Do request
            $.ajax({
                beforeSend: function(req) {
                    req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token); 
                    req.setRequestHeader('Accept', 'application/json'); 
                },
                dataType: 'json',
                type: method,
                url: url,
                complete: function(data) {
                   var pre = $('<pre>', {'class': 'prettyprint lang-json'});
                   pre.text(data.responseText);
                   myOp.find('.entityresponsebody').html(pre);
                   if (data && data.responseText.length < pretty_max_source_length) {
                       prettyPrint();
                   } else {
                       // add class to prevent it from being pretty-printed by script response prettyprint
                       pre.addClass('prettyprinted');
                   }
                   
                   // Add curl statement
                   myOp.find('.shell-body').text(getCurlUrlGetDelete(method, url));
                   myOp.find('.shell-body').show();
                }
            });
        
        } else {
            // Get data input
            var requestdata = myRequest.find('textarea.call-entityinput').val();
            addSpinner();
            $.ajax({
                beforeSend: function(req) {
                    req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token); 
                    req.setRequestHeader('Accept', 'application/json');
                    req.setRequestHeader('content-type', 'application/json');
                },
                dataType: 'json',
                data: requestdata,
                type: method,
                url: url,
                complete: function(data) {
                   var pre = $('<pre>', {'class': 'prettyprint lang-json'});
                   pre.text(data.responseText);
                   myOp.find('.entityresponsebody').html(pre);
                   if (data && data.responseText.length < pretty_max_source_length) {
                         prettyPrint();
                   } else {
                       // add class to prevent it from being pretty-printed by script response prettyprint
                          pre.addClass('prettyprinted');
                   }
                   
                   // Add curl statement
                   myOp.find('.shell-body').text(getCurlUrlPostPut(method, url, requestdata));
                   myOp.find('.shell-body').show();
                }
            });
        }
        
        function addSpinner() {
            myOp.find('.entityresponsebody').html('<div class="loader spinner"> <div class="cube1"></div><div class="cube2"></div></div><div class="loadertext">Loading...</div>')

        }
        
        /* Helper functions */
        function getCurlUrlGetDelete(method, url) {
            var curl=  'curl ';
            curl += '-X' + method;
            curl += ' -H "Authorization: Bearer ' + sessionStorage.token +'"';
            curl += ' -H "Accept: application/json" ';
            curl += '"' + encodeURI(url) + '"'
            return curl;
        }
        
        function getCurlUrlPostPut(method, url, data) {
            var curl=  'curl ';
            curl += '-X' + method;
            curl += ' -H "Authorization: Bearer ' + sessionStorage.token +'"';
            curl +=  ' -H "Accept: application/json" ';
            curl += '-d "';
            curl += data.replace(new RegExp('\"', 'g'), '\\"').replace(new RegExp('\n', 'g'), '')
            curl += '" ';
            curl += '"' + encodeURI(url) + '"'
            return curl;
        }
        
    });
    
    
    $(document).on('click', '.tryentitybutton', function() {
        var parent = $(this).closest('.operation');
        if ($(this).hasClass('cancel')) {
            $(this).text("Try it out");
            
            parent.find('.call-entityinput').remove();
            parent.find('.shell-body').hide();
            parent.find('.tableinputheader').remove();
            parent.find('.tableinputtd').remove();
            parent.find('.entityresponsebody').children().remove();
            parent.find('.responsebody').show();
            parent.find('.requestinput').show();
            parent.children('.perform-call-entity').hide();
            parent.find('.callentitybutton').hide();
            
           
            $(this).removeClass('cancel');
        } else {
            $(this).text("Cancel");
            $(this).addClass('cancel');
            
            // Hide response
            parent.find('.responsebody').hide();
            parent.find('.callentitybutton').show();
             
            // Add input to table
            var method = parent.find('a').attr('method') ;
            
            if (method === "GET" || method === "DELETE") {
                parent.find('.requestTable th:last-child').after('<th class="tableinputheader">Input</th>');
                parent.find('.requestTable tbody td:last-child').after('<td class="tableinputtd"><input type="text"></input></td>');
            } else {
                var requestinput = parent.find('.requestinput');
                requestinput.hide();
                var input = requestinput.text().trim();
                var textinput = $('<textarea>', {'class': 'call-entityinput'});
                textinput.text(input);
                parent.find('.requestbody').prepend(textinput);
            }
         
            parent.find('.perform-call-entity').show();
        }
       
    });
});

