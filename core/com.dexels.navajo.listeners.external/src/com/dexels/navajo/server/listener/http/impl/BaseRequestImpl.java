package com.dexels.navajo.server.listener.http.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.ClientInfo;
import com.dexels.navajo.script.api.LocalClient;


public abstract class BaseRequestImpl extends BaseInMemoryRequest implements
		AsyncRequest {

	protected final HttpServletRequest request;
	protected HttpServletResponse response;

	protected final String recvEncoding;
	protected final String sendEncoding;
	protected final Object cert;
	protected long connectedAt = -1;
	private String url;
	private Navajo inDoc;
	private final LocalClient myLocalClient;

	private final static Logger logger = LoggerFactory
			.getLogger(BaseRequestImpl.class);
	
	private final static Logger statLogger = LoggerFactory
			.getLogger("stats");

	
	public BaseRequestImpl(LocalClient lc, HttpServletRequest request,
			HttpServletResponse response, String sendEncoding,
			String recvEncoding, Object cert) throws UnsupportedEncodingException, IOException {
		// this.event = event;
		this.myLocalClient = lc;
		this.response = response;
		this.request = request;
		this.recvEncoding = recvEncoding;
		this.sendEncoding = sendEncoding;
		this.cert = cert;
		this.connectedAt = System.currentTimeMillis();
		setUrl(createUrl(this.request));
		
		copyResource( getRequestOutputStream(), request.getInputStream());
		
		this.inDoc = parseInputNavajo();
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

	protected final Navajo parseInputNavajo() throws IOException,
			UnsupportedEncodingException {
		BufferedReader r;
		InputStream is = getRequestInputStream();
		Navajo in = null;
		logger.info("Send encoding: "+sendEncoding);
		if (sendEncoding != null
				&& sendEncoding.equals(AsyncRequest.COMPRESS_JZLIB)) {
			r = new BufferedReader(new java.io.InputStreamReader(
					new InflaterInputStream(is)));
		} else if (sendEncoding != null
				&& sendEncoding.equals(AsyncRequest.COMPRESS_GZIP)) {
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

	public ClientInfo createClientInfo(long scheduledAt, long startedAt,
			int queueLength, String queueId) {
		ClientInfo clientInfo = new ClientInfo(
				request.getRemoteAddr(),
				"unknown",
				recvEncoding,
				(int) (scheduledAt - connectedAt),
				(int) (startedAt - scheduledAt),
				queueLength,
				queueId,
				(recvEncoding != null && (recvEncoding.equals(COMPRESS_GZIP) || recvEncoding
						.equals(COMPRESS_JZLIB))),
				(sendEncoding != null && (sendEncoding.equals(COMPRESS_GZIP) || sendEncoding
						.equals(COMPRESS_JZLIB))), request.getContentLength(),
				new java.util.Date(connectedAt));
		return clientInfo;
	}

	@Override
	public Object getCert() {
		return cert;
	}

	public void writeOutput(Navajo inDoc, Navajo outDoc,long scheduledAt, long startedAt, String threadStatus) throws IOException,
			FileNotFoundException, UnsupportedEncodingException,
			NavajoException {
		OutputStream out;
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Connection", "close"); // THIS IS NOT SUPPORTED,
													// I.E. IT DOES NOT
													// WORK...EH.. PROBABLY..

		if (recvEncoding != null && recvEncoding.equals(COMPRESS_JZLIB)) {
			response.setHeader("Content-Encoding", COMPRESS_JZLIB);
			out = new DeflaterOutputStream(response.getOutputStream());
		} else if (recvEncoding != null && recvEncoding.equals(COMPRESS_GZIP)) {
			response.setHeader("Content-Encoding", COMPRESS_GZIP);
			out = new java.util.zip.GZIPOutputStream(response.getOutputStream());
		} else {
//			logger.warn("No content encoding specified: (" + sendEncoding + "/"
//					+ recvEncoding + ")");
			out = response.getOutputStream();
		}

		long finishedScriptAt = System.currentTimeMillis();
		// postTime =
		long postTime = scheduledAt - connectedAt;
		long queueTime = startedAt - scheduledAt;
		long serverTime = finishedScriptAt - startedAt;

		outDoc.getHeader().setHeaderAttribute("postTime", "" + postTime);
		outDoc.getHeader().setHeaderAttribute("queueTime", "" + queueTime);
		outDoc.getHeader().setHeaderAttribute("serverTime", "" + serverTime);
		outDoc.getHeader().setHeaderAttribute("threadName",
				"" + Thread.currentThread().getName());

		File temp = null;
		temp = File.createTempFile("navajo", ".xml");
		FileOutputStream fos = new FileOutputStream(temp);

		// Maybe remove this flush. Will be more efficient, but the writing time
		// will be less accurate.
		outDoc.write(fos);
		fos.close();

		response.setContentLength((int) temp.length());

		FileInputStream fis = new FileInputStream(temp);
		copyResource(out, fis);

		fis.close();
		out.close();
		temp.delete();

		long writeFinishedAt = System.currentTimeMillis();
		long writeTime = writeFinishedAt - finishedScriptAt;

		// int threadsActive = getActiveCount();

		if (inDoc != null
				&& inDoc.getHeader() != null
				&& outDoc != null
				&& outDoc.getHeader() != null
				&& !myLocalClient.isSpecialWebservice(inDoc.getHeader()
						.getRPCName())) {
			statLogger.info ("("
					+ myLocalClient.getApplicationId()
					+ "): "
					+ new java.util.Date(connectedAt)
					+ ": "
					+ outDoc.getHeader().getHeaderAttribute("accessId")
					+ ":"
					+ inDoc.getHeader().getRPCName()
					+ "("
					+ inDoc.getHeader().getRPCUser()
					+ "):"
					+ (System.currentTimeMillis() - connectedAt)
					+ " ms. "
					+ "(st="
					+ outDoc.getHeader().getHeaderAttribute("serverTime")
					+ ",rpt="
					+ outDoc.getHeader().getHeaderAttribute("requestParseTime")
					+ ",at="
					+ outDoc.getHeader()
							.getHeaderAttribute("authorisationTime") + ",pt="
					+ outDoc.getHeader().getHeaderAttribute("processingTime")
					+ ",tc="
					+ outDoc.getHeader().getHeaderAttribute("threadCount")
					+ ",cpu="
					+ outDoc.getHeader().getHeaderAttribute("cpuload")
					+ ",cpt=" + postTime + ",cqt=" + queueTime + ",qst="
					+ serverTime + ",cta=" + threadStatus + ",cwt=" + writeTime
					+ ")" + " (" + sendEncoding + "/" + recvEncoding +

					")");
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
		logger.error("Error: ", e);
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
