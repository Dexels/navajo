package com.dexels.navajo.tipi;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiScreen extends TipiComponent{
  public void addTipi(Tipi t, TipiContext context,Map td);
}