package com.dexels.navajo.dsl.expression.validation;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.Check;

import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.FunctionCall;
 

public class NavajoExpressionJavaValidator extends AbstractNavajoExpressionJavaValidator {

//	@Check
//	public void checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.getName().charAt(0))) {
//			warning("Name should start with a capital", MyDslPackage.GREETING__NAME);
//		}
//	}
	
	/**
	 * @param fc  
	 */
	@Check
	public void checkFunction(FunctionCall fc) {
//		warning("I hate functions.",ExpressionPackage.FUNCTION_CALL__NAME);
	}

	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> ePackages = super.getEPackages();
		ePackages.add(ExpressionPackage.eINSTANCE);
		return ePackages;
	}

	
}
