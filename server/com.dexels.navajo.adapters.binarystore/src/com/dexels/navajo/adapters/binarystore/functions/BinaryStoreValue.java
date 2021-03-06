/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
public Object evaluate(){
	String tenant = super.getInstance();
	String resourceName = getStringOperand(0);
	BinaryDigest binaryDigest = getBinaryDigestOperand(1);
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

