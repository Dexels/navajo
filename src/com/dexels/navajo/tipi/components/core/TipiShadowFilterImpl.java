package com.dexels.navajo.tipi.components.core;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.filter.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiShadowFilterImpl extends TipiDataComponentImpl implements TipiDataComponent {

	protected String messagePath = null;
	protected String shadowNavajoName = null;

	protected final List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();
	private String currentMethod;

	public Object createContainer() {
		return null;
	}


	public void setComponentValue(String name, Object object) {
		if("messagePath".equalsIgnoreCase(name)) {
			messagePath = (String) object;
		}
		if("shadowNavajoName".equalsIgnoreCase(name)) {
			shadowNavajoName = (String) object;
		}
		if("simpleFilter".equalsIgnoreCase(name)) {
			propertyFilters.clear();
			try {
				propertyFilters.add(new PropertyFilter("*", (String)object, "string", "contains"));
				reloadNavajo();
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TipiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TipiBreakException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}

	private void reloadNavajo() throws TipiException, TipiBreakException {
		if(getNavajo()!=null) {
			loadData(getNavajo(), currentMethod);
		}
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
		super.loadData(n, method);
		currentMethod = method;
		Navajo result = n.copy();
		Message currentSet = result.getMessage(messagePath);
		Set<Message> toBeRemoved = new HashSet<Message>();
		try {
			for (PropertyFilter pf : propertyFilters) {
				long s = System.currentTimeMillis();
				for (int i = 0; i < currentSet.getArraySize(); i++) {
					Message c = currentSet.getMessage(i);
					if (!pf.compliesWith(c)) {
						toBeRemoved.add(c);
					}
				}
				System.err.println("Scan took: "+(System.currentTimeMillis()-s));
			}
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Message message : toBeRemoved) {
			currentSet.removeMessage(message);
		}
		long s = System.currentTimeMillis();
		getContext().addNavajo(shadowNavajoName, result);
		getContext().loadNavajo(result, shadowNavajoName);
		System.err.println("Load took: "+(System.currentTimeMillis()-s));

	}
}
