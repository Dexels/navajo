package tipi;

import java.io.IOException;

import com.dexels.navajo.tipi.TipiContext;

public interface TipiApplicationInstance {
	public TipiContext getCurrentContext();
	public void setCurrentContext(TipiContext currentContext);
	public void startup() throws IOException;

	public TipiContext createContext() throws IOException;
	
	public void dispose(TipiContext t);

	
	public void reboot() throws IOException;
}
