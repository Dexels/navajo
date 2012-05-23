// removed username / password
env.client.server="http://penelope1.dexels.com/sportlink/knvb/servlet/Postman";

var init = env.getTml('initjoin.tml');
output = init;
resultMessage = output.getMessage('ResultingMessage');

callMapFunctionCallback('com.dexels.navajo.adapter.MessageMap',{joinType:"'inner'",joinMessage1:"'message1'",joinMessage2:"'message2'",joinCondition:"'propje1=blieblab'"}, null,[{name:'resultMessage'},{func:function( a) {
	elt = env.addElement(resultMessage);
	env.addProperty(elt,'tralala',a.getProperty('blieblab'));
}}]);


var fields = {datasource:"sportlinkkernel",query:'select poolid ,  (SELECT C.competitionkind from competitiontype C where C.competitiontypeid =  teamcompetitiontypeseason.competitiontypeid) AS competitionkind from teamcompetitiontypeseason where teamid = ? and seasonid = get_current_season'};
var dbaseServiceCall = [{parameter:'189254'}];
//env.log('aaaap');

var result =  callSimpleService("internal/SQLService",fields, dbaseServiceCall);

env.dump(result);

