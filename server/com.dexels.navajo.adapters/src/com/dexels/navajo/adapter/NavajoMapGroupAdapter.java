/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

class AppendNavajoMap {
	
	public AppendNavajoMap(String messageOffset, String appendTo) {
		this.append = messageOffset;
		this.appendTo = appendTo;
	}
	
	public final String append;
	public final String appendTo;

}

public class NavajoMapGroupAdapter implements Mappable, NavajoMapResponseListener {

	private Map<String, NavajoMap> joinedMaps = new ConcurrentHashMap<>();
	private Map<String, Boolean> finishedMap = new ConcurrentHashMap<>();
	private Map<String, AppendNavajoMap> appendMap = new ConcurrentHashMap<>();
	
	private Object hasResult = new Object();
	private NavajoMap resultMap = null;
	private int started = 0;
	private int finished = 0;
	public int join;
	
	public String id;
	public String appendTo;
	public String messagePointer;
	public com.dexels.navajo.adapter.navajomap.MessageMap [] messages;
	
	private static final Logger logger = LoggerFactory.getLogger(NavajoMapGroupAdapter.class);

	private static final String FIRST_RESULT = "_ANY_";
	private int sequence = 0;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		// Check if appendMap was specified.
		if ( appendMap.size() > 0 ) {
			for (Entry<String,AppendNavajoMap> entry : appendMap.entrySet()) {
				NavajoMap nm = null;
				AppendNavajoMap value = entry.getValue();
				if ( id.equals(FIRST_RESULT) ) {
					waitForFirstResult();
					nm = resultMap;
				} else {
					nm = joinedMaps.get(id);
				}
				if (value.appendTo != null) {
					nm.setAppendTo(value.appendTo);
				}
				nm.setAppend(value.append);
			}
		}
		Iterator<NavajoMap> all = joinedMaps.values().iterator();
		while ( all.hasNext() ) {
			NavajoMap nm = all.next();
			// Clear responselistener.
			nm.setMyResponseListener(null);
		}
		joinedMaps.clear();
		finishedMap.clear();
	}

	@Override
	public void kill() {
		try {
			store();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	} 
	
	public void setJoinedMap(NavajoMap nm) {
		if ( nm.getId() == null ) {
			id = (sequence++) + "";
			nm.setId(id);
			logger.warn("Cannot add NavajoMap without id to navajomapgroup. Generating id: {}", id);
		}
		logger.debug("Adding NavajoMap: {} with id: {}",nm, nm.getId());
		finishedMap.put(nm.getId(), false);
		nm.setMyResponseListener(this);
		joinedMaps.put(nm.getId(), nm);
		started++;
	}

	@Override
	public void onNavajoResponse(NavajoMap nm) {
		logger.debug("Received response for NavajoMap: {}", nm.getId());
		synchronized (hasResult) {
			finishedMap.put(nm.getId(), true);
			if ( resultMap == null ) {
				resultMap = nm;
			}
			hasResult.notifyAll();
		}
		finished++;
	}
	
	private void waitForFirstResult() {
		while ( resultMap == null ) {
			synchronized (hasResult) {
				try {
					hasResult.wait(60000);
				} catch (InterruptedException e) {
					logger.error("Error: ", e);
				}
			}
		}
	}
	
	public Object getBarrier(Integer timeout) {
		long start = System.currentTimeMillis();
		while ( started != finished && ( timeout == -1 || ( System.currentTimeMillis() - start ) < timeout ) ) {
			synchronized (hasResult) {
				try {
					hasResult.wait(timeout);
				} catch (InterruptedException e) {
					logger.error("Error: ", e);
				}
			}
		}
		logger.debug("Leaving barrier with {} NavajoMaps still running",(started - finished));
		return (started - finished);

	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setAppendTo(String a) {
		this.appendTo = a;
	}
	
	public void setAppend(String messageOffset) {
		if ( id == null ) {
			id = FIRST_RESULT;
		}
		AppendNavajoMap anm = new AppendNavajoMap(messageOffset, appendTo);
		setAppend(id, anm);
	}
	
	public void setMessagePointer(String p) {
		this.messagePointer = p;
	}
	
	public com.dexels.navajo.adapter.navajomap.MessageMap [] getMessages() throws UserException {
		if ( this.id == null || this.messagePointer == null ) {
			throw new UserException(-1, "Specify navajomap id and set messagepointer before calling getMessages");
		}
		return getMessages(id, messagePointer);
	}
	
	public com.dexels.navajo.adapter.navajomap.MessageMap [] getMessages(String id, String messagePointer) throws UserException {
		NavajoMap nm = joinedMaps.get(id);
		nm.setMessagePointer(messagePointer);
		return nm.getMessages();
	}
	
	private final void setAppend(String id, AppendNavajoMap messageOffset) {
		appendMap.put(id, messageOffset);
	}
	
	public Object getProperty(String id, String n) throws UserException  {
		
		if ( joinedMaps.get(id) == null ) {
			throw new UserException(-1, "Could not find NavajoMap with id: " + id);
		}
		NavajoMap nm = joinedMaps.get(id);
		return nm.getProperty(n);
	}
	
	public Object getProperty(String n) throws UserException {
		waitForFirstResult();
		return resultMap.getProperty(n);
	}

}
