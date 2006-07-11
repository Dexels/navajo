

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.*;

public final class NavajoExceptionImpl extends NavajoException {

    private Exception exception;

    public NavajoExceptionImpl() {
        super();
        return;
    }

    /**
     * Creates a new NavajoException wrapping another exception, and with a detail message.
     * @param message the detail message.
     * @param exception the wrapped exception.
     */
    public NavajoExceptionImpl(String message, Exception exception) {
        super(message);
        this.exception = exception;
        return;
    }

    /**
     * Creates a NavajoException with the specified detail message.
     * @param message the detail message.
     */
    public NavajoExceptionImpl(String message) {
        this(message, null);
        return;
    }

    /**
     * Creates a new NavajoException wrapping another exception, and with no detail message.
     * @param exception the wrapped exception.
     */
    public NavajoExceptionImpl(Exception exception) {
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

    

}
