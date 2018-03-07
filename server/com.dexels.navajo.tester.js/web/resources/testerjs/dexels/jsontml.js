function convertJsonToTml(jsonString) {
    var jsonObj = JSON.parse(jsonString);
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === "object") {
        		if(Array.isArray(value)){
        			console.log("It's an array :) ")
        			xmlString += '<message name="' + key + '" type="array">\n';
        			xmlString += jsonObjToTml(key,value,true);
        			xmlString += '</message>\n';
        		}else{
	            xmlString += '<message name="' + key + '">\n';
	            xmlString += jsonObjToTml(key,value,false);
	            xmlString += '</message>\n';
        		}
        } else {
            xmlString += '<property name="' + key + '" value="'+value+'" />\n'
        }
    });
    return formatXml(xmlString);
}

function jsonObjToTml(parent,jsonObj,isParentArray) {
    var xmlString = '';
    $.each(jsonObj, function(key, value) {
        if (typeof value === 'undefined' || value === null) {
            xmlString += '<property name="' + key + '" />\n';
        } else if (typeof value === "object") {
        		if(Array.isArray(value)){
        			if(isParentArray){
            		    xmlString += '<message name="' + parent + '" type="array_element">\n';
                    xmlString += jsonObjToTml(key,value,false);
                    xmlString += '</message>\n';
            		}else{
            			xmlString += '<message name="' + key + '">\n';
                    xmlString += jsonObjToTml(key,value,true);
                    xmlString += '</message>\n';
            		}
        		}else{
        			if(isParentArray){
            		    xmlString += '<message name="' + parent + '" type="array_element">\n';
                    xmlString += jsonObjToTml(key,value,false);
                    xmlString += '</message>\n';
            		}else{
            			xmlString += '<message name="' + key + '">\n';
                    xmlString += jsonObjToTml(key,value,false);
                    xmlString += '</message>\n';
            		}
        		}
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
