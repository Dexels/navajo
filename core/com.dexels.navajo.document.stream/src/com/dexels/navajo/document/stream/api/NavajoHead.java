package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public class NavajoHead {
	
	private final String name;
	private final String username;
	private final String password;
	private final Map<String, String> attributes;
	private final Map<String, String> transactionAttributes;
	private final Map<String, String> piggybackAttributes;
	private final Map<String, String> asyncAttributes;

	public NavajoHead(String name, String username, String password, Map<String,String> attributes,Map<String,String> transactionAttributes,Map<String,String> piggybackAttributes, Map<String,String> asyncAttributes) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.transactionAttributes = transactionAttributes;
		this.piggybackAttributes = piggybackAttributes;
		this.asyncAttributes = asyncAttributes;
		if (attributes==null) {
			this.attributes = Collections.emptyMap();
		} else {
			this.attributes = attributes;
		}

	}
	public static NavajoHead createDummy() {
		return createSimple("dummy", "dummy", "dummy");
//		return new NavajoHead("dummy","dummy","dummy",Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap());
	}	
	
	public static NavajoHead createSimple(String name, String username, String password) {
		return new NavajoHead(name,username,password,Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap());
	}	
	public static NavajoHead create(Map<String, String> headerAttributes, Map<String, String> transactionAttributes,
			Map<String, String> piggybackAttriutes, Map<String, String> asyncAttributes) {
		// TODO deal with other header content, like piggyback or async

		return new NavajoHead(transactionAttributes.get("rpc_name"),transactionAttributes.get("rpc_usr"),transactionAttributes.get("rpc_pwd"),headerAttributes,transactionAttributes,piggybackAttriutes,asyncAttributes);
	}
	
	public String name() {
		return name;
	}
	
	public String username() {
		return username;
	}
	
	public String password() {
		return password;
	}

	public Map<String,String> attributes() {
		return attributes;
	}

	public void print(Writer w, int indentsize) throws IOException {
		startElement(w, "header", indentsize, 1,this.attributes,false);
		startElement(w, "transaction", indentsize, 2, this.transactionAttributes,true);
		startElement(w, "callback", indentsize, 2, Collections.emptyMap(),true);
		startElement(w, "client", indentsize, 2, Collections.emptyMap(),true);
		endElement(w, "header", indentsize, 1);
	}
	private void endElement(Writer w,String tag, int indentsize,int indent) throws IOException {
		 for (int a = 0; a < indent*indentsize; a++) {
			 w.write(" ");
		 }		
		 w.write("</");
		 w.write(tag);
		 w.write(">\n");
	}
	private void startElement(Writer w, String tag, int indentsize,int indent, Map<String,String> attributes,boolean closed) throws IOException {
		 for (int a = 0; a < indent*indentsize; a++) {
			 w.write(" ");
		 }
		 w.write("<");
		 w.write(tag);
		 w.write("");
		 for (Entry<String,String> e : attributes.entrySet()) {
			w.write(" "+e.getKey()+"=\""+e.getValue()+"\"" );
		}
		
		 w.write(closed?"/>\n":">\n");
	}
	public Map<String, String> getPiggybackAttributes() {
		return piggybackAttributes;
	}
	public Map<String, String> getAsyncAttributes() {
		return asyncAttributes;
	}
}
