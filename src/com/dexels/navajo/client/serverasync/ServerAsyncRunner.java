package com.dexels.navajo.client.serverasync;

import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;

/**
 * The ServerAsyncRunner is the class that controls the execution of webservices that have asynchronous compponents. ie. that don't give
* immediate results, but also progress when executed again. This class will start a webservice and then poll it until it is finished. Upon every poll
* the associated ServerAsyncListener is informed on the current webservice status. When finished, the result is sent to the same ServerAsyncListener.
* This class also uses some logic to adjust the polling interval. ie. When after polling the progress is less than expected, the interval will be
* increased. This also works the other way around and when the estimated time of finishing is less than the polling interval the interval is set to
* half the estimated time of finishing.
 */
public class ServerAsyncRunner
    extends Thread {
  private ClientInterface myClientInterface = null;
  private Navajo myNavajo = null;
  private Navajo myResult = null;
  private String myMethod = null;
  private ServerAsyncListener myListener = null;
  private String myClientId = null;
  private int myPollingInterval = -1;
  //private int maxIter = 2;
  private boolean iterate = true;
  private volatile boolean kill = false;
  int prev_progress = 0;
  long prev_time = 0;
  private int serverIndex = 0;
  private final static int MAX_POLLING_INTERVAL = 30000;


  /**
   * Construct a new ServerAsyncRunner
   * @param client ClientInterface
   * @param in Navajo
   * @param method String
   * @param listener ServerAsyncListener
   * @param clientId String
   * @param pollingInterval int
   */
  public ServerAsyncRunner(ClientInterface client, Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) {
    myClientInterface = client;
    myNavajo = in;
    myMethod = method;
    myListener = listener;
    myClientId = clientId;
    myPollingInterval = pollingInterval;
    serverIndex = client.getAsyncServerIndex();
    if (in.getHeader() != null) {
      in.getHeader().removeCallBackPointers();
    }
  }

  private Navajo doSimpleSend(Navajo n, String method) throws ClientException {
    return myClientInterface.doSpecificSend(n, method,serverIndex);
  }

  /**
   * Main thread
   */
  public void run() {

    try {
      prev_time = System.currentTimeMillis();
      while (isIterating()) {
        if (kill) {
          System.err.println("Kill called in ServerAsyncRunner...");
          myResult.getHeader().setCallBackInterrupt("kill");
          setIterating(false);
        }
        if (myPollingInterval > 0) {
          myNavajo.removeHeader();
          myNavajo.addHeader(myResult.getHeader());
          Navajo temp = doSimpleSend(myNavajo, myMethod);

          if (temp.getMessage("ConditionErrors") != null) {
            System.err.println("Had ConditionErrors in Asyncsend.. ");
            killServerAsyncSend();
            continue;
          }

          Header head = temp.getHeader();
          if (head == null) {
            System.err.println("Received no header. returning and killing thread");
            throw new ClientException( -1, -1, "No async header!");
          }
          if (isFinished(temp)) {
            // Really dont know what I should pass to getCallBackPointer
            if (myListener != null) {
              myListener.setProgress(head.getCallBackPointer(null), 100);
              myListener.receiveServerAsync(temp, myMethod, head.getCallBackPointer(null), myClientId);
            }
            myClientInterface.deRegisterAsyncRunner(myClientId);
            myNavajo.removeHeader();
            return;
          }
          else {
            if (myListener != null) {
              myListener.setProgress(head.getCallBackPointer(null), head.getCallBackProgress());
            }
          }
          checkPollingInterval(head.getCallBackProgress());
          System.err.println("Start sleep");
          try {
            if (myPollingInterval > MAX_POLLING_INTERVAL) {
              myPollingInterval = MAX_POLLING_INTERVAL;
            }
            sleep(myPollingInterval);
          }
          catch (InterruptedException ex1) {
            System.err.println("Interrupted. Continuing with next iteration.");
          }
          System.err.println("End sleep");
        }
      }
    }
    catch (ClientException ex) {
      System.err.println("oooops");
      myListener.handleException(ex);
      return;
    }
    if (myListener != null) {
      myListener.handleException(new ClientException( -1, -1, "Operation aborted"));
    }
  }

  private void checkPollingInterval(int current_progress) {

    int dif = current_progress - prev_progress;
    long now = System.currentTimeMillis();
    long time_dif = now - prev_time;
    prev_time = now;
    long eta = 0;
    if (dif > 0) {
      eta = (100 - current_progress) * (time_dif / dif);
    }
    System.err.println("Previous progress: " + prev_progress + ", Current progress: " + current_progress + ", myPollingInterval: " + myPollingInterval + ", ETA: " + eta);
    if (dif < 1) {
      myPollingInterval = 2 * myPollingInterval;
      prev_progress = current_progress;
      return;
    }
    if (dif < 5) {
      myPollingInterval = (int) (1.5 * myPollingInterval);
      prev_progress = current_progress;
      return;
    }
    if (dif > 20) {
      System.err.println("dif > 20, should speed up polling?");
      if (myPollingInterval > 2500) {
        myPollingInterval = (int) (myPollingInterval / 1.5);
      }
    }
    if (eta < myPollingInterval) {
      System.err.println("ETA is smaller than polling interval, decreasing interval");
      myPollingInterval = (int) (eta / 2.0);
    }
    prev_progress = current_progress;
  }

  private boolean isIterating() {
    return iterate;
  }

  private void setIterating(boolean b) {
    iterate = b;
  }


  /**
   * Start the ServerAsyncRunner
   * @throws ClientException
   * @return String
   */
  public String startAsync() throws ClientException {

    if (myNavajo.getHeader() != null) {
      myNavajo.removeHeader();
    }

    myResult = doSimpleSend(myNavajo, myMethod);
    Header resultHead = myResult.getHeader();
    if (resultHead != null) {
      if (myListener != null) {
        myListener.serviceStarted(resultHead.getCallBackPointer(null));
        start();
        return resultHead.getCallBackPointer(null);
      }
    }
    else {
      System.err.println("Received no header. returning and killing thread");
      throw new ClientException( -1, -1, "No async header!");
    }
    return null;
  }

  private boolean isFinished(Navajo n) {
    Header h = n.getHeader();

    if (h == null || n.getMessage("error") != null) {
      return true;
    }
    return h.isCallBackFinished();
  }

  private double getProgress(Navajo n) {
    Header h = n.getHeader();
    if (h == null) {
      return -1;
    }
    return h.getCallBackProgress();
  }

  /**
   * Kill the ServerAsyncRunner
   * @throws ClientException
   */
  public synchronized void killServerAsyncSend() throws ClientException {
    kill = true;
    interrupt();
  }

  /**
   * Not implemented
   * @throws ClientException
   */
  public synchronized void pauseServerAsyncSend() throws ClientException {
  }

  /**
   * Not implemented
   * @throws ClientException
   */
  public synchronized void resumeServerAsyncSend() throws ClientException {
  }

}
