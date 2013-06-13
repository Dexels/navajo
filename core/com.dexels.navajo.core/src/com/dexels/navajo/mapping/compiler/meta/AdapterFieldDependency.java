package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Dependency;

public class AdapterFieldDependency extends Dependency {

	private String type;
	private String adapterClass;
	
	public AdapterFieldDependency(long timestamp, String adapterClass, String type, String id) {
		super(timestamp, id);
		this.type = type;
		this.adapterClass = adapterClass;
		
	}
	
	/**
	 * Check whether this dependency instance has in fact led to multiple dependencies.
	 * 
	 * @return
	 */
	public final boolean hasMultipleDependencies() {
		return ( getMultipleDependencies() != null );
	}
	
	/**
	 * A single adapterfielddependency could lead to multiple dependencies.
	 * 
	 * @return
	 */
	public Dependency [] getMultipleDependencies() {
		return null;
	}
	
	@Override
	public long getCurrentTimeStamp() {
		return 0;
	}

	@Override
	public boolean recompileOnDirty() {
		return false;
	}

	public String getType() {
		return type;
	}

	/**
	 * The id is ALWAYS a Navajo Expression. Try to evaluate it in order to determine the id of the 'dependency'.
	 * @return
	 */
	public String getEvaluatedId() {
		try {
			Operand o = Expression.evaluate(getId(), NavajoFactory.getInstance().createNavajo());
			return o.value+"";
		} catch (Exception e) {
			return "[runtime dependency]";
		}
	}
	
	public String getJavaClass() {
		return adapterClass;
	}

//	public static void main(String [] args) {
//		  Class<? extends AdapterFieldDependency> depClass = AdapterFieldDependency.class;
//		  try {
//			Constructor c = depClass.getConstructor(new Class[]{long.class, String.class, String.class, String.class});
//			AdapterFieldDependency afd = (AdapterFieldDependency) c.newInstance(new Object[]{-1, "Apenoot", "pipo", "de clown"});
//			System.err.println(afd.getJavaClass() + ", " + afd.getTimestamp() + ", " + afd.getType());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
}
