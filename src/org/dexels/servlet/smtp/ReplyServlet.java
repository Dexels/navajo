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
import javax.servlet.*;

public class ReplyServlet extends SmtpServlet {

  public ReplyServlet() {
  }

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    System.out.println("in ReplyServlet init()");
  }

  public void doSend(SmtpServletRequest req, SmtpServletResponse res) throws java.io.IOException, javax.servlet.ServletException {

    PrintWriter out = res.getWriter();
    String from = req.getFrom();
    String subject = req.getSubject();
    res.setSubject("Re:"+subject);
    out.println("Beste " + from + ",\n");
    out.println("Ik zal u zo spoedig mogelijk terugmailen over " + subject + "\n");
    out.println("Met vriendelijke groet,\nArjen Schoneveld");
    out.close();
  }
}