package com.dexels.navajo.studio.eclipse;

/*
 * (c) Copyright Sysdeo SA 2001, 2002.
 * All Rights Reserved.
 */

import java.io.*;
import java.util.ArrayList;

import org.eclipse.core.internal.resources.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.*;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jdt.debug.ui.*;
import org.eclipse.jdt.launching.ExecutionArguments;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.jdt.launching.VMRunnerConfiguration;
import org.eclipse.jface.preference.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;
import org.eclipse.ui.part.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.*;
import com.dexels.navajo.studio.script.plugin.navajobrowser.preferences.*;
import com.sun.rsasign.*;

/**
 * Utility class for launching a JVM in Eclipse and registering it to debugger
 * 
 * It might exist better way to implements those operations,
 * or they might already exist in other form JDT
 */

public class VMLauncherUtility {

	static public IVMInstall getVMInstall() {
		IVMInstallType[] vmTypes = JavaRuntime.getVMInstallTypes();

// Maybe extract this from the JavaProject? Or maybe that is already the case...
		//		for (int i = 0; i < vmTypes.length; i++) {
//			IVMInstall[] vms = vmTypes[i].getVMInstalls();
//			for (int j = 0; j < vms.length; j++) {
//				if (vms[j].getId().equals(NavajoScriptPluginPlugin.getDefault().getNavajoJRE())) {
//					return vms[j];
//				}
//			}
//		}
		return JavaRuntime.getDefaultVMInstall();
	}


	static public Launch runVM(String label, String classToLaunch, String[] classpath, String[] bootClasspath, String vmArgs, String prgArgs, ISourceLocator sourceLocator, boolean debug, boolean showInDebugger, final String scriptId, final IProject project,final  Job afterLaunch)
		throws CoreException {
//	    System.err.println("label: "+label+" class: "+classToLaunch);
//	    for (int i = 0; i < bootClasspath.length; i++) {
//            System.err.println("bootclass: "+bootClasspath[i]);
//        }

	    final IFolder tml = NavajoScriptPluginPlugin.getDefault().getTmlFolder(project);

	    
//	    for (int i = 0; i < classpath.length; i++) {
//            System.err.println("classp: "+classpath[i]);
//        }
//	    System.err.println("Programargs: "+prgArgs+" vm: "+vmArgs);
		IVMInstall vmInstall = getVMInstall();
		String mode = "";
		if (debug)
			mode = ILaunchManager.DEBUG_MODE;
		else
			mode = ILaunchManager.RUN_MODE;

		IVMRunner vmRunner = vmInstall.getVMRunner(mode);
		//		Launch launch = createLaunch(label, classToLaunch, classpath, bootClasspath, vmArgs, prgArgs, sourceLocator, debug, showInDebugger, false);
		ILaunchConfigurationWorkingCopy config = createConfig(label, classToLaunch, classpath, bootClasspath, vmArgs, prgArgs, sourceLocator, debug, showInDebugger, false);
		final Launch launch = new Launch(config, mode, sourceLocator);
		if (vmRunner != null) {
			VMRunnerConfiguration vmConfig = new VMRunnerConfiguration(classToLaunch, classpath);
			ExecutionArguments executionArguments = new ExecutionArguments(vmArgs, prgArgs);
			vmConfig.setVMArguments(executionArguments.getVMArgumentsArray());
			vmConfig.setProgramArguments(executionArguments.getProgramArgumentsArray());

			if (bootClasspath.length == 0) {
				vmConfig.setBootClassPath(null); // use default bootclasspath	
			} else {
				vmConfig.setBootClassPath(bootClasspath);
			}
			vmRunner.run(vmConfig, launch, null);
		}
    	if (afterLaunch!=null) {
    	    afterLaunch.setPriority(Job.LONG);
    	    // TODO Begone with this filthyness!
    	    afterLaunch.schedule(1000);           
        }    
			// Show in debugger
		if (showInDebugger) {
			DebugPlugin.getDefault().getLaunchManager().addLaunch(launch);
		}
		return launch;
	}

	static public ILaunchConfigurationWorkingCopy createConfig(String label, String classToLaunch, String[] classpath, String[] bootClasspath, String vmArgs, String prgArgs, ISourceLocator sourceLocator, boolean debug, boolean showInDebugger, boolean saveConfig) throws CoreException {
		IVMInstall vmInstall = getVMInstall();

		ILaunchConfigurationType launchType = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType("org.eclipse.jdt.launching.localJavaApplication");
		ILaunchConfigurationWorkingCopy config = launchType.newInstance(null, label);
		config.setAttribute(IDebugUIConstants.ATTR_PRIVATE, !saveConfig);
// Dont know what this is supposed to mean
		//		String targetPerspective = NavajoScriptPluginPlugin.getDefault().getTargetPerspective();
//		config.setAttribute(IDebugUIConstants.ATTR_TARGET_DEBUG_PERSPECTIVE, targetPerspective);
//		config.setAttribute(IDebugUIConstants.ATTR_TARGET_RUN_PERSPECTIVE, targetPerspective);

		config.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, JavaUISourceLocator.ID_PROMPTING_JAVA_SOURCE_LOCATOR);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, classToLaunch);

		ArrayList classpathMementos = new ArrayList();
		for (int i = 0; i < classpath.length; i++) {
			IRuntimeClasspathEntry cpEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(new Path(classpath[i]));
			cpEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
			classpathMementos.add(cpEntry.getMemento());
		}
		if (bootClasspath.length == 0) {
			LibraryLocation[] librairies = vmInstall.getVMInstallType().getDefaultLibraryLocations(vmInstall.getInstallLocation());
			for (int i = 0; i < librairies.length; i++) {
				IRuntimeClasspathEntry cpEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(librairies[i].getSystemLibraryPath());
				cpEntry.setClasspathProperty(IRuntimeClasspathEntry.BOOTSTRAP_CLASSES);
				classpathMementos.add(cpEntry.getMemento());
			}
		} else {
			for (int i = 0; i < bootClasspath.length; i++) {
				IRuntimeClasspathEntry cpEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(new Path(bootClasspath[i]));
				cpEntry.setClasspathProperty(IRuntimeClasspathEntry.BOOTSTRAP_CLASSES);
				classpathMementos.add(cpEntry.getMemento());
			}
		}

		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpathMementos);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, prgArgs);
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, vmArgs);

		if(saveConfig) {
			config.doSave();
		} 

		return config;
	}



}
