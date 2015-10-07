// Holds the input navajo document for the next RPC call
var xml = $.parseXML('<tml documentImplementation="SAXP"><header><transaction rpc_usr="" rpc_name="" rpc_pwd=""/> </header></tml>');;

function getScripts() {
    var scriptssource = $("#scripts-template").html();
    var scriptstemplate = Handlebars.compile(scriptssource);

    var foldersource = $("#folder-template").html();
    var foldertemplate = Handlebars.compile(foldersource);

    Handlebars.registerPartial('subscripts', foldertemplate);

    $.getJSON("/testerapi?query=getscripts", function(data) {
        sortFileObject(data)
        $("#scripts").html(scriptstemplate(data));
    });

};

function sortFileObject(element) {
    $.each(element, function(index, subelem) {
        if (subelem.type  === 'FOLDER') {
           sortFileObject(subelem.entries);
            
        }
    });
  
    element.sort(function(a, b) {
        return a.name.localeCompare(b.name);
    });
}

function processLoginForm(){
    hideLoginTable();
    sessionStorage.instance = $( "#handlers option:selected" ).text()
    sessionStorage.user =     $('#navajousername').val();
    sessionStorage.password = $('#navajopassword').val();
    
    $('#navajopassword').val('');
    
    if (sessionStorage.script && !loginTableVisible()) {
        runScript(sessionStorage.script);
    }
    
    return true;
}

function loginTableVisible() {
    var instance =  $( "#handlers option:selected" ).text();
    return (instance === "" || !sessionStorage.user) 
}

function updateInstanceHandlers() {
    if (!sessionStorage.instance) {
        return;
    }
    $('#handlers').val(sessionStorage.instance);
    $('#handlers').trigger("chosen:updated")
    
}

function showLoginTable() {
    $('#loginform').show();
    $('#showLessArrow').show();
    $('#showMoreArrow').hide();
}

function hideLoginTable() {
    $('#loginform').hide();
    $('#showLessArrow').hide();
    $('#showMoreArrow').show();
}

function runScript(script) {
   
    $('#loadedScript').text(script);
    
    if (loginTableVisible()) {
        showLoginTable();
       
        $('.LoginButton').attr('value', 'Run script');
        $('#logintable').trigger('startRumble');
        setTimeout(function(){$('#logintable').trigger('stopRumble');}, 750);
        return;
    }
    
    var instance =  $( "#handlers option:selected" ).text();
    hourglassOn();
    $('.overlay').show();
    
    // If we have sourcefile visible, show HTML page. Otherwise leave it
    if ($('#TMLSourceview').is(":visible")) {
        $('#TMLSourceview').hide();
        $('#HTMLview').show();
    }

    navajoinput = prepareInputNavajo(script);
     
    
    $.post("/navajo/" + instance , navajoinput, function(xmlobj) {
        xml = xmlobj;
        $('#scriptcontent').removeClass('prettyprinted');
        var xmltext = (new XMLSerializer()).serializeToString(xmlobj)
        $('#scriptcontent').text(xmltext)
        prettyPrint();
        parseTmlToHtml($('#HTMLview'), $('#methods'));
       
        $('.overlay').hide(200);
        $('#scriptMainView').show();
        hourglassOff();
        $('html, body').animate({
            scrollTop : 0
        }, 50);

    });

    $.get("/testerapi?query=getfilecontent&file=" + script, function(data) {
        $('#scriptsourcecontent').removeClass('prettyprinted');
        $('#scriptsourcecontent').text(data)
        prettyPrint();
    });
}

function hourglassOn() {
    if ($('style:contains("html.wait")').length < 1) $('<style>').text('html.wait, html.wait * { cursor: wait !important; }').appendTo('head');
    $('html').addClass('wait');
}

function hourglassOff() {
    $('html').removeClass('wait');
}


function prepareInputNavajo(script) {
    var $xml = $(xml);
    var $transaction = $xml.find('tml header transaction')
   
    $transaction.attr('rpc_name', script);
    $transaction.attr('rpc_usr', sessionStorage.user);
    $transaction.attr('rpc_pwd', sessionStorage.password)
    
    return (new XMLSerializer()).serializeToString(xml);
}

function updateVisibility(filter, element) {
    var anyMatch = false;

    element.children('li').each(function() {
        var scriptid = $(this).children('div').first().attr('id');
        var className = $(this).children('div').first().attr('class');

        var match = scriptid.search(new RegExp(filter, "i"));
        var isFolder = className === 'folder';

        if (isFolder) {
            // First check for text search
            var match = $(this).text().search(new RegExp(filter, "i"));
            if (match < 0) {
                // no need to check children at all
                $(this).hide()
            } else {
                var childHasMatches = updateVisibility(filter, $(this).children('ul').first());
                if (childHasMatches) {
                    $(this).show()
                    anyMatch = true;
                } else {
                    $(this).hide()
                }
            }

        } else {
            if (match < 0) {
                $(this).hide()
            } else {
                $(this).show()
                anyMatch = true;
            }
        }
    });
    return anyMatch;
}

function getMyEntries(data, element) {
    if (element.parents('.folder').length < 1) {
        var itemsIndex = data.indexOfPath(element.attr('id'));
        var subEntries = data[itemsIndex].entries;
        subEntries.sort(function(a, b) {
            return a.name.localeCompare(b.name);
        });
        return subEntries;
    } else {
        // Find my parents entries and check him
        var myParent = element.parents('.folder').first();
        var myParentEntries = getMyEntries(data, myParent);
        var itemsIndex = myParentEntries.indexOfPath(element.attr('id'));
        var subEntries = myParentEntries[itemsIndex].entries;
        subEntries.sort(function(a, b) {
            return a.name.localeCompare(b.name);
        });
        return subEntries;

    }
};

/* Event handlers */

$(document).on('click', '.script', function() {
    runScript($(this).attr("id"));
});

$(document).on('click', '.folder', function() {
    $(this).next().children('ul li').toggle();
});

$(document).on('click', '.folder', function(e) {
    if ($(this).children().length > 0) {
        $(this).children().remove();
    } else {
        // Find higest element
        var entries = getMyEntries(data, $(this))
        $(this).append(foldertemplate(entries));
    }
    e.stopPropagation();
});

$(document).on('click', '#showMoreArrow', function() {
    $('#loginform').show(200);
    $('#showLessArrow').show();
    $('#showMoreArrow').hide();
})

$(document).on('click', '#showLessArrow', function() {
    $('#loginform').hide(200);
    $('#showLessArrow').hide();
    $('#showMoreArrow').show();
})


$(document).on('click', '#HTMLviewLink', function() {
    $('#HTMLview').show(100);
    $('#TMLview').hide(100);
    $('#TMLSourceview').hide(100);
    return false;
});

$(document).on('click', '#TMLviewLink', function() {
    $('#HTMLview').hide(100);
    $('#TMLview').show(100);
    $('#TMLSourceview').hide(100);
    return false;
});

$(document).on('click', '#TMLSourceviewLink', function() {
    $('#HTMLview').hide(100);
    $('#TMLview').hide(100);
    $('#TMLSourceview').show(100);
    return false;
});

$(document).on('input propertychange', '#scriptsFilter', function(evt) {
    // If it's the propertychange event, make sure it's the value that
    // changed.
    if (window.event && event.type == "propertychange" && event.propertyName != "value")
        return;

    
    // Clear any previously set timer before setting a fresh one
    window.clearTimeout($(this).data("timeout"));
    $(this).data("timeout", setTimeout(function() {
        var filter = $("#scriptsFilter").val();
        if (filter.length == 0) {
            getScripts();
            return;
        }
        
        if (filter.length < 3) 
            return;
        
        updateVisibility(filter, $(".scripts"))
    }, 300));
});

// TML change events
$(document).on('input propertychange', '.tmlinputtext', function(evt) {
    // If it's the propertychange event, make sure it's the value that changed.
    if (window.event && event.type == "propertychange" && event.propertyName != "value")
        return;
    var xpath = $(this).attr('id');
    
    var element = xml.evaluate( xpath, xml, null, XPathResult.ANY_UNORDERED_NODE_TYPE  , null ).singleNodeValue;
   
    if (typeof element != 'undefined') {
        var $element = $(element);
        $element.attr('value',  $(this).val());
    } 
});



$(document).on('input change', '.tmlinputcheckbox', function(evt) {
    var xpath = $(this).attr('id');
    var element = xml.evaluate( xpath, xml, null, XPathResult.ANY_UNORDERED_NODE_TYPE  , null ).singleNodeValue;
    if (typeof element != 'undefined') {
        var $element = $(element);
        $element.attr('value',  $(this).prop('checked'));
    } 
});

$(document).on('input change', '.tmlinputselect', function(evt) {
    var  xpath = $(this).attr('id');
    var $input = $(this);
    var element = xml.evaluate( xpath, xml, null, XPathResult.ANY_UNORDERED_NODE_TYPE  , null ).singleNodeValue;
   
    
    if (typeof element != 'undefined') {
        var $element = $(element);
        $element.children('option').each(function() {
            // find option under current input
            var value = $(this).attr('value');
            
            // See whether the option with this name is now selected
            var isChecked = $input.children('option[value="'+value+'"]').first().prop('selected');
            if (isChecked) {
                $(this).attr('selected', 1);
            } else {
                $(this).attr('selected', 0);
            }   
        }); 
    } 
});


var encodedUri;
var link;
$(document).on('click', '.exportcsv', function() {
    var filename = $(this).children('h3').text();
    console.log(filename);
    if (typeof filename === 'undefined') {
        filename = 'export';
    }
    
    var csvContent = getCsvContent($(this));
    encodedUri = encodeURI(csvContent);
    link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "export" + filename + ".csv");
    document.body.appendChild(link);
    link.click(); // This will download the data file named "my_data.csv".
    
    //document.body.removeChild(link);
});

function getCsvContent(divelement) {
    var csvData = [];
    var defdata = [];
    var  xpath = divelement.attr('id');
    var  element = xml.evaluate( xpath, xml, null, XPathResult.ANY_UNORDERED_NODE_TYPE  , null ).singleNodeValue;
   
    if (typeof element != 'undefined') {
        var $element = $(element);
        
        // If we have a definition message, this is put in the header row
        $element.children('message[type="definition"]').each(function() {
            $(this).children('property').each(function() {
                defdata.push($(this).attr('name'));
            });
        });
           
        // Loop over the array_elements
        $element.children('message[type="array_element"]').each(function() {
            var row = [];
            // go over properties
            $(this).children('property').each(function() {
                var proptype = $(this).attr('type')
                if (proptype === 'selection') {
                    property.children('option').each(function() {
                        var selected = $(this).attr('selected');
                        if (selected) {
                            row.push($(this).attr('name'));
                        }
                    });
                } else {
                    row.push($(this).attr('value'));
                }
               
               
            })
            csvData.push(row);
        }); 
    }

    
    var csvContent = "data:text/csv;charset=utf-8,";
    csvContent += defdata.join(",");
    csvContent += "\n"
    csvData.forEach(function(infoArray, index){
       dataString = infoArray.join(";");
       csvContent += dataString+ "\n";
    }); 

    return csvContent;
}

/* Some helpers */
Array.prototype.indexOfPath = function(path) {
    for (var i = 0; i < this.length; i++)
        if (this[i].path === path)
            return i;
    return -1;
}