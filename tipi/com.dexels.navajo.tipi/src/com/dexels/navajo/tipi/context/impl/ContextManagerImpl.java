package com.dexels.navajo.tipi.context.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.tipi.context.ContextFactory;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.context.ContextManager;

public class ContextManagerImpl implements ContextManager {
	
	private final Map<String,ContextInstance> contexts = new HashMap<String,ContextInstance>();
	private static ContextManager instance = null;
			
			
	public void activate(Map<String,String> params) {
		ContextFactory.setInstance(this);
	}

	public void deactivate() {
		ContextFactory.setInstance(null);
	}

	public static ContextManager getInstance() {
		return instance;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#addContextInstance(com.dexels.navajo.tipi.context.ContextInstance)
	 */
	@Override
	public void addContextInstance(ContextInstance ci) {
		String context = ci.getContext();
		contexts.put(context, ci);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#removeContextInstance(com.dexels.navajo.tipi.context.ContextInstance)
	 */
	@Override
	public void removeContextInstance(ContextInstance ci) {
		contexts.remove(ci.getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#getContext(java.lang.String)
	 */
	@Override
	public ContextInstance getContext(String context) {
		return contexts.get(context);
	}

}
