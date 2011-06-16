package com.dexels.navajo.tipi;

import java.util.List;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiLayout;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public interface TipiDataComponent extends TipiComponent {
	public Navajo getNavajo();

	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException;

	public String getCurrentMethod();

	/**
	 * @deprecated
	 */
	@Deprecated
	public void performService(TipiContext context, String tipiPath, String service, boolean breakOnError, TipiEvent event,
			long expirationInterval, String hostUrl, String username, String password, String keystore, String keypass)
			throws TipiException, TipiBreakException;

	public List<String> getServices();

	public void addService(String service);

	public void removeService(String service);

	public void clearProperties();

	public boolean loadErrors(Navajo n, String method);


	public void replaceLayout(TipiLayout tl);

	public void refreshLayout();

	public void registerPropertyChild(TipiComponent tipiComponentImpl);

}
