package com.dexels.navajo.sharedstore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SharedStoreSession {

	SharedStoreInterface mySharedStore;
	String parentPath = "";

	public SharedStoreSession(SharedStoreInterface ssi) {
		mySharedStore = ssi;
	}

	public String showImplementation() {
		return mySharedStore.getClass().getName();
	}
	
	public String pwd() {
		return ( parentPath.equals("") ? "/" : parentPath );
	}
	
	public void rm(String file) throws Exception {

		List<SharedStoreSessionEntry> matches = ls(file);
		if ( matches.size() == 0 ) {
			throw new Exception("File " + file + " does not exist.");
		}
		for ( SharedStoreSessionEntry s: matches ) {
			mySharedStore.remove(parentPath, s.getObjectName());
		}
	}
	
	public void rmOlderThan(Date date) {
	    mySharedStore.removeOlderThan(parentPath, date);       
	}
	
	public String rmdir(String name, boolean force) throws Exception {
		String savedPath = parentPath;
		try {
			cdInternal(name);
			if ( !force && ( mySharedStore.getParentObjects(parentPath).length > 0 || mySharedStore.getEntries(parentPath).length > 0 ) ) {
				throw new Exception("Directory not empty: " + parentPath);
			}
			mySharedStore.removeAll(parentPath);
			return parentPath;
		} finally {
			parentPath = savedPath;
		}
	}
	
	public void mkdir(String name) throws Exception {
		String savedPath = parentPath;
		cdInternal(name);
		mySharedStore.createParent(parentPath);
		parentPath = savedPath;
	}
	
	public List<SharedStoreSessionEntry> ls(String filter) {

		Set<String> unique = new HashSet<String>();
		List<SharedStoreSessionEntry> objects = new ArrayList<SharedStoreSessionEntry>();
		String [] all = mySharedStore.getParentObjects(null);

		for ( int i = 0; i < all.length; i++ ) {
			if (!parentPath.equals(all[i])) {
				String candidate = getLevelParent(all[i], parentPath);
				if ( candidate != null && (filter == null || filter.equals("") || candidate.matches(filter))) {
					String formatted =  "[" + candidate + "]" ;
					if (  !unique.contains( candidate ) ){
						objects.add(new SharedStoreSessionEntry(candidate, formatted));
						unique.add(candidate);
					}
				}
			}
		}

		SharedStoreEntry files [] = mySharedStore.getEntries(parentPath);
		for ( int i = 0; i < files.length; i++ ) {
			if ( filter == null || filter.equals("") || files[i].getName().matches(filter) ) {
				// Show lastupdate + contenttype...
				objects.add(new SharedStoreSessionEntry(files[i].getName(), files[i].getLength() + "\t" + new java.util.Date(files[i].getLastModified()) + "\t" + files[i].getContentType() + "\t" +  files[i].getName()));
			}
		}

		return objects;
	}

	public Serializable cat(String file) throws Exception {

		String files [] = mySharedStore.getObjects(parentPath);
		for ( int i = 0; i < files.length; i++ ) {
			if ( files[i].equals(file) ) {
				try {
					Serializable s = mySharedStore.get(parentPath, file);
					return s;
				} catch (SharedStoreException e) {
					throw new Exception("Could not open file: " + file + " (" + e.getMessage() + ")");
				}
			}
		}
		throw new Exception("File not found: " + file);
	}

	public void put(final String sourcePath, final String source) throws Exception {
		try {
			
			File dir = new File(sourcePath);
			File [] files = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.matches(source);
				}
			});
			
			if ( files.length == 0 ) {
				throw new Exception("Could not find any files matching pattern: " + source + " on source path " + dir.getAbsolutePath());
			}
			for ( File url: files ) {
				//File url = new File(source);
				InputStream is = new FileInputStream(url);
				OutputStream os =  mySharedStore.getOutputStream(parentPath, url.getName(), false);
				copyResource(os, is);
			}
			
		} catch (Exception e) {
			throw new Exception("Could not put file: " + source + " (" + e.getMessage() + ")");
		}
	}
	
	public String get(final String source, String destination) throws Exception {

		File parent = new File(destination);
		if ( !parent.exists() ) {
			throw new Exception("Could not find destination path " + destination);
		}
		try {

			String [] matchingObjects = mySharedStore.getObjects(parentPath);
			File url = null;
			int count = 0;
			for ( int i = 0; i < matchingObjects.length; i++ ) {
				if ( matchingObjects[i].matches(source) ) {
					url = new File(parent, matchingObjects[i]);
					OutputStream os = new FileOutputStream(url);
					InputStream is = mySharedStore.getStream(parentPath, matchingObjects[i]);
					count++;
					copyResource(os, is);
				}
			}

			if ( count > 0 ) {
				return "Downloaded " + count + " files to: " + parent.getAbsolutePath();
			} else {
				return "No matching files";
			}
		} catch (Exception e) {
			throw new Exception("Could not get file: " + source + " (" + e.getMessage() + ")");
		}
	}

	private boolean parentExists(String parent) {
		String [] all = mySharedStore.getParentObjects(null);
		for ( int i = 0; i < all.length; i++ ) {
			if ( parent.equals(all[i]) || all[i].startsWith(parent) ) {
				return true;
			}
		}
		return false;
	}


	public void cd(String parent) throws Exception {
		String savedPath = parentPath;
		cdInternal(parent);
		if ( !parentExists(parentPath) ) {
			parentPath = savedPath;
			throw new Exception("Path not found: " + parent);
		}
	}

	private void cdInternal(String parent) {
		if ( parent.equals("/") ) {
			parentPath = "";
			return;
		}
		if ( parent == null || parent.equals("") ) {
			return;
		} else if ( parent.indexOf("/") != -1 ) {
			String subParents [] = parent.split("/");
			for ( int i = 0; i < subParents.length; i++ ) {
				cdInternal(subParents[i]);
			}
		} else if ( parent.equals("..") &&! parentPath.equals("") ) {
			parentPath = ( parentPath.indexOf("/") != -1 ? parentPath.substring(0, parentPath.lastIndexOf("/")) : "");
		} else {
			parentPath = ( parentPath.equals("") ? parent : parentPath + "/" + parent );
		}
	}

	private String getLevelParent(String parent, String path) {
		path = ( path.equals("") ? path : path + "/");
		if ( !parent.startsWith(path) ) {
			return null;
		}
		String s = (  path.equals("") ? parent : 
			parent.substring( ( path.equals("") ? 0 : path.length() ), parent.length()));
		return ( s.indexOf("/") != -1 ? s.substring(0, s.indexOf("/")) : s);
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}

}
