package com.dexels.navajo.server;

import java.util.Date;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

public class ClientInfo {

  public String ip;
  public String host;
  public int parseTime;
  public String encoding;
  public boolean compressedRecv;
  public boolean compressedSend;
  public int contentLength;
  public Date created;

  public ClientInfo(String ip, String host, String encoding, int parseTime, boolean compressedrecv, boolean compressedsend, int contentLength, Date created) {
    this.ip = ip;
    this.host = host;
    this.parseTime = parseTime;
    this.encoding = encoding;
    this.compressedRecv = compressedrecv;
    this.compressedSend = compressedsend;
    this.contentLength = contentLength;
    this.created = created;
  }

  public String getIP() {
    return this.ip;
  }

  public String getHost() {
    return this.host;
  }
  public int getParseTime() {
    return parseTime;
  }
  public String getEncoding() {
    return encoding;
  }
  public boolean isCompressedRecv() {
    return compressedRecv;
  }
  public boolean isCompressedSend() {
    return compressedSend;
  }
  public int getContentLength() {
    return contentLength;
  }
  public Date getCreated() {
    return created;
  }

}