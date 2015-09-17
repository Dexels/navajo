function parseTmlToHtml( navajoelement, methodselement, tmlString) {
    navajoelement.html('')
    methodselement.html('')
    
    xml = $.parseXML(tmlString),
    $xml = $( xml ),
    $tml = $xml.children('tml');
    
    var $table = $('<table/>');
    
    $tml.children('message').each(function(index){
        var $row = parseTmlMessage($(this))
        $row.appendTo($table);
    });
    $table.appendTo(navajoelement);
    
    
    $("td input").each(function() {
        var value = $(this).val();
        var size  = value.length;
        if (size < 30) {
            size= 30;
        }

        $(this).css("width", (size / 2)+'em');
    });
    
    
    var $methods = $('<ul>');
    $tml.children('methods').each(function(){
        $(this).children('method').each(function() {
            var $li = $('<li/>');
            var $div = $('<div/>');
            $div.attr('id', $(this).attr('name'));
            $div.attr('class', "script");
            $div.text($(this).attr('name'));
            $div.appendTo($li);
            $li.appendTo($methods)
        });
    });
    $methods.appendTo(methodselement);
    
    // Add change events
    $(".tmlinput").bind("input propertychange", function (evt) {
        // If it's the propertychange event, make sure it's the value that changed.
        if (window.event && event.type == "propertychange" && event.propertyName != "value")
            return;
        var xpath = $(this).attr('id');
        
        var element = xml.evaluate( xpath, xml, null, XPathResult.ANY_UNORDERED_NODE_TYPE  , null ).singleNodeValue;
        console.log(element);
        if (typeof element == 'undefined') {
            var str = (new XMLSerializer()).serializeToString(xml);
            console.log("No element found with " + xpath + " in " + str)
        } else {
            var $element = $(element);
            $element.attr('value',  $(this).val());
        }
    });
}



function parseTmlMessage(message) {
    var name =  message.attr('name');
    var $tr = $('<tr />');
    var $td1 = $('<td />')
    var $td2 = $('<td />')
    
    $td1.text(name)
    $td1.appendTo($tr);
    
    var $subtable = $('<table />');
    
    message.children('property').each(function() {
        var $subtr = $('<tr />');
        var $subtd1 = $('<td/>');
        var $subtd2 = $('<td/>');
        
        var propname = $(this).attr('name');
        var propvalue = $(this).attr('value');
        var propdirection = $(this).attr('direction')
        var proptype = $(this).attr('type')
                
        
        $subtd1.text(propname);
        $subtd1.appendTo($subtr);
        
        $input = $('<input/>');
        $input.attr('type', tmlTypeToHtml(proptype));
        $input.attr('value', propvalue);
        $input.attr('id', getElementXPath(this));
        $input.attr('class', "tmlinput");
        if (propdirection != 'in') {
            $input.prop('disabled', true);
        }
        $input.appendTo($subtd2);
        $subtd2.appendTo($subtr);
       
        $subtr.appendTo($subtable);
    });
   
    
    var submessages = message.children('message')    
    submessages.each(function(index){
        var $subrow = parseTmlMessage($(this));
        $subrow.appendTo($subtable);
    });
    $subtable.appendTo($td2);
    $td2.appendTo($tr);
    return $tr;
}

function tmlTypeToHtml(tmlType) {
    if (tmlType === "string") {
        return "text";
    }
    
    if (tmlType === "boolean") {
        return "checkbox";
    }
    return "text"
}
