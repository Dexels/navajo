package com.dexels.navajo.tipi;

import java.util.*;

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
public interface TipiEventListener {
	public boolean performTipiEvent(String eventtype, Map<String,Object> source, boolean sync) throws TipiException;

	public void eventStarted(TipiExecutable te, Object event);

	public void eventFinished(TipiExecutable te, Object event);
}
