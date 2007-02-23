package com.dexels.navajo.adapter.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Dispatcher;

public class FileStore implements MessageStore {

	private static String path = null; 
	private static Object semaphore = new Object();
	
	public FileStore() {
		synchronized (semaphore) {
			if ( path == null ) {
				path = Dispatcher.getInstance().getNavajoConfig().getRootPath() + "/adapterqueue";
				File f = new File(path);
				f.mkdir();
			}
		}
	}
	
	public Queable getNext() {
				
		Queable q = null;
		synchronized ( path ) {
			File queue = new File(path);
			File [] files = queue.listFiles();
			System.err.println("Getting work from store, size: " + files.length);
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
					q = (Queable) ois.readObject();
					ois.close();
					System.err.println("Read object: " + q.hashCode());
					f.delete();
					Binary b = q.getRequest();
					if ( b != null ) {
						b.removeRef();
					}
					System.err.println("Delete file");
					return q;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return null;
	}
	
	public void putMessage(Queable handler) {
		System.err.println("Putting work in store: " + handler.getClass().getName());
		
		synchronized ( path ) {
			File f = new File(path + "/" + handler.hashCode() + "_" + System.currentTimeMillis() + ".queue");
			try {
				// Persist request data, make sure binary base file does not get garbage collected.
				String fileRef = handler.getRequest().getTempFileName(true);
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
				System.err.println("Read object: " + q.hashCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	public int getSize() {
		File queue = new File(path);
		File [] files = queue.listFiles();
		return files.length;
	}

}
