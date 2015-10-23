"use strict"

// Holds the input navajo document for the next RPC call
var xml = $.parseXML('<tml documentImplementation="SAXP"><header><transaction rpc_usr="" rpc_name="" rpc_pwd=""/> </header></tml>');
var serializer = new XMLSerializer();
var hooverdiv = '<div class="customRunOption">';
hooverdiv += '  <div class="scriptcompile">Compile</div>';
hooverdiv += '  <div class="scriptsource">Source</div>';
hooverdiv += '  <div class="scriptinput">Custom Input</div>';
hooverdiv += '</div>';

function getScripts() {
    $("#scripts").html("");
    var scriptssource = $("#scripts-template").html();
    var scriptstemplate = Handlebars.compile(scriptssource);

    var foldersource = $("#folder-template").html();
    var foldertemplate = Handlebars.compile(foldersource);

    Handlebars.registerPartial('subscripts', foldertemplate);
    try {
        $.ajax({
            dataType: "json",
            url: "/testerapi?query=getscripts",
            success: function(data) {
                sortFileObject(data)
                $("#scripts").html(scriptstemplate(data));
                
                $(".scriptli").hoverIntent({
                    over: function() {
                        $(this).append(hooverdiv);
                     },
                     out: function() {
                         $(this).parent().find('.customRunOption').remove();
                     }, 
                     interval: 400
                });

            },
            error: function () {
                $("#scripts").html("Error getting scripts - retrying in a few seconds...");
                setTimeout( 
                        function(){
                            $("#scripts").html("");
                        }, 2000  );
                setTimeout( function(){
                        getScripts()
                    }, 3000 );
                
            }
            
        });
          
        
    } catch (err) {
        $("#scripts").html("Error from server - retrying in a few seconds...");
        setTimeout( function(){getScripts()}, 3000 );
    }
    

};

function sortFileObject(element) {
    $.each(element, function(index, subelem) {
        if (subelem.type  === 'FOLDER') {
           sortFileObject(subelem.entries);
        }
    });
  
    element.sort(function(a, b) {
        if (a.type === 'FILE' && b.type === 'FOLDER') {
            return 1;
        }
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
        var match = false;
        // See if the current url matches one of the handlers. If so, we use
        // that as default handler
        $('#handlers option').each(function(index, option) {
            var optionValue = $(option).attr('value');
            if (window.location.href.toLowerCase().indexOf(optionValue.toLowerCase()) > -1) {
                sessionStorage.instance = optionValue;
                match = true;
            }
        });
        if (!match) {
            return;
        }
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
    $('#scriptCustomInputView').hide();
    $('#loadedScript').text(script);
    $('html, body').animate({
        scrollTop : 0
    }, 50);
    
    if (loginTableVisible()) {
        showLoginTable();
       
        $('.LoginButton').attr('value', 'Run script');
        $('#logintable').trigger('startRumble');
        setTimeout(function(){$('#logintable').trigger('stopRumble');}, 750);
        return;
    }
    
    var instance =  $( "#handlers option:selected" ).text();
    try {
        hourglassOn();
        $('.overlay').show();
        
        // If we have sourcefile visible, show HTML page. Otherwise leave it
        if ($('#TMLSourceview').is(":visible")) {
            $('#TMLSourceview').hide();
            $('#HTMLview').show();
        }

        var navajoinput = prepareInputNavajo(script);
        
        $.ajax({
            type: "POST",
            url: "/navajo/" + instance,
            data: navajoinput,
            success: function(xmlObj) {
                replaceXml(script, xmlObj);
                var stateObj = { script: script, xml:  serializer.serializeToString(xml) };
                history.pushState(stateObj, script, "tester.html?script="+script);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                $('#HTMLview')[0].innerHTML = "Error on running script: <br/><br/>" + xhr.responseText; 
                $('#scriptMainView').show();
                $('.overlay').hide();
                hourglassOff();
            }
        });
    } catch(err) {
        $('#HTMLview')[0].innerHTML = "Error on running script: " + err.message;
        $('#scriptMainView').show();
        $('.overlay').hide();
        hourglassOff();
    }
    
    $.get("/testerapi?query=getfilecontent&file=" + script, function(data) {
        $('#scriptsourcecontent').removeClass('prettyprinted');
        $('#scriptsourcecontent').text(data)
        prettyPrint();
    });
}

function replaceXml(script, xmlObj) {
    try {
        xml = xmlObj;
        $('#scriptcontent').removeClass('prettyprinted');
        var xmltext = serializer.serializeToString(xmlObj)
        $('#scriptcontent').text(xmltext)
        prettyPrint();
        parseTmlToHtml(script, $('#HTMLview'), $('#methods'));
        
        
       
        $('.overlay').hide(200);
        $('#scriptMainView').show();
        $('#scriptheader').text(script);
        hourglassOff();
    } catch (err) {
        console.log("Caugh error " +  err.message);
        $('#HTMLview')[0].innerHTML = "Error on running script: " + err.message;
        $('#scriptMainView').show();
        $('.overlay').hide();
        hourglassOff();
    }
    
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
    
    return serializer.serializeToString(xml);
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
    var script =  $('#loadedScript').text();
    var stateObj = {script: script,  xml:  serializer.serializeToString(xml) };
    history.replaceState(stateObj, script, "tester.html?script=" + script);
    
    
    runScript($(this).attr("id"));
});

$(document).on('click', '.folder', function() {
    $(this).next().children('ul li').toggle();
});


$(document).on('click', '.scriptcompile', function() {
    var script = $(this).parent().parent().children('.script').attr('id');
    hourglassOn();
    
    $.ajax({
        type: "GET",
        url: "/compile?script=" + script,
        dataType: "text",
        success: function() {
            $('.customRunOption').html('<p>OK</p>');
            hourglassOff();
            setTimeout( function(){
                        $('.customRunOption').remove();
                        }, 1000  
                    );  
        }
    }); 
    
});

$(document).on('click', '.scriptinput', function() {
    var script = $(this).parent().parent().children('.script').attr('id');

    $('html, body').animate({
        scrollTop : 0
    }, 50);
    $('#loadedScript').text(script);
    $('#scriptheader').text(script);
    
    $('#scriptMainView').hide();
    $('#scriptCustomInputView').show();
    
    
});

$(document).on('click', '.scriptsource', function() {
    var script = $(this).parent().parent().children('.script').attr('id');
    hourglassOn();
    $('html, body').animate({
        scrollTop : 0
    }, 50);
    
    $.get("/testerapi?query=getfilecontent&file=" + script, function(data) {
        $('#scriptsourcecontent').removeClass('prettyprinted');
        $('#scriptsourcecontent').text(data)
        prettyPrint();
        $('#scriptheader').text(script);
        $('#scriptMainView').show();
        $('#TMLSourceviewLink').click();
        hourglassOff();
    });
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


$(document).on('input change', '.custominputtype', function(evt) {
    var value = $('.custominputtype:checked').val();
    if (value === "JSON") {
        $('#CustomInputRunButton').val("Convert to TML");
    } else {
        $('#CustomInputRunButton').val("Run Script");
    }
});


$(document).on('click', '#CustomInputRunButton', function() {
    // Going to run loaded script with custom input...
    var inputtype = $('.custominputtype:checked').val();
    var inputString = $('#customInputText').val();
    if (inputtype === "JSON") {
        try {
            var inputXml = convertJsonToTml(inputString);
            $(this).val("Run script");
            $('#customInputText').val(inputXml);
            $('.custominputtype[value="TML"]').prop("checked", true)
        } catch(err) {
            window.alert("Error parsing JSON:\n\n "+  err.message);
            return;
        }
     
        return;
    } 
    
   
    var xmlStringStart = '<tml documentImplementation="SAXP"><header><transaction rpc_usr="" rpc_name="" rpc_pwd=""/> </header>';
    var xmlStringEnd = '</tml>';
    var inputXml =  inputString;

    try {
        xml = $.parseXML(xmlStringStart + inputXml + xmlStringEnd);
    } catch(err) {
        window.alert("Error parsing XML:\n\n "+  err.message);
        return;
    }
    
    var script = $('#loadedScript').text();
    runScript(script)
});

function convertJsonToTml(jsonString) {
    var jsonObj = JSON.parse(jsonString);
    console.log(jsonObj);
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === "object") {
            xmlString += '<message name="' + key + '">\n';
            xmlString += jsonObjToTml(value);
            xmlString += '</message>\n';
        } else {
            xmlString += '    <property name="' + key + '" value="'+value+'" />\n'
        }
    });
    return xmlString;
}

function jsonObjToTml(jsonObj) {
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === "object") {
            xmlString += '    <message name="' + key + '">\n';
            xmlString += jsonObjToTml(value);
            xmlString += '    </message>\n';
        } else {
            xmlString += '        <property name="' + key + '" value="'+value+'" />\n'
        }
    });
    return xmlString;
}


$(document).on('click', '.messagediv h3', function() {
    $(this).closest('.messagediv').children('div').each(function() {
        if ($(this).attr('class') !== 'exportcsv') {
            $(this).toggle();
        }
        
    });
    return false;
});


$(document).on('input propertychange', '#scriptsFilter', function(evt) {
    // If it's the propertychange event, make sure it's the value that changed.
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
    var element = $(xml).xpath(xpath)[0];
    if (typeof element != 'undefined') {
        var $element = $(element);
        $element.attr('value',  $(this).val());
    } 
});



$(document).on('input change', '.tmlinputcheckbox', function(evt) {
    var xpath = $(this).attr('id');
    var element = $(xml).xpath(xpath)[0];
    if (typeof element != 'undefined') {
        var $element = $(element);
        $element.attr('value',  $(this).prop('checked'));
    } 
});

$(document).on('input change', '.tmlinputselect', function(evt) {
    var  xpath = $(this).attr('id');
    var $input = $(this);
    var element = $(xml).xpath(xpath)[0];
   
    
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


window.onpopstate = function(event) {
    if (!event.state) {
        console.log('clear page')
    } else {
        replaceXml(event.state.script, $.parseXML(event.state.xml));
    }
};


$(document).on('click', '.exportcsv', function() {
    var filename = $(this).children('h3').text().trim();
   
    if (typeof filename === 'undefined') {
        filename = 'export.csv';
    } else {
        filename =  "export" + filename + ".csv";
    }

    var blob = getCsvContent($(this));
   
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename)
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { 
            //  Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }  
    }
});

$(document).on('click', '.tmlbinary', function() {
    var filename = $(this).attr('filename');
    if (typeof filename === 'undefined') {
        var filename = "binary";
    }

    var extension = $(this).attr('extension');
    if (typeof extension !== 'undefined') {
        filename += '.' + extension
    }
    
    var mimetype = $(this).attr('mimetype');
    if (typeof mimetype === 'undefined') {
        mimetype =  'application/octet-stream';
    }
    var data = $(this).attr('data');
    var blob = base64toBlob(data, mimetype);
   
    if (navigator.msSaveBlob) { // IE 10+
        navigator.msSaveBlob(blob, filename)
    } else {
        var link = document.createElement("a");
        if (link.download !== undefined) { 
            //  Browsers that support HTML5 download attribute
            var url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
        }  
    }

});

function base64toBlob(base64Data, contentType) {
    contentType = contentType || '';
    var sliceSize = 1024;
    var byteCharacters = atob(base64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
        var begin = sliceIndex * sliceSize;
        var end = Math.min(begin + sliceSize, bytesLength);

        var bytes = new Array(end - begin);
        for (var offset = begin, i = 0 ; offset < end; ++i, ++offset) {
            bytes[i] = byteCharacters[offset].charCodeAt(0);
        }
        byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
}


/* Some helpers */
Array.prototype.indexOfPath = function(path) {
    for (var i = 0; i < this.length; i++)
        if (this[i].path === path)
            return i;
    return -1;
}