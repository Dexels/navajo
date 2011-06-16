package com.dexels.navajo.tipi.connectors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.filter.PropertyFilter;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

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
public class TipiShadowFilterImpl extends TipiBaseConnector {

	protected String messagePath = null;
	protected final List<PropertyFilter> propertyFilters = new ArrayList<PropertyFilter>();

	public Object createContainer() {
		// return new ShadowFilter();
		System.err.println("CREATING A TIPI FILTER!!!\n\n");
		return null;
	}

	public void setComponentValue(String name, Object object) {
		if ("messagePath".equalsIgnoreCase(name)) {
			messagePath = (String) object;
		}

		if ("simpleFilter".equalsIgnoreCase(name)) {
			System.err.println("Adding filter: " + object);
			propertyFilters.clear();
			try {
				propertyFilters.add(new PropertyFilter("*", (String) object, "string", "contains"));
				// reloadNavajo();
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		}
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		// super.loadData(n, method);
		Navajo result = n.copy();
		Message currentSet = result.getMessage(messagePath);
		Set<Message> toBeRemoved = new HashSet<Message>();
		System.err.println("Working: # of filters: " + propertyFilters.size());
		try {
			for (PropertyFilter pf : propertyFilters) {
				long s = System.currentTimeMillis();
				for (int i = 0; i < currentSet.getArraySize(); i++) {
					Message c = currentSet.getMessage(i);
					if (!pf.compliesWith(c)) {
						toBeRemoved.add(c);
					}
				}
				System.err.println("Scan took: " + (System.currentTimeMillis() - s));
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		for (Message message : toBeRemoved) {
			currentSet.removeMessage(message);
		}
		long s = System.currentTimeMillis();
		injectNavajo(service, n);
		System.err.println("Load took: " + (System.currentTimeMillis() - s));

	}

	public String getConnectorId() {
		return "filter";
	}

	public Set<String> getEntryPoints() {
		return null;
	}

	public String getDefaultEntryPoint() {
		return "*";
	}

}
