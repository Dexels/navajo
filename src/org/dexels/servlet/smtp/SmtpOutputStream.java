package org.dexels.servlet.smtp;

import javax.servlet.ServletOutputStream;
import java.io.*;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpOutputStream extends ServletOutputStream {

  protected StringBuffer content = null;
  protected int offset = 0;
  private String subject = "";

  public SmtpOutputStream() {
    content = new StringBuffer();
    content.append("org.dexels.servlet.SmtpServletException: empty response");
  }

  protected void setSubject(String subject) {
    this.subject = subject;
  }

  protected String getSubject() {
    return this.subject;
  }

  public void write(int b) throws java.io.IOException {
    if (offset == 0)
      content = new StringBuffer();
    content.append((char) b);
    offset++;
  }
}