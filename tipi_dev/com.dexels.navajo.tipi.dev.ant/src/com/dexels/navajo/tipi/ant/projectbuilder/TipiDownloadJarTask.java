package com.dexels.navajo.tipi.ant.projectbuilder;

import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;


public class TipiDownloadJarTask extends BaseTipiClientTask {
	public void execute() throws BuildException {
		if (extensions == null || "".equals(extensions)) {
			throw new BuildException("No extensions defined ");
		}
		List<String> missing = ClientActions.checkExtensions(repository,extensions);
		if(!missing.isEmpty()) {
			throw new BuildException("Requested extension(s) not available on repository: "+missing);
		}
//		StringTokenizer st = new StringTokenizer(extensions, ",");
//		while (st.hasMoreTokens()) {
//			String ext = st.nextToken();
//			try {
//				VersionResolver vr = new VersionResolver();
//				vr.load(repository);
//				Map<String,String> resolveMap = vr.resolveVersion(ext);
//				String path = vr.resultVersionPath(ext);
////				ClientActions.downloadExtensionJars(ext,new URL(repository+path+"/"),ClientActions.getExtensionXml(resolveMap.get("extension"),resolveMap.get("version") ,repository),getProject().getBaseDir(),true);
//			} catch (IOException e) {
//				logger.error("Error: ",e);
//			}
//		}
	}
}
