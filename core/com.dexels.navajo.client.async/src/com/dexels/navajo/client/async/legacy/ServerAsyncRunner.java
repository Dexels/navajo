package com.dexels.navajo.client.async.legacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;

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
  private boolean iterate = true;
  private volatile boolean kill = false;
  private int prevProgress = 0;
  private long prevTime = 0;
  private final AsyncRegistry registry;
  private static final int MAX_POLLING_INTERVAL = 30000;

  
private static final Logger logger = LoggerFactory.getLogger(ServerAsyncRunner.class);


  /**
   * Construct a new ServerAsyncRunner
   * @param client ClientInterface
   * @param in Navajo
   * @param method String
   * @param listener ServerAsyncListener
   * @param clientId String
   * @param pollingInterval int
   */
  public ServerAsyncRunner(ClientInterface client,AsyncRegistry registry, Navajo in, String method, ServerAsyncListener listener, String clientId, int pollingInterval) {
    myClientInterface = client;
    this.registry = registry;
    myNavajo = in;
    myMethod = method;
    myListener = listener;
    myClientId = clientId;
    myPollingInterval = pollingInterval;
    if (in.getHeader() != null) {
      in.getHeader().removeCallBackPointers();
    }
  }

  private Navajo doSimpleSend(Navajo n, String method) throws ClientException {
	 return myClientInterface.doSimpleSend(n, method);
  }

  /**
   * Main thread
   */
  @Override
  public void run() {

    try {
      prevTime = System.currentTimeMillis();
      while (isIterating()) {
        if (kill) {
          logger.warn("Kill called in ServerAsyncRunner...");
          myResult.getHeader().setCallBackInterrupt("kill");
          setIterating(false);
        }
        if (myPollingInterval > 0) {
          myNavajo.removeHeader();
          myNavajo.addHeader(myResult.getHeader());
          Navajo temp = doSimpleSend(myNavajo, myMethod);

          if (temp.getMessage("ConditionErrors") != null) {
        	  	logger.warn("Had ConditionErrors in Asyncsend.. ");
            killServerAsyncSend();
          } else {
              poll(temp);
          }
        }
      }
    }
    catch (ClientException ex) {
    	logger.debug("oooops");
      myListener.handleException(ex);
      return;
    }
    if (myListener != null) {
      myListener.handleException(new ClientException( -1, -1, "Operation aborted"));
    }
  }

private void poll(Navajo temp) throws ClientException {
	Header head = temp.getHeader();
	  if (head == null) {
		  	logger.warn("Received no header. returning and killing thread");
	    throw new ClientException( -1, -1, "No async header!");
	  }
	  if (isFinished(temp)) {
	    // Really dont know what I should pass to getCallBackPointer
	    if (myListener != null) {
	      myListener.setProgress(head.getCallBackPointer(null), 100);
	      myListener.receiveServerAsync(temp, myMethod, head.getCallBackPointer(null), myClientId);
	    }
	    registry.deRegisterAsyncRunner(myClientId);
	    myNavajo.removeHeader();
	    this.iterate = false;
	  }
	  else {
	    if (myListener != null) {
	      myListener.setProgress(head.getCallBackPointer(null), head.getCallBackProgress());
	    }
	  }
	  checkPollingInterval(head.getCallBackProgress());
	  sleep();
}

private void sleep() {
	logger.debug("Start sleep");
	  try {
	    if (myPollingInterval > MAX_POLLING_INTERVAL) {
	      myPollingInterval = MAX_POLLING_INTERVAL;
	    }
	    sleep(myPollingInterval);
	  }
	  catch (InterruptedException ex1) {
		  	logger.debug("Interrupted. Continuing with next iteration.");
	  }
	  logger.debug("End sleep");
}

  private void checkPollingInterval(int currentProgress) {

    int dif = currentProgress - prevProgress;
    long now = System.currentTimeMillis();
    long timeDiff = now - prevTime;
    prevTime = now;
    long eta = 0;
    if (dif > 0) {
      eta = (100 - currentProgress) * (timeDiff / dif);
    }
    if (dif < 1) {
      myPollingInterval = 2 * myPollingInterval;
      prevProgress = currentProgress;
      return;
    }
    if (dif < 5) {
      myPollingInterval = (int) (1.5 * myPollingInterval);
      prevProgress = currentProgress;
      return;
    }
    if (dif > 20 && myPollingInterval > 2500) {
        myPollingInterval = (int) (myPollingInterval / 1.5);
    }
    if (eta < myPollingInterval) {
      myPollingInterval = (int) (eta / 2.0);
    }
    prevProgress = currentProgress;
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
      logger.info("Received no header. returning and killing thread");
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


  /**
   * Kill the ServerAsyncRunner
   * @throws ClientException
   */
  public synchronized void killServerAsyncSend() {
    kill = true;
    interrupt();
  }

  /**
   * Not implemented
   * @throws ClientException
   */
  public synchronized void pauseServerAsyncSend()  {
  }

  /**
   * Not implemented
   * @throws ClientException
   */
  public synchronized void resumeServerAsyncSend()  {
  }

}
