package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface Tipi extends TipiBase {
  public Navajo getNavajo();
  public void loadData(Navajo n,TipiContext context) throws TipiException;
  public void addAnyInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException;
//  public void performService(TipiContext context) throws TipiException;
  public void performService(TipiContext context, String service) throws TipiException;
  public void addMethod(MethodComponent m);
  public String getName();
  public ArrayList getServices();
  public Tipi getTipiByPath(String path);
  public TipiComponent getTipiComponent(String name);
  public TipiComponent getTipiComponentByPath(String path);
  public TipiLayout getLayout();
  public void clearProperties();
  public void setContainerLayout(LayoutManager layout);
  public LayoutManager getContainerLayout();
  public boolean loadErrors(Navajo n);
  public void setConstraints(Object td);
 }

