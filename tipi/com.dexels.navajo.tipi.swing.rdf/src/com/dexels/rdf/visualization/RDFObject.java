package com.dexels.rdf.visualization;

import java.util.*;

import com.dexels.navajo.document.Message;

public class RDFObject {

	private ArrayList<RDFObject> objects = new ArrayList<RDFObject>();
	private ArrayList<RDFObject> subjects = new ArrayList<RDFObject>();
	private HashMap<String, String> detailMap = new HashMap<String, String>();
	private HashMap<RDFObject, ArrayList<String>> connections = new HashMap<RDFObject, ArrayList<String>>();
	private String name;
	private String type;
	private String url;
	private int id;
	
	public RDFObject(String url, String name, String type){
		this.name = name;
		this.type = type;
		this.url = url;		
	}
	
	public void addObject(RDFObject obj){
		objects.add(obj);
	}
	
	public void addSubject(RDFObject subj){
		subjects.add(subj);
	}
	
	public void addConnection(RDFObject obj, String conn){
		ArrayList<String> cons = connections.get(obj);
		if(cons == null){
			cons = new ArrayList<String>();
		}
		if(!cons.contains(conn)){
			cons.add(conn);
		}
		connections.put(obj, cons);
	}
	
	public ArrayList<String> getConnectionsForObject(RDFObject obj){
		ArrayList<String> conn = connections.get(obj);
		ArrayList<String> result = new ArrayList<String>();
		
		if(conn == null){
			return conn;
		}
		for(int i=0;i<conn.size();i++){
			String c = conn.get(i);
			if(c.indexOf("/") > -1 && !c.endsWith("/")){
				c = c.substring(c.lastIndexOf("/") + 1);
			}
			result.add(c);
		}
		
		return result;
	}
	
	public ArrayList<String> getConnectionURLForObject(RDFObject obj){
		return connections.get(obj);
	}
	
	public ArrayList<RDFObject> getObjects(){
		return objects;
	}
	
	public ArrayList<RDFObject> getSubjects(){
		return subjects;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public String getType(){
		return this.type;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setProperties(Message details){
		if(details != null){
			detailMap.clear();
			for(int i=0;i<details.getArraySize();i++){
				Message detail = details.getMessage(i);
				String predicate = detail.getProperty("Predicate").getValue();
				predicate = getName(predicate);
				String object = detail.getProperty("Object").getValue();
				detailMap.put(predicate, object);
//				logger.info("Adding property: " + predicate + ", " + object);
			}
		}
	}
	
	public HashMap<String, String> getProperties(){
		return detailMap;
	}
	
	
	private String getName(String objectURL) {
		String name = null;
		Iterator<String> it = RDFGraph.namespaces.keySet().iterator();
		int max_length = 0;
		while (it.hasNext()) {
			String key = it.next();
			String url = RDFGraph.namespaces.get(key);
			if (objectURL.startsWith(url) && url.length() > max_length) {
				max_length = url.length();
				name = objectURL.substring(max_length);
			}
		}		
		return name;
	}

}

