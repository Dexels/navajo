package org.dexels.swing.event;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Alternative events dispatching queue. The benefit over the
 * default Event Dispatch queue is that you can add as many
 * watchdog timers as you need and they will trigger arbitrary
 * actions when processing of single event will take longer than
 * one timer period.
 * <p/>
 * Timers can be of two types:
 * <ul>
 * <li><b>Repetitive</b> - action can be triggered multiple times
 * for the same "lengthy" event dispatching.
 * </li>
 * <li><b>Non-repetitive</b> - action can be triggered only once
 * per event dispatching.</li>
 * </ul>
 * <p/>
 * The queue records time of the event dispatching start.  This
 * time is used by the timers to check if dispatching takes
 * longer than their periods. If so the timers trigger associated
 * actions.
 * <p/>
 * In order to use this queue application should call
 * <code>install()</code> method. This method will create,
 * initialize and register the alternative queue as appropriate.
 * It also will return the instance of the queue for further
 * interactions. Here's an example of how it can be done:
 * <p/>
 * <pre>
 * <p/>
 *  EventQueueWithWD queue = EventQueueWithWD.install();
 *  Action edtOverloadReport = ...;
 * <p/>
 *  // install single-shot wg to report EDT overload after
 *  // 10-seconds timeout
 *  queue.addWatchdog(10000, edtOverloadReport, false);
 * <p/>
 * </pre>
 */
public class EventQueueWithWD extends EventQueue {
  // Main timer
  public final java.util.Timer timer = new java.util.Timer(true);

  // Group of informational fields for describing the event
  public final static Object eventChangeLock = new Object();
  public volatile long eventDispatchingStart = -1;
  public volatile AWTEvent event = null;

  public Watchdog myDog = null;
  /**
   * Hidden utility constructor.
   */
  private EventQueueWithWD() { }

  /**
   * Install alternative queue.
   *
   * @return instance of queue installed.
   */
  public static EventQueueWithWD install() {
    EventQueue eventQueue =
        Toolkit.getDefaultToolkit().getSystemEventQueue();
    
    EventQueueWithWD newEventQueue = new EventQueueWithWD();
    eventQueue.push(newEventQueue);
    System.err.println("Installed new event queue: " + newEventQueue);
    return newEventQueue;
  }

  /**
   * Record the event and continue with usual dispatching.
   *
   * @param anEvent event to dispatch.
   */
  protected void dispatchEvent(AWTEvent anEvent) {
    setEventDispatchingStart(anEvent, System.currentTimeMillis());
    super.dispatchEvent(anEvent);
    setEventDispatchingStart(null, -1);
  }

  /**
   * Register event and dispatching start time.
   *
   * @param anEvent   event.
   * @param timestamp dispatching start time.
   */
  private void setEventDispatchingStart(AWTEvent anEvent,
                                        long timestamp) {
    synchronized (eventChangeLock) {
      event = anEvent;
      eventDispatchingStart = timestamp;
    }
  }

  /**
   * Add watchdog timer. Timer will trigger <code>listener</code>
   * if the queue dispatching event longer than specified
   * <code>maxProcessingTime</code>. If the timer is
   * <code>repetitive</code> then it will trigger additional
   * events if the processing 2x, 3x and further longer than
   * <code>maxProcessingTime</code>.
   *
   * @param maxProcessingTime maximum processing time.
   * @param listener          listener for events. The listener
   *                          will receive <code>AWTEvent</code>
   *                          as source of event.
   * @param repetitive        TRUE to trigger consequent events
   *                          for 2x, 3x and further periods.
   */
  public void addWatchdog(long maxProcessingTime,
                          ActionListener listener,
                          boolean repetitive) {
    Watchdog checker = new Watchdog(maxProcessingTime, listener, repetitive, this);
    timer.schedule(checker, maxProcessingTime, maxProcessingTime);
    myDog = checker;
  }
}

