
package com.dexels.navajo.dsl.tsl;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TslStandaloneSetup extends TslStandaloneSetupGenerated{

	public static void doSetup() {
		new TslStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

