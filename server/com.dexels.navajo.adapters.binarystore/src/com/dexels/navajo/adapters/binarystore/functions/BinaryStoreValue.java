package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.resource.binarystorage.BinaryStore;
import com.dexels.navajo.resource.binarystorage.BinaryStoreFactory;

/**
 */

public class BinaryStoreValue extends FunctionInterface {

  public static final String DATASOURCEDELIMITER = ":";
  public static final String USERDELIMITER = "@";
  private String dbIdentifier = null;

  public String getDbIdentifier() { return this.dbIdentifier; }
  public void setDbIdentifier(String dbIdentifier) {
      this.dbIdentifier = dbIdentifier;
  }

  public BinaryStoreValue() {
	  super();
  }

  
  @Override
public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
	  String tenant = super.getInstance();
	  String resourceName = (String) super.getOperand(0);
	  String objectName = (String) super.getOperand(1);
		BinaryStore os = BinaryStoreFactory.getInstance().getOpenstackStore(resourceName, tenant);
		return os.get(objectName);
  }
  
  @Override
public String usage() {
    return "SwiftValue(resource, objectname)";
  }
  @Override
public String remarks() {
    return "A basic get for an object store";
  }
  
}

