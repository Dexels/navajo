package com.dexels.navajo.jsp.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.jsp.tags.BaseNavajoTag;
import com.uwyn.jhighlight.renderer.Renderer;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;

public class FormattedFileTag  extends BaseNavajoTag {
	private String filePath;
	private String absoluteFilePath;
	private String extension;
	private String name;
	private NavajoServerContext serverContext;
	private Object content;

	
	private final static Logger logger = LoggerFactory
			.getLogger(FormattedFileTag.class);
	
	public synchronized Object getContent() {
		return content;
	}

	public synchronized void setContent(Object content) {
		this.content = content;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}
	
	public synchronized String getExtension() {
		return extension;
	}

	public synchronized void setExtension(String extension) {
		this.extension = extension;
	}


	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}
	
	public NavajoServerContext getServerContext() {
		return serverContext;
	}

	public void setServerContext(NavajoServerContext serverContext) {
		this.serverContext = serverContext;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int doEndTag() throws JspException {
		return 0;
	}
	public static void highlightFile(String name, InputStream in, OutputStream out, String encoding) throws IOException {
		Renderer renderer = XhtmlRendererFactory.getRenderer(FileUtils.getExtension(name));
		if(renderer==null) {
			renderer = XhtmlRendererFactory.getRenderer("java");
		}
		renderer.highlight(name, in,out, encoding, false);
	}

	public int doStartTag() throws JspException {
		try {
			String realPath = null;
			if(filePath!=null) {
				realPath = resolveScriptPath(filePath);
			} else if(absoluteFilePath!=null) {
				realPath = absoluteFilePath;
			} else if(content!=null) {
				highlightContent(content,name);
				return SKIP_BODY;
			} else {
				// nothing to do
				return SKIP_BODY;
			}
			
			File f = new File (realPath);
			if(!f.exists()) {
				getPageContext().getOut().write("File: "+realPath+" not found!");
				return -1;
			}
			InputStream fis = new FileInputStream(f);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			highlightFile(f.getName(), fis, baos, "UTF-8");
			String result = new String(baos.toByteArray(),"UTF-8");
			getPageContext().getOut().write(result);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return 0;
	}



	private void highlightContent(Object content, String name) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if(content instanceof Binary) {
			Binary bin = (Binary)content;
			highlightFile(name, bin.getDataAsStream(), baos, "UTF-8");
		} else {
			String cont = content.toString();
			highlightFile(name, new ByteArrayInputStream(cont.getBytes()), baos, "UTF-8");
		}
		String result = new String(baos.toByteArray(),"UTF-8");
		getPageContext().getOut().write(result);
		
	}

	public String resolveScriptPath(String path) {
		if(serverContext==null) {
			 return getPageContext().getServletContext().getRealPath(path);
		} 
		File root = getServerContext().getNavajoRoot();
		String absolutePath = new File(root,path).getAbsolutePath();
		return absolutePath;
	}

}
