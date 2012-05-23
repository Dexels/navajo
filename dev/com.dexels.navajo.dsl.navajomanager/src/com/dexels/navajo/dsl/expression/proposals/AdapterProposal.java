package com.dexels.navajo.dsl.expression.proposals;

import java.util.ArrayList;
import java.util.List;

//<map>
//<tagname>mail</tagname>
//<object>com.dexels.navajo.adapter.MailMap</object>
//<values>
//    <value name="mailServer" type="string" required="true" direction="in"/>
//    <value name="queuedSend" type="boolean" required="false" direction="in"/>
//    <value name="ignoreFailures" type="boolean" required="false" direction="in"/>
//    <value name="sender" type="string" required="false" direction="in"/>
//    <value name="subject" type="string" required="false" direction="in"/>
//    <value name="text" type="string" required="false" direction="in"/>
//    <value name="recipients" type="string" required="false" direction="in"/>
//    <value name="attachment" map="attachment" required="false" direction="in"/>
//    <value name="multipleAttachments" map="attachment []" required="false" direction="in"/>
//</values>
//</map>

public class AdapterProposal implements Comparable<AdapterProposal>{
	private String tagName;
	private final List<AdapterValueEntry> values = new ArrayList<AdapterValueEntry>();
	private final List<AdapterMethodEntry> methods = new ArrayList<AdapterMethodEntry>();
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public void addValueEntry(AdapterValueEntry e) {
		values.add(e);
	}

	public void addMethodEntry(AdapterMethodEntry e) {
		methods.add(e);
	}

	
	@Override
	public int compareTo(AdapterProposal ap) {
		return getTagName().compareTo(ap.getTagName());
	}
	
	public String getRequiredSettersAsProposal() {
		StringBuffer sb = new StringBuffer();
		for (AdapterValueEntry w : values) {
			if("in".equals(w.getDirection()) && w.isRequired()) {
				sb.append(w.getName()+"=\"["+w.getType()+"]\" ");
			}
		}
		return sb.toString();
	}

	
	
	
	public List<String> getSetters() {
		List<String> result = new ArrayList<String>();
		for (AdapterValueEntry w : values) {
			if(w.getDirection().equals("in")) {
				result.add(w.getName());
			}
		}
		return result;
	}
	

	
	public String[] getRequiredSetters() {
		List<String> result = new ArrayList<String>();
		for (AdapterValueEntry w : values) {
			if(w.getDirection().equals("in") && w.isRequired()) {
				result.add(w.getName());
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	public List<String> getMethodProposals() {
		List<String> result = new ArrayList<String>();
		for (AdapterMethodEntry a : methods) {
			String res = "<_"+getTagName()+"."+a.getProposal()+"/>";
			result.add(res);
		}
		return result;
	}
	
	public List<String> getGetterMapRefs() {
		List<String> result = new ArrayList<String>();
		for (AdapterValueEntry w : values) {
			// are 'in' values also implicitely 'out'? In that case, remove this check
			if(w.getDirection().equals("out")) {
				if(w.getMap()!=null) {
					result.add(w.getName());
				}
			}
		}
		return result;
	}
	
	
	public String getGetterMapType(String name) {
		for (AdapterValueEntry w : values) {
			// are 'in' values also implicitely 'out'? In that case, remove this check
//			if(w.getDirection().equals("out")) {
				if(w.getMap()!=null && name.equals(w.getName())) {
					System.err.println("Encountered: "+w.getMap());
					return w.getMap();
				}
//			}
		}
		return null;
	}
	
	public List<String> getGetters() {
		List<String> result = new ArrayList<String>();
		for (AdapterValueEntry w : values) {
			// are 'in' values also implicitely 'out'? In that case, remove this check
			if(w.getDirection().equals("out")) {
				
				if(w.getMap()==null) {
					result.add(w.getName());
				}
			}
		}
		return result;
	}
	
	
	public List<String> getMapRefProposals() {
		List<String> result = new ArrayList<String>();
		for (String ref : getGetterMapRefs()) {
			result.add("<map ref=\""+ref+"\">\n</map>");
		}
		return result;

	}
	
	public String getTypeOfValue(String value) {
		for (AdapterValueEntry w : values) {
			if(w.getName().equals(value)) {
				return w.getType();
			}
		}
		return null;
	}

	public String getFullProposal() {
		return "<map."+getTagName()+" "+getRequiredSettersAsProposal()+">\n</map."+getTagName()+">";
	}
	
	public String getMapIdProposal(String currentId) {
		String rest;
		if(currentId==null) {
			rest = getTagName();
		} else {
			rest = getTagName().substring(currentId.length());			
		}
		return rest+" "+getRequiredSettersAsProposal()+">\n</map."+getTagName()+">";
		
	}
}
