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

  private final ThreadPool myPool = new ThreadPool();

  public ClientQueueImpl() {
  }

  public void doAsyncSend(final Navajo in, final String method, final ResponseListener response, final String responseId,
                          final ConditionErrorHandler v) throws ClientException {
    Runnable r = new Runnable() {

      final Navajo nc = in;
      final ResponseListener rc = response;
      final String mc = method;
      final String ic = responseId;

      public final void run() {
//        System.err.println("Starting new asyncsend METHOD: " + method +
//                           ", ID: " + responseId + ", LISTENER: " +
//                           response.getIdentifier());
        try {
          final Navajo n;
          if (v == null) {
            n = doSimpleSend(nc, mc);
          }
          else {
            n = doSimpleSend(nc, mc, v);
          }
          //StringWriter sw = new StringWriter();
          //n.write(sw);
          //System.err.println("ASYNCDSS ("+ Thread.currentThread().toString() +")returned: " + sw.toString().substring(0,Math.min(sw.toString().length(), 800)) + " for mc: " + mc + ", " + method);

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
    myPool.enqueueExecutable(r);
//    t.run();

  }

}
