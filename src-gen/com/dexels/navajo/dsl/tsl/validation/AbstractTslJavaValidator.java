package com.dexels.navajo.dsl.tsl.validation;
 
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import com.dexels.navajo.dsl.expression.validation.NavajoExpressionJavaValidator;
import org.eclipse.xtext.validation.ComposedChecks;

@ComposedChecks(validators= {org.eclipse.xtext.validation.ImportUriValidator.class, org.eclipse.xtext.validation.NamesAreUniqueValidator.class})
public class AbstractTslJavaValidator extends NavajoExpressionJavaValidator {

	@Override
	protected List<EPackage> getEPackages() {
	    List<EPackage> result = new ArrayList<EPackage>();
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.dexels.com/navajo/dsl/tsl/NavajoTsl"));
	    result.add(EPackage.Registry.INSTANCE.getEPackage("http://www.dexels.com/expression/1.0"));
		return result;
	}

}
