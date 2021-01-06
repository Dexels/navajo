package com.dexels.navajo.compiler.navascript;

import java.io.FileInputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;

public class NSXMLToNS3 {

	/**
	 * 
	 * 
	 * 
	 * <navascript xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://www.navajo.nl/schemas/navascript.xsd">
<map.sqlquery>
<param name="PhotoData" type="array" mode="overwrite">
                <map ref="resultSet" filter="$columnValue('data') != null">
                    <field name="../../name">
                        <expression value="$columnValue('personid') + '.jpg'"/>
                    </field>
                    <field name="../../content">
                        <expression value="$columnValue('data')"/>
                    </field>
                </map>
            </param>
</map.sqlquery>
</navascript>
	 * @param args
	 * @throws Exception
	 */
	public static void main(String [] args)  throws Exception {
		
		FileInputStream fis = new FileInputStream("/Users/arjenschoneveld/CData.xml");
		NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis);
		navascript.setMapChecker(new MapDefinitionInterrogatorImpl());
		
		navascript.formatNS3(0, System.err);
		//navascript.write(System.err);
	}
}

