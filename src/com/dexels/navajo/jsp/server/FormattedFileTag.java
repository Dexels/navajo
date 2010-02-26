package com.dexels.navajo.jsp.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.jsp.tags.BaseNavajoTag;
import com.uwyn.jhighlight.renderer.Renderer;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;

public class FormattedFileTag  extends BaseNavajoTag {
	private String filePath;
	private String absoluteFilePath;
	public String getAbsoluteFilePath() {
		return absoluteFilePath;
	}

	public void setAbsoluteFilePath(String absoluteFilePath) {
		this.absoluteFilePath = absoluteFilePath;
	}



	private NavajoServerContext serverContext;
	
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
			} else {
				if(absoluteFilePath!=null) {
					realPath = absoluteFilePath;
				}
			}
			File f = new File (realPath);
			if(!f.exists()) {
				getPageContext().getOut().write("File: "+realPath+" not found!");
				return -1;
			}
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			highlightFile(f.getName(), fis, baos, "UTF-8");
			String result = new String(baos.toByteArray(),"UTF-8");
			getPageContext().getOut().write(result);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return 0;
	}



	public String resolveScriptPath(String path) throws IOException {
		if(serverContext==null) {
			 return getPageContext().getServletContext().getRealPath(path);
		} else {
			File root = getServerContext().getNavajoRoot();
			String absolutePath = new File(root,path).getAbsolutePath();
			System.err.println("FormatFile: Resolved path: "+absolutePath);
			return absolutePath;
		}
	}

}
