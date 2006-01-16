package com.dexels.navajo.tipi;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiException
    extends Exception {
  public TipiException() {
  }

  public TipiException(String desc, Throwable cause) {
      super(desc, cause);
    }

  public TipiException(Throwable cause) {
    super(cause);
  }

  public TipiException(String desc) {
    super(desc);
  }
}
