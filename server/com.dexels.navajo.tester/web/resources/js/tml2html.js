function parseTmlToHtml( element, tmlString) {
    element.html('')
    
    var xml = $.parseXML(tmlString),
    $xml = $( xml ),
    $tml = $xml.children('tml');
    
    var html = '<ul>';
    $tml.children('message').each(function(index){
        html += parseTmlMessage($(this));
    });
    html +='</ul>';
    element.html(html);
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