package tipi;

import java.io.IOException;

import navajo.ExtensionDefinition;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

public interface TipiApplicationInstance {
	public TipiContext getCurrentContext();
	public void setCurrentContext(TipiContext currentContext);
	public void startup() throws IOException, TipiException;

	public TipiContext createContext() throws IOException;
	
	public void dispose(TipiContext t);
	public String getDefinition();
	
	public void reboot() throws IOException, TipiException;
}
