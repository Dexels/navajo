env.log("Ready to go!");
var monkey = new Packages.java.lang.String("hoempapa");
//var conditionErrors = new Packages.com.dexels.navajo.rhino.flow.ConditionError();

function callMap(mapClass, callback) {
	map = env.createMappable(mapClass,input, output, env.getAccess().getCurrentOutMessage());
	// push mappabletreenode
	env.pushMappableTreeNode(map);
	env.loadMap(map);
	if(callback!=undefined && callback!=null) {
		callback();
	}
	env.storeMap(map);
	// TODO: Handle exceptions and call kill()
	env.popMappableTreeNode();
	// pop mappabletreenode
}

function mapOntoMessage(field,path,filter,callback) {
	if(callback==undefined) {
		env.log("\n\n NO CALLBACK.....")
		callback=filter;
		filter = null;
	}
	isArr = env.isArrayMapRef(field);
	mm = env.getInputMessage(path);
	
	isArrMsg = mm.isArrayMessage();
	if(isArr!=isArrMsg) {
		env.log("We can not map an array field onto a non-array message or vice versa");
		return;
	}
	i = 0;
	if(isArr) {
		ref = env.createMapRef(field);
		list = mm.getAllMessages().toArray();
		env.log("Array size: "+list.length+" : "+ref.length);
		count = Math.min(list.length,ref.length);
		for(a=0; a<count; a++) {
			env.log("Iteration: "+i+" message: "+list[a]);
			env.pushMappableTreeNode(ref[a]);
			env.pushInputMessage(list[a]);
			if((filter==null || filter==undefined) || evaluateNavajo(filter)==true) {
				callback(ref[a],list[a]);
			} else {
				env.log("FILTER FAILED!");
			}
			env.popMappableTreeNode();
			env.popInputMessage();
			i++;
		}
	} else {
		mm = env.getInputMessage(path);
		env.pushInputMessage(mm);
		ref = env.createMapRef(field);
		callback(ref);
		env.popInputMessage();
	}
	env.popMappableTreeNode();
}

function callReferenceMap(field,filter,callback){
	//ref = env.pushMapReference(field);
	if(callback==undefined) {
		callback=filter;
		filter=null;
	}
	isArr = env.isArrayMapRef(field);
	if(isArr) {
		env.log('it is an array');
		ref = env.createMapRef(field);
		env.log('Found ref: '+ref+' is array? '+isArr);
		env.log("Elements: "+ref.length);
		for(a in ref) {
			env.log('Entering loop');
			env.log('Element: '+ref[a]);
			env.addElement();
			env.pushMappableTreeNode(ref[a]);
			if((filter==null || filter==undefined) || evaluateNavajo(filter)==true) {
				callback(ref[a]);
			} else {
				env.log("FILTER FAILED!");
			}

			env.popMappableTreeNode();
			env.log('Should be poppin the element now:');
			env.popMessage();
		}
	} else {
		env.log('it is not an array');
		ref = env.createMapRef(field);
			//env.pushMappableTreeNode(ref);
			callback(ref);
			//env.popMappableTreeNode();
	}
	env.popMappableTreeNode();
}
    

function forEachSubMap(field,callback) {
	isArr = env.isArrayMapRef(field);
	if(isArr) {
		env.log('it is an array');
	} else {
		env.log('it is not an array');
		ref = env.createMapRef(field);
			//env.pushMappableTreeNode(ref);
			callback();
			//env.popMappableTreeNode();
	}
	env.popMappableTreeNode();
}
	
function addMethod(methodName,requiredMessages) {
	env.addMethod(methodName); 
}

function evaluateNavajo(expression) {
	if(expression==null) {
		return null;
	}
	result = env.navajoEvaluate(expression);
	
//	if(result==true) {
//		env.log('result evaluated as true: '+result)
//	}
//	env.log('result evaluated '+result)
	return result;
}

function navajoDebug(expression) {
	env.log("DEBUG:::: "+evaluateNavajo(expression));
}

function forEachMessage(path,filter,callback) {
	mm = env.getInputMessage(path);
	list = mm.getAllMessages().toArray();
	for(i in list) {
		m = list[i];
		env.pushInputMessage(m);
		if(filter!=null) {
			if(evaluateNavajo(filter)==true) {
				callback(m);
			} else {
				env.log("FILTER FAILED!");
			}
		} else {
			callback(mm);
		}
		env.popInputMessage();
	}
}

// TODO Add filter implementation for non-array?
function forMessage(path,filter,callback) {
	if(callback==undefined) {
		callback = filter;
		filter = undefined;
	}
	mm = env.getInputMessage(path);
	if(mm.isArrayMessage()) {
		forEachMessage(path,filter,callback);
	} else {
		env.pushInputMessage(mm);
		callback(mm);
		env.popInputMessage();
	}
}

function forEachParamMessage(path,filter,callback) {
	env.log('looping over children at path: '+path)
	mm = env.getInputMessage(path);
	env.log('looping over resultant: '+mm.toString())

	list = mm.getAllMessages().toArray();
	for(i in list) {
		m = list[i];
		env.log(i);
		env.log(m);
		
		env.pushParamMessage(m);
		
		if(filter!=null && filter!=undefined) {
			if(evaluateNavajo(filter)==true) {
				callback();
			} else {
				env.log("FILTER FAILED!");
			}
		} else {
			callback();
		}
		env.popParamMessage();
	}
}





function addParam(propertyName,value, attributes) {
	// not sure if this still is necessary
	map = new java.util.HashMap();
	if(attributes!=undefined && attributes!=null) {
		for(i in attributes) {
			object[i] = attributes[i];
		}
	}
	p = env.addParam(propertyName,value,map);
	//TODO
//	addAttributes(p,attributes);

	return p;
}


// only used for 'expression-style' fields
function addField(fieldName,value) {
	//env.log("setting field: "+fieldName+" value: "+value);
	env.addField(fieldName,value);
}


// NOT USED YET, shorthand to get a bit more compact scripts.
function fieldExpression(fieldName,expression) {
	addField(fieldName,env.evaluateNavajo(expression))
}
function paramExpression(fieldName,expression,attributes) {
	addField(fieldName,env.evaluateNavajo(expression),attributes);
}
function propertyExpression(fieldName,expression,attributes) {
	addProperty(fieldName,env.evaluateNavajo(expression),attributes)
}


function addProperty(propertyName,value, attributes) {
	p = env.addProperty(propertyName,value);
	addAttributes(p,attributes);
	return p;
}


	
function addSelection(property, name,value, selected) {
	p = env.addSelection(property,name,value,selected);
	return p;
}


function addAttributes(object, attributes) {
//	if(attributes == undefined) {
//		return;
//	}
	for(i in attributes) {
		object[i] = attributes[i];
	}
}

function addMessage(messageName, callback) {
	result = env.addMessage(messageName);
	if(callback!=undefined && callback!=null) {
		callback();
	}
	env.popMessage();
	return result;
}

function addElement(callback) {
	result = env.addElement();
	if(callback!=undefined && callback!=null) {
		callback();
	}
	env.popMessage();
	return result;
}


function addArrayMessage(messageName, callback) {
	result = env.addArrayMessage(messageName);
	if(callback!=undefined && callback!=null) {
		callback();
	}
	env.popMessage();
	return result;
}

function addParamArrayMessage(messageName, callback) {
	result = env.addParamArrayMessage(messageName);
	if(callback!=undefined && callback!=null) {
		callback();
	}
	env.popMessage();
	return result;
}

function appendConditionError(code,description) {
	conditionErrors.append(code, description);
}

function breakOnConditionErrors() {
	if(conditionErrors.hasConditionErrors()) {
		throw conditionErrors;
	}
}

function throwBreak() {
	throw "Breaking navajo script";
}

function debug(message) {
	env.log('Debug: '+message);
}

