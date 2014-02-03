package com.dexels.navajo.repository.api.util;

import org.osgi.service.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.diff.EntryChangeType;
import com.dexels.navajo.repository.api.diff.RepositoryChange;

import java.util.ArrayList;
import java.util.List;

public class RepositoryEventParser {

	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryEventParser.class);
	
	public static boolean touched(Event e, List<String> paths) {
		List<String> all = extractForChangeTypes(e, new EntryChangeType[]{EntryChangeType.ADD,EntryChangeType.COPY,EntryChangeType.MODIFY});
		if(all==null) {
			return false;
		}
		for (String path : paths) {
			if(all.contains(path)) {
				return true;
			}
		}
		return false;
	}

	private static List<String> extractForChangeTypes(Event e, EntryChangeType[] types) {
		String topic = e.getTopic();
		if(!RepositoryChange.TOPIC.equals(topic)) {
			logger.warn("Attempt to parse repository event with wrong topic. Found: "+topic+" expected: "+RepositoryChange.TOPIC+". Ignoring.");
			return null;
		}
		List<String> all = new ArrayList<String>();
		for (EntryChangeType entryChangeType : types) {
			List<String> a = (List<String>) e.getProperty(entryChangeType.name());
			if(a!=null) {
				all.addAll(a);
			}
		}
		return all;
	}

	public static List<String> filterChanged(Event e, String prefix) {
		List<String> result = new ArrayList<String>();
		List<String> all = extractForChangeTypes(e, new EntryChangeType[]{EntryChangeType.ADD,EntryChangeType.COPY,EntryChangeType.MODIFY});
		for (String path : all) {
			if(path.startsWith(prefix)) {
				result.add(path);
			}
		}
		return result;
	}
	
	public static List<String> filterDeleted(Event e, String prefix) {
		List<String> result = new ArrayList<String>();
		List<String> all = extractForChangeTypes(e, new EntryChangeType[]{EntryChangeType.DELETE});
		for (String path : all) {
			if(path.startsWith(prefix)) {
				result.add(path);
			}
		}
		return result;
	}
}
