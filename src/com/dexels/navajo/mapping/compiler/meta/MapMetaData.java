package com.dexels.navajo.mapping.compiler.meta;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Vector;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MapMetaData {

	protected HashMap<String, MapDefinition> maps = new HashMap<String, MapDefinition>();
	
	public MapMetaData() {
		// Create empty MapDefinition.
		MapDefinition empty = new MapDefinition(this);
		empty.tagName = "__empty__";
		empty.objectName = "null";
		maps.put("__empty__", empty);
	}
	
	public MapDefinition getMapDefinition(String name) {
		return maps.get(name);
	}
	
	public void generateCode(XMLElement in, XMLElement out) {
		maps.get("__empty__").generateCode(in, out);
	}
	
	public static void main(String [] args) throws Exception {
		String example = 
		"<navascript>" +
		"  <message name=\"aap\" mode=\"ignore\" type=\"array\"/>" + 
		"  <map:navajomap/>" + 
	    "  <map:sqlquery datasource=\"sportlinkkernel\"> " +
		"   <sqlquery:set field=\"query\">" +
		"      SELECT * FROM person WHERE personid = ?" +
		"   </sqlquery:set>" + 
		"   <sqlquery:addParameter value=\"'CHGP12Y'\"/> " +
		"   <map:navajomap/>" + 
		"   <message name=\"Result\"> " +
		"     <sqlquery:resultSet> " +
		"        <property name=\"LastName\" direction=\"out\"> " +
		"           <expression value=\"$columnValue('lastname')\"/> " +
		"        </property>" +
		"     </sqlquery:resultSet>" + 
		"   </message>" +
		"  </map:sqlquery>" + 
		"</navascript>";

		MapMetaData mmd = new MapMetaData();
		
		MapDefinition md = new MapDefinition(mmd);
		md.tagName = "sqlquery";
		md.objectName = "com.dexels.navajo.adapter.SQLMap";
		
		MapDefinition md2 = new MapDefinition(mmd);
		md2.tagName = "navajomap";
		md2.objectName = "com.dexels.navajo.adapter.NavajoMap";

		mmd.maps.put("sqlquery", md);
		mmd.maps.put("navajomap", md2);
		
//		Add value definitions.
		ValueDefinition vd1 = new ValueDefinition("datasource", "string", false, "in");
		md.values.put(vd1.getName(), vd1);
		ValueDefinition vd2 = new ValueDefinition("query", "stringliteral", false, "in");
		md.values.put(vd2.getName(), vd2);
		ValueDefinition vd3 = new ValueDefinition("resultSet", "map:resultrow []", false, "out");
		md.values.put(vd3.getName(), vd3);

//		Add method definitions.
		HashMap<String, ParameterDefinition> pds = new HashMap<String, ParameterDefinition>();
		ParameterDefinition pd1 = new ParameterDefinition("value", "parameter", "object", true, "in");
		pds.put(pd1.getName(), pd1);
		MethodDefinition mds1 = new MethodDefinition("addParameter", pds);
		System.err.println(">>>>>>>>>>>>>>>>>>>>>> Putting in map: " +  mds1.getName());
		md.methods.put(mds1.getName(), mds1);

		XMLElement in = new CaseSensitiveXMLElement();
		in.parseString(example);

		XMLElement result = new CaseSensitiveXMLElement();
		result.setName("tsl");
		mmd.generateCode(in, result);

		StringWriter sw = new StringWriter();
		result.write(sw);

		System.err.println(sw.toString());
	}
}
