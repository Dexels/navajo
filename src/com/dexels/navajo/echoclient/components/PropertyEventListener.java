package com.dexels.navajo.echoclient.components;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: Seperate project for Navajo echo client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public interface PropertyEventListener {
    public void propertyEventFired(Property p, String eventType);
}
