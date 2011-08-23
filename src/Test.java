import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleHtmlRendererContext;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dexels.navajo.tipi.cobra.impl.NavajoHtmlRendererContext;

public class Test {

	
	 private HtmlPanel myItem;
	private static UserAgentContext localContext;
	private static SimpleHtmlRendererContext renderingContext;

	  public Object createContainer() {
	    myItem = new HtmlPanel();
	    return myItem;
	  }
	  
	  
	public static void main(String[] args) throws SAXException, IOException {
		Logger.getLogger("org.lobobrowser").setLevel(Level.WARNING);
		
		HtmlPanel panel = new HtmlPanel();
		// This panel should be added to a JFrame or
		// another Swing component.
		
		localContext = new SimpleUserAgentContext();
		renderingContext = new NavajoHtmlRendererContext(panel, localContext,null);
		// Note that document builder should receive both contexts.
		DocumentBuilderImpl dbi = new DocumentBuilderImpl(localContext, renderingContext);
		URL u = new URL("file:///");
//		Reader documentReader = new InputStreamReader(u.openStream());
		Reader documentReader = new FileReader("mailexample.html");
		String documentURI = u.toString();
		// A documentURI should be provided to resolve relative URIs.
		Document document = dbi.parse(new InputSourceImpl(documentReader, documentURI));
		// Now set document in panel. This is what causes the document to render.
		panel.setDocument(document, renderingContext);
		JFrame jf = new JFrame("test");
		jf.setSize(500,300);
		jf.getContentPane().add(panel);
		jf.setVisible(true);
	}
}
