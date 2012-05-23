// removed username / password
//env.client.server="http://penelope1.dexels.com/sportlink/knvb/servlet/Postman";
env.client.server="http://hera1.dexels.com/sportlink/knvb/servlet/Postman";

var initservice = env.createNavajo();
var parameters = env.addMessage(initservice,'parameters');
env.addProperty(parameters,'teamcode','189254').type="integer";
env.dump(initservice);

res = env.callService('clubsites/nl/teamuitslagen',initservice);
env.dump(res);
