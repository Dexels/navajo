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

public class ClientQueueImpl
    extends NavajoClient {

  private final ThreadPool myPool = new ThreadPool(this);

  public ClientQueueImpl() {
  }

  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final String responseId,
                          final ConditionErrorHandler v) throws ClientException {
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
    return myPool.getQueueSize();
  }

  public int getActiveThreads(){
    return myPool.getActiveThreads();
  }

  public void destroy() {
	  myPool.destroy();
  }
  
}
