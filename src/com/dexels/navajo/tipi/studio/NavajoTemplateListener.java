package com.dexels.navajo.tipi.studio;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface NavajoTemplateListener {
  public void navajoLoaded(String service, Navajo n);
  public void navajoSelected(String service, Navajo n);
  public void navajoRemoved(String service);
}
