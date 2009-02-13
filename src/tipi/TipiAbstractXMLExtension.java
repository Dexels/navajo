package tipi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

import navajo.ExtensionDefinition;

public abstract class TipiAbstractXMLExtension extends AbstractTipiExtension implements TipiExtension, ExtensionDefinition {

	private final List<String> thirdPartyList = new ArrayList<String>();
	private final List<String> includes = new ArrayList<String>();
	private final List<String> requires = new ArrayList<String>();
	private String id = null;
	private String requiresMain = null;
	private String description = null;
	private String project = null;
	
	protected TipiAbstractXMLExtension(String description)  {
		try {
			InputStream is = getClass().getResourceAsStream(description);
			Reader r = new InputStreamReader(is);
			XMLElement xx = new CaseSensitiveXMLElement();
			xx.parseFromReader(r);
			r.close();
			is.close();
			loadXML(xx);
		} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private void loadXML(XMLElement xx) {
		id = xx.getStringAttribute("id");
		project = xx.getStringAttribute("project");
		List<XMLElement> desc = xx.getElementsByTagName("description");
		if(desc!=null && !desc.isEmpty()) {
			description = desc.get(0).getContent();
		}
		List<XMLElement> includes = xx.getElementsByTagName("includes");
		if(includes!=null && !includes.isEmpty()) {
			System.err.println("INCLUDES: "+includes);
			XMLElement include = includes.get(0);
			for (XMLElement element : include.getChildren()) {
				this.includes.add(element.getStringAttribute("path"));
			}
		}
		
		List<XMLElement> requires = xx.getElementsByTagName("requires");
		if(requires!=null && !requires.isEmpty()) {
			System.err.println("requires: "+requires);
			XMLElement require = requires.get(0);
			for (XMLElement element : require.getChildren()) {
				this.requires.add(element.getStringAttribute("id"));
			}
		}
	}


	@Deprecated
	public final String getConnectorId() {
		return null;
	}

	public final List<String> getDependingProjectUrls() {
		return thirdPartyList;
	}

	@Deprecated
	public final String getDeploymentDescriptor() {
		return null;
	}

	public final String getDescription() {
		return description;
	}

	public final String getId() {
		return id;
	}

	public final String[] getIncludes() {
		String[] in = new String[includes.size()];
		for (int i = 0; i < in.length; i++) {
			in[i] = includes.get(i);
		}
		return in;
	}

	@Deprecated
	public final List<String> getLibraryJars() {
		return null;
	}

	@Deprecated
	public final List<String> getMainJars() {
		return null;
	}

	public final String getProjectName() {
		return project;
	}

	public final List<String> getRequiredExtensions() {
		return requires;
	}



	public final boolean isMainImplementation() {
		System.err.println("Need to implement main detection");
		return false;
	}

	public final  String requiresMainImplementation() {
		return requiresMain;
	}

}
