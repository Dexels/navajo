package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.resource.binarystore.BinaryStore;
import com.dexels.navajo.resource.binarystore.BinaryStoreFactory;

/**
 */

public class BinaryStoreValue extends FunctionInterface {

  public BinaryStoreValue() {
	  super();
  }

  
  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
	String tenant = super.getInstance();
	String resourceName = (String) super.getOperand(0);
	BinaryDigest binaryDigest = (BinaryDigest) super.getOperand(1);
	BinaryStore os = BinaryStoreFactory.getInstance().getBinaryStore(resourceName, tenant);
	return os.resolve(binaryDigest);
  }
  
  @Override
public String usage() {
    return "BinaryStoreValue(resource, digest)->Binary";
  }
  @Override
public String remarks() {
    return "A basic get for an object store";
  }
  
}

