/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi;

import java.util.List;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
	@Override
	public Navajo getNavajo();

	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException;

	public String getCurrentMethod();

	/**
	 * @deprecated
	 */
	@Deprecated
	public void performService(TipiContext context, String tipiPath,
			String service, boolean breakOnError, TipiEvent event,
			long expirationInterval, String hostUrl, String username,
			String password, String keystore, String keypass)
			throws TipiException, TipiBreakException;

	public List<String> getServices();

	public boolean loadErrors(Navajo n, String method);

	public void refreshLayout();

	public void registerPropertyChild(TipiComponent tipiComponentImpl);

}
