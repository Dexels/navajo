package com.dexels.navajo.tipi;

import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface TipiDataComponent
    extends TipiComponent {
  public Navajo getNavajo();

  public void loadData(Navajo n, TipiContext context) throws TipiException;

  public void performService(TipiContext context, String tipiPath, String service, boolean breakOnError, TipiEvent event) throws TipiException, TipiBreakException;

  public ArrayList getServices();

  public void addService(String service);

  public void removeService(String service);

  public TipiDataComponent getTipiByPath(String path);

  public void clearProperties();

  public boolean loadErrors(Navajo n);

  public void autoLoadServices(TipiContext context, TipiEvent event) throws TipiException;

  public void tipiLoaded();

  public void replaceLayout(TipiLayout tl);

  public void refreshLayout();

  public boolean listensTo(String service);

  public boolean hasProperty(String path);
}
