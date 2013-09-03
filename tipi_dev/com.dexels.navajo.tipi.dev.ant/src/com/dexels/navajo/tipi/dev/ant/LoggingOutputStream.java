package com.dexels.navajo.tipi.dev.ant;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;

public class LoggingOutputStream extends OutputStream {

    /**
     * Used to maintain the contract of [EMAIL PROTECTED] #close()}.
     */
    private boolean hasBeenClosed = false;

    /**
     * The internal buffer where data is stored.
     */
    private byte[] buf;

    /**
     * The number of valid bytes in the buffer. This value is always
     * in the range <tt>0</tt> through <tt>buf.length</tt>; elements
     * <tt>buf[0]</tt> through <tt>buf[count-1]</tt> contain valid
     * byte data.
     */
    private int count;

    /**
     * Remembers the size of the buffer for speed.
     */
    private int bufLength;

    /**
     * The default number of bytes in the buffer. =2048
     */
    public static final int DEFAULT_BUFFER_LENGTH = 2048;


    /**
     * The category to write to.
     */
    private Logger logger;

    /**
     * The priority to use when writing to the Category.
     */


    /**
     * Creates the LoggingOutputStream to flush to the given Category.
     *
     * @param log   the Logger to write to
     * @param level the Level to use when writing to the Logger
     * @throws IllegalArgumentException if cat == null or priority ==
     *                                  null
     */
    
    public LoggingOutputStream(Logger log)
            throws IllegalArgumentException {
        if (log == null) {
            throw new IllegalArgumentException("cat == null");
        }


        logger = log;
        bufLength = DEFAULT_BUFFER_LENGTH;
        buf = new byte[DEFAULT_BUFFER_LENGTH];
        count = 0;
    }


    /**
     * Closes this output stream and releases any system resources
     * associated with this stream. The general contract of
     * <code>close</code>
     * is that it closes the output stream. A closed stream cannot
     * perform
     * output operations and cannot be reopened.
     */
    @Override
	public void close() {
        flush();
        hasBeenClosed = true;
    }


    /**
     * Writes the specified byte to this output stream. The general
     * contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     *
     * @param b the <code>byte</code> to write
     * @throws java.io.IOException if an I/O error occurs. In particular, an <code>IOException</code> may be
     *                 thrown if the output stream has been closed.
     */
    @Override
	public void write(final int b) throws IOException {
        if (hasBeenClosed) {
            throw new IOException("The stream has been closed.");
        }

        // would this be writing past the buffer?

        if (count == bufLength) {
            // grow the buffer
            final int newBufLength = bufLength + DEFAULT_BUFFER_LENGTH;
            final byte[] newBuf = new byte[newBufLength];

            System.arraycopy(buf, 0, newBuf, 0, bufLength);
            buf = newBuf;

            bufLength = newBufLength;
        }

        buf[count] = (byte) b;

        count++;
    }


    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out. The general contract of <code>flush</code> is
     * that calling it is an indication that, if any bytes previously
     * written have been buffered by the implementation of the output
     * stream, such bytes should immediately be written to their
     * intended destination.
     */
    @Override
	public void flush() {

        if (count == 0) {
            return;
        }

        // don't print out blank lines; flushing from PrintStream puts

        // For linux system

        if (count == 1 && ((char) buf[0]) == '\n') {
            reset();
            return;
        }

        // For mac system

        if (count == 1 && ((char) buf[0]) == '\r') {
            reset();
            return;
        }

        // On windows system

        if (count == 2 && (char) buf[0] == '\r' && (char) buf[1] == '\n') {
            reset();
            return;
        }

        final byte[] theBytes = new byte[count];
        System.arraycopy(buf, 0, theBytes, 0, count);
//        logger.log(level, new String(theBytes));
        logger.info( new String(theBytes));
        reset();

    }

    private void reset() {
        // not resetting the buffer -- assuming that if it grew then it will likely grow similarly again
        count = 0;
    }

}