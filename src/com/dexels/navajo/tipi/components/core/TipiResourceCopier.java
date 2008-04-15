package com.dexels.navajo.tipi.components.core;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 * 
 */
public class TipiResourceCopier extends TipiHeadlessComponentImpl {

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		if (name.equals("copy")) {
			Operand source = compMeth.getEvaluatedParameter("source", event);
			Operand target = compMeth.getEvaluatedParameter("target", event);
			String targetFile = (String) target.value;
			if (targetFile.startsWith("%user.home%")) {
				String file = targetFile.substring(targetFile.lastIndexOf("/"));
				targetFile = System.getProperty("user.home") + file;
			}
			System.err.println("TargetFile: " + targetFile);

			try {
				FileInputStream bin = new FileInputStream((String) source.value);
				File file_out = new File(targetFile);
				file_out.getParentFile().mkdirs();
				FileOutputStream fout = new FileOutputStream(file_out);
				byte[] buffer = new byte[1024];
				while (bin.read(buffer) >= 0) {
					fout.write(buffer);
				}
				fout.flush();
				bin.close();
				fout.close();
				System.err.println("Resource copied!");
			} catch (IOException ex) {
				System.err.println("Error reading from resource");
				ex.printStackTrace();
			}
		}
	}
}
