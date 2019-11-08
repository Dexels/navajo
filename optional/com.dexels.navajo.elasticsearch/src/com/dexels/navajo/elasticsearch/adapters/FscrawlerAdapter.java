package com.dexels.navajo.elasticsearch.adapters;

import java.io.File;
import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class FscrawlerAdapter implements Mappable {

	private Binary binaryFile = null;
	private String id = null;
	private String name = null;
	
	
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() throws MappableException, UserException {
		//end fo the tag
		try {
			if(binaryFile == null) {
				File f = new File("/Users/vgemistos/eclipse-workspace/com.dexels.elasticsearch/src/com/dexels/elasticsearch/test_java_file.txt");
				binaryFile = new Binary(f);
			}
			FscrawlerFactory.getInstance().upload(binaryFile, id, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MappableException("error uploading, id: " + id,e);
		}
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void setBinary(Binary binaryFile) {
		this.binaryFile = binaryFile;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

}
