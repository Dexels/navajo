function callMap(mapClass, callback) {
	//map = map.createMappable(mapClass,input, output, env.getMessage());
	// push mappabletreenode
	env.pushMappableTreeNode(null);
	callback();
	env.popMappableTreeNode();
	// pop mappabletreenode
}

function addMethod(methodName,requiredMessages) {
	env.log("adding method!");
	env.addMethod(methodName);
}

function evaluateNavajo(expression) {
	result = env.navajoEvaluate(expression);
	
	if(result==true) {
		env.log('result evaluated as true: '+result)
	}
	env.log('result evaluated '+result)
	return result;
}

function addParam(paramName,expression, attributes) {
}

function addField(paramName,expression, attributes) {
	env.log("setting field!");
}

function addProperty(propertyName,expression, attributes) {
}

function addMessage(messageName, callback) {
	env.log('boah!');
	env.addMessage(messageName);
	callback();
}

function addArrayMessage(messageName, callback) {

}
