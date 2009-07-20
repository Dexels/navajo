package tipi;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiUserInterface;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.components.swingimpl.TipiScreen;
import com.dexels.navajo.tipi.swingclient.SwingClient;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class SwingTipiApplicationInstance extends TipiApplicationInstance {

	private String definition;
	private String definitionPath;
	private List<String> args;
	private TipiApplet appletRoot;
	private RootPaneContainer otherRoot;

	public SwingTipiApplicationInstance(String definition, String definitionPath, List<String> args, TipiApplet appletRoot, RootPaneContainer otherRoot) {
		this.definition = definition;
		this.definitionPath = definitionPath;
		this.args = args;
		this.appletRoot = appletRoot;
		this.otherRoot = otherRoot;
	}

	@Override
	public TipiContext createContext() throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		Map<String, String> properties = parseProperties(args);

		if (definitionPath == null) {
			definitionPath = definition;
		}
		SwingTipiContext context = null;
		context = new SwingTipiContext(this, null);
		context.setAppletRoot(appletRoot);
		context.setOtherRoot(otherRoot);
		SwingTipiUserInterface stui = new SwingTipiUserInterface(context);
		SwingClient.setUserInterface(stui);
		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
		// context.parseRequiredIncludes();
		// context.processRequiredIncludes();

		context.processProperties(properties);

		System.err.println("Openingin definition: " + definition+" resolve to url: "+context.getTipiResourceURL("definitionPath")+"");
		InputStream tipiResourceStream = context.getTipiResourceStream(definitionPath);
		if (tipiResourceStream == null) {
			System.err.println("Error starting up: Can not load tipi. Resource not found: "+definitionPath);
			System.err.println("Codebase: "+context.getTipiResourceLoader());
			String fatalErrorMsg="No connection allowed to server by security software, check your connection and security settings.";
			try {
				String msg = System.getProperty("fatalSystemErrorMessage");
				if(msg!=null) {
					fatalErrorMsg = msg;
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			tipiResourceStream = context.getTipiResourceStream("init.xml");
			if (tipiResourceStream == null) {
				System.err.println("Still failed");
				context.showFatalStartupError(fatalErrorMsg);
				context.shutdown();
			} else {
				System.err.println("recovered");
			}
		} else {
			try {
				context.parseStream(tipiResourceStream, definition, false);
			} catch (XMLParseException e) {
				e.printStackTrace();
			} catch (TipiException e) {
				e.printStackTrace();
			}
		}
		return context;
	}

}
