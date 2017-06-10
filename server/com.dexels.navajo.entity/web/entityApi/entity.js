$(document).ready(function() {
    
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
    
    $(document).on('click', '.docallentitybutton', function() {
        var method = $(this).attr('method');
        $('.entityresponsebody').children().remove();
        $('.shell-body').text('');
        var url = $(this).closest('.operation').find('.url').text();
        try {
            if (method === "GET" || method === "DELETE") {
                // prepare URL
                $(this).closest('.entityDescription').find('.requestTable tbody tr').each(function() {
                    var name = $(this).find('.propname').text();
                    var value = $(this).find('input').val();
                    if (value !== '') {
                        url += "&" + name + "=" + value;
                    }
                    
                });
                url = "/entity/" + url.replace('&', '?'); // Replaces first &
                
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
                       $('.entityresponsebody').append(pre);
                       prettyPrint();
                       
                       // Add curl statement
                       $('.shell-body').text(getCurlUrlGetDelete(url));
                       $('.shell-body').show();
                    }
                });
            } else {
                // Get data input
                
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
                    url: "/entity/" + url,
                    complete: function(data) {
                       var pre = $('<pre>', {'class': 'prettyprint lang-json'});
                       pre.text(data.responseText);
                       $('.entityresponsebody').append(pre);
                       prettyPrint();
                    }
                });
            }
      
        } catch(err) {
            console.log("Caugh error " +  err.message);
            console.log(err.stack);
        }
        
    });
    
    function getCurlUrlGetDelete(url) {
        var curl=  'curl ';
        curl += '-H "Authorization: Bearer ' + sessionStorage.token +'" ';
        curl += '-H "Accept: application/json" ';
        if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
            curl += '-H "X-Navajo-Instance: ' + sessionStorage.tenant +'" ';
        }
        
        curl += '"' +window.location.origin + encodeURI(url) + '"'
        return curl;
    }
    
    $(document).on('click', '#authbutton', function() {
        if ($(this).hasClass('set')) {
            $('#setauth').hide();
            $(this).removeClass('set');
            $(this).text('Authorize');
            sessionStorage.token = $('#bearertoken').val();
            if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
                sessionStorage.tenant = $('#tenantinput').val();
            }
        } else {
            $('#setauth').show();
            $(this).addClass('set');
            $(this).text('Set');
            if (location.hostname === "localhost" || location.hostname === "127.0.0.1") {
                $('#tenant').show();
            }
        }
        
    });
	
    $(document).on('click', '.callentitybutton', function() {
        if ($(this).hasClass('cancel')) {
            $(this).text("Try it out");
            $(this).removeClass('cancel');
            var parent =  $(this).closest('.entityDescription');
            
            parent.find('.call-entityinput').remove();
            $('.shell-body').hide();
            parent.find('.tableinputheader').remove();
            parent.find('.tableinputtd').remove();
            parent.find('.entityresponsebody').children().remove();
            parent.children('.responsebody').show();
            parent.find('.requestinput').show();
            parent.children('.perform-call-entity').hide();
        } else {
            $(this).text("Cancel");
            $(this).addClass('cancel');
            var parent =  $(this).closest('.entityDescription');
            
            // Hide response
            parent.children('.responsebody').hide();
             
            // Add input to table
            parent.find('.requestTable th:last-child').after('<th class="tableinputheader">Input</th>');
            parent.find('.requestTable tbody td:last-child').after('<td class="tableinputtd"><input type="text"></input></td>');

            parent.children('.perform-call-entity').show();
        }
       
    });
	
	
	
});

