package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiEventListener;

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
public interface PropertyComponent extends TipiComponent {
	public String getPropertyName();

	public void addTipiEventListener(TipiEventListener listener);

	public void addTipiEvent(TipiEvent te);

	public Property getProperty();

	public void setProperty(Property p);
	
	public Boolean isDirty();

	public void setDirty(Boolean b);

	// public void checkForConditionErrors(Message m);

	// public void validate();

}
