package com.dexels.navajo.server.listener.http;

import http.utils.multipartrequest.MultipartRequest;
import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.java_cup.internal.parse_action;

public class LaszloUploadServlet extends HttpServlet {
	ResourceBundle res;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  System.err.println("Proxiying GET command to POST");
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.err.println("UPLOADING.. WELL TRYING TO ANYWAY....");
			String sessionId = request.getSession().getId();
			System.err.println("Incoming sessionId: " + sessionId);
			String loc = "/home/laszlo/public_html/deploy/designer/uploads/" + sessionId + "/";
			res = ResourceBundle.getBundle("servlet");
			try{
				loc = res.getString("LaszloUploadLocation") + sessionId + "/";
			}catch(MissingResourceException e){
				e.printStackTrace();
			}
			if (request.getContentLength() > 0) {

				System.err.println("Uploading file!...................................................");
				ServletMultipartRequest upload = new ServletMultipartRequest(request, 5048576); // Max 5 Meg.
				System.out.println(MultipartRequest.MAX_READ_BYTES);
				System.out.println(upload.getContentType("Filedata"));
				
				InputStream is = upload.getFileContents("Filedata");
				File f = new File(loc);
				f.mkdirs();
				FileOutputStream fos = new FileOutputStream(loc + upload.getFileSystemName("Filedata"));
				
				byte[] buffer = new byte[1024];
				int len = 0;
						
				while (len != (-1)) {
					len = is.read(buffer, 0, 1024);
					if (len != (-1)) fos.write(buffer, 0, len);
				}
				
				fos.close();
				
				System.err.println("Stored: " + loc + upload.getFileSystemName("Filedata"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}