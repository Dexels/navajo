package com.dexels.navajo.serviceplugin;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Dependency;

/**
 * JavaPlugin can be used to write scripts directly in Java.
 * Order or precedence is:
 * 1. Check navascript/tsl .xml file in scripts
 * 2. Check compiled java/class file in compiled
 * 3. Check java .java file in scripts
 * 
 * @author arjen
 *
 */
public abstract class JavaPlugin extends CompiledScript {

	private Access myAccess;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(JavaPlugin.class);
	
	
	protected final Message addMessage(Navajo n, String path) {
		return addMessage(n,null,path);
	}
	
	@Override
	public ArrayList<Dependency> getDependentObjects() {
		return null;
	}
	
	protected final Property addProperty(Navajo n, String path, Object value) throws Exception {
		if(path.indexOf("/")==-1) {
			throw NavajoFactory.getInstance().createNavajoException("Invalid property specified: " + path);
		}
		String messagePath = path.substring(0, path.lastIndexOf("/"));
		Message parent = addMessage(n, messagePath);
		
		String propertyName = path.substring(path.lastIndexOf("/")+1,path.length());
		
		Property p;
	
		p = NavajoFactory.getInstance().createProperty(n, propertyName, Property.STRING_PROPERTY,"",99,"",Property.DIR_IN);
		parent.addProperty(p);
		p.setAnyValue(value);
		return p;
		
	}
	
	private final Message addMessage(Navajo n, Message parent, String path) {
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		Message m = null;
		if(parent!=null) {
			m = parent.getMessage(path);
		} else {
			m = n.getMessage(path);
		}
		if(m!=null) {
			return m;
		}
		if(path.indexOf("/")==-1) {
			Message mm =  NavajoFactory.getInstance().createMessage(n, path);
			if(parent==null) {
				try {
					n.addMessage(mm);
				} catch (NavajoException e) {
					logger.error("Error: ", e);
				}
			} else {
				parent.addMessage(mm);
			}
			return mm;
		}
		String parentPath = path.substring(0, path.lastIndexOf("/"));
		String messageName = path.substring(path.lastIndexOf("/")+1,path.length());
		
		Message parentResult = addMessage(n, parent, parentPath);
		
		if(parentResult==null) {
			return null;
		} else {
			return addMessage(n, parentResult,messageName);
		}
		
	}
	
	@Override
	public void execute(Access access) throws Exception {
		myAccess = access;
		Navajo out = process(access.getInDoc());
		access.setOutputDoc(out);
	}

	@Override
	public final void finalBlock(Access access) throws Exception {
		// Do nothing.
	}

	@Override
	public final void setValidations() {
		// Do nothing.
	}

	public Access getAccess() {
		return myAccess;
	}
	
	public abstract Navajo process(Navajo in) throws Exception;
	
}
