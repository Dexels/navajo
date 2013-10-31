package com.dexels.navajo.tipi.vaadin.application.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.application.VaadinInstallationPathResolver;

/**
 * The File servlet for serving from absolute path.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/07/fileservlet.html
 */
public class VaadinFileServlet extends HttpServlet{

	private static final long serialVersionUID = -4298683043064936546L;

	// Constants
	// ----------------------------------------------------------------------------------

	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	// Properties
	// ---------------------------------------------------------------------------------

	private String filePath;

	private ContextInstance contextInstance = null;

	
	private final static Logger logger = LoggerFactory
			.getLogger(VaadinFileServlet.class);
	// Actions
	// ------------------------------------------------------------------------------------

	@Override
	public void init() throws ServletException {

		// Define base path somehow. You can define it as init-param of the
		// servlet.
		if(contextInstance !=null) {
			logger.info("Injected context instance found!");
			this.filePath = contextInstance.getPath();
		} else {
			try {
				this.filePath = VaadinInstallationPathResolver.getInstallationPath(getServletContext()).get(0);
			} catch (TipiException e) {
				throw new ServletException("Error resolving Tipi installation path. ",e);
			}
			
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Get requested file by path info.
		String requestedFile = request.getPathInfo();
		// Check if file is actually supplied to the request URI.
		if (requestedFile == null) {
			// Do your thing if the file is not supplied to the request URI.
			// Throw an exception, or send 404, or show default/warning page, or
			// just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Decode the file name (might contain spaces and on) and prepare file
		// object.
		
		File vaadinPath = new File(filePath,"VAADIN");
		File file = new File(vaadinPath,
				URLDecoder.decode(requestedFile, "UTF-8"));
		// Check if file actually exists in filesystem.
		if (!file.exists()) {
			// Do your thing if the file appears to be non-existing.
			// Throw an exception, or send 404, or show default/warning page, or
			// just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Get content type by filename.
		String contentType = getServletContext().getMimeType(file.getName());

		// If content type is unknown, then set the default value.
		// For all content types, see:
		// http://www.w3schools.com/media/media_mimeref.asp
		// To add new content types, add new mime-mapping entry in web.xml.
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		// Init servlet response.
		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		
//		response.setHeader("Content-Disposition", "attachment; filename=\""
//				+ file.getName() + "\"");
		
		// Prepare streams.
		InputStream input = null;
		OutputStream output = null;

		try {
			input = new BufferedInputStream(new FileInputStream(file),DEFAULT_BUFFER_SIZE);
			String acceptEncoding = request.getHeader("Accept-Encoding");
			String ae = acceptEncoding.toLowerCase();
			String ct = contentType.toLowerCase();
			
			//CACHE
			if(ct.contains("text/html") || ct.contains("image/gif") || ct.contains("image/gif") || ct.contains("image/png") || ct.contains("text/css") || ct.contains("application/x-javascript") || ct.contains("application/json")){
				String ETag = request.getHeader("If-None-Match");
				MessageDigest md = MessageDigest.getInstance("MD5");
		        FileInputStream fis = new FileInputStream(file);
		        byte[] dataBytes = new byte[1024];
		        int nread = 0; 
		        while ((nread = fis.read(dataBytes)) != -1) {
		          md.update(dataBytes, 0, nread);
		        }
		        byte[] mdbytes = md.digest();
		        fis.close();
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < mdbytes.length; i++) {
		        	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		        }
		        
				if(ETag == null){
					response.setHeader("ETag", sb.toString());
				}else{
			        if(ETag.equals(sb.toString())){
						response.setStatus(304);
						return;
			        }else{
			        	response.setHeader("ETag", sb.toString());
			        }
				}
			}
			
			//favicon
			if(ct.contains("image/x-icon")){
				response.setHeader("Cache-Control", "max-age=31536000, public"); //1 year
			}

			response.setContentType(contentType);
			//CACHE
			if(acceptEncoding != null && ae.contains("gzip") && (ct.contains("text/css") 
					|| ct.contains("application/x-javascript") || ct.contains("text/html") || ct.contains("application/json"))){
				response.setHeader("Content-Encoding", "gzip");
				output = new GZIPOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);	
			}else{//Normal flow
				response.setHeader("Content-Length", String.valueOf(file.length()));
				output = new BufferedOutputStream(response.getOutputStream(),DEFAULT_BUFFER_SIZE);
			}
			
			//Write to output
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}	

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}
	}

	// Helpers (can be refactored to public utility class)
	// ----------------------------------------

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it.
				logger.error("Error: ",e);
			}
		}
	}

	public void setContextInstance(ContextInstance ci) {
		this.contextInstance = ci;
	}
	
	public void clearContextInstance(ContextInstance ci) {
		this.contextInstance = null;
	}

}