package com.dexels.navajo.tipi;
import java.awt.*;
import com.dexels.navajo.tipi.impl.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiEventMapper {
  public void registerEvents(TipiComponent tc, ArrayList eventList);
//  public void defaultRegisterEvents(Component c);
//  public void defaultRegisterEvent(Component c, TipiEvent te);
}