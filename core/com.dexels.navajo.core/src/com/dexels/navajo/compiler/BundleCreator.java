package com.dexels.navajo.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.osgi.framework.BundleException;

import com.dexels.navajo.script.api.CompiledScriptInterface;

/**
 * Interface for TSL compiling service
 * @author frank
 *
 */
public interface BundleCreator {

	public static final String SCRIPTPROTOCOL = "script://";

	/**
	 * Install a bundle of a specific script
	 * @param force 
	 * @param scriptPath
	 * @throws BundleException
	 * @throws FileNotFoundException
	 */
//	public long installBundle(String scriptName) throws BundleException,
//			FileNotFoundException;
	
//	public Collection<Long> installBundles(String scriptPrefix) throws BundleException;

	public void createBundle(String script, Date date, String extension,
			List<String> failures, List<String> success, List<String> skipped, boolean force,boolean keepIntermediateFiles) throws Exception;

	public Date getBundleInstallationDate(String scriptPath, String tenant, String extension);
	public Date getScriptModificationDate(String scriptPath, String tenant,String extension) throws FileNotFoundException;
	public Date getCompiledModificationDate(String scriptPath, String extension);

//	public void installBundles(File baseDir, List<String> failures, List<String> success) throws Exception;
	public void installBundles(String scriptPath,List<String> failures,List<String> success, List<String> skipped, boolean force,String extension) throws Exception;
	public void installBundle(String scriptPath,
			List<String> failures, List<String> success, List<String> skipped, boolean force,String extension);

	public void verifyScript(String script, List<String> failed,
			List<String> success);

	/**
	 * Format the compilation timestamp in a consistent way
	 * @param d
	 * @return
	 */
	public String formatCompilationDate(Date d);

	/**
	 * Tries to resolve a compiledscript. Returns null if not found.
	 * @param rpcName
	 * @param tenantQualified TODO
	 * @return Null if not found
	 * @throws ClassNotFoundException if something weird happened
	 */
	public CompiledScriptInterface getCompiledScript(String rpcName, String tenant,String extension, boolean tenantQualified) throws ClassNotFoundException;

	/**
	 * Same as getCompiledScript, only will try to install (and compile if needed) bundle if it isn't there.
	 * @param rpcName
	 * @return
	 * @throws Exception
	 */
	public CompiledScriptInterface getOnDemandScriptService(String scriptName, String rpcName, String tenant, boolean tenantQualified,boolean force,String extension) throws Exception;

}
