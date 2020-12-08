/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipi;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.connectors.TipiConnector;
import com.dexels.navajo.tipi.locale.LocaleListener;

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

	public void addLocaleListener(LocaleListener l);

	public void removeLocaleListener(LocaleListener l);

}
