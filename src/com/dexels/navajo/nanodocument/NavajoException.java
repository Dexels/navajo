package com.dexels.navajo.nanodocument;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class NavajoException extends Exception {

   public final static String NOT_PROPERTY_SELECTION = "Illegal method. Wrong property type.";
    private Exception exception;

    public NavajoException() {
        super();
        return;
    }

    /**
     * Creates a new NavajoException wrapping another exception, and with a detail message.
     * @param message the detail message.
     * @param exception the wrapped exception.
     */
    public NavajoException(String message, Exception exception) {
        super(message);
        this.exception = exception;
        return;
    }

    /**
     * Creates a NavajoException with the specified detail message.
     * @param message the detail message.
     */
    public NavajoException(String message) {
        this(message, null);
        return;
    }

    /**
     * Creates a new NavajoException wrapping another exception, and with no detail message.
     * @param exception the wrapped exception.
     */
    public NavajoException(Exception exception) {
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
        if (exception instanceof NavajoException) {
            return ((NavajoException) exception).getRootCause();
        }
        return exception == null ? this : exception;
    }
}