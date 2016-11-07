package com.dexels.navajo.server.listener.http.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;


public class BaseRequestImpl implements AsyncRequest {
    private final static Logger logger = LoggerFactory.getLogger(BaseRequestImpl.class);
    private final static Logger statLogger = LoggerFactory.getLogger("stats");
    
	protected final HttpServletRequest request;
	protected HttpServletResponse response;

	protected final String contentEncoding;
	protected final String acceptEncoding;
	protected final Object cert;
	protected long connectedAt = -1;
	private String url;
	private Navajo inDoc;
	private final String instance;
	private String dataPath = null;
	private String fileName;
	private String contentType;
	


	
	public BaseRequestImpl(HttpServletRequest request,
			HttpServletResponse response, String acceptEncoding,
			String contentEncoding, Object cert, String instance) throws UnsupportedEncodingException, IOException {
		// this.event = event;
		this.response = response;
		this.request = request;
		this.contentEncoding = contentEncoding;
		this.acceptEncoding = acceptEncoding;
		this.cert = cert;
		this.connectedAt = System.currentTimeMillis();
		
//		StringBuilder stringBuilder = new StringBuilder(10000);
//		 Scanner scanner = new Scanner(request.getInputStream());
//		    while (scanner.hasNextLine()) {
//		        stringBuilder.append(scanner.nextLine());
//		    }
//
//		    String body = stringBuilder.toString();
		    
		setUrl(createUrl(this.request));
		this.inDoc = parseInputNavajo(request.getInputStream());
		this.instance = instance;
		this.dataPath = request.getParameter("dataPath");
		this.fileName = request.getParameter("fileName");
		this.contentType = request.getParameter("ContentType");
	}

	public BaseRequestImpl(Navajo in, HttpServletRequest request, HttpServletResponse response, String instance)  {
		// this.event = event;
		this.contentEncoding = null;
		this.acceptEncoding = null;
		this.request = request;
		this.response = response;
		this.cert= request.getAttribute("javax.servlet.request.X509Certificate");
		this.connectedAt = System.currentTimeMillis();
		setUrl(createUrl(this.request));
		this.inDoc = in;
		this.instance = instance;
		this.dataPath = request.getParameter("dataPath");
		this.fileName = request.getParameter("fileName");
		this.contentType = request.getParameter("ContentType");
		
	}
	
	@Override
	public String getInstance() {
		return instance;
	}
	
	@Override
	public long getConnectedAt() {
		return connectedAt;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String createUrl(HttpServletRequest req) {
		String scheme = req.getScheme(); // http
		String serverName = req.getServerName(); // hostname.com
		int serverPort = req.getServerPort(); // 80
		String contextPath = req.getContextPath(); // /mywebapp
		String servletPath = req.getServletPath(); // /servlet/MyServlet
		String pathInfo = req.getPathInfo(); // /a/b;c=123
		String queryString = req.getQueryString(); // d=789

		// Reconstruct original requesting URL
		String url = scheme + "://" + serverName + ":" + serverPort
				+ contextPath + servletPath;
		if (pathInfo != null) {
			url += pathInfo;
		}
		if (queryString != null) {
			url += "?" + queryString;
		}
		return url;
	}

	protected final Navajo parseInputNavajo(InputStream is) throws IOException,
			UnsupportedEncodingException {
		BufferedReader r;
		//InputStream is = getRequestInputStream();
		Navajo in = null;
		logger.debug("Send encoding: "+contentEncoding);
		if (contentEncoding != null
				&& contentEncoding.equals(AsyncRequest.COMPRESS_JZLIB)) {
			r = new BufferedReader(new java.io.InputStreamReader(
					new InflaterInputStream(is), "UTF-8"));
		} else if (contentEncoding != null
				&& contentEncoding.equals(AsyncRequest.COMPRESS_GZIP)) {
			r = new BufferedReader(new java.io.InputStreamReader(
					new java.util.zip.GZIPInputStream(is), "UTF-8"));
		} else {
			r = new BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
		}
		in = NavajoFactory.getInstance().createNavajo(r);

		if (in == null) {
			throw new IOException("Invalid request.");
		}

		Header header = in.getHeader();
		if (header == null) {
			throw new IOException("Empty Navajo header.");
		}

		is.close();
		return in;
	}

	@Override
	public ClientInfo createClientInfo(long scheduledAt, long startedAt,
			int queueLength, String queueId) {
	    String ip = getIpAddress();
		ClientInfo clientInfo = new ClientInfo(
				ip,
				"unknown",
				contentEncoding,
				(int) (scheduledAt - connectedAt),
				(int) (startedAt - scheduledAt),
				queueLength,
				queueId,
				(contentEncoding != null && (contentEncoding.equals(COMPRESS_GZIP) || contentEncoding
						.equals(COMPRESS_JZLIB))),
				(acceptEncoding != null && (acceptEncoding.equals(COMPRESS_GZIP) || acceptEncoding
						.equals(COMPRESS_JZLIB))), request.getContentLength(),
				new java.util.Date(connectedAt));
		return clientInfo;
	}

	@Override
    public String getIpAddress() {
        String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null || ip.equals("")) {
	        ip = request.getRemoteAddr();
	    }
        return ip;
    }

	@Override
	public Object getCert() {
		return cert;
	}

	private static OutputStream getOutputStreamByEncoding(String encoding, OutputStream source) throws IOException {
		if(COMPRESS_JZLIB.equals(encoding)) {
			return new DeflaterOutputStream(source);
		} else if(COMPRESS_GZIP.equals(encoding)) {
			return new java.util.zip.GZIPOutputStream(source);
		}
		// unsupported:
		return null;
	}
	private  OutputStream getOutputStream(String acceptEncoding, OutputStream source) throws IOException {
		if(acceptEncoding == null) {
			return source;
		} else {
			List<String> accepted = Arrays.asList(acceptEncoding.split(","));
			for (String encoding : accepted) {
				OutputStream o = getOutputStreamByEncoding(encoding, source);
				if(o!=null) {
					response.setHeader("Content-Encoding", encoding);
					return o;
				}
			}
			return source;
		}

	}
	@Override
	public void writeOutput(Navajo inDoc, Navajo outDoc,long scheduledAt, long startedAt, String threadStatus) throws IOException,
			FileNotFoundException, UnsupportedEncodingException,
			NavajoException {

		long finishedScriptAt = System.currentTimeMillis();
		// postTime =
		long postTime = scheduledAt - connectedAt;
		long queueTime = startedAt - scheduledAt;
		long serverTime = finishedScriptAt - startedAt;
		if(dataPath!=null) {
			Property data = outDoc.getProperty(dataPath);
			Object dataObject = data.getTypedValue();
			if(dataObject instanceof Binary) {
				Binary b = (Binary)dataObject;
				if(contentType!=null) {
					response.setContentType(contentType);
				} else {
					response.setContentType(b.guessContentType());
				}
				if(this.fileName!=null) {
					response.setHeader("Content-Disposition","attachment; filename="+this.fileName);

				}
				OutputStream out = getOutputStream(acceptEncoding, response.getOutputStream());
				b.write(out);
				out.close();
				return;
			} else {
				response.setContentType("text/plain; charset=UTF-8");
				OutputStream out = getOutputStream(acceptEncoding, response.getOutputStream());
				OutputStreamWriter osw = new OutputStreamWriter(out);
				osw.write(""+dataObject);
				osw.close();
			}
		}
		OutputStream out = getOutputStream(acceptEncoding, response.getOutputStream());
		response.setContentType("text/xml; charset=UTF-8");
//		response.setHeader("Connection", "close"); 

		if(outDoc==null) {
			logger.warn("Null outDoc. This is going to hurt");
			response.sendError(500, "No response received, possible scheduling problem.");
			return;
		} else if (outDoc.getHeader()==null) {
			logger.warn("Null outDoc header. This is going to hurt");
			response.sendError(500, "No response header received, possible scheduling problem.");
			return;
		}
		outDoc.getHeader().setHeaderAttribute("postTime", "" + postTime);
		outDoc.getHeader().setHeaderAttribute("queueTime", "" + queueTime);
		outDoc.getHeader().setHeaderAttribute("serverTime", "" + serverTime);
		outDoc.getHeader().setHeaderAttribute("threadName","" + Thread.currentThread().getName());

		outDoc.write(out);
		
		out.close();
		
		if (inDoc != null
				&& inDoc.getHeader() != null
				&& outDoc.getHeader() != null) {
		    
            statLogger.info("Finished {} ({}) in {}ms - {}", outDoc.getHeader().getHeaderAttribute("accessId"), inDoc.getHeader().getRPCName(),
                    (System.currentTimeMillis() - connectedAt), threadStatus);
			
		}
	}

	protected final void copyResource(OutputStream out, InputStream in) {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			try {
				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}
			} catch (IOException e) {
				logger.error("Error: ", e);
				ready = true;
			}
			if (read <= -1) {
				ready = true;
			}
		}
		try {
			bout.flush();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	@Override
	public void fail(Exception e) {
//		logger.error("Error: ", e);
		try {
			response.sendError(500, e.getMessage());
		} catch (IOException e1) {
			logger.error("Error: ", e1);
		}		
	}

	@Override
	public void endTransaction() throws IOException {
//		response.getOutputStream().close();
		
	}

	@Override
	public Navajo getInputDocument() {
		return inDoc;
	}

	@Override
	public ServletRequest getHttpRequest() {
		return request;
	}
	

}
