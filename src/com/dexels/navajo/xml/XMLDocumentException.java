package com.dexels.navajo.xml;

/**
 * This class implements an exception which can wrapped a lower-level exception.
 *
 * @version $Revision$
 */
public class XMLDocumentException extends Exception {
  private Exception exception;

  /**
   * Creates a new XMLDocumentException wrapping another exception, and with a detail message.
   * @param message the detail message.
   * @param exception the wrapped exception.
   */
  public XMLDocumentException(String message, Exception exception) {
    super(message);
    this.exception = exception;
    return;
  }

  /**
   * Creates a XMLDocumentException with the specified detail message.
   * @param message the detail message.
   */
  public XMLDocumentException(String message) {
    this(message, null);
    return;
  }

  /**
   * Creates a new XMLDocumentException wrapping another exception, and with no detail message.
   * @param exception the wrapped exception.
   */
  public XMLDocumentException(Exception exception) {
    this(null, exception);
    return;
  }

  /**
   * Gets the wrapped exception.
   *
   * @return the wrapped exception.
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Retrieves (recursively) the root cause exception.
   *
   * @return the root cause exception.
   */
  public Exception getRootCause() {
    if (exception instanceof XMLDocumentException) {
      return ((XMLDocumentException) exception).getRootCause();
    }
    return exception == null ? this : exception;
  }
}
