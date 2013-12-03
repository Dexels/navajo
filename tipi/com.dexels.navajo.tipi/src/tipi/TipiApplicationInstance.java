package tipi;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.connectors.TipiConnector;

public interface TipiApplicationInstance {
	public TipiContext getCurrentContext();

	public void setCurrentContext(TipiContext currentContext);

	public void startup() throws IOException, TipiException;

	public TipiContext createContext() throws IOException;

	public void dispose(TipiContext t);

	public String getDefinition();

	public void reboot() throws IOException, TipiException;
	
	public void setEvalUrl(URL context, String relativeUri);

	public void setContextUrl(URL contextUrl);

	public URL getContextUrl();

	public Locale getLocale();
	
	public void close();

	void setDefaultConnector(TipiConnector tipiConnector);

	void addTipiContextListener(TipiContextListener t);

		public void setLocaleCode(String locale);
		public String getLocaleCode();
		public void setSubLocaleCode(String locale);
		public String getSubLocaleCode();

}
