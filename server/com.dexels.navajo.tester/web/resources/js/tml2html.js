function parseTmlToHtml( element, tmlString) {
    element.html('')
    
    var xml = $.parseXML(tmlString),
    $xml = $( xml ),
    $messages = $xml.find('message');
    
    $messages.each(function(index){
       var name =  $(this).attr('name');
       var properties = $(this).find('property')
       var html = '';
       html += '<ul>';
       html += name;
       html += '<ul>';
       properties.each(function(index){
           var propname = $(this).attr('name');
           var propvalue = $(this).attr('value');
           html +='<li>';
           html +=propname;
           html +=': ';
           html +=propvalue;
           html +='</li>';
       });
       html += '</ul>'
       html += '</ul>'
       element.append(html)
    });
}