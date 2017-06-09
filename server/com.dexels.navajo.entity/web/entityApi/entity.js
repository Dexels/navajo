$(document).ready(function() {
    prettyPrint();
    
    $(document).on('click', 'a', function(event) {
        event.preventDefault();
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
        var url = $(this).closest('.operation').find('.url').text();
        try {
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
        } catch(err) {
            console.log("Caugh error " +  err.message);
            console.log(err.stack);
        }
        
    });
    
    
    
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
            // Hide requestinput
            parent.find('.requestinput').hide();
            var input = parent.find('.requestinput').text().trim();
            var textinput = $('<textarea>', {'class': 'call-entityinput'});
            textinput.text(input);
            parent.children('.requestbody').append(textinput);
           
            parent.children('.perform-call-entity').show();
        }
       
    });
	
	
	
});

