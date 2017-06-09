$(document).ready(function() {
    $(document).on('click', 'a', function() {
		$(this).next().filter(".entityDescription").slideToggle(); 
	});
    
    
    $(document).on('click', '.outputFormatJSON', function() {
		$("div.JSON").slideDown(); 
		$("div.XML").slideUp(); 
	});

    $(document).on('click', '.outputFormatXML', function() {
		$("div.JSON").slideUp(); 
		$("div.XML").slideDown(); 
		
	});
	
    $(document).on('click', '.callentitybutton', function() {
        if ($(this).hasClass('cancel')) {
            $(this).text("Try it out");
            $(this).removeClass('cancel');
            var parent =  $(this).closest('.entityDescription');
            
            parent.find('.call-entityinput').remove();
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

