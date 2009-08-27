package com.dexels.navajo.jsp.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.dexels.navajo.jsp.tags.BaseNavajoTag;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;

public class FormattedFileTag  extends BaseNavajoTag {
	private String filePath;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}
	public static void highlightFile(String name, InputStream in, OutputStream out, String encoding) throws IOException {
		XhtmlRendererFactory.getRenderer(FileUtils.getExtension(name)).highlight(name, in,out, encoding, false);

	}

	public int doStartTag() throws JspException {
		String realPath =  resolveScriptPath(filePath);
		File f = new File (realPath);
		try {
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			highlightFile(f.getName(), fis, baos, "UTF-8");
			String result = new String(baos.toByteArray(),"UTF-8");
			getPageContext().getOut().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



	public String resolveScriptPath(String path) {
		 return getPageContext().getServletContext().getRealPath(path);
	}

}
