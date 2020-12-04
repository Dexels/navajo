/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.geo.zipcode.Gemeente2Population;

public class Test {

	
	private final static Logger logger = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) throws NavajoException, IOException {
		Gemeente2Population.init();
		Navajo n = new Test().createTestMessage();
		FileOutputStream stream = new FileOutputStream("test.tml");
		n.write(stream);
		stream.close();
	}
	
	public Navajo createTestMessage() throws NavajoException {
		Navajo navajo = NavajoFactory.getInstance().createNavajo();
		Message element = NavajoFactory.getInstance().createMessage(navajo,"Data",Message.MSG_TYPE_ARRAY);
		navajo.addMessage(element);

		
		init(element);
		navajo.write(System.err);
		return navajo;
	}

	public void init(Message m) throws NavajoException {
		String line;
		try {
			InputStream inStream = new FileInputStream("exampledata/leden.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(inStream,"UTF-8"));
				line = br.readLine();
				while(line!=null) {
					processLine(line,m);
					line = br.readLine();
				}
				br.close();
				inStream.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
//	0060;AMELAND;681
	
	private void processLine(String line, Message parent) throws NavajoException {
		//"5392 Maasdonk";"795";"1671";"Maasdonk"
		StringTokenizer st = new StringTokenizer(line,";");
		String code = st.nextToken();
		st.nextToken();
		String members = st.nextToken();
		if(code==null) {
			throw new RuntimeException();
		}
		int total = Gemeente2Population.getGemeente(code);
		int member = Integer.parseInt(members);
		double res =  (double)member/(double)total / 0.12;
		Navajo navajo = parent.getRootDoc();
		Message element = NavajoFactory.getInstance().createMessage(navajo,"Data",Message.MSG_TYPE_ARRAY_ELEMENT);
		parent.addMessage(element);
		Property key = NavajoFactory.getInstance().createProperty(navajo, "Key", Property.STRING_PROPERTY, code, 9, "", Property.DIR_IN);
		Property value = NavajoFactory.getInstance().createProperty(navajo, "Value", Property.STRING_PROPERTY, ""+res, 9, "", Property.DIR_IN);
		Property description = NavajoFactory.getInstance().createProperty(navajo, "Description", Property.STRING_PROPERTY, "Leden per 1000 inwoners: "+(int)(res*1000), 99, "", Property.DIR_IN);
		element.addProperty(key);
		element.addProperty(value);
		element.addProperty(description);

		
	}
}
