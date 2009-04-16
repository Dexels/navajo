package com.dexels.navajo.server.listener.http;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.DispatcherFactory;

public class DocumentServlet extends TmlHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8117552615430912926L;
	String anonymousUser;
	String anonymousPassword;
	
	public void init(ServletConfig config) throws ServletException {
		anonymousUser = config.getInitParameter("anonymous_user");
		anonymousPassword  = config.getInitParameter("anonymous_passwd");
	}

	protected Header constructHeader(Navajo tbMessage, String service, String username, String password, long expirationInterval) {
		return NavajoFactory.getInstance().createHeader(tbMessage,service, anonymousUser, anonymousPassword, expirationInterval);
	}

	private final String getChecksum(String s) {
		return (s+"_44P!E_"+new java.util.Date()).hashCode()+"";
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException  {
		
		String documentid = req.getParameter("id");
		
		// Processing without short url.
		if ( documentid == null || documentid.equals("") )  {
		
			String hash = req.getParameter("checksum");
			if ( hash == null || hash.equals("") )  {
				 res.sendError(404, "Could not find document.");
				 return;
			}
			String queryString = req.getQueryString();
			queryString = queryString.substring(0, queryString.indexOf("checksum")-1);
			
			if ( !hash.equals( getChecksum(queryString) )) {
				 res.sendError(404, "Could not find document (invalid checksum).");
				 return;
			}
			super.doGet(req, res);
			return;
		}
		
		// First get document URL...
		try {
			String documentName = ( req.getPathInfo() != null ? req.getPathInfo().substring(1) : "" );
			System.err.println("Documentname: " + documentName);
			Navajo out = NavajoFactory.getInstance().createNavajo();
			Header h = NavajoFactory.getInstance().createHeader(out, "lucene/ProcessGetDocumentURL", anonymousUser, anonymousPassword, -1);
			out.addHeader(h);
			Message m = NavajoFactory.getInstance().createMessage(out, "Document");
			Property p = NavajoFactory.getInstance().createProperty(out, "DocumentId", "integer", documentid, 0, "", "out");
			m.addProperty(p);
			Property p2 = NavajoFactory.getInstance().createProperty(out, "Name", "string", req.getPathInfo().replaceAll("\\/", ""), 0, "", "out");
			m.addProperty(p2);
			out.addMessage(m);
			
			Navajo n = DispatcherFactory.getInstance().handle(out); //NavajoClientFactory.getClient().doSimpleSend(out, "lucene/ProcessGetDocumentURL");
			Property urlProp = n.getProperty("/Document/URL");
			if ( urlProp != null ) {
				String redirectURL = req.getRequestURI() + "?"+urlProp.getValue() + "&checksum=" + getChecksum(urlProp.getValue());
				// Forward to stored URL...
				res.sendRedirect( redirectURL); 
			} else {
				 res.sendError(404, "Could not find document (no short url).");
				 return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Invalid request.");
		}
		
		//super.doGet(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		// Not supported
		return;
	}
}
