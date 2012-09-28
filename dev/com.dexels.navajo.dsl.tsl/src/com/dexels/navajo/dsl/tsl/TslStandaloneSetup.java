
package com.dexels.navajo.dsl.tsl;

import com.dexels.navajo.dsl.tsl.formatting.TslFormatter;
import com.google.inject.Injector;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TslStandaloneSetup extends TslStandaloneSetupGenerated{

	public static void doSetup() {
		Injector ij = new TslStandaloneSetup().createInjectorAndDoEMFRegistration();
//		InjectionTest i = ij.getInstance(InjectionTest.class);
//		i.doSomething();
		//		ij.getInstance(Class<? extends IFormatter>)
		ij.getInstance(TslFormatter.class);
	
	}

	public static void main(String[] args) {
		doSetup();
		
		String name = "Gorilla";
		if( !(name.length()<1)) {
			if (!Character.isUpperCase(name.charAt(0))) {
				// lower case name
				System.err.println("Not upper.");
			}
		}
	}
	
}

