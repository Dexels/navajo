package com.dexels.navajo.adapter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class XMLStreamMap implements Mappable {
	private String docSpecUrl = "http://www.w3.org/";
	private String attribName;
	public int indent = 2;
	private int current_indentation = 0;
	private XMLStreamWriter xtw;
	public Binary content = null;
	private boolean newLineFlag = false;  // flag if we're on a new line

	// @Override
	public void kill() {
		// TODO Auto-generated method stub
	}

	// @Override
	public void load(Access access) throws MappableException, UserException {
		try {
			content = new Binary();
			OutputStream ob = content.getOutputStream();
			XMLOutputFactory xof = XMLOutputFactory.newInstance();
			OutputStreamWriter out = new OutputStreamWriter(ob, "UTF8");
			xtw = xof.createXMLStreamWriter(out);
			xtw.writeStartDocument("UTF-8", "1.0");
			xtw.setPrefix("", docSpecUrl);
			xtw.writeCharacters("\n");
		} catch (Exception e) {
			throw new UserException(450, e.getMessage());
		}
	}
	
	public void setIndent(int indent){
		this.indent = indent;
	}

	public void setStartElement(String name) throws UserException {
		try {
			indent();
			xtw.writeStartElement(docSpecUrl, name);
			current_indentation += indent;
			newLineFlag = false;
		} catch (Exception e) {
			throw new UserException(452, e.getMessage());
		}
	}
	
	public void setEndElement(boolean b) throws UserException {
		try {
			current_indentation -= indent;
			if(newLineFlag){
				indent();
			}
			xtw.writeEndElement();
			setNewline(true);			
		} catch (Exception e) {
			throw new UserException(452, e.getMessage());
		}
	}
	
	public void setAttributeName(String attribName){
		this.attribName = attribName;
	}
	
	public void setAttributeValue(String attribValue) throws UserException{
		setAttribute(attribName, attribValue);
	}
	
	private void setAttribute(String name, String value) throws UserException{
		try{
			if(name != null && !"".equals(name) && value != null){
				xtw.writeAttribute(name, value);
			}
		}catch(Exception e){
			throw new UserException(454, e.getMessage());
		}
	}
	
	public void setNewline(boolean b) throws UserException{
		try {
			newLineFlag = true;
			xtw.writeCharacters("\n");
		} catch (Exception e) {
			throw new UserException(453, e.getMessage());
		}
	}

	private void indent() throws UserException {
		try {
			for (int i = 0; i < current_indentation; i++) {
				xtw.writeCharacters(" ");
			}
		} catch (Exception e) {
			throw new UserException(451, e.getMessage());
		}
	}

	// @Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public Binary getContent() throws UserException {
		try{
			xtw.flush();
			xtw.close();
			return content;
		}catch(Exception e){
			throw new UserException(455, e.getMessage());
		}		
	}
	
	public static void main(String[] args){
	try{
		XMLStreamMap map = new XMLStreamMap();
		map.load(null);
		map.setIndent(1);
		
		map.setStartElement("TOP");
		map.setNewline(true);
		for(int i=0;i<10000000;i++){
			map.setStartElement("LEVEL_ONE");
			map.setAttribute("code", ""+i);
			
			map.setNewline(true);
			map.setStartElement("LEVEL_TWO");
			map.setNewline(true);
			map.setStartElement("LEVEL_THREE");
			
			map.setEndElement(true);
			map.setEndElement(true);
			map.setEndElement(true);
			if(i%1000 == 0){
				System.err.println("at: " + i);
			}
		}
		map.setEndElement(true);
		
		Binary content = map.getContent();
		content.write(new FileOutputStream("c:/serial-xml.xml"));
		System.err.println("done..");
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}

}
