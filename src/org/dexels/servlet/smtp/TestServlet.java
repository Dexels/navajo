package org.dexels.servlet.smtp;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */
import java.io.*;
import javax.servlet.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.util.Util;
import gnu.regexp.*;


public class TestServlet extends SmtpServlet {

    public TestServlet() {}

    public void init(ServletConfig config) throws ServletException {
        System.out.println("TestServlet init() called");
    }

    private String stripReturns(String s) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);

            if (a != '\n' && a != ',')
                result.append(a);
            if (a == ',')
                result.append(' ');
        }
        return result.toString();
    }

    public String trim(String s) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ')
                result.append(s.charAt(i));
        }
        return result.toString();
    }

    public void doSend(SmtpServletRequest req, SmtpServletResponse res) throws java.io.IOException, javax.servlet.ServletException {
        BufferedReader reader = req.getReader();
        String line = "";
        String postcode = "";
        String huisnummer = "";
        String toevoeging = "";
        String value = "";

        try {
            RE objectRE = new RE("[1-9][0-9]{3} ?[A-z]{2} ?[1-9][0-9]* ?[A-z]*");
            RE postcodeRE = new RE("[1-9][0-9]{3} ?[A-z]{2}");
            RE huisnummerRE = new RE("[1-9][0-9]*");
            gnu.regexp.REMatch matchObject = null;

            while ((line = reader.readLine()) != null) {
                System.out.println("line = " + line);
                matchObject = objectRE.getMatch(line);
                System.out.println("matchObject = " + matchObject);
                if (matchObject != null)
                    break;
            }
            if (matchObject == null)
                throw new ServletException("No object data found");
            String objectString = matchObject.toString();
            gnu.regexp.REMatch matchPostcode = postcodeRE.getMatch(objectString);

            postcode = trim(matchPostcode.toString());
            gnu.regexp.REMatch matchHuisnummer = huisnummerRE.getMatch(objectString.substring(postcode.length(), objectString.length()));

            huisnummer = trim(matchHuisnummer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        System.out.println("Request content type = " + req.getContentType());
        System.out.println("Postcode = " + postcode);
        System.out.println("Huisnummer = " + huisnummer);
        System.out.println("Toevoeging = " + toevoeging);

        ServletContext context = this.getServletContext();

        String atvalue = (String) context.getAttribute("attr");

        System.out.println("atvalue = " + atvalue);
        if (atvalue == null) {
            atvalue = "Een waarde";
            context.setAttribute("attr", atvalue);
        }

        // getServletNames is deprecated. As of Java Servlet API 2.1, with no replacement.
        // it is used here only to print out all servlet names
        // Enumeration allServlets = context.getServletNames();
        // while (allServlets.hasMoreElements()) {
        // System.out.println("SERVLET = " + (String) allServlets.nextElement());
        // }
        System.out.println("ATTRIBUTE VALUE = " + atvalue);
        System.out.println("Server info = " + context.getServerInfo());
        System.out.println("context = " + context);
        System.out.println("INIT PARAMETERS:");
        Enumeration enum = context.getInitParameterNames();

        while (enum.hasMoreElements()) {
            String name = (String) enum.nextElement();

            System.out.println("parameter = " + name + ": " + context.getInitParameter(name));
        }
        PrintWriter out = res.getWriter();

        res.setSubject("Resultaat waardebepaling " + postcode + " " + huisnummer + toevoeging);
        res.setFrom("navajo@nbwo.nl");
        res.setContentType("text/html");

        /**
         try {
         Navajo doc = new Navajo();
         ResourceBundle properties = ResourceBundle.getBundle("toolboxagent");
         NavajoAgent agent = new NavajoAgent(properties);
         agent.send("NBWO_basistoets", doc, false);
         Property prop = null;
         prop = doc.getProperty("user/username");
         prop.setValue("veh_rem");
         prop = doc.getProperty("begin_pagina/Huisnummer");
         prop.setValue(huisnummer);
         prop = doc.getProperty("begin_pagina/HuisnummerToevoeging");
         prop.setValue(toevoeging);
         prop = doc.getProperty("begin_pagina/Postcode");
         prop.setValue(postcode);
         agent.send("NBWO_toetsing", doc, false);
         Message resultaat = doc.getMessage("resultaat_waardebepaling");
         if (resultaat != null) {  // Got result
         Message taxatie = resultaat.getMessage("taxatie");

         String datum = taxatie.getProperty("Toetsdatum").getValue();
         String zakelijkrecht = stripReturns(taxatie.getProperty("ZakelijkrechtOmschrijving").getValue());
         String belemmering = stripReturns(taxatie.getProperty("BelemmeringOmschrijving").getValue());
         String vvdatum = taxatie.getProperty("VorigeTransaktieDatum").getValue();
         String vvwaarde = taxatie.getProperty("VorigeTransactieKoopsom").getValue();
         String vvindex = taxatie.getProperty("VorigeTransactieKoopsomIndex").getValue();
         String omschrijving = stripReturns(taxatie.getProperty("Omschrijving").getValue().trim());
         String kultuurKode = ((Selection) resultaat.getMessage("woning_type").getProperty("TypeWoning").getAllSelectedSelections().get(0)).getName();

         String gmwaarde = taxatie.getProperty("GlobaalmodelWaarde").getValue();
         String idbaantal = taxatie.getProperty("AantalTransaktiesInBuurt").getValue();
         String idbwaarde = taxatie.getProperty("InBuurtModelWaarde").getValue();
         String waarde = taxatie.getProperty("Eindewaarde").getValue();
         String minwaarde = taxatie.getProperty("MinimaleWaarde").getValue();
         String maxwaarde = taxatie.getProperty("MaximaleWaarde").getValue();
         String afwijking = taxatie.getProperty("Afwijking").getValue();

         out.println("<BODY>");
         out.println("<HTML>");
         out.println("<IMG SRC=\"http://nbtodev:8080/nbwosite/img/logonbwo.gif\">");
         out.println("<TABLE BORDER=\"1\" BGCOLOR=\"#dbd29e\" COLOR=\"#000000\">");
         out.println("<TR><TD>Kultuurtekst</TD><TD>"+omschrijving+"</TD></TR>");
         out.println("<TR><TD>Modelwaarde</TD><TD>" + waarde+"</TD></TR>");
         out.println("<TR><TD>Minimale waarde</TD><TD>" + minwaarde+"</TD></TR>");
         out.println("<TR><TD>Maximale waarde</TD><TD>" + maxwaarde+"</TD></TR>");
         out.println("<TR><TD>Afwijking</TD><TD>" + afwijking+"</TD></TR>");
         out.println("</TABLE>");
         out.println("</HTML>");
         out.println("</BODY>");

         } else {
         if (doc.getMessage("extra_gegevens") != null) { // Extra gegevens nodig
         out.println("Niet bekend in kadaster");
         } else {  // Waardebepaling kan niet worden uitgevoerd.
         System.out.println("KAN TOETSING NIET UITVOEREN");
         }
         }
         } catch (Exception e) {
         e.printStackTrace();
         }
         */

        out.close();

    }
}
