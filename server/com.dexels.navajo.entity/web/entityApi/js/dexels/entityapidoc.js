
var modal;

var pretty_max_source_length = 80000;


function setupLoginDialog() {
    // fill important paramenters
    // extract tenant from the url
    // ONLY if it's local host in the url
    var url = window.location.href;
    if(url.indexOf('localhost') > -1){
        sessionStorage.setItem('isLocalhost', '1');
        var regex = /entityDocumentation\/(\w+)\//;
        var tenant = url.match(regex)[1].toUpperCase();
        $('#bauth_tenant').val(tenant);
        $('#cauth_tenant').val(tenant);
    } else{
        sessionStorage.setItem('isLocalhost', 0);
        $('.tenantinput').hide();
    }
    
    // instantiate new modal
    modal= new tingle.modal({
        footer: true,
        stickyFooter: false,
        closeMethods: ['overlay', 'button', 'escape'],
        closeLabel: "Close",
        onOpen: function() {
        		sessionStorage.authType = ''
        			
        		
        },
        onClose: function() {
            //console.log('modal closed');
        },
        beforeClose: function() {
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
        
        //http://localhost:9090/oauth?redirect_uri=http://localhost:9090/entityDocumentation/knvb/voetbalnl/website&response_type=token&client_id=Xsm3pXOVzh&state=123abcdef&login_page_type=full
        sessionStorage.authType = 'oauth';
        window.location = url;
    });
    
    /* Clicking on the bauth authorize button should store username and password for all the upcoming reuqests
     */
    $(document).on('click', '#bauthflowbutton', function() {
        var bauth_username = $('#bauth_username').val();
        var bauth_password = $('#bauth_password').val();
        var bauth_tenant = $('#bauth_tenant').val();
        
        sessionStorage.bauth_username = bauth_username;
        sessionStorage.bauth_password = bauth_password;
        sessionStorage.token = btoa(bauth_username + ":" + bauth_password);
        sessionStorage.tenant =  bauth_tenant.toUpperCase();
        sessionStorage.authType = 'basic';
        
        modal.close();
    });
    
    /* Custom authorization button handler */
    $(document).on('click', '#customauthorizebutton', function() {
        var cauth_type = $('#cauth_type').val();
        var cauth_token = $('#cauth_token').val();
        var cauth_tenant = $('#cauth_tenant').val();
        
        sessionStorage.cauth_type = cauth_type.charAt(0).toUpperCase() + cauth_type.substring(1,cauth_type.length);
        sessionStorage.token = cauth_token;
        sessionStorage.tenant =  cauth_tenant.toUpperCase();
        sessionStorage.authType = 'custom';
        
        modal.close();
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
                		if(sessionStorage.authType == 'oauth'){
                			req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token);
                		}else if(sessionStorage.authType == 'basic'){
                			req.setRequestHeader("Authorization", 'Basic ' + btoa(sessionStorage.bauth_username + ":" + sessionStorage.bauth_password));
                			req.setRequestHeader("X-Navajo-Instance", sessionStorage.tenant);//if we aren't on localhost, the framework adds the header on the reuquest
                		}else{
                			req.setRequestHeader('Authorization', sessionStorage.cauth_type + ' ' + sessionStorage.token);
                			req.setRequestHeader("X-Navajo-Instance", sessionStorage.tenant);//if we aren't on localhost, the framework adds the header on the reuquest
                		}
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
	                	if(sessionStorage.authType == 'oauth'){
	            			req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token);
	            		}else if(sessionStorage.authType == 'basic'){
	            			req.setRequestHeader("Authorization", 'Basic ' + btoa(sessionStorage.bauth_username + ":" + sessionStorage.bauth_password));
	            			req.setRequestHeader("X-Navajo-Instance", sessionStorage.tenant); //if we aren't on localhost, the framework adds the header on the reuquest
	            		}else{
	            			req.setRequestHeader('Authorization', sessionStorage.cauth_type + ' ' + sessionStorage.token);
	            			req.setRequestHeader("X-Navajo-Instance", sessionStorage.tenant); //if we aren't on localhost, the framework adds the header on the reuquest
	            		} 
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
        
        function getCurlAuth() {
            var response = '';
            if(sessionStorage.authType == 'oauth'){
                response += ' -H "Authorization: Bearer ' + sessionStorage.token +'"';
            } else if(sessionStorage.authType == 'basic'){
                response += ' -H "Authorization: Basic ' + btoa(sessionStorage.bauth_username + ':' + sessionStorage.bauth_password) + '"';
                if (sessionStorage.isLocalhost == '1') response += '-H "X-Navajo-Instance: ' + sessionStorage.tenant +'"';
            } else {
                response += ' -H "Authorization: ' +sessionStorage.cauth_type + ' ' + sessionStorage.token +'"';
                if (sessionStorage.isLocalhost == '1') response += '-H "X-Navajo-Instance: ' + sessionStorage.tenant +'"';
            }
            return response + ' ';
        }
        
        /* Helper functions */
        function getCurlUrlGetDelete(method, url) {
            var curl=  'curl ';
            curl += '-X' + method;
            curl += getCurlAuth();
            curl += ' -H "Accept: application/json" ';
            curl += '"' + encodeURI(url) + '"'
            return curl;
        }
        
        function getCurlUrlPostPut(method, url, data) {
            var curl=  'curl ';
            curl += '-X' + method;
            curl += getCurlAuth();
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

