package com.dexels.navajo.repository.file.impl;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class WatchDir implements Closeable {

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private final boolean recursive = true;
	private boolean trace = false;
	private final FileRepositoryInstanceImpl fileRepository;
	
	private final static Logger logger = LoggerFactory
			.getLogger(WatchDir.class);
	
	
	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
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

	public WatchDir(Path dir,  FileRepositoryInstanceImpl fileRepository) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.fileRepository = fileRepository;
		if (recursive) {
			System.out.format("Scanning %s ...\n", dir);
			registerAll(dir);
			System.out.println("Done.");
		} else {
			register(dir);
		}

		this.trace = true;
	}

	public int refreshApplication(WatchEvent<Path> ev, Path path) throws IOException {
			
			List<String> added = new ArrayList<String>();
			List<String> modified = new ArrayList<String>();
			List<String> deleted = new ArrayList<String>();

			Kind<Path> k = ev.kind();
				
				if (k == ENTRY_CREATE) {
					added.add(path.toFile().getAbsolutePath());
				} else if (k == ENTRY_MODIFY) {
					modified.add(path.toFile().getAbsolutePath());
				} else if (k == ENTRY_DELETE) {
					deleted.add(path.toFile().getAbsolutePath());
				}
				
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(EntryChangeType.ADD.name(), added);
			properties.put(EntryChangeType.MODIFY.name(), modified);
			properties.put(EntryChangeType.DELETE.name(), deleted);

			fileRepository.sendChangeEvent("repository/change", properties);
			return 1;


	}

	
	
	public void processEvents() {
		for (;;) {

			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				System.err.println("WatchKey not recognized!!");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				System.err.println("Displaying events:");
				WatchEvent.Kind kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					continue;
				}

				// Context for directory entry event is the file name of entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);

				// print out event
				System.out.format("%s: %s\n", event.kind().name(), child);
				try {
					refreshApplication(ev,child);
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
					// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (IOException x) {
						// ignore to keep sample readbale
					}
				}
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					break;
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		if(this.watcher!=null) {
			this.watcher.close();
		}
	}
}
