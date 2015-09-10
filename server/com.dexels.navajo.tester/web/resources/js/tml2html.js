function parseTmlToHtml( navajoelement, methodselement, tmlString) {
    navajoelement.html('')
    methodselement.html('')
    
    var xml = $.parseXML(tmlString),
    $xml = $( xml ),
    $tml = $xml.children('tml');
    
    var html = '<ul>';
    $tml.children('message').each(function(index){
        html += parseTmlMessage($(this));
    });
    html +='</ul>';
    navajoelement.html(html);
    
    var methodshtml = '<ul>';
    $tml.children('methods').each(function(){
        $(this).children('method').each(function() {
            methodshtml += '<li> <div id="';
            methodshtml += $(this).attr('name');
            methodshtml += '"class="script">';
            methodshtml += $(this).attr('name');
            methodshtml += '</div></li>';
        });
    });
    methodshtml +='</ul>';
    methodselement.html(methodshtml);
}

function parseTmlMessage(message) {
    var name =  message.attr('name');
    
    var html = '<li>';
    html += ' <b>' + name + '</b>';
    
    
    var properties = message.children('property')
    html += '<ul>'
    properties.each(function(index){
        var propname = $(this).attr('name');
        var propvalue = $(this).attr('value');
        html +='<li>';
        html +=propname;
        html +=': ';
        html +=propvalue;
        html +='</li>';
    });
    
    var submessages = message.children('message')
    
    submessages.each(function(index){
        console.log("In "+ name + " child found: " + $(this).attr('name'))
       
        html += parseTmlMessage($(this));
      
    });
    html += '</ul>'
    html += '</li>'
 
    return html;
}