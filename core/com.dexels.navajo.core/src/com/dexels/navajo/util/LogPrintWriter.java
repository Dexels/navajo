/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.slf4j.Logger;

/**
 * Implements a PrintWriter which allows to alternatively plug in a
 * <code>Writer</code> or a <code>Logger</code>.
 */
public class LogPrintWriter extends PrintWriter {

    /**
     * Internal buffer.
     */
    private StringBuilder buffer = new StringBuilder();

    /**
     * Logger for message output.
     */
    private Logger log;

    /**
     * Creates a new <code>LogPrintWriter</code> which is based on a
     * <code>Logger</code>.
     *
     * @param log
     *            the base <code>Logger</code>.
     */
    public LogPrintWriter(Logger log) {
        super(new NullWriter());
        this.log = log;
    }

    /**
     * Sets a new output <code>Writer</code>. Calling this method will flush
     * this <code>LogPrintWriter</code> before the new <code>Writer</code>
     * <code>out</code> is set.
     *
     * @param out
     *            the <code>Writer</code> to use for output.
     */
    public void setWriter(Writer out) {
        flushBuffer();
        this.out = out;
        this.log = null;
    }

    /**
     * Sets a new <code>Logger</code>. Calling this method will flush this
     * <code>LogPrintWriter</code> before the new <code>Logger</code> is set.
     *
     * @param log
     *            the new <code>Logger</code> to use for output.
     */
    public void setLogger(Logger log) {
        flushBuffer();
        out = new NullWriter();
        this.log = log;
    }

    // ------------------< overrides from PrintWriter >-------------------------

    @Override
    public void close() {
        flushBuffer();
        super.close();
    }

    @Override
    public void flush() {
        flushBuffer();
        super.flush();
    }

    @Override
    public void write(int c) {
        buffer.append(c);
    }

    @Override
    public void write(char[] cbuf, int off, int len) {
        buffer.append(cbuf, off, len);
    }

    @Override
    public void write(String str, int off, int len) {
        buffer.append(str.substring(off, off + len));
    }

    @Override
    public void println() {
        if (log == null) {
            // only add newline when operating on a writer
            buffer.append('\n');
        }
        flushBuffer();
    }

    // -----------------------< private methods
    // >--------------------------------

    private void flushBuffer() {
        if (buffer.length() == 0) {
            return;
        }
        if (log != null) {
            log.debug(buffer.toString());
        } else {
            try {
                out.write(buffer.toString());
            } catch (IOException e) {
                this.setError();
            }
        }
        // reset buffer
        buffer.setLength(0);
    }

    // ------------------------< inter classes
    // >---------------------------------

    /**
     * Implements a Writer that simply ignores all calls.
     */
    private static class NullWriter extends Writer {

        @Override
        public void close() throws IOException {
            // ignore
        }

        @Override
        public void flush() throws IOException {
            // ignore
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            // ignore
        }
    }
}