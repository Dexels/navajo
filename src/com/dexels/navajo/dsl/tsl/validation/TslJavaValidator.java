package com.dexels.navajo.dsl.tsl.validation;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.Check;

import com.dexels.navajo.dsl.model.expression.FunctionCall;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.Property;
import com.dexels.navajo.dsl.model.tsl.Tml;
import com.dexels.navajo.dsl.model.tsl.TslPackage;



public class TslJavaValidator extends AbstractTslJavaValidator {

	
//	@Check
//	public void checkGreetingStartsWithCapital(Greeting greeting) {
//		if (!Character.isUpperCase(greeting.getName().charAt(0))) {
//			warning("Name should start with a capital", MyDslPackage.GREETING__NAME);
//		}
//	}

	@Check
	public void checkMessage(FunctionCall fc) {
		warning("Messages suck.",TslPackage.MESSAGE);
	}

	
	@Check
	public void checkProperty(Property p) {
		System.err.println("Checking property!");
		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
		validateNeeds("property",attr, new String[]{"name"}, new String[]{"direction", "type"}, new String[]{"name","value","description","direction","subtypes","type","length","cardinality"});
		if("selection".equals(attr.get("type"))) {
			if(attr.get("cardinality")==null) {
				// cardinality required!
				
				warning("All selection properties should have a cardinality",TslPackage.PROPERTY);
				
			}
		}
	}

	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> ePackages = super.getEPackages();
//		ePackages.add(ExpressionPackage.eINSTANCE);
		ePackages.add(TslPackage.eINSTANCE);
		return ePackages;
	}


	@Check
	public void checkToplevel(Tml p) {
		System.err.println("Checking toplevel!");
	}
	
	private java.util.Map<String,String> createAttributeMap(EList<PossibleExpression> aa) {
		java.util.Map<String,String> result = new HashMap<String, String>();
		for (PossibleExpression attribute : aa) {
			
			String value = attribute.getValue();
			if(value==null) {
				value = attribute.getExpressionValue().toString();
			}
			result.put(attribute.getKey(), value);
		}
		return result;
	}
	
	private void validateNeeds(String tagName, java.util.Map<String,String> map, String[] required, String[] requiredWarning,String[] allowed) {
		System.err.println("Attributes: "+map);
		for (String string : required) {
			String result = map.get(string);
			if(result==null) {
				error("Tag: "+tagName+" is missing attribute: "+string+"",TslPackage.POSSIBLE_EXPRESSION);
			}
		}
		for (String string : requiredWarning) {
			String result = map.get(string);
			if(result==null) {
				warning("Tag: "+tagName+" should have attribute: "+string+"",TslPackage.POSSIBLE_EXPRESSION);
			}
		}
		for (java.util.Map.Entry<String, String> e : map.entrySet()) {
			if(!contains(allowed,e.getKey())) {
				error("Tag: "+tagName+" does not allow: "+e.getKey()+" (Currently "+e.getValue()+")",TslPackage.POSSIBLE_EXPRESSION);
			}
		}
//		for (String string : allowed) {
//			String result = map.get(string);
//			if(result!=null) {
//				warning("Tag: "+tagName+" is missing attribute: "+string+"",TslPackage.ATTRIBUTE);
//			}
//		}

	}
	
	private boolean contains(String[] l, String e) {
		for (String c : l) {
			if(c.equals(e)) {
				return true;
			}
		}
		return false;
	}
}
