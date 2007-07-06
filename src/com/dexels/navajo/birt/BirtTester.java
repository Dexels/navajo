package com.dexels.navajo.birt;

import java.io.File;
import java.io.IOException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;

public class BirtTester {

	static {
		NavajoClientFactory.getClient().setServerUrl("ficus:3000/sportlink/knvb/servlet/Postman");
		NavajoClientFactory.getClient().setUsername("ROOT");
		NavajoClientFactory.getClient().setPassword("");

	}
	public static void main(String[] args) throws IOException, NavajoException, ClientException {
		System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
		NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
		BirtUtils b = new BirtUtils();
//		b.createEmptyReport(new File("c:/projecten/NavajoBIRT/clubmembers.xml"),new File("c:/birt_workspace/BirtReport"), "clubsearch.rptdesign","club/ProcessSearchClubs");
//		b.createEmptyReport(new File("c:/projecten/NavajoBIRT/ProcessSearchClubs.tml"),new File("c:/birt_workspace/BirtReport"), "clubsearch.rptdesign","club/ProcessSearchClubs");
		b.createEmptyReport(new File("c:/projecten/NavajoBIRT/ProcessGetStoredStatistics.tml"),new File("c:/birt_workspace/BirtReport"), "ProcessGetStoredStatistics.rptdesign","ProcessGetStoredStatistics");
		b.createEmptyReport(new File("c:/projects/sportlink-serv/navajo-tester/auxilary/tml/member/ProcessQueryMember.tml"),new File("c:/birt_workspace/BirtReport"), "ProcessQueryMember.rptdesign","ProcessQueryMember");

		b.buildReportFolder(new File("c:/projects/sportlink-serv/navajo-tester/auxilary/"));
		
		if(true) {
			return;
		}
		
		
					Message init = NavajoClientFactory.getClient().doSimpleSend("club/InitSearchClubs", "ClubSearch");
			init.getProperty("ClubName").setValue("veld");
			Navajo n = NavajoClientFactory.getClient().doSimpleSend(init.getRootDoc(), "club/ProcessSearchClubs");	
			b.createEmptyReport(n,new File("c:/reporttest"), "clubsearch.rptdesign","club/ProcessSearchClubs");

	
	}

}
