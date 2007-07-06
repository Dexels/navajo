package com.dexels.navajo.adapter.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.enterprise.queue.Queable;

public class FileStore implements MessageStore {

	private static String path = null; 
	private static String deadQueue = null;
	private static Object semaphore = new Object();
	private final HashSet currentObjects = new HashSet();
	private Iterator objectPointer = null;
	
	public FileStore() {
		synchronized (semaphore) {
			if ( path == null ) {
				path = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/adapterqueue";
				// Define deadqueue to put in failures that have more than max retries. 
				// If some problem was solved in the mean time, simply put back file into normal queue.
				deadQueue = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/adapterqueue/failures";
				File f1 = new File(path);
				f1.mkdir();
				File f2 = new File(deadQueue);
				f2.mkdir();
			}
		}
	}
	
	public void rewind() {
		currentObjects.clear();
		File queue = new File(path);
		File [] files = queue.listFiles();

		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if ( f.isFile() ) {
				currentObjects.add(f);
			}
		}

		objectPointer = currentObjects.iterator();

	}
	
	public HashSet getQueuedAdapters() {
		HashSet queuedAdapters = new HashSet();
		synchronized ( path ) {
			File queue = new File(path);
			File [] files = queue.listFiles();

			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if ( f.isFile() ) {
					
					NavajoObjectInputStream ois;
					try {
						ois = new NavajoObjectInputStream(new FileInputStream(f), NavajoConfig.getInstance().getClassloader());
						Queable q = (Queable) ois.readObject();
						// Persist binary file references after reading object.
						q.persistBinaries();
						ois.close();
						QueuedAdapter qa = new QueuedAdapter(q);
						qa.ref = f.getAbsolutePath();
						queuedAdapters.add(qa);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return queuedAdapters;
	}
	
	public Queable getNext() throws Exception {

		if ( objectPointer == null ) {
			throw new Exception("Call rewind() first before calling getNext()");
		}
		
		if (!objectPointer.hasNext() ) {
			objectPointer = null;
			currentObjects.clear();
			return null;
		}
		Queable q = null;
		File f = (File) objectPointer.next();
		try {
			NavajoObjectInputStream ois = new NavajoObjectInputStream(new FileInputStream(f), NavajoConfig.getInstance().getClassloader());
			q = (Queable) ois.readObject();
            // Persist binary file references after reading object.
			q.persistBinaries();
			ois.close();
			//System.err.println("Read object: " + q.getClass().getName() + ", retries " + q.getRetries() + ", max retries " + q.getMaxRetries());
			// Only return object if it is not sleeping
			if ( q.getWaitUntil() < System.currentTimeMillis() ) {
				f.delete();
				System.err.println("Read object: " + q.getClass().getName() + ", retries " + q.getRetries() + ", max retries " + q.getMaxRetries());
				//System.err.println("Delete file");
				return q;
			}
			//System.err.println("This one is sleeping, try next object");
			return getNext();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public void putMessage(Queable handler, boolean failure) {
		//System.err.println(">> Putting work in store: " + handler.getClass().getName());
		
		synchronized ( path ) {
			if ( failure ) {
				// Reset retries if failure, such that it can easily be put back into normal queue..
				handler.resetRetries();
			}
			File f = new File( (failure ? deadQueue : path ) + "/" + handler.hashCode() + "_" + System.currentTimeMillis() + ".queue");
			try {
				// Persist request data, make sure binary base file does not get garbage collected.
				handler.persistBinaries();
				if ( handler.getRequest() != null ) {
					String fileRef = handler.getRequest().getTempFileName(true);
				}
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(handler);
				oos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String [] args) {
		File queue = new File(path);
		File [] files = queue.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				Queable q = (Queable) ois.readObject();
				System.err.println("Read object: " + q.getClass().getName() + ", retries " + q.getRetries() + ", max retries " + q.getMaxRetries());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	public void emptyQueue() {
		synchronized ( path ) {
			File queue = new File(path);
			File [] files = queue.listFiles();
			for (int i = 0; i < files.length; i++) {
				if ( files[i].isFile() ) { // Only delete files.
					files[i].delete();
				}
			}
		}
	}
	
	public int getSize() {
		return currentObjects.size();
	}

}
