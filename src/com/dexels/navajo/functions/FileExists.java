package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.io.File;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class FileExists extends FunctionInterface {
  public FileExists() {
  }
  public String remarks() {
    return "Returns true if the filename exists. (Either as a file or as a directory";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    String path = (String) getOperand(0);
    if (path==null) {
      return new Boolean(false);
    }
    File f = new File(path);
    return new Boolean(f.exists());
  }
  public String usage() {
    return "FileExists(filename)";
  }

}
