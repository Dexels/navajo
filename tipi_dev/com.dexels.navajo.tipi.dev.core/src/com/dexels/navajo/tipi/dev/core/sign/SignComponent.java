package com.dexels.navajo.tipi.dev.core.sign;

import java.io.File;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;

public interface SignComponent {

	public void signJnlp(File jnlpFile, String profile, String alias,
			String storepass, File keystore, File baseDir);

	public void signdependency(Dependency d, String alias, String storepass,
			File keystore, File repo);

}