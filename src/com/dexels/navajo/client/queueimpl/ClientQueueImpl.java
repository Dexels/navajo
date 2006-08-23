package com.dexels.navajo.client.queueimpl;

import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.client.ResponseListener;
import com.dexels.navajo.client.ConditionErrorHandler;
import com.dexels.navajo.client.ClientException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClientQueueImpl extends NavajoClient {

  private ThreadPool myPool = null;

  public ClientQueueImpl() {
  }

  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final String responseId,
                          final ConditionErrorHandler v) throws ClientException {
	  
	if ( myPool == null ) {
		myPool = new ThreadPool(this);
	}
	
    Runnable r = new Runnable() {

      final Navajo nc = in.copy();
      final ResponseListener rc = response;
      final String mc = method;
      final String ic = responseId;

      public final void run() {
        try {
          final Navajo n;
          if (v == null) {
            n = doSimpleSend(nc, mc);
          }
          else {
            n = doSimpleSend(nc, mc, v);
          }
          if (response != null) {
            rc.receive(n, mc, ic);
          }
        }
        catch (Throwable ex) {
          ex.printStackTrace();
          if (rc != null) {
            rc.setWaiting(false);
            rc.handleException( (Exception) ex);
          }
        }
      }
    };

    myPool.enqueueExecutable(r, method);
  }

  public int getQueueSize(){
	  if ( myPool != null ) {
		  return myPool.getQueueSize();
	  } else {
		  return 0;
	  }
  }
  
  public int getActiveThreads(){
	  if ( myPool != null ) {
		  return myPool.getActiveThreads();
	  } else {
		  return 0;
	  }
  }
  
  public void destroy() {
	  if ( myPool != null ) {
		  myPool.destroy();
	  }
  }
  
}
