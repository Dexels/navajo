
//var avail = inputContext.getResourceAvailability();

response.setResponse('accept');

//if ( inputContext.getServiceName()=='match/ReplicateMatch' ) {
  response.setQueueName('fastThread');
//} else {
  //response.setQueueName('fastThread');
//}

//if (avail=='verybusy') {
 //  response.setQueueName('slowThread');
//} else if (avail=='busy') {
//   response.setQueueName('normalThread');
//} else if (avail=='dead') {
//   response.setResponse('refuse');
//} else if (inputContext.getUserName()=='ROOT') {
//   response.setQueueName('priorityThread'); 
//} else {
//}

//log.log('in resolve for ' + inputContext.getServiceName() + '(' + inputContext.getUserName() + ') -> queue: ' + response.getQueueName() + ', IP-address: ' + inputContext.getRequest().getRemoteHost() );

// Cache the decision for a second:
response.cacheDecision(1);

