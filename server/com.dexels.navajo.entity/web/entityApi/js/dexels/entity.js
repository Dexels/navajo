var modal;

function setupLoginDialog() {
	// instantiate new modal
	modal= new tingle.modal({
	    footer: true,
	    stickyFooter: false,
	    closeMethods: ['overlay', 'button', 'escape'],
	    closeLabel: "Close",
	    onOpen: function() {
	    	if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
               $('#tenant').show();
            }
	    },
	    onClose: function() {
	        //console.log('modal closed');
	    },
	    beforeClose: function() {
	    	if ($('#bearertoken').val()) {
	    		sessionStorage.token = $('#bearertoken').val();
	            sessionStorage.tenant = $('#tenantinput').val();
	    	}
	        return true; // close the modal
	    }
	});

	// set content
	modal.setContent($('#setauth').html());

	// add a button
	modal.addFooterBtn('Authorize', 'tingle-btn tingle-btn--pull-right tingle-btn--primary', function() {
	    // here goes some logic
	    modal.close();
	});
}

$(document).ready(function() {
	setupLoginDialog();
	
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
            myOp.find('.entityresponsebody').html('<div class="loader sk-rotating-plane"></div><div class="loadertext">Loading...</div>')
            // Do request
            $.ajax({
                beforeSend: function(req) {
                    req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token); 
                    req.setRequestHeader('Accept', 'application/json'); 
                    if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
                        req.setRequestHeader('X-Navajo-Instance', sessionStorage.tenant); 
                    }
                },
                dataType: 'json',
                type: method,
                url: url,
                complete: function(data) {
                   var pre = $('<pre>', {'class': 'prettyprint lang-json'});
                   pre.text(data.responseText);
                   myOp.find('.entityresponsebody').html(pre);
                   prettyPrint();
                   
                   // Add curl statement
                   myOp.find('.shell-body').text(getCurlUrlGetDelete(method, url));
                   myOp.find('.shell-body').show();
                }
            });
        
        } else {
            // Get data input
            var requestdata = myRequest.find('textarea.call-entityinput').val();
            $.ajax({
                beforeSend: function(req) {
                    req.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.token); 
                    req.setRequestHeader('Accept', 'application/json');
                    req.setRequestHeader('content-type', 'application/json');
                    if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
                        req.setRequestHeader('X-Navajo-Instance', sessionStorage.tenant); 
                    }
                },
                dataType: 'json',
                data: requestdata,
                type: method,
                url: url,
                complete: function(data) {
                   var pre = $('<pre>', {'class': 'prettyprint lang-json'});
                   pre.text(data.responseText);
                   myOp.find('.entityresponsebody').append(pre);
                   prettyPrint();
                   
                   // Add curl statement
                   myOp.find('.shell-body').text(getCurlUrlPostPut(method, url, requestdata));
                   myOp.find('.shell-body').show();
                }
            });
        }
    });
    
    function getCurlUrlGetDelete(method, url) {
        var curl=  'curl ';
        curl += '-X' + method;
        curl += ' -H "Authorization: Bearer ' + sessionStorage.token +'"';
        curl += ' -H "Accept: application/json" ';
        if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
            curl += '-H "X-Navajo-Instance: ' + sessionStorage.tenant +'" ';
        }
        
        curl += '"' + encodeURI(url) + '"'
        return curl;
    }
    
    function getCurlUrlPostPut(method, url, data) {
        var curl=  'curl ';
        curl += '-X' + method;
        curl += ' -H "Authorization: Bearer ' + sessionStorage.token +'"';
        curl +=  ' -H "Accept: application/json" ';
        if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
            curl += '-H "X-Navajo-Instance: ' + sessionStorage.tenant +'" ';
        }
        curl += '-d "';
        curl += data.replace(new RegExp('\"', 'g'), '\\"').replace(new RegExp('\n', 'g'), '')
        curl += '" ';
        curl += '"' + encodeURI(url) + '"'
        return curl;
    }
    
    $(document).on('click', '#authbutton', function() {
        modal.open();
    });
	
    $(document).on('click', '.tryentitybutton', function() {
    	var parent = $(this).closest('.operation');
        if ($(this).hasClass('cancel')) {
            $(this).text("Try it out");
            $(this).removeClass('cancel');
            
            parent.find('.call-entityinput').remove();
            parent.find('.shell-body').hide();
            parent.find('.tableinputheader').remove();
            parent.find('.tableinputtd').remove();
            parent.find('.entityresponsebody').children().remove();
            parent.find('.responsebody').show();
            parent.find('.requestinput').show();
            parent.children('.perform-call-entity').hide();
           
             
        } else {
            $(this).text("Cancel");
            $(this).addClass('cancel');
            
            // Hide response
            parent.find('.responsebody').hide();
             
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

