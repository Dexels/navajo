package org.dexels.servlet.smtp;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SmtpSender {

  public static void send(Session session, SmtpServletResponse response) {

    javax.mail.Message message = null;
    try {
      message = new MimeMessage(session);
      message.setFrom(new InternetAddress(response.getFrom()));
      message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(response.getRecipient()));
      message.setSubject(response.getSubject());
      java.io.StringWriter writer = new java.io.StringWriter();
      message.setContent(((SmtpOutputStream) response.getOutputStream()).content.toString(), response.getContentType());
      Transport.send(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}