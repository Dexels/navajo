package com.dexels.navajo.server.enterprise.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

public class MetricsManager implements Mappable {

	private final static Map<String,HasMetrics> registeredModules = new HashMap<String,HasMetrics>();
	
	public static void addModule(String moduleName, HasMetrics hm) {
		registeredModules.put(moduleName, hm);
	}
	
	public Map<String,String> getModuleMetrics(String name) throws Exception {
		if ( registeredModules.get(name) == null ) {
			throw new Exception("Could not find metrics for module: " + name);
		}
		return registeredModules.get(name).getMetrics();
	}
	
	public Iterator<ModuleMetrics> getModules() {
		List<ModuleMetrics> allMetrics = new ArrayList<ModuleMetrics>();
		for ( String s: registeredModules.keySet() ) {
			allMetrics.add(new ModuleMetrics(s, registeredModules.get(s)));
		}
		return allMetrics.iterator();
	}
	
	
	public static String getStatus() {
		StringBuffer sb = new StringBuffer();
		sb.append("NavajoInstance : " + TribeManagerFactory.getInstance().getMyUniqueId() + "\n");
		Set<String> allModules = registeredModules.keySet();
		for ( String mod : allModules) {
			sb.append("\t Module : " + mod + "\n");
			Set<String> metrics = registeredModules.get(mod).getMetrics().keySet();
			for ( String metric : metrics ) {
				sb.append("\t\t" + metric + " : " + registeredModules.get(mod).getMetrics().get(metric) + "\n");
			}
		}
		sb.append("\n");
		return sb.toString();
	}

	public static void removeModule(String module) {
		registeredModules.remove(module);
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
