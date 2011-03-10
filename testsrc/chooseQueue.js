/**
 * 
 */
log.log('Service: '+inputContext.getServiceName());

log.log(queueContext.getQueueNames());

if(inputContext.getUserName()=='ROOT') {
	response.setQueueName('PriorityQueue');
} else {
	response.setQueueName('NormalQueue');
}

if(queueContext.getQueueHealth('PriorityQueue')<0.8) {
	log.log('Warning: Unhealthy priority queue');
}

response.setResponse('accept');

// Cache the decision for a second:
response.cacheDecision(1000);

