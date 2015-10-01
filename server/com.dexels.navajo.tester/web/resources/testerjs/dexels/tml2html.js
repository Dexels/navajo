function parseTmlToHtml( navajoelement, methodselement) {
    navajoelement.html('')
    methodselement.html('')
    
    $xml = $( xml ),
    $tml = $xml.children('tml');
    
    var $messagesdiv = $('<div/>');
    
    $tml.children('message').each(function(index){
        var $messagediv = parseTmlMessage($(this))
        $messagediv.appendTo($messagesdiv);
    });
    $messagesdiv.appendTo(navajoelement);
    
    // Fix input to match their value length
    $(".propertyvaluediv input").each(function() {
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
}

function parseTmlArrayMessage(arraymessage) {
    var name =  arraymessage.attr('name');
    var $div = $('<div />');
    $div.attr('class', 'messagediv');
    
    var $msgname = $('<h3 />');
    $msgname.text(name);
    $msgname.appendTo($div);
    
    var $table = $('<table />');
    
    // Count the properties an array-element has. We assume the first element contains
    // the same amount as the other elements
    var propcount = arraymessage.find('message[type="array_element"]:eq(0) property').length;
    
    for (var i = 0; i < propcount; i++) { 
        var $tr = $('<tr />');
        arraymessage.children('message[type="array_element"]').each(function(index){
            $(this).children('property:eq('+i+')').each(function() {
                if (index == 0) {
                    var $th = $('<th>')
                    $th.text($(this).attr('name'));
                    $th.appendTo($tr);
                    
                }
                var $propertydiv = processProperty($(this));
                $propertydiv.find('div[class="propertynamediv"]').remove();
                var $td = $('<td />');
                
                $propertydiv.appendTo($td);
                $td.appendTo($tr);
               
                
            });
        });
        $tr.appendTo($table);
    }
    
   
    $table.appendTo($div);
    
    return $div;
    
    
}

function parseTmlMessage(message) {
    var name =  message.attr('name');
    var type = message.attr('type');
    if (type === 'array') {
        return parseTmlArrayMessage(message);
    }
    var $div = $('<div />');
    $div.attr('class', 'messagediv');
    
    var $msgname = $('<h3 />');
    $msgname.text(name);
    $msgname.appendTo($div);
    
    message.children('message').each(function(index){
        var $messagediv = parseTmlMessage($(this));
        $messagediv.appendTo($div);
    });
    
    // Add all my properties
    message.children('property').each(function() {
        var $propertydiv = processProperty($(this));
        $propertydiv.appendTo($div);
        
    });
   

   

    return $div;
}

function processProperty(property) {
    var $propertynamediv = $('<div />');
    var $propertyvaluediv = $('<div />');
    $propertynamediv.attr('class', 'propertynamediv');
    $propertyvaluediv.attr('class', 'propertyvaluediv');
    
    $propertynamediv.text(property.attr('name'));

    var propvalue = property.attr('value');
    var propdirection = property.attr('direction')
    var proptype = property.attr('type')
    var htmltype = tmlTypeToHtml(proptype)
    
    // TODO: property description
   
    if (htmltype === 'select') {
        $select = $('<select/>');
        $select.attr('id', getElementXPath(property[0]));
        $select.attr('class', "tmlinput" + htmltype);
        if (property.attr('cardinality') !== '1')  {
            $select.attr('multiple', 'multiple')
        }
        if (propdirection != 'in') {
            $input.attr('readOnly', 'readOnly');
        }
        property.children('option').each(function() {
            
            $option = $('<option/>');
            $option.attr('value', $(this).attr('value'));
            var selected = $(this).attr('selected');
            if (typeof selected !== typeof undefined && selected === '1') {
                $option.attr('selected', 'selected');
            }
            $option.text( $(this).attr('name'))
            $option.appendTo($select);
        });
        $select.appendTo($propertyvaluediv);
    } else if (htmltype === 'binary') {
        // TODO: display binary somehow
    } else {
        $input = $('<input/>');
        $input.attr('type', htmltype );
        $input.attr('value', propvalue);
        $input.attr('id', getElementXPath(property[0]));
        $input.attr('class', "tmlinput" + htmltype);
        if (propdirection != 'in') {
            if (htmltype === 'checkbox') {
                $input.attr('disabled', 'disabled');
            } else {
                $input.attr('readOnly', 'readOnly');
            }
        }
        $input.appendTo($propertyvaluediv);
    }
    
    var $propertydiv = $('<div />');
    $propertydiv.attr('class', 'propertydiv');

    
    $propertynamediv.appendTo($propertydiv);
    $propertyvaluediv.appendTo($propertydiv);
    
    return $propertydiv;
}

function tmlTypeToHtml(tmlType) {
    if (tmlType === "string") {
        return "text";
    }
    
    if (tmlType === "boolean") {
        return "checkbox";
    }
    if (tmlType === "selection") {
        return "select";
    }
    if (tmlType === "binary") {
        return "binary";
    }
    
    return "text"
}