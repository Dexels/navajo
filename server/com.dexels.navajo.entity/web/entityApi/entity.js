$(document).ready(function() {
	$("a").click(function(event) { 
		event.preventDefault();
		$(this).next().filter(".entityDescription").slideToggle(); 
	});
	
	$(".outputFormatJSON").click(function(event) { 
		event.preventDefault();
		$("div.JSON").slideDown(); 
		$("div.XML").slideUp(); 
	});

	$(".outputFormatXML").click(function(event) { 
		event.preventDefault();
		$("div.JSON").slideUp(); 
		$("div.XML").slideDown(); 
		
	});
	
	
});

