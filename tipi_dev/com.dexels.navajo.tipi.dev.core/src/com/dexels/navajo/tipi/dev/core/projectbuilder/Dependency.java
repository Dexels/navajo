package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class Dependency {
	
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String type;
	private final String classifier;
	private URL url;

	public Dependency(String url) throws MalformedURLException {
		this.url = new URL(url);
		String[] p1 = url.split(":");
		if(p1.length!=2) {
			throw new MalformedURLException("URL: "+url+" is invalid, it should have exactly 1 ':'");
		}
		if(!"mvn".equals(p1[0])) {
			throw new MalformedURLException("URL: "+url+" is invalid, only mvn urls allowed");
		}
		// TODO support repository (using '!')
		String rest = p1[1];
//		StringTokenizer st = new StringTokenizer(rest,"/");
		String[] parts = rest.split("/");
		groupId = parts[0];
		artifactId = parts[1];
		version = parts[2];
		if(parts.length>3) {
			String tType =parts[3];
			if("".equals(tType)) {
				type = null;
			} else {
				type = tType;
			}
			if(parts.length>4) {
				classifier = parts[4];
			} else {
				classifier=null;
			}
 		} else {
			type=null;
			classifier=null;
		}
		
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public String getType() {
		return type;
	}

	public String getClassifier() {
		return classifier;
	}

	public URL getUrl() {
		return url;
	}
	
	public String getFileName() {
		String ttype = type==null|"".equals(type)?"jar":type;
		String classifier_suffix = classifier==null?"":("-"+classifier);
		return artifactId+"_"+version+classifier_suffix+"."+ttype;
	}

	public String getFileNameWithVersion() {
		String ttype = type==null|"".equals(type)?"jar":type;
		String classifier_suffix = classifier==null?"":("-"+classifier);
		return artifactId+"_"+version+classifier_suffix+"__V"+version+"."+ttype;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("group: "+getGroupId()+"\n");
		res.append("artifact: "+getArtifactId()+"\n");
		res.append("version: "+getVersion()+"\n");
		res.append("type: "+getType()+"\n");
		res.append("classifier: "+getClassifier()+"\n");
		res.append("FileName: "+getFileName()+"\n");
		return res.toString();
	}
	public static void main(String[] args) throws MalformedURLException {
		Dependency d = new Dependency("mvn:com.miglayout/miglayout/3.7.4//swing");
		System.err.println("D: "+d.toString());
	}
	
}
