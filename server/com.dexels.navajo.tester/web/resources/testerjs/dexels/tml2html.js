var inputNode = $(document.createElement('input'));

var $propertynamedivElem = $(document.createElement('div'));
var $propertyvaluedivElem = $(document.createElement('div'));
$propertynamedivElem.attr('class', 'propertynamediv');
$propertyvaluedivElem.attr('class', 'propertyvaluediv');


function parseTmlToHtml( navajoelement, methodselement) {
    navajoelement.html('')
    methodselement.html('')
    
    $xml = $( xml ),
    $tml = $xml.children('tml');
    
    var messagesdiv = '<div>'
    
    $tml.children('message').each(function(index){
        messagesdiv += parseTmlMessage($(this));
    });
    messagesdiv += '</div>'
    navajoelement[0].innerHTML = messagesdiv
    
    // Fix input to match their value length
    $(".propertyvaluediv input").each(function() {
        var value = $(this).val();
        var size  = value.length;
        if (size < 30) {
            size= 30;
        }

        $(this).css("width", (size / 2)+'em');
    });
    
    
    var $methods = $(document.createElement('ul'));
    $tml.children('methods').each(function(){
        $(this).children('method').each(function() {
            var $li = $(document.createElement('li'));
            var $div =$(document.createElement('div'));
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
    
    var divString = '<div class="messagediv"><h3> '+arraymessage.attr('name')+'</h3>'
    console.time("array")
    divString += printArrayHorizontal(arraymessage);
    console.timeEnd("array")
    divString += '</div>'
    return divString;    
}

function printArrayHorizontal(arraymessage) {
    
        
    // Store properties in an array to prevent looping over dom element
    var properties = [];
    var arrayName = arraymessage.attr('name');
    arraymessage.children('message[type="array_element"]').each(function(msgindex){
        if (typeof properties[msgindex] === 'undefined' ) {
            properties[msgindex] = [];
        }
        $(this).children('property').each(function(propindex){ 
            properties[msgindex][propindex] = [];
            properties[msgindex][propindex]['value'] = processArrayProperty($(this));
            properties[msgindex][propindex]['name'] = $(this).attr('name');
        });
    });
    
    // Make header table
    var tableString = '<div style="float: left;"> <table class="tmlarraymessagetable"> ';
    // create empty row for array index row
    tableString += '<tr><th ></th></tr>';
    
    // Print table header for each property'
    for (var propindex = 0; propindex < properties[0].length; propindex++) { 
        tableString += '<tr><th >' + properties[0][propindex]['name'] + '</th></tr>';
    }
    tableString += '</table></div> <div style="overflow: auto;"> <table class="tmlarraymessagetable"><tr> ';
    
    // Print message index first as top row
    for (var msgindex = 0; msgindex < properties.length; msgindex++) { 
        tableString += '<th>' + arrayName + '[' + msgindex + '] </th>';
    }
    tableString += '</tr>';
      
    // Iterate over remaining array
    for (var propindex = 0; propindex < properties[0].length; propindex++) { 
        tableString += '<tr>'
            for (var msgindex = 0; msgindex < properties.length; msgindex++) { 
                tableString += '<td>' + properties[msgindex][propindex]['value']  + '</td>';
            }
        tableString += '</tr>';
    }
    tableString += '</table></div>';
    return tableString;
}

function printArrayVertical(arraymessage) {
    // Count the properties an array-element has. We assume the first element contains
    // the same amount as the other elements
  
    var tableString = '<table class="tmlarraymessagetable"> '
        
    arraymessage.children('message[type="array_element"]').each(function(index){
        // TODO: support for messages in array
        tableString += '<tr>'
        if (index == 0) {
            tableString += '<th>' + $(this).attr('name') + '</th>';
        }
        
        $(this).children('property').each(function() {
            tableString += '<td>' + processArrayProperty($(this)) + '</td>';
        });
        tableString += '</tr>';
    });

    tableString += '</table>';
    return tableString;
}


function parseTmlMessage(message) {
    var name =  message.attr('name');
    var type = message.attr('type');
    if (type === 'array') {
        return parseTmlArrayMessage(message);
    }
    var divString = '<div class="messagediv"><h3> '+name+'</h3>'

    message.children('message').each(function(index){
        divString += parseTmlMessage($(this));
    });
    
    // Add all my properties
    message.children('property').each(function() {
        divString += processProperty($(this));        
    });
    divString += '</div>';
    return divString;
}

function processProperty(property) {
    // Use strings for performance
    var propertyString = '<div class="propertydiv"><div class="propertynamediv">'   
    
    propertyString += property.attr('name');
    propertyString += '</div><div class="propertyvaluediv">'
    
    var propvalue = property.attr('value');
    var propdirection = property.attr('direction')
    var proptype = property.attr('type')
    var htmltype = tmlTypeToHtml(proptype)

    // TODO: property description
   
    if (htmltype === 'select') {
        propertyString += '<select ';
        if (property.attr('cardinality') !== '1')  {
            propertyString += 'multiple="multiple" ';
        }
        if (propdirection !== "" && propdirection.indexOf('in') < 1 ) {
            propertyString += 'readOnly="readOnly" ';
        } else {
            propertyString += 'class="tmlinput' + htmltype + '" ';
            propertyString += 'id="'+getElementXPath(property[0])+'" >';
        }
        property.children('option').each(function() {
            propertyString += '<option value="'+$(this).attr('value')+'" '
            var selected = $(this).attr('selected');
            if (typeof selected !== typeof undefined && selected === '1') {
                propertyString += 'selected="selected"';
            }
            propertyString += '>' +  $(this).attr('name') + '</option>'
        });
        propertyString += '</select>';
    } else if (htmltype === 'binary') {
        // TODO: display binary somehow
    } else {
        propertyString += '<input type="'+htmltype+'" value="'+propvalue+'" ';

        if (propdirection !== "" && propdirection.indexOf('in') < 1) {
            if (htmltype === 'checkbox') {
                propertyString += ' disabled="disabled" '
            } else {
                propertyString += ' readOnly="readOnly" '
            }
        } else {
         // This is only needed if the element can be changed
            propertyString += ' class="tmlinput' + htmltype + '" '
            propertyString += ' id="' + getElementXPath(property[0]) + '"'

        }
    }
    propertyString += '></div></div>'
    return propertyString;
}


function processArrayProperty(property) {
    // Use strings for performance
    var propertyString = '<div class="propertydiv">'   
    
    propertyString += '<div class="propertyvaluediv">'
    
    var propvalue = property.attr('value');
    var propdirection = property.attr('direction')
    var proptype = property.attr('type')
    var htmltype = tmlTypeToHtml(proptype)

    // TODO: property description
   
    if (htmltype === 'select') {
        propertyString += '<select ';
        if (property.attr('cardinality') !== '1')  {
            propertyString += 'multiple="multiple" ';
        }
        if (propdirection != 'in') {
            propertyString += 'readOnly="readOnly" ';
        } else {
            propertyString += 'class="tmlinput' + htmltype + '" ';
            propertyString += 'id="'+getElementXPath(property[0])+'" >';
        }
        property.children('option').each(function() {
            propertyString += '<option value="'+$(this).attr('value')+'" '
            var selected = $(this).attr('selected');
            if (typeof selected !== typeof undefined && selected === '1') {
                propertyString += 'selected="selected"';
            }
            propertyString += '>' +  $(this).attr('name') + '</option>'
        });
        propertyString += '</select>';
    } else if (htmltype === 'binary') {
        // TODO: display binary somehow
    } else {
        propertyString += '<input type="'+htmltype+'" value="'+propvalue+'" ';

        if (propdirection != 'in') {
            if (htmltype === 'checkbox') {
                propertyString += ' disabled="disabled" '
            } else {
                propertyString += ' readOnly="readOnly" '
            }
        } else {
         // This is only needed if the element can be changed
            propertyString += ' class="tmlinput' + htmltype + '" '
            propertyString += ' id="' + getElementXPath(property[0]) + '"'

        }
    }
    propertyString += '></div></div>'
    return propertyString;
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