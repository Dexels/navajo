
package com.dexels.navajo.dsl.expression;


/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class NavajoExpressionStandaloneSetup extends NavajoExpressionStandaloneSetupGenerated{

	public static void doSetup() {
		new NavajoExpressionStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
	
	public static void main(String args[]) {
		doSetup();
		
	}
}

