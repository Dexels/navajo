/*
 * BufferStream.java
 *
 * (BSD license)
 *
 * Copyright (c) 2007, Matthias Mann (www.matthiasmann.de)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *   * Neither the name of the Matthias Mann nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on December 2, 2006, 7:53 PM
 *
 */

package de.matthiasmann.p200ant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Matthias Mann
 */
public class BufferStream extends OutputStream {
    
    static final int PAGE_SIZE = 4096;
    
    Page head;
    Page cur;
    int curPos;
    
    /**
     * Creates a new instance of BufferStream
     */
    public BufferStream() {
        this.head = new Page();
        this.cur = this.head;
    }
    
    public void write(int value) {
        if(curPos == PAGE_SIZE) {
            newPage();
        }
        cur.buffer[curPos++] = (byte)value;
    }
    
    public void write(byte[] b, int off, int len) {
        while(len > 0) {
            int copyCnt = PAGE_SIZE - curPos;
            if(copyCnt == 0) {
                newPage();
                copyCnt = PAGE_SIZE;
            }
            if(copyCnt > len) {
                copyCnt = len;
            }
            System.arraycopy(b, off, cur.buffer, curPos, copyCnt);
            curPos += copyCnt;
            off += copyCnt;
            len -= copyCnt;
        }
    }
    
    public InputStream getInputStream() {
        return new BufferIS(head, curPos);
    }
    
    private void newPage() {
        cur = cur.next = new Page();
        curPos = 0;
    }
    
    static class Page {
        final byte[] buffer;
        Page next;
        
        Page() {
            this.buffer = new byte[PAGE_SIZE];
        }
    }
    
    static class BufferIS extends InputStream {
        Page cur;
        int lastPageSize;
        int offset;
        
        BufferIS(Page head, int lastPageSize) {
            this.cur = head;
            this.lastPageSize = lastPageSize;
        }

        public int read() throws IOException {
            if(!nextPage()) {
                return -1;
            }
            return cur.buffer[offset++] & 255;
        }

        public int available() throws IOException {
            if(!nextPage()) {
                return 0;
            }
            if(cur.next == null) {
                return lastPageSize - offset;
            }
            return PAGE_SIZE - offset;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            if(len <= 0) {
                return 0;
            }
            int avail = available();
            if(len > avail) {
                len = avail;
            }
            if(len == 0) {
                return -1;
            }
            System.arraycopy(cur.buffer, offset, b, off, len);
            offset += len;
            return len;
        }

        public long skip(long n) throws IOException {
            int skip = (int)Math.min(n, available());
            if(skip > 0) {
                offset += skip;
                return skip;
            }
            return 0;
        }
        
        private boolean nextPage() {
            if(cur != null) {
                if(offset == PAGE_SIZE || (offset == lastPageSize && cur.next == null)) {
                    offset = 0;
                    cur = cur.next;
                }
            }
            return cur != null;
        }
    }
}
