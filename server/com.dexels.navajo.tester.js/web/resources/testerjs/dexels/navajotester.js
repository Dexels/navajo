"use strict";

// Holds the input navajo document for the next RPC call
var xml = $.parseXML('<tml documentImplementation="SAXP"><header ><transaction rpc_usr="" rpc_name="" rpc_pwd="" /> </header></tml>');
var serializer = new XMLSerializer();
var editor;
var titleloader;

var pretty_max_source_length = 80000;
var pretty_max_response_length = 80000;

var rsp = "INIT";

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


function checkUseAAA() {
	
	$.ajax({
		dataType: "json",
        url: "testerapi?query=useaaa",
	    type : "GET",
	    async : true,
	    success : function(response) {
	        	console.log("use aaaaaaaa   ");
	        	console.log("use aaa:"+response.useAAA);
	        	console.log("response : "+response);
	        	if(response.type === "NONE") {
		        	hideLoginTable();
		        	rsp = "NONE";
	        	}
	        	if(response.type === "PASSWORD"){
	        		rsp = "PASSWORD";
	        	}
	    }
	})
	
	return rsp;
	};
function updateTenants() {
	$.ajax({
		dataType: "json",
        url: "testerapi?query=gettenants",
	    type : "GET",
	    async : true,
	    success : function(response) {
	    	var cnt = 0;
	    	console.log("response length: "+response.length); //should I do as in update applications?
	    	
	        $.each(response, function(key, value) {
	            console.log(">>>>> value::: "+response+" index: "+cnt);
	            if(cnt==0) {
	 	           $('#handlers').append($('<option selected=true>').text(value));
	            } else {
	 	           $('#handlers').append($('<option>').text(value));
	            }
	           
	           // If we don't have a instance in our session storage, check if a part of
	           // the url matches this instance
	           if (!sessionStorage.instance) {
	        	   if (window.location.href.toLowerCase().indexOf(value.toLowerCase()) > -1) {
	                    sessionStorage.instance = value;
	               }
	           }
	           cnt++;
	           console.log("count 1: "+cnt);
	        });

	        console.log("count 2: "+cnt);
	        if(cnt == 0){
	        	$('#handlers').attr('disabled',true); //vg
	        }
	     
	        if (sessionStorage.instance) {
	        	 $('#handlers').val(sessionStorage.instance);
	        }
	        $("#handlers").trigger("chosen:updated");
	        
	        
	    }
	});
}

function updateApplications() {
	$.ajax({
		dataType: "json",
        url: "testerapi?query=getapplications",
	    type : "GET",
	    async : true,
	    success : function(response) {
	    	console.log("aap: "+response.length);
	    	if(response.length == 1) {
	    		$('#applications').attr('disabled',true);
	    	} else {
	    		$('#applications').attr('disabled',false);
		        $.each(response, function(key, value) {
		        	console.log(key)
		        	console.log(value)
			        $('#applications').append($('<option></options>').text(value.description).attr('value',value.id));
		        	
			           // If we don't have a instance in our session storage, check if a part of
			           // the url matches this instance
			        });
			        if (sessionStorage.instance) {
			        	 $('#applications').val(sessionStorage.app);
			        }
	    		
	    	}
	        $("#applications").trigger("chosen:updated");
	    }
	});
}


function updateFavorites() {
	$("#favorites").html("");
	var favorites = localStorage.getItem("scriptfavorites");
	if (favorites == null || typeof favorites === "undefined" || favorites === "") {
		return;
	}
	var splitted = favorites.split("|");
	for (var i = 0; i < splitted.length; i++) {
		var script = splitted[i];
		if (script.length < 1) {
			continue;
		}
		var li = $("<li>");
		li.attr('class', 'scriptli');

		var div = $("<div>");
		div.attr('class', 'script clickable');
		div.attr('id', splitted[i]);
		div.text(splitted[i]);
		li.append(div);
		$("#favorites").append(li);
	}

	$(".favoritestitle").show();
}

function isFavorite(script) {
	var favorites = localStorage.getItem("scriptfavorites");
	if (favorites == null || typeof favorites === "undefined") {
		return false;
	}
	if (favorites.indexOf(script + "|" ) !== -1) {
		return true;
	}
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
            url: "testerapi?query=getscripts",
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
    sessionStorage.app = $("#applications :selected").val();
   
    sessionStorage.instance = $( "#handlers option:selected" ).text()
    sessionStorage.user =     $('#navajousername').val();
    sessionStorage.password = $('#navajopassword').val();

    var locale = $("#locale :selected").val();
    if (locale !== "empty")
    		sessionStorage.locale = locale;
    
    if ($('.LoginButton').attr('value') === 'Run script' && sessionStorage.script && !loginTableVisible()) {
        runScript(sessionStorage.script);
    }
    $('.LoginButton').attr('value', 'Login');

    return true;
}

function loginTableVisible() {
	
    var instance =  $( "#handlers option:selected" ).text();
    var length = $('#handlers > option').length;
    //var disabled = $('#handlers').attr('disabled');
    
    var isDisabled = $('#handlers').prop('disabled');
    
    console.log("DISABLED: "+isDisabled);
    
    if(isDisabled){
    	return false;
    }
    console.log("what is instance: "+instance);
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
	var rsp = checkUseAAA();
    $('#scriptCustomInputView').hide();
    if (script.indexOf('_') !== -1) {
    	var tenant = script.substring(script.indexOf('_'));
    	script = script.substring(0, script.indexOf('_'));
    	window.alert('Stripping '+tenant+' part of script! Calling ' + script)
    	
    }
    
    $('#loadedScript').text(script);
    sessionStorage.script = script;
    $('html, body').animate({
        scrollTop : 0
    }, 50);
    
    console.log("what is instanceGRE: "+loginTableVisible())
    if (loginTableVisible()) {
        showLoginTable();

        $('.LoginButton').attr('value', 'Run script');
        $('#logintable').trigger('startRumble');
        setTimeout(function(){$('#logintable').trigger('stopRumble');}, 750);
        return;
    }
    var instance = $( "#handlers option:selected" ).text();
    try {
        hourglassOn();
        $('.overlay').show();

        // If we have sourcefile visible, show HTML page. Otherwise leave it
        if ($('#TMLSourceview').is(":visible")) {
            $('#TMLSourceview').hide();
            $('#HTMLview').show();
        }

        var birtMode = (script == "BIRT")
        var navajoinput = prepareInputNavajo(script, birtMode);
        var authHeader = "Basic " + btoa(sessionStorage.user + ":" + sessionStorage.password)
        
        $.ajax({
        	beforeSend: function(req) {
        		startTitleLoader();
        		req.setRequestHeader('Content-Type', "text/xml;charset=utf-8");
        	},
        	complete: function() {stopTitleLoader();},
        	type: "POST",
            url: "/navajo/" + instance,
            data: navajoinput,
            headers: {"X-Navajo-Tester": "true","Authorization": authHeader, "X-Navajo-Service": script},
            success: function(result) {
            	  if(result instanceof Node) {
                      replaceXml(script, result);
                      var stateObj = { script: script, xml:  serializer.serializeToString(result) };
                      history.pushState(stateObj, script, "tester.html?script="+script);
            	  } else {
                      var stateObj = { script: script, xml:  result };
                      history.pushState(stateObj, script, "tester.html?script="+script);
                      $('#HTMLview')[0].innerHTML = result;
                      $('#scriptMainView').show();
                      $('.overlay').hide();
                      hourglassOff();
            	  }
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

    $.get("testerapi?query=getfilecontent&file=" + script, function(data) {
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
        if (isFavorite(script) ) {
        	$('#scriptheader').attr('class', 'scriptheader2');
        } else {
        	$('#scriptheader').attr('class', 'scriptheader');
        }
        hourglassOff();
    } catch (err) {
        console.log("Caugh error " +  err.message);
        console.log(err.stack);
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
	return prepareInputNavajo(script, false);
}

function prepareInputNavajo(script, birtMode) {
    var $xml = $(xml);
    var $transaction = $xml.find('tml header transaction')

    $transaction.attr('rpc_name', script);
    $transaction.attr('rpc_usr', sessionStorage.user);
    $transaction.attr('rpc_pwd', sessionStorage.password);
    
    if (sessionStorage.locale) {
    		$xml.find('tml header').attr('locale', sessionStorage.locale);
    }

    if (birtMode)
	{
	    $xml.find('tml header').attr('GenerateBirt',birtMode);
	}
    

    var $header = $xml.find('tml header ');
    console.log('using application: '+sessionStorage.app);
    if (sessionStorage.app === 'legacy') {
    	 $header.attr('application', null)
    } else {
    	 $header.attr('application', sessionStorage.app)
    }

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

function callScript(script, addToRecentScripts)
{
    var oldScript =  $('#loadedScript').text();
    var stateObj = {script: oldScript,  xml:  serializer.serializeToString(xml) };
    try {
    		history.replaceState(stateObj, oldScript, "tester.html?script=" + oldScript);
    }
    catch(err) {
        console.log(err) 
    }

    // Remove all hoover divs and append the one to the current script
    $('.customRunOptionContainer').remove();
  
    if (addToRecentScripts)
    {
	    var isRecent = $('li.recentScript>[id=\''+script+'\']').length > 0
	    if (!$(this).parent().hasClass('recentScript') && !isRecent) {
	    	var clonedDiv = $(this).parent().clone(true);
	        clonedDiv.addClass('recentScript')
	        clonedDiv.find('.script').text(script)
	        $('#recentscriptslist').prepend(clonedDiv);
	        $('#recentscriptslist').find(".scriptli").slice(5, 10).remove();
	    }
	    $('li.recentScript>[id=\''+script+'\']').parent().append(hooverdiv);
    }

    runScript(script);
}

/* Event handlers */


$(document).on('click', '.script', function() {
	var script = $(this).attr("id");
	callScript(script, true);
});

$(document).on('click', '.folder', function() {
    $(this).next().children('ul li').toggle();
});


$(document).on('click', '.refreshscripts', function() {
	getScripts();
});

$(document).on('click', '.birttitle', function() {
	var script = $(this).attr("id");
	callScript(script, false);
});


$(document).on('click', '.scriptheader', function() {
	$(this).attr('class', 'scriptheader2');
	// Add id to localstorage
	if (isFavorite($(this).text())) {
		return;
	}
	var favorites = localStorage.getItem("scriptfavorites");
	if (favorites == null || typeof favorites === "undefined") {
		favorites = "";
	}
    localStorage.setItem("scriptfavorites", $(this).text() + "|"  + favorites);
    updateFavorites();
});

$(document).on('click', '.scriptheader2', function() {
	$(this).attr('class', 'scriptheader');
	// Remove script from favorites

	var favorites = localStorage.getItem("scriptfavorites");
	if (typeof favorites === "undefined") {
		// Weird, but hey, I'm not going to complain!
		return;
	}
	favorites = favorites.replace($(this).text() + "|" , "");

    localStorage.setItem("scriptfavorites", favorites);
    updateFavorites();
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
    if (isFavorite(script) ) {
    	$('#scriptheader').attr('class', 'scriptheader2');
    } else {
    	$('#scriptheader').attr('class', 'scriptheader');
    }


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

    $.get("testerapi?query=getfilecontent&file=" + script, function(data) {
    	$('#scriptsourcecontent').attr('class', 'prettyprint lang-xml linenums');
        $('#scriptsourcecontent').text(data)
        if (data.length < pretty_max_source_length) {
        	 prettyPrint();
        } else {
        	// add class to prevent it from being pretty-printed by script response prettyprint
        	$('#scriptsourcecontent').addClass('prettyprinted');
        }
        $('#scriptheader').text(script);
        if (isFavorite(script) ) {
        	$('#scriptheader').attr('class', 'scriptheader2');
        } else {
        	$('#scriptheader').attr('class', 'scriptheader');
        }
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
        	$.get("testerapi?query=getcompiledcontent&file=" + script, function(data) {
                $('#scriptsourcecontent').attr('class', 'prettyprint lang-java linenums');
                $('#scriptsourcecontent').text(data)
                prettyPrint();
                $('#scriptheader').text(script);
                if (isFavorite(script) ) {
                	$('#scriptheader').attr('class', 'scriptheader2');
                } else {
                	$('#scriptheader').attr('class', 'scriptheader');
                }
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

$(document).on('textarea change keyup paste', '.tmlinputtextarea', function(evt) {
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

$(document).on('input change', '.tmlinputtuple', function(evt) {
    var xpath = $(this).attr('id').replace("/lon","").replace("/lat","");
    var element = $(xml).xpath(xpath)[0];
    if (typeof element != 'undefined') {
        var $element = $(element);
        // Create the new value 
        var value = $element.attr('value')
        value = value.replace("[","");
        value = value.replace("]","");
        var lon = value.split(",")[0];
        var lat = value.split(",")[1];
        
        if($(this).attr('id').includes("\lon")){
        	$element.attr('value',  "["+$(this)[0].value+","+lat+"]");
        }else{
        	$element.attr('value',  "["+lon+","+$(this)[0].value+"]");
        }
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


/* Some helpers */
Array.prototype.indexOfPath = function(path) {
    for (var i = 0; i < this.length; i++)
        if (this[i].path === path)
            return i;
    return -1;
}


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



function startTitleLoader(){
	document.title = 'Tester.js ';
	function loadTitle() {
	    var title = $(document).prop('title');
	    if (title.indexOf('.......') == -1) {
	        $(document).prop('title', title + '.');
	    } else {
	    	$(document).prop('title', 'Tester.js .');
	    }
	};
	titleloader = setInterval(loadTitle, 750);
}

function stopTitleLoader() {
	if (titleloader) {
		 clearInterval(titleloader);
		 titleloader = 0;
		 document.title = 'Tester.js';
	}
}
