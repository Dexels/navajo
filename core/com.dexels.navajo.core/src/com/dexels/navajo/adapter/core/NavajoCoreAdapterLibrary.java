package com.dexels.navajo.adapter.core;

import java.io.InputStream;
import java.util.List;

import com.dexels.navajo.mapping.MappableTreeNode;
import com.dexels.navajo.mapping.MappingException;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.mapping.base.MappableTreeNodeInterface;
import com.dexels.navajo.mapping.compiler.meta.MapMetaDataFactory;
import com.dexels.navajo.mapping.compiler.meta.impl.MapMetaDataImpl;
import com.dexels.navajo.mapping.wrapper.MappingUtilFactory;
import com.dexels.navajo.mapping.wrapper.MappingUtilInterface;
import com.dexels.navajo.script.api.UserException;

import navajo.ExtensionDefinition;



public class NavajoCoreAdapterLibrary implements ExtensionDefinition {

	
	private static final long serialVersionUID = 3364296653069922647L;
	private transient ClassLoader extensionClassLoader = null;
	
	public NavajoCoreAdapterLibrary() {
		MappingUtilFactory.setInstance(new MappingUtilInterface() {
			
			@Override
			public Object getAttributeValue(MappableTreeNodeInterface o, String name,
					Object[] arguments) throws UserException {
				try {
					return MappingUtils.getAttributeObject((MappableTreeNode) o, name, arguments);
				} catch (com.dexels.navajo.server.UserException e) {
					throw new UserException(e.code,e.getMessage(),e);
				} catch (MappingException e) {
					throw new UserException(-1,e.getMessage(),e);
				}
			}
		});
		MapMetaDataFactory.setInstance(new MapMetaDataImpl());

	}
	
	public InputStream getDefinitionAsStream() {
		return getClass().getResourceAsStream("coreadapters.xml");
	}

	public String getConnectorId() {
		return null;
	}

	public List<String> getDependingProjectUrls() {
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	public String getDescription() {
		return "The Enterprise Navajo Adapter Library";
	}

	public String getId() {
		return "Navajo";
	}

	public String[] getIncludes() {
		return null;
	}

	public List<String> getLibraryJars() {
		return null;
	}

	public List<String> getMainJars() {
		return null;
	}

	public String getProjectName() {
		return "Navajo";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String requiresMainImplementation() {
		return null;
	}

	public ClassLoader getExtensionClassloader() {
		return extensionClassLoader;
	}

	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}

}
