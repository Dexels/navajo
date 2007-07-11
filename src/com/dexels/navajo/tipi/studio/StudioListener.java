package com.dexels.navajo.tipi.studio;

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
public interface StudioListener extends ComponentSelectionListener {
	public void addComponentSelectionListener(ComponentSelectionListener cs);

	public void removeComponentSelectionListener(ComponentSelectionListener cs);
}