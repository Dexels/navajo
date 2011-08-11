package tipi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiSwingDockingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiSwingDockingExtension() throws XMLParseException,
			IOException {
//		loadXML();
	}

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		
		loadDescriptor();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}
