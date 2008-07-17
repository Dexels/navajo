package com.dexels.navajo.geo.test;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.geo.zipcode.*;

public class Test {

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
			BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
				line = br.readLine();
				while(line!=null) {
					processLine(line,m);
					line = br.readLine();
				}
				br.close();
				inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		double res =  (double)member/(double)total;
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
