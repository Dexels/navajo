// removed username / password
env.client.server="http://penelope1.dexels.com/sportlink/knvb/servlet/Postman";

function callSqlService( datasource, query,  param) {
	// create init
	var initservice = env.createNavajo();
	var request = env.addMessage(initservice,'request');
	env.addProperty(request,'datasource',datasource);
	env.addProperty(request,'query',query);

	var parameters = env.addArrayMessage(request,'parameter');

	if(parameters!=null) {
		for(var i in param) {
			var element;
			element = env.addElement(parameters);
			env.addProperty(element,'value',param[i]);
		}
	}


	var result = env.callService("internal/SQLService", initservice);
	return result;
}

//<joinmessage.join message1="'/uitslagen'" message2="'/pools'" ignoreSource="true" joinCondition="'poulecode=poulecode'" suppressProperties="'poulecode'" />

//<map>
//<tagname>joinmessage</tagname>
//<object>com.dexels.navajo.adapter.MessageMap</object>
//<methods>
//    <method name="join">
//        <param name="message1" field="joinMessage1" type="string" required="true"/>
//        <param name="message2" field="joinMessage2" type="string" required="false"/>
//        <param name="joinCondition" field="joinCondition" type="string" required="false"/>
//        <param name="type" field="joinType" type="string" required="false"/>
//        <param name="ignoreSource" field="removeSource" type="boolean" required="false"/>
//        <param name="suppressProperties" field="suppressProperties" type="string" required="false"/>
//    </method>
//</methods>
//</map>

function callJoinService(joinParams) {
	m = new com.dexels.navajo.adapter.MessageMap();
	m.joinMessage1 = joinParams['joinMessage1']; 
}

var result = callSqlService('sportlinkkernel','select poolid\
                ,      (SELECT C.competitionkind\
                from competitiontype C\
                where C.competitiontypeid =  teamcompetitiontypeseason.competitiontypeid\
                ) AS competitionkind\
                from teamcompetitiontypeseason where teamid = ? and seasonid = get_current_season',['189254']);


env.dump(result);

var list = result.getMessage('ResultSetMap').getAllMessages();
uitslagen = env.addArrayMessage(output,'uitslagen');
pools = env.addArrayMessage(output,'pools');
for ( var msg2 in Iterator(list)) {
	pool = env.addElement(pools);
	if(msg2.getProperty('COMPETITIONKIND').getValue()=='TROPHY_COMPETITION') {
		env.addProperty(pool,'competitiesoort','beker');
	} else {
		env.addProperty(pool,'competitiesoort','regulier');
	}
	env.addProperty(pool,'poulecode',msg2.getProperty('POOLID').getValue());

	//initservice = env.callService('clubsites/nl/init');

	var initservice = env.createNavajo();
	var parameters = env.addMessage(initservice,'parameters');
	env.addProperty(parameters,'poulecode',env.getValue(msg2,'POOLID'));
	env.addProperty(parameters,'eigenwedstrijden','ja');
//	initservice.getProperty('parameters/poulecode').setValue(env.getValue(msg2,'POOLID'));
//	env.dump(initservice);

	res = env.callService('clubsites/nl/pouleuitslagen',initservice);
	env.log('\n\nResult:');
	env.dump(res);
	env.log('\n=======================\n\n');
}