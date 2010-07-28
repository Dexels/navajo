
function callMapFunction(mapClass, params, currentOutMessage) {
	map = map.createMappable(mapClass,input, output, currentOutMessage);
	for(var a in params) {
		env.log(params[a]);	
		map[a] = env.navajoEvaluate(params[a], output);
	}
	return map;
}

function callMapFunctionCallback(mapClass, params, currentOutMessage, callbackReferences) {
	map = callMapFunction(mapClass,params,currentOutMessage);
	for(c in callbackReferences) {
		m = callbackReferences[c].message;
		f = callbackReferences[c].func;
		processRefs(map,m,f);
	}
}

function callMapFunctionSingleCallback(mapClass, params, currentOutMessage, callback) {
	map = callMapFunction(mapClass,params,currentOutMessage);
	// push mappabletreenode
	callback(map);
	// pop mappabletreenode
}


function processRefs(map, refName, refFunction) {
	mapz = map[refName];
	for(var a in mapz) {
		refFunction(mapz[a]);
	}
}


function callSimpleService(service, fields,params) {
	var initservice = env.createNavajo();
	var request = env.addMessage(initservice,'request');
	var parameters = env.addArrayMessage(request,'parameter');
	for(var pp in fields) {
		env.addProperty(request,pp,fields[pp]);
	}
	for(var prm in params) {
		var element;
		currentObject = params[prm];
		for(var ee in currentObject) {
			element = env.addElement(parameters);
			env.addProperty(element,'value',currentObject[ee]);
		}
	}
	var result = env.callService(service, initservice);
	return result;
}

