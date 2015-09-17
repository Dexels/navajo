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
        
        var type = tmlTypeToHtml(proptype)
        
        $subtd1.text(propname);
        $subtd1.appendTo($subtr);
        
       
        if (type === 'select') {
            $select = $('<select/>');
            $select.attr('id', getElementXPath(this));
            $select.attr('class', "tmlinput" + type);
            if ($(this).attr('cardinality') !== '1')  {
                $select.attr('multiple', 'multiple')
            }
            if (propdirection != 'in') {
                $input.attr('readOnly', 'readOnly');
            }
            $(this).children('option').each(function() {
                
                $option = $('<option/>');
                $option.attr('value', $(this).attr('value'));
                var selected = $(this).attr('selected');
                if (typeof selected !== typeof undefined && selected === '1') {
                    $option.attr('selected', 'selected');
                }
                $option.text( $(this).attr('name'))
                $option.appendTo($select);
            });
            $select.appendTo($subtd2);
        } else if (type === 'binary') {
            
        } else {
            $input = $('<input/>');
            $input.attr('type', type );
            $input.attr('value', propvalue);
            $input.attr('id', getElementXPath(this));
            $input.attr('class', "tmlinput" + type);
            if (propdirection != 'in') {
                if (type === 'checkbox') {
                    $input.attr('disabled', 'disabled');
                } else {
                    $input.attr('readOnly', 'readOnly');
                }
            }
            $input.appendTo($subtd2);
        }
       
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
    if (tmlType === "selection") {
        return "select";
    }
    if (tmlType === "binary") {
        return "binary";
    }
    
    return "text"
}