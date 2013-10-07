/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.dexels.navajo.tipi.dev.server.appmanager.impl;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.core.util.zip.ZipUtils;



public class UnsignJarTask  {

	
	private final static Logger logger = LoggerFactory
			.getLogger(UnsignJarTask.class);
	

	public static void downloadDepencency(Dependency d, File repoDir, File destinationFolder, List<String> extraHeaders) throws IOException {
		String assembledName = d.getFileNameWithVersion();
		String tmpAssembledFile = "tmp_"+assembledName;
		String tmpAssembled = d.getArtifactId()+"_"+d.getVersion();
		File dest = new File(destinationFolder,tmpAssembledFile);
		FileOutputStream fos = new FileOutputStream(dest);
		File ff = d.getFilePathForDependency(repoDir);
		File parent = ff.getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
		logger.info("Downloading: "+ff.getAbsolutePath());
		ZipUtils.copyResource(fos, d.getUrl().openStream());
		File tmpDir = new File(destinationFolder,tmpAssembled);
		tmpDir.mkdirs();
		ZipUtils.unzip(dest, tmpDir);
		cleanSigningData(tmpDir,extraHeaders);
		File destinationZip = new File(destinationFolder,assembledName);
		ZipUtils.zipAll(tmpDir,destinationZip);
		FileUtils.copyFileToDirectory(destinationZip,parent);
		FileUtils.deleteDirectory(tmpDir);
		dest.delete();
	}

	private static void cleanSigningData(File tmpDir, List<String> extraHeaders) throws IOException {
		File metainf = new File(tmpDir,"META-INF");
		if(!metainf.exists()) {
			logger.warn("No META-INF. Odd.");
			return;
		}
		File[] list = metainf.listFiles();
		File manifest = null;
		for (File file : list) {
			if(file.getName().toUpperCase().endsWith(".SF")) {
				file.delete();
			}
			if(file.getName().toUpperCase().endsWith(".RSA")) {
				file.delete();
			}
			if(file.getName().equals("MANIFEST.MF")) {
				manifest = file;
			}
		}
		File tmpManifest = new File(metainf,"TMPMANIFEST.MF");
		FileUtils.moveFile(manifest, tmpManifest);
		logger.info("Moving file from: "+manifest.getAbsolutePath()+" to: "+tmpManifest.getAbsolutePath());
		if(manifest!=null) {
			cleanManifest(tmpManifest,manifest,extraHeaders);
		}
		tmpManifest.delete();
	}

	private static void cleanManifest(File manifest, File outputManifest, List<String> extraHeaders) throws IOException {
		
		BufferedReader fr = null;
		List<StringBuffer> manifestheaders;
		try {
			fr = new BufferedReader(new FileReader(manifest));
			String line = null;
			manifestheaders = new ArrayList<StringBuffer>();  
			StringBuffer current = null;
			do {
				line = fr.readLine();
				if(line==null) {
					continue;
				}
				if(line.startsWith(" ")) {
					current.append(line);
					current.append("\n");
				} else {
					if(current!=null) {
						manifestheaders.add(current);
					}
					current = new StringBuffer();
					current.append(line.trim());
					current.append("\n");
				}
			} while(line!=null);
		} finally {
			if(fr!=null) {
				try {
					fr.close();
				} catch (Exception e) {
				}
			}
		}
//		if(checkLine(line)) {
//			System.err.println("line: >"+line+"<");
//		}
		BufferedWriter fw = null;
		try {
			fw = new BufferedWriter(new FileWriter(outputManifest));
			List<StringBuffer> output = new ArrayList<StringBuffer>();
			
			for (StringBuffer header : manifestheaders) {
//				System.err.println(">>"+header.toString()+"<<");
				if(checkLine(header.toString())) {
					output.add(header);
					fw.write(header.toString());
				}
			}
			for (String header : extraHeaders) {
				fw.write(header+"\n");
			}
		} finally {
			if(fw!=null) {
				try {
					fw.close();
				} catch (Exception e) {
				}
			}
		}
	
	}

	private static boolean checkLine(String line) {
		if(line==null) {
			return false;
		}
		if(line.indexOf('\r')!=-1) {
			logger.info("Found CR!");
		}
		if(line.trim().equals("")) {
			return false;
		}
		if(line.trim().startsWith("SHA1-")) {
			return false;
		}
		if(line.trim().startsWith("SHA-")) {
			return false;
		}
		if(line.trim().startsWith("MD5-Digest")) {
			return false;
		}
		
		if(line.startsWith("Name: ")) {
			return false;
		}
		if(line.startsWith("Java-Bean:")) {
			return false;
		}
		
		return true;
	}
	

}
