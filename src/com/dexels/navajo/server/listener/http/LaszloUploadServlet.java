package com.dexels.navajo.server.listener.http;

import http.utils.multipartrequest.MultipartRequest;
import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LaszloUploadServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.err.println("Go baby go....");
			if (request.getContentLength() > 0) {

				System.err.println("Uploading file!...................................................");
				ServletMultipartRequest upload = new ServletMultipartRequest(request, 104857600); // Max 100 Meg.
				System.out.println(MultipartRequest.MAX_READ_BYTES);
				System.out.println(upload.getContentType("Filedata"));
				
				InputStream is = upload.getFileContents("Filedata");
				FileOutputStream fos = new FileOutputStream("/home/orion/projects/laszlo/laszlo/designer/uploads/" + upload.getFileSystemName("Filedata"));
				
				byte[] buffer = new byte[1024];
				int len = 0;
				
				
				
				while (len != (-1)) {
					len = is.read(buffer, 0, 1024);
					if (len != (-1)) fos.write(buffer, 0, len);
				}
				
				fos.close();
				
				System.err.println("Stored: " + "/home/orion/projects/laszlo/laszlo/designer/uploads/" + upload.getFileSystemName("Filedata"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}