package com.dexels.navajo.repository.file.impl;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.diff.EntryChangeType;

public class WatchDir implements Closeable,Runnable {

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private boolean trace = false;
	private final FileRepositoryInstanceImpl fileRepository;
	private boolean isClosed = false;
	private Thread monitorThread;
	private final Path repoRoot;
	
	private final static Logger logger = LoggerFactory
			.getLogger(WatchDir.class);
	
	
	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	private void start() {
		monitorThread = new Thread(this);
		monitorThread.start();

	}
	
	@Override
	public void run() {
		while(!isClosed) {
		try {
			processEvents();
		} catch (Throwable e) {
			logger.error("Error while processing file events. ",e);
			logger.error("Chilling out for a moment an then I'll continue");
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e1) {
			}
		}
		
		}
		System.err.println("Watch thread terminated");
	}
	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				System.out.format("register: %s\n", dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.format("update: %s -> %s\n", prev, dir);
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	public WatchDir(FileRepositoryInstanceImpl fileRepository) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.fileRepository = fileRepository;
		this.repoRoot = Paths.get(fileRepository.getRepositoryFolder().toURI());
		for (String folder : fileRepository.getMonitoredFolders()) {
			File folderF = new File(fileRepository.getRepositoryFolder(),folder);
			System.err.println("Registering folder: "+folderF.getAbsolutePath());
			Path dir = Paths.get(folderF.toURI());
			registerAll(dir);
			
		}
		this.trace = true;
		start();
	}

	public int refreshApplication(List<WatchEvent<Path>> eventList, Path dir) throws IOException {
			

			List<String> added = new ArrayList<String>();
			List<String> modified = new ArrayList<String>();
			List<String> deleted = new ArrayList<String>();
			for (WatchEvent<Path> ev : eventList) {
				Kind<Path> k = ev.kind();
				Path name = ev.context();
				Path child = dir.resolve(name);
				String relativePath = repoRoot.relativize(child).toString();
				if (k == ENTRY_CREATE) {
					added.add(relativePath);
				} else if (k == ENTRY_MODIFY) {
					modified.add(relativePath);
				} else if (k == ENTRY_DELETE) {
					deleted.add(relativePath);
				}
				
			}
				
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(EntryChangeType.ADD.name(), added);
			properties.put(EntryChangeType.MODIFY.name(), modified);
			properties.put(EntryChangeType.DELETE.name(), deleted);
			properties.put("repositoryRoot", repoRoot.toString());
			fileRepository.sendChangeEvent("repository/change", properties);
			return 1;


	}

	
	public void processEvents() throws IOException {
			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			} catch (ClosedWatchServiceException e) {
				logger.warn("Watch dir closed!");
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				throw new IOException("WatchKey not recognized!!");
			}
			List<WatchEvent<Path>> eventList = new ArrayList<WatchEvent<Path>>();
			for (WatchEvent<?> event : key.pollEvents()) {
				System.err.println("Displaying events:");
				WatchEvent.Kind<?> kind = event.kind();

				// TODO provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					continue;
				}

				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);

				eventList.add(ev);
				// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (kind == ENTRY_CREATE) {
					if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
						registerAll(child);
					}
				}
			}
			refreshApplication(eventList, dir);
			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					throw new IOException("Can't listen no more.");
				}
			}
	}

	@Override
	public void close() throws IOException {
		isClosed = true;
		if(this.watcher!=null) {
			this.watcher.close();
		}
		
	}
}
