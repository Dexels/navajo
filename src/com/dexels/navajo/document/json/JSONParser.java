package com.dexels.navajo.document.json;

/*
 * Auhtor: Arnoud Philip
 * 
 */
import java.io.Reader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.dexels.navajo.document.*;

public class JSONParser {
	private Navajo myNavajo;
	
	public JSONParser(){
		
	}

	public Navajo getNavajo(){
		return myNavajo;
	}
	
	public void parse(Reader r) throws Exception{
		StringWriter sw = new StringWriter();
		
		char[] buffer = new char[1024];
		int read = 0;
		
		while((read = r.read(buffer, 0, buffer.length)) > 0){
			sw.write(buffer, 0, read);
		}
		r.close();
		String json = sw.getBuffer().toString();
		JSONObject jso = new JSONObject(json);
		parse(jso, null);
	}
	
	private void parse(JSONObject jso, String nodeName) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);
				
			if(value instanceof JSONObject){
				if("tml".equals(key)){
					myNavajo = NavajoFactory.getInstance().createNavajo();
					parseNavajo((JSONObject)value, myNavajo);
				}				
			}		
		}
	}
	
	private void parseNavajo(JSONObject jso, Navajo n) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);
					
			if(value instanceof JSONObject){
				if("message".equals(key)){
					Message m = NavajoFactory.getInstance().createMessage(n, "dummy_name");
					n.addMessage(m);
					parseMessage((JSONObject)value, m);
				}	
				if("methods".equals(key)){
					parseMethods((JSONObject)value, n);
				}
			}
			if(value instanceof JSONArray){
				if("message".equals(key)){
					JSONArray jsa = (JSONArray)value;
					for(int i=0;i<jsa.length();i++){
						JSONObject jo = jsa.getJSONObject(i);
						Message m = NavajoFactory.getInstance().createMessage(n, "dummy_name");
						n.addMessage(m);
						parseMessage(jo, m);
					}
				}				
			}
		}
	}
	
	private void parseMessage(JSONObject jso, Message m) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);
					
			if(value instanceof JSONObject){
				if("property".equals(key)){
					Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(),  "dummy_name", "string", "", 1, "", Property.DIR_IN);
					m.addProperty(p);
					parseProperty((JSONObject)value, p);
				}				
			}
			if(value instanceof JSONArray){
				if("message".equals(key)){
					JSONArray jsa = (JSONArray)value;
					for(int i=0;i<jsa.length();i++){
						JSONObject jo = jsa.getJSONObject(i);
						Message element = NavajoFactory.getInstance().createMessage(m.getRootDoc(), "dummy_name");
						m.addElement(element);
						parseMessage(jo, element);
					}
				}			
				if("property".equals(key)){
					JSONArray jsa = (JSONArray)value;
					for(int i=0;i<jsa.length();i++){
						JSONObject jo = jsa.getJSONObject(i);
						Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(),  "dummy_name", "string", "", 1, "", Property.DIR_IN);						
						parseProperty(jo, p);
						m.addProperty(p);
					}
				}	
			}
			if(value instanceof String){
				String v = (String)value;
				if("type".equals(key)){
					m.setType(v);
				}
				if("name".equals(key)){
					m.setName(v);
				}
				if("index".equals(key)){
					if(v != null && !"".equals(v)){
						m.setIndex(Integer.parseInt(v));
					}
				}
				if("mode".equals(key)){
					m.setMode(v);
				}
			}
		}
	}
	
	private void parseProperty(JSONObject jso, Property p) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);
					
			if(value instanceof JSONObject){
				if("option".equals(key)){
					Selection s = NavajoFactory.getInstance().createSelection(p.getRootDoc(), "dummy_selection", "dummy_value", false);
					p.addSelection(s);
					parseOption((JSONObject)value, s);
				}
			}
			if(value instanceof JSONArray){			
				if("option".equals(key)){
					JSONArray jsa = (JSONArray)value;
					for(int i=0;i<jsa.length();i++){
						JSONObject jo = jsa.getJSONObject(i);
						Selection s = NavajoFactory.getInstance().createSelection(p.getRootDoc(), "dummy_selection", "dummy_value", false);
						parseOption(jo, s);
						p.addSelection(s);
					}
				}	
			}
			if(value instanceof String){
				// Navajo (tsl) attributes
				String v = (String)value;
				if("type".equals(key)){
					p.setType(v);
				}
				if("name".equals(key)){
					p.setName(v);
				}
				if("direction".equals(key)){
					p.setDirection(v);
				}
				if("description".equals(key)){
					p.setDescription(v);
				}
				if("length".equals(key)){
					if(v != null && !"".equals(v)){
						p.setLength(Integer.parseInt(v));
					}
				}
				if("cardinality".equals(key)){
					p.setCardinality(v);
				}
				if("subtype".equals(key)){
					p.setSubType(v);
				}
				if("value".equals(key)){
					p.setValue(v);
				}
			}
		}
	}
	
	private void parseOption(JSONObject jso, Selection s) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);

			if(value instanceof String){
				// Navajo (tsl) attributes
				String v = (String)value;
				
				if("name".equals(key)){
					s.setName(v);
				}
				if("value".equals(key)){
					s.setValue(v);
				}
				if("selected".equals(key)){
					s.setSelected("1".equals(v));
				}
			}
		}
	}
	
	private void parseMethods(JSONObject jso, Navajo n) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);
			
			if(value instanceof JSONObject){
				if("method".equals(key)){
					Method meth = NavajoFactory.getInstance().createMethod(myNavajo, "dummy_method", "DUMMY_SERVER;");
					parseMethod((JSONObject)value, meth);
					n.addMethod(meth);
				}
			}
			if(value instanceof JSONArray){			
				if("method".equals(key)){
					JSONArray jsa = (JSONArray)value;
					for(int i=0;i<jsa.length();i++){
						JSONObject jo = jsa.getJSONObject(i);
						Method meth = NavajoFactory.getInstance().createMethod(myNavajo, "dummy_method", "DUMMY_SERVER;");
						parseMethod(jo, meth);
						n.addMethod(meth);
					}
				}	
			}
		}
	}
	
	private void parseMethod(JSONObject jso, Method m) throws Exception{
		Iterator<String> keys = jso.keys();
		while(keys.hasNext()){
			String key =  keys.next();
			Object value = jso.get(key);

			if(value instanceof String){
				// Navajo (tsl) attributes
				String v = (String)value;
				
				if("name".equals(key)){
					m.setName(v);
				}				
			}
		}
	}
}
