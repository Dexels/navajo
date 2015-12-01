package com.dexels.navajo.scalacompiler;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Manifest;

/**
 * Goal which counts the total lines of code
 * 
 * @goal scalacompiler
 * 
 * @phase test
 */
public class MyMojo extends AbstractMojo {

	/**
	 * Base-dir.
	 * 
	 * @parameter default-value="${basedir}"
	 * @required
	 */
	private File basedir;

	public void execute() throws MojoExecutionException {
		File manFile = new File(basedir, "META-INF/MANIFEST.MF");
		Manifest manifest = null;

		try {
			FileInputStream is = new FileInputStream(manFile);
			manifest = new Manifest(is);
			is.close();
		} catch (FileNotFoundException e) {
			getLog().warn("Unable to find MANIFEST.MF!");
			return;
		} catch (IOException e) {
			getLog().warn("Error in reading MANIFEST.MF!");
			return;
		}
 
		String navajoExtensionHeader = manifest.getMainAttributes().getValue("Navajo-Extension");
		String navajoName = manifest.getMainAttributes().getValue("Navajo-Name");

		if (navajoExtensionHeader != null && navajoName != null) {
			getLog().info("Found navajoExtensionHeader! " + navajoExtensionHeader);
			getLog().info("Found navajoName! " + navajoName);
			ApiGenerator i = new ApiGenerator();
			File dest = new File(basedir, "../");
			i.createScalaFragment(manifest, basedir, dest);
		}
	}


}
