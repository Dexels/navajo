package com.dexels.navajo.tipi;

import com.dexels.navajo.document.Navajo;

//import com.dexels.navajo.document.nanoimpl.*;
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
public interface TipiErrorHandler {
	public String hasErrors(Navajo n);
	public Boolean hasServerErrors(Navajo n);

	// public Object createContainer();
	public void setContext(TipiContext c);

	public TipiContext getContext();
	
    public String getGenericErrorTitle();

	public String getGenericErrorDescription();
	public String getErrorMoreDetailsText();
	public String getErrorLessDetailsText();
	public String getErrorCloseText();
	
	public String getInactivityTitleText();
	public String getInactivityMsgText();
    public void removeContext(TipiContext c);
	


}