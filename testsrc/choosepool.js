/**
 * 
 */
log.log('Service: '+inputContext.getServiceName());

log.log(poolContext.getPoolNames());

if(inputContext.getUserName()=='ROOT') {
	response.setPoolName('PriorityPool');
} else {
	response.setPoolName('NormalPool');
}

if(poolContext.getPoolHealth('PriorityPool')<0.8) {
	log.log('Warning: Unhealthy priority pool');
}

response.setResponse('accept');

// Cache the decision for a second:
response.cacheDecision(1000);