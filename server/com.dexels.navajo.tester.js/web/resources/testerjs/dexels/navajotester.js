"use strict"

// Holds the input navajo document for the next RPC call
var xml = $.parseXML('<tml documentImplementation="SAXP"><header><transaction rpc_usr="" rpc_name="" rpc_pwd=""/> </header></tml>');
var serializer = new XMLSerializer();
var editor ;

var pretty_max_source_length = 80000;
var pretty_max_response_length = 80000;


var hooverdiv = '<div class="customRunOptionContainer">';
hooverdiv += '  <div class="customRunOption scriptcompile">Compile</div> |';
hooverdiv += '  <div class="customRunOption scriptsource">Src</div> | ';
hooverdiv += '  <div class="customRunOption compiledsource">Compiled src</div> | ';
hooverdiv += '  <div class="customRunOption scriptinput">Input</div>';
hooverdiv += '</div>';


function createEditor() {
    editor= ace.edit("editor");
    editor.setTheme("ace/theme/eclipse");
    editor.getSession().setMode("ace/mode/xml");
    editor.setBehavioursEnabled(true);
    editor.setHighlightActiveLine(true);
}

function updateTenants() {
	$.ajax({
		dataType: "json",
        url: "/testerapi?query=gettenants",
	    type : "GET",
	    async : true,
	    success : function(response) {
	        $.each(response, function(key, value) {
	           $('#handlers').append($('<option>').text(value));
	           
	           // If we don't have a instance in our session storage, check if a part of
	           // the url matches this instance
	           if (!sessionStorage.instance) {
	        	   if (window.location.href.toLowerCase().indexOf(value.toLowerCase()) > -1) {
	                    sessionStorage.instance = value;
	               }
	           }
	        });
	        if (sessionStorage.instance) {
	        	 $('#handlers').val(sessionStorage.instance);
	        }
	        $("#handlers").trigger("chosen:updated");
	    }
	});
	
}


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
                        // Only add if we don't have it yet
                        if ( $(this).find('.customRunOptionContainer').length === 0) {
                           $(this).append(hooverdiv);
                        }
                     },
                     out: function() {
                         var activeScript = $('#loadedScript').text();
                         var myScript = $(this).find('.script').attr('id');
                         var isRecentScript = $(this).hasClass('recentScript');

                         if (myScript !== activeScript || !isRecentScript ) {
                        	 $(this).find('.customRunOptionContainer').remove();
                         }
                     }, 
                     interval: 300
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
	// Sort subentries
    $.each(element, function(index, subelem) {
        if (subelem.type  === 'FOLDER') {
           sortFileObject(subelem.entries);
        }
    });
  
    element.sort(function(a, b) {
    	if (a.type !== b.type) {
    		// Sort folders before files
    		return b.type.localeCompare(a.type);
    	}
    	// Default sort based on name
    	return a.name.localeCompare(b.name);

    });
}

function processLoginForm(){
    hideLoginTable();
    sessionStorage.instance = $( "#handlers option:selected" ).text()
    sessionStorage.user =     $('#navajousername').val();
    sessionStorage.password = $('#navajopassword').val();
    
    $('#navajopassword').val('***********');
    
    if ($('.LoginButton').attr('value') === 'Run script' && sessionStorage.script && !loginTableVisible()) {
        runScript(sessionStorage.script);
    }
    $('.LoginButton').attr('value', 'Login');
    
    return true;
}

function loginTableVisible() {
    var instance =  $( "#handlers option:selected" ).text();
    return (instance === "" || !sessionStorage.user) 
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
    sessionStorage.script = script;
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
    	$('#scriptsourcecontent').attr('class', 'prettyprint lang-xml linenums');
        $('#scriptsourcecontent').text(data)
        if (data.length < pretty_max_source_length) {
        	 prettyPrint();
        } else {
        	// add class to prevent it from being pretty-printed by script response prettyprint
        	$('#scriptsourcecontent').addClass('prettyprinted');
        }
       
    });
}

function replaceXml(script, xmlObj) {
    try {
        xml = xmlObj;
        $('#scriptcontent').removeClass('prettyprinted');
        var xmltext = serializer.serializeToString(xmlObj)
        $('#scriptcontent').text(xmltext)

        if (xmltext.length < pretty_max_response_length) {
        	prettyPrint();
        } else {
        	// add class to prevent it from being pretty-printed by script source prettyprint
        	$('#scriptcontent').addClass('prettyprinted');
        }
        
        
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
              
                $(this).find("li").filter(":visible").hide();
                $(this).hide();
            } else {
                var childHasMatches = updateVisibility(filter, $(this).children('ul').first());
                if (childHasMatches) {
                    $(this).show()
                    anyMatch = true;
                } else {
                    console.error("This shouldn't happen?");

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
    var oldScript =  $('#loadedScript').text();
    var stateObj = {script: oldScript,  xml:  serializer.serializeToString(xml) };
    history.replaceState(stateObj, oldScript, "tester.html?script=" + oldScript);
    
    var newScript = $(this).attr("id");
    // Remove all hoover divs and append the one to the current script
    $('.customRunOptionContainer').remove();
    
    var isRecent = $('li.recentScript>[id=\''+newScript+'\']').length > 0
    if (!$(this).parent().hasClass('recentScript') && !isRecent) {
    	var clonedDiv = $(this).parent().clone(true);
        clonedDiv.addClass('recentScript')
        clonedDiv.find('.script').text(newScript)
        $('#recentscriptslist').prepend(clonedDiv);
        $('#recentscriptslist').find(".scriptli").slice(5, 10).remove();
    }
    $('li.recentScript>[id=\''+newScript+'\']').parent().append(hooverdiv);
    
   
    runScript(newScript);
});

$(document).on('click', '.folder', function() {
    $(this).next().children('ul li').toggle();
});


$(document).on('click', '.scriptcompile', function() {
	var parentLi = $(this).parent().parent();
    var script = parentLi.children('.script').attr('id');
    hourglassOn();
    
    $.ajax({
        type: "GET",
        url: "/compile?script=" + script,
        dataType: "text",
        success: function() {
            $('.scriptcompile').html('OK');
            hourglassOff();
            setTimeout( function(){
            			$('.customRunOptionContainer').remove();
            			parentLi.append(hooverdiv);
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
    editor.setValue("");
    $('#AddInit').hide();
    if (localStorage.getItem("scriptinput"+script) !== null) {
        var custominput = localStorage.getItem("scriptinput"+script);
        editor.setValue(custominput);   
    }
    var scriptName = $(this).parent().parent().children('.script').text();
    if (scriptName.indexOf("Process") > -1) {
        var initScript = scriptName.replace("Process", "Init");
        // Does such an init script exist?
        var match = $('#scripts').text().search(new RegExp(initScript, "i"));
        if (match > -1) {
            $('#AddInit').show();
        }
    }
    
    $('#scriptCustomInputView').show();
    
    
});


$(document).on('click', '#AddInit', function() {
    var script = $('#loadedScript').text();
    var initScript = script.replace("Process", "Init");
    
    hourglassOn();
    var instance =  $( "#handlers option:selected" ).text();
    var navajoinput = prepareInputNavajo(initScript);
    // Going to try to get init script...
    $.ajax({
        type: "POST",
        url: "/navajo/" + instance,
        data: navajoinput,
        success: function(xmlObj) {
            var messages = $(xmlObj).find('message');
            $.each(messages, function(index, message) {
                if ($(message).attr('name') !== 'error') {
                    var xmltext = serializer.serializeToString(message)
                    editor.insert(xmltext);
                } 
            });
            hourglassOff();
            //
           
        },
        error: function(xhr, ajaxOptions, thrownError) {
            // ignore
            hourglassOff();
        }
    });
});


$(document).on('click', '.scriptsource', function() {
    var script = $(this).parent().parent().children('.script').attr('id');
    hourglassOn();
    $('html, body').animate({
        scrollTop : 0
    }, 50);
    
    $.get("/testerapi?query=getfilecontent&file=" + script, function(data) {
    	$('#scriptsourcecontent').attr('class', 'prettyprint lang-xml linenums');
        $('#scriptsourcecontent').text(data)
        if (data.length < pretty_max_source_length) {
        	 prettyPrint();
        } else {
        	// add class to prevent it from being pretty-printed by script response prettyprint
        	$('#scriptsourcecontent').addClass('prettyprinted');
        }
        $('#scriptheader').text(script);
        $('#scriptMainView').show();
        $('#TMLSourceviewLink').click();
        hourglassOff();
    });
});

$(document).on('click', '.compiledsource', function() {
    var script = $(this).parent().parent().children('.script').attr('id');
    hourglassOn();
    $('html, body').animate({
        scrollTop : 0
    }, 50);
    
    $.ajax({
        type: "GET",
        url: "/compile?script=" + script + '&keepIntermediateFiles=true',
        dataType: "text",
        success: function() {
        	$.get("/testerapi?query=getcompiledcontent&file=" + script, function(data) {
                $('#scriptsourcecontent').attr('class', 'prettyprint lang-java linenums');
                $('#scriptsourcecontent').text(data)
                prettyPrint();
                $('#scriptheader').text(script);
                $('#scriptMainView').show();
                $('#TMLSourceviewLink').click();
                hourglassOff();
            });
        }
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
        editor.getSession().setMode("ace/mode/json");
        $('#CustomInputRunButton').val("Convert to TML");
    } else {
        editor.getSession().setMode("ace/mode/xml");
        $('#CustomInputRunButton').val("Run Script");
    }
});


$(document).on('click', '#CustomInputRunButton', function() {
    // Going to run loaded script with custom input...
    var inputtype = $('.custominputtype:checked').val();
    var inputString = editor.getValue();
    if (inputtype === "JSON") {
        try {
            var inputXml = convertJsonToTml(inputString);
            $(this).val("Run script");
            editor.getSession().setMode("ace/mode/xml");
            editor.setValue(inputXml);
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
    // Store input in local storage 
    localStorage.setItem("scriptinput" + script, inputString);
    editor.setValue("");
    var idEscpated = script.replace(/\//g, "\\/");
    $('#' + idEscpated +'.script').first().click();
});

function convertJsonToTml(jsonString) {
    var jsonObj = JSON.parse(jsonString);
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === "object") {
            xmlString += '<message name="' + key + '">\n';
            xmlString += jsonObjToTml(value);
            xmlString += '</message>\n';
        } else {
            xmlString += '<property name="' + key + '" value="'+value+'" />\n'
        }
    });
    return formatXml(xmlString);
}

function jsonObjToTml(jsonObj) {
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === 'undefined' || value === null) {
            xmlString += '<property name="' + key + '" />\n';
        } else if (typeof value === "object") {
            xmlString += '<message name="' + key + '">\n';
            xmlString += jsonObjToTml(value);
            xmlString += '</message>\n';
        } else {
            xmlString += '<property name="' + key + '" ';
            if (isInt(value)) {
                xmlString += ' type="integer" value="'+value+'" />\n';
            } else if (isFloat(value)) {
                xmlString += ' type="long" value="'+value+'" />\n';
            } else if (isBoolean(value)) {
                
                xmlString += ' type="boolean" value="'+value+'" />\n';
            } else {
                xmlString += ' value="'+value+'" />\n'
            }
           
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
        	$(".scripts").find("li").filter(":visible").hide();
        	$(".scripts").children("li").show();
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
    if (event.state) {
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


function isInt(n){
    return Number(n) === n && n % 1 === 0;
}

function isFloat(n){
    return n === Number(n) && n % 1 !== 0;
}
function isBoolean(n){
    return n === true || n === false;
}

function formatXml(xml) {
    var formatted = '';
    var reg = /(>)(<)(\/*)/g;
    xml = xml.replace(reg, '$1\r\n$2$3');
    var pad = 0;
    jQuery.each(xml.split('\n'), function(index, node) {
        var indent = 0;
        if (node.match(/.+<\/\w[^>]*>$/)) {
            indent = 0;
        } else if (node.match(/^<\/\w/)) {
            if (pad != 0) {
                pad -= 1;
            }
        } else if (node.match(/^<\w[^>]*[^\/]>.*$/)) {
            indent = 1;
        } else {
            indent = 0;
        }

        var padding = '';
        for (var i = 0; i < pad; i++) {
            padding += '    ';
        }

        formatted += padding + node + '\r\n';
        pad += indent;
    });

    return formatted;
}
