package com.dexels.navajo.jsp.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.jsp.tags.BaseNavajoTag;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;
import com.uwyn.jhighlight.tools.FileUtils;

public class DumpFormattedTmlTag extends BaseNavajoTag  {

	private String myService;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DumpFormattedTmlTag.class);
	
	public void setService(String service) {
		myService = service;
	}
	public void highlightFile(String name, InputStream in, OutputStream out, String encoding) throws IOException {
		XhtmlRendererFactory.getRenderer(FileUtils.getExtension(name)).highlight(name, in,out, encoding, false);
	}
	
	public int doStartTag() throws JspException {
		Navajo n = null;
		if(myService!=null) {
				n = getNavajoContext().getNavajo(myService);
		} else {
			n = getNavajoContext().getNavajo();
			myService = n.getHeader().getRPCName();
		}

		getNavajoContext().getNavajo();
		try {
			File f= File.createTempFile("tmpTml", ".xml");
			FileOutputStream fos = new FileOutputStream(f);
			n.write(fos);
			fos.flush();
			fos.close();
			
			FileInputStream fis = new FileInputStream(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			highlightFile(myService+".xml", fis, baos, "UTF-8");
			String result = new String(baos.toByteArray(),"UTF-8");
			getPageContext().getOut().write(result);
			
//			getPageContext().getOut().write("Dumped: " + myService);
			f.delete();
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		return EVAL_BODY_INCLUDE;
	}

}
