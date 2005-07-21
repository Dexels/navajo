package com.dexels.navajo.adapter.crystal;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;
import java.util.*;
import java.net.*;
import java.io.*;
import com.dexels.navajo.document.types.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CrystalAdapter
    implements Mappable {
  public String reportParameter;
  private HashMap parameters;
  public String baseUrl, username, password, reportname;
  public Binary report;

  public CrystalAdapter() {
    //http://slwebsvr3.sportlink.enovation.net:8080/knvb-oraacc/rapportage.jsp?report=\\Oracle\\ControleoverzichtJournaalpostSpelerspassen.rpt&username=finance&password=sp0rtl1nk
  }

  public void setReportParameter(String param) throws UserException {
    if (parameters == null) {
      parameters = new HashMap();
    }
    if (param.indexOf("=") > 0) {
      parameters.put(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=") + 1));
    }
    else {
      throw new UserException(434343, "Supplied parameter [" + param + "] is not a key value pair separated by a \'=\' sign");
    }
  }

  public void setUsername(String un) {
    username = un;
  }

  public void setPassword(String pw) {
    password = pw;
  }

  public void setReportname(String rn) {
    reportname = rn;
  }

  public void setBaseUrl(String url) {
    baseUrl = url;
  }

  public Binary getReport() {
    try {
      Iterator it = parameters.keySet().iterator();
      String Url = baseUrl + reportname;

      while (it.hasNext()) {
        String key = (String) it.next();
        String value = (String) parameters.get(key);
        System.err.println("Param: " + key + ", value: " + value);
        Url = Url + "&" + key.substring(1) + "=" + value;
      }

      Url = Url + "&viewmode=pdf";
      Url = Url + "&username=" + username + "&password=" + password;
      Url = Url.replaceAll(" ", "%20");

      System.err.println("------------------------------------=>> Opening: " + Url);

      URL myUrl = new URL(Url);
      InputStream in = myUrl.openStream();
      report = new Binary(in);
      System.err.println("New binary constructed from reportdata");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return report;
  }

  public void store() throws MappableException, UserException {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void kill() {
  }

  public static void main(String[] arg) {
    try {
      for (int i = 0; i < 500; i++) {
        long start_time = System.currentTimeMillis();
        CrystalAdapter ca = new CrystalAdapter();
        ca.setBaseUrl("http://slwebsvr3.sportlink.enovation.net:8080/knvb-oraacc/rapportage.jsp?report=");

        ca.setReportname("\\Oracle\\Wedstrijdwijzigingen\\MatchChangesofficials_veld_email.rpt");
//        ca.setReportname("\\Oracle\\ControleoverzichtJournaalpostSpelerspassen.rpt");
        ca.setUsername("knvbkern");
        ca.setPassword("knvb");
        ca.setReportParameter("@district=KNVB-DISTRICT-NOORD");
        ca.setReportParameter("@pubtype=VELD");
        ca.setReportParameter("@personid=BFFB94G");
        Binary rep = ca.getReport();
        if (rep != null) {
          byte[] data = rep.getData();
          long diff = System.currentTimeMillis() - start_time;
          System.err.println("Received: " + data.length + " bytes, which took: " + diff + " milliseconds");
        }
        else {
          System.err.println("REPORT WAS NULL");
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
