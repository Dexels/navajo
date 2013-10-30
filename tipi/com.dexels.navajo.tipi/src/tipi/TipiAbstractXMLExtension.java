package tipi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public abstract class TipiAbstractXMLExtension extends AbstractTipiExtension
		implements TipiExtension {

	private static final long serialVersionUID = 4653876721511905248L;
	private final List<String> thirdPartyList = new ArrayList<String>();
	private final List<String> includes = new ArrayList<String>();
	private final List<String> requires = new ArrayList<String>();
	private String id = null;
	private String requiresMain = null;
	private String description = null;
	private String project = "";
	
	

	private final static Logger logger = LoggerFactory
			.getLogger(TipiAbstractXMLExtension.class);
	
//	private final static Logger logger = LoggerFactory.getLogger(TipiAbstractXMLExtension.class);

//	private transient ClassLoader extensionClassLoader;
	private boolean isMain;

	public TipiAbstractXMLExtension() {
	}

	@Override
	public final void loadDescriptor() {
		String xmlName =  getClass().getSimpleName() + ".xml";
		loadXMLClass(xmlName);
		// Added for Vaadin (OSGi, actually)
		setExtensionClassloader(getClass().getClassLoader());
	}

	private void loadXMLClass(String xmlName) {
		try {
			InputStream is = getClass().getResourceAsStream(
					xmlName);
			if (is == null) {
				throw new IllegalArgumentException(
						"Problem loading extension: " + xmlName);
			}
			Reader r = new InputStreamReader(is);
			XMLElement xx = new CaseSensitiveXMLElement();
			xx.parseFromReader(r);
			r.close();
			is.close();
			loadXML(xx);
		} catch (XMLParseException e) {
			logger.error("Error: ",e);
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
	}
//	private void loadXML(String xmlName) {
//		try {
//			InputStream is = getClass().getClassLoader().getResourceAsStream(
//					xmlName);
//			if (is == null) {
//				throw new IllegalArgumentException(
//						"Problem loading extension: " + xmlName);
//			}
//			Reader r = new InputStreamReader(is);
//			XMLElement xx = new CaseSensitiveXMLElement();
//			xx.parseFromReader(r);
//			r.close();
//			is.close();
//			loadXML(xx);
//		} catch (XMLParseException e) {
//			logger.error("Error: ",e);
//		} catch (IOException e) {
//			logger.error("Error: ",e);
//		}
//	}

	private void loadXML(XMLElement xx) {
		id = xx.getStringAttribute("id");
		project = xx.getStringAttribute("project");
		isMain = xx.getBooleanAttribute("isMain", "true", "false", false);
		List<XMLElement> desc = xx.getElementsByTagName("description");
		if (desc != null && !desc.isEmpty()) {
			description = desc.get(0).getContent();
		}
		List<XMLElement> includes = xx.getElementsByTagName("includes");
		if (includes != null && !includes.isEmpty()) {
			XMLElement include = includes.get(0);
			for (XMLElement element : include.getChildren()) {
				boolean external = element.getBooleanAttribute("external", "true", "false", false);
				String path = element.getStringAttribute("path");
				if(external && getBundleContext()!=null) {
					logger.debug("Skipping external element: "+path);
				} else {
					this.includes.add(path);
				}
			}
		}

		List<XMLElement> requires = xx.getElementsByTagName("requires");
		if (requires != null && !requires.isEmpty()) {
			XMLElement require = requires.get(0);
			for (XMLElement element : require.getChildren()) {
				this.requires.add(element.getStringAttribute("id"));
			}
		}
	}

	@Override
	@Deprecated
	public final String getConnectorId() {
		return null;
	}

	@Override
	public final List<String> getDependingProjectUrls() {
		return thirdPartyList;
	}

	@Override
	public final String getDescription() {
		return description;
	}

	@Override
	public final String getId() {
		return id;
	}

	@Override
	public final boolean isMainImplementation() {
		return isMain;
	}

	@Override
	public final String[] getIncludes() {
		String[] in = new String[includes.size()];
		for (int i = 0; i < in.length; i++) {
			in[i] = includes.get(i);
		}
		return in;
	}

	// @Deprecated
	// public final List<String> getLibraryJars() {
	// return null;
	// }
	//
	// @Deprecated
	public final List<String> getJars() {
		return null;
	}

	@Override
	public final String getProjectName() {
		return project;
	}

	@Override
	public final List<String> getRequiredExtensions() {
		return requires;
	}

	@Override
	public final String requiresMainImplementation() {
		return requiresMain;
	}

	@Override
	public ClassLoader getExtensionClassloader() {
		return getClass().getClassLoader();
	}

	@Override
	public void setExtensionClassloader(ClassLoader extensionClassLoader) {
//		logger.info("Ignoring setExtensionClassLoader");
	}

}
