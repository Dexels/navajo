package org.dexels.swing.event;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

/**
   * Checks if the processing of the event is longer than the
   * specified <code>maxProcessingTime</code>. If so then
   * listener is notified.
   */
  public class Watchdog extends TimerTask {
    // Settings
    private final long maxProcessingTime;
    private final ActionListener listener;
    private final boolean repetitive;

    // Event reported as "lengthy" for the last time. Used to
    // prevent repetitive behaviour in non-repeatitive timers.
    private AWTEvent lastReportedEvent = null;
    private EventQueueWithWD myQueue = null;
    
    /**
     * Creates timer.
     *
     * @param maxProcessingTime maximum event processing time
     *                           before listener is notified.
     * @param listener          listener to notify.
     * @param repetitive       TRUE to allow consequent
     *                           notifications for the same event
     */
    public Watchdog(long maxProcessingTime,
                    ActionListener listener,
                    boolean repetitive,
                    EventQueueWithWD queue
                    ) {
      if (listener == null)
        throw new IllegalArgumentException(
            "Listener cannot be null.");
      myQueue = queue;
      if (maxProcessingTime < 0)
        throw new IllegalArgumentException(
          "Max locking period should be greater than zero");
      this.maxProcessingTime = maxProcessingTime;
      this.listener = listener;
      this.repetitive = repetitive;
    }

    @Override
	public void run() {
      long time;
      AWTEvent currentEvent;

      // Get current event requisites
      synchronized (EventQueueWithWD.eventChangeLock) {
        time = myQueue.eventDispatchingStart;
        currentEvent = myQueue.event;
      }

      long currentTime = System.currentTimeMillis();

      // Check if event is being processed longer than allowed
      if (time != -1 && (currentTime - time > maxProcessingTime) &&
          (repetitive || currentEvent != lastReportedEvent)) {
        listener.actionPerformed(
            new ActionEvent(currentEvent, -1, null));
        lastReportedEvent = currentEvent;
      }
    }
  }
