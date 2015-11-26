
function getCsvContent(divelement) {
    
    var csvData = '';
    var xpath = divelement.attr('id');
    var element = $(xml).xpath(xpath)[0];
   

    if (typeof element != 'undefined') {
        var $element = $(element);
        
        // If we have a definition message, this is put in the header row
        $element.children('message[type="definition"]').each(function() {
            var row = [];
            $(this).children('property').each(function() {
                row.push($(this).attr('name'));
            });
            csvData += row.join(",") + "\n"
        });
           
        // Loop over the array_elements
        $element.children('message[type="array_element"]').each(function() {
            var row = [];
            // go over properties
            $(this).children('property').each(function() {
                var proptype = $(this).attr('type')
                if (proptype === 'selection') {
                    var hasselection = false;
                    property.children('option').each(function() {
                        var selected = $(this).attr('selected');
                        if (selected) {
                            row.push($(this).attr('name'));
                            hasselection = true;
                        }
                    });
                    if (!hasselection) {
                        row.push("");
                    }
                } else {
                    row.push($(this).attr('value'));
                }
               
               
            })
            csvData += row.join(",") + "\n"
        }); 
    }

   return new Blob([csvData],{type: "text/csv;charset=utf-8;"});

    
}
