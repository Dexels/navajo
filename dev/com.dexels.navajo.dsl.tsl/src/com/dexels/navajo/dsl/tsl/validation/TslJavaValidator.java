package com.dexels.navajo.dsl.tsl.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.validation.Check;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider;
import com.dexels.navajo.dsl.model.tsl.Tml;
import com.dexels.navajo.dsl.model.tsl.TslPackage;
import com.google.inject.Inject;
 

public class TslJavaValidator extends AbstractTslJavaValidator {

	@Inject
	protected  INavajoContextProvider navajoContext;


	
	public static final String ISSUE_ILLEGAL_ATTRIBUTE = "ISSUE_ILLEGAL_ATTRIBUTE";
	public static final String ISSUE_ILLEGAL_ATTRIBUTE_VALUE = "ISSUE_ILLEGAL_ATTRIBUTE_VALUE";
	public static final String ISSUE_SHOULD_BE_EXPRESSION = "ISSUE_SHOULD_BE_EXPRESSION";
	public static final String ISSUE_SHOULD_NOT_BE_EXPRESSION = "ISSUE_SHOULD_NOT_BE_EXPRESSION";
	public static final String ISSUE_MISSING_ATTRIBUTE = "ISSUE_MISSING_ATTRIBUTE";
//	public static final String ISSUE_ILLEGAL_OPTIONAL_ATTRIBUTE_VALUE = "ISSUE_ILLEGAL_OPTIONAL_ATTRIBUTE_VALUE";

//	private static final String DEFAULT_CARDINALITY = "1";

	
	// TODO rewrite into property file based version	
	public static List<String> getDefaultValueForAttribute(String type, String attributeName) {
		System.err.println("Getting default for: "+type+" attr: "+attributeName);
		List<String> result = new ArrayList<String>();
		if("property".equals(type)) {
			if("direction".equals(attributeName)) {
				result.add("in");
				result.add("out");
			}
			if("type".equals(attributeName)) {
				result.addAll(NavajoFactory.getInstance().getNavajoTypes());
				Collections.sort(result);
			}
			if("name".equals(attributeName)) {
				result.add("propertyName");
			}
			if("cardinality".equals(attributeName)) {
				result.add("1");
				result.add("+");
			}

		}
		if("message".equals(type)) {
			if("type".equals(attributeName)) {
				result.add("simple");
				result.add("array");
				result.add("array_element");
			}
			if("mode".equals(attributeName)) {
				result.add("default");
				result.add("ignore");
			}
			if("name".equals(attributeName)) {
				result.add("messageName");
			}
		}
		return result;
	}
	
	
	
	protected INavajoContextProvider getNavajoContext() throws  IOException {
		return navajoContext;
	}
	
	protected List<EPackage> getEPackages() {
		List<EPackage> ePackages = super.getEPackages();
		ePackages.add(TslPackage.eINSTANCE);
		return ePackages;
	}

	@Check
	public void checkTml(Tml p) {
	}
	
//	@Check
//	public void checkMessage(Message p) {
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		validateNeeds("message",p.getAttributes(),attr, new String[]{"name"}, new String[]{}, new String[]{"name","type","filter","condition","index","mode"},new String[]{"filter","condition"},new String[]{"filter","condition"});
//		validateAttribute("message",p.getAttributes(),"type",getDefaultValueForAttribute("message", "type"),true);
//		validateAttribute("message",p.getAttributes(),"mode",getDefaultValueForAttribute("message", "mode"),true);
//		if(attr.get("mode")!=null) {
//			if(!"ignore".equals(attr.get("mode")) && !"default".equals(attr.get("mode")) ) {
////				warning("Only 'ignore' and 'default' are valid modes!",TslPackage.MESSAGE,ISSUE_ILLEGAL_ATTRIBUTE,"mode");
//// TODO FIX, don't know why it fires on every message
//			}
//		}
//
//	}

//	@Check
//	public void checkOption(Option p) {
//		// TODO Check if parent property is of type 'selection'
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		validateNeeds("option",p.getAttributes(),attr, new String[]{"name","value","selected"}, new String[]{}, new String[]{"name","value","selected","condition"},new String[]{"value","selected","condition"},new String[]{"condition"});
//	}
//
//	
	
//	@Check
//	public void checkProperty(Property p) {
//		if(p instanceof Param) {
//			return;
//		}
//
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//
//		// TODO Check if it has expression children, if so, it shouldn't have a value
//		// TODO If only one conditionless expression, propose conversion to value attribute
//		
//		EList<Element> ll = p.getChildren();
//		for (Element element : ll) {
//			if(element instanceof ExpressionTag) {
//				String value = attr.get("value");
//				if(value!=null) {
//					warning("Either supply a value attribute, or an expression node, not both.", p, TslPackage.EXPRESSION_TAG);
//				}
//			}
//		}
//		
//		validateNeeds("property",p.getAttributes(),attr, new String[]{"name"}, new String[]{"direction", "type"}, new String[]{"name","value","description","direction","subtypes","type","length","cardinality"},new String[]{"value","condition"},new String[]{"condition"});
//		String type = stripQuotes(attr.get("type"));
//		if("selection".equals(type)) {
//			if(attr.get("cardinality")==null) {
//				// cardinality required!
//				warning("All selection properties should have a cardinality",p,TslPackage.PROPERTY,ISSUE_MISSING_ATTRIBUTE,"property","cardinality");
//			}
//		}
//		validateAttribute("property",p.getAttributes(),"cardinality",TslJavaValidator.getDefaultValueForAttribute("property", "cardinality"),true);
//		validateAttribute("property",p.getAttributes(),"type",TslJavaValidator.getDefaultValueForAttribute("property", "type"),true);
//	}


//	@Check
//	public void checkExpression(ExpressionTag p) {
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		// TODO refactor a bit, only required value is different
//		if(p.getExpression()==null) {
//			validateNeeds("expression",p.getAttributes(),attr, new String[]{"value"}, new String[]{}, new String[]{"value","condition","space"},new String[]{"value","condition"},new String[]{"value","condition"});
//		} else {
//			validateNeeds("expression",p.getAttributes(),attr, new String[]{}, new String[]{}, new String[]{"value","condition","space"},new String[]{"value","condition"},new String[]{"value","condition"});
//		}
//	}

	 public  IProject getCurrentProject(EObject p){
		 Resource eResource = p.eResource();
		 URI eUri = eResource.getURI();
		 if (eUri.isPlatformResource()) {
			 String platformString = eUri.toPlatformString(true);
			 return ResourcesPlugin.getWorkspace().getRoot().findMember(platformString).getProject();
		 }
		 return null;
	 }
//		  log.info("Class: "+getCurrentObject().getClass());
//		  
//		  IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
//		  IEditorInput input =  editor.getEditorInput();
//		  IFile file = null;
//		  if(input instanceof IFileEditorInput){
//		   file = ((IFileEditorInput)input).getFile();
//		  }
//		  if(file==null) {
//			  return null;
//		  }
//		  IProject project = file.getProject();
//		  return project;
//
//		 }

	
//	@Check
//	public void checkMap(Map p) {
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		if(p.getMapName()!=null) {
//			// new style map
//			if(!p.getMapName().equals(p.getMapClosingName()) && p.isSplitTag()) {
//				error("Map closing tag: "+p.getMapClosingName()+" does not match opening tag!", p, TslPackage.MAP__MAP_CLOSING_NAME);
//			}
//			try {
//				AdapterProposal pp = getNavajoContext().getAdapter(getCurrentProject(p), p.getMapName());
//				if(pp==null) {
//					// can't do much then, probably a grammar error anyway
//					error("Map: "+p.getMapName()+" is unknown!", p, TslPackage.MAP);
//					return;
//				}
//				String[] allSetters = pp.getSetters().toArray(new String[pp.getSetters().size()+1]);
//				allSetters[allSetters.length-1] = "ref";
//				validateNeeds("map",p.getAttributes(),attr, pp.getRequiredSetters(), new String[]{}, allSetters ,allSetters,allSetters);
//							
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
////			AdapterProposal ap = con
//		} else {
//			String mapObject = attr.get("object");
//			String mapRef = attr.get("ref");
//			if(mapObject==null && mapRef==null) {
//				warning("Unspecified map, add .<mapname> suffix to tag, or supply an object attribute",p,TslPackage.MAP);
//			}
//		}
//		
//	}
	
//	@Check
//	public void checkParam(Param p) {
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		validateNeeds("param",p.getAttributes(), attr,new String[]{"name"}, new String[]{}, new String[]{"name","value","description","direction","subtypes","type","length","cardinality","condition"},new String[]{"value","condition"},new String[]{"condition"});
//		if("selection".equals(attr.get("type"))) {
//			if(attr.get("cardinality")==null) {
//				// cardinality required!
//				
//				warning("All selection properties should have a cardinality",p,TslPackage.PROPERTY,ISSUE_MISSING_ATTRIBUTE,"property","cardinality");
//				
//			}
//		}
//	}
//
//	@Check
//	public void checkFields(Field p) {
//		java.util.Map<String,String> attr = createAttributeMap(p.getAttributes());
//		validateNeeds("field",p.getAttributes(),attr, new String[]{"name"}, new String[]{}, new String[]{"name","value","condition"},new String[]{"value","condition"},new String[]{"value","condition"});
//	
//	}
//
//	
//	private java.util.Map<String,String> createAttributeMap(EList<PossibleExpression> aa) {
//		java.util.Map<String,String> result = new HashMap<String, String>();
//		for (PossibleExpression attribute : aa) {
//			
//			String value = attribute.getValue();
//			if(value==null) {
//				value = attribute.getExpressionValue().toString();
//			}
//			result.put(attribute.getKey(), value);
//		}
//		return result;
//	}

//
//	private void validateAttribute(String tagName, EList<PossibleExpression> eList, String attributeName,List<String> allowedValues, boolean isError) {
//		System.err.println("Validating: "+tagName+" atri: "+attributeName+" allowedValues: "+allowedValues);
//		String[] res = new String[allowedValues.size()];
//		for (int i=0; i<res.length;i++) {
//			res[i] = allowedValues.get(i);
//		}
//		try {
//			validateAttribute(tagName, eList, attributeName, res, isError);
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	
//	private void validateAttribute(String tagName, EList<PossibleExpression> eList, String attributeName, String[] allowedValues, boolean isError) {
////		EList<PossibleExpression> eList = tag.getAttributes();
//		PossibleExpression pp = null;
//		for (PossibleExpression attribute : eList) {
//			String value = stripQuotes(attribute.getValue());
//			if(attributeName.equals(attribute.getKey())) {
//				pp = attribute;
//				boolean found = false;
//				
//				for (String allowed : allowedValues) {
//					if(allowed.equals(value)) {
//						found = true;
//					}
//				}
//				if(!found) {
//					List<String> allowedValuesList = new LinkedList<String>();
//					for (String string2 : allowedValues) {
//						allowedValuesList.add(string2);
//					}
//					if (isError) {
//						error("Tag: "+tagName+" has an attribute '"+attributeName+"', which should be one of the following values: "+allowedValuesList,pp,TslPackage.POSSIBLE_EXPRESSION,ISSUE_ILLEGAL_ATTRIBUTE_VALUE,new String[]{tagName,attributeName});
//					} else {
//
//						warning("Tag: "+tagName+" has an attribute '"+attributeName+"', which should be one of the following values: "+allowedValuesList,pp,TslPackage.POSSIBLE_EXPRESSION,ISSUE_ILLEGAL_ATTRIBUTE_VALUE,new String[]{tagName,attributeName});
//
//					}
//				}
//				
//			}
//		}		
//	}
//	
//	private String stripQuotes(String label) {
//		if(label==null) {
//			return null;
//		}
//		if(label.startsWith("\"") && label.endsWith("\"")) {
//			return label.substring(1, label.length()-1);
//		}
//		return label;
//	}
//	
//	private void validateNeeds(String tagName, EList<PossibleExpression> eList, java.util.Map<String,String> map, String[] required, String[] requiredWarning,String[] allowed,String[] canBeExpression,String[] mustBeExpression) {
//		for (String string : required) {
//			String result = map.get(string);
//			if(result==null) {
//				error("Tag: "+tagName+" is missing attribute: "+string+"",TslPackage.POSSIBLE_EXPRESSION,ISSUE_MISSING_ATTRIBUTE,tagName,string);
//			}
//		}
//		for (String string : requiredWarning) {
//			String result = map.get(string);
//			if(result==null) {
//				warning("Tag: "+tagName+" should have attribute: "+string+"",TslPackage.POSSIBLE_EXPRESSION,ISSUE_MISSING_ATTRIBUTE,tagName,string);
//			}
//		}
//
//		for (java.util.Map.Entry<String, String> e : map.entrySet()) {
//			if(!contains(allowed,e.getKey())) {
//				error("Tag: "+tagName+" does not allow: "+e.getKey(),getExpressionByName(e.getKey(), eList),TslPackage.POSSIBLE_EXPRESSION,ISSUE_ILLEGAL_ATTRIBUTE);
//			}
//		}
//
//		for (java.util.Map.Entry<String, String> e : map.entrySet()) {
//			PossibleExpression pe = getExpressionByName(e.getKey(), eList);
//			if(pe!=null) {
//				if(pe.isExpression()) {
//					boolean found = false;
//					for (String elt : canBeExpression) {
//						if(elt.equals(e.getKey())) {
//							found = true;
//						}
//					}
//					if(!found) {
//						error("Attribute: "+e.getKey()+" of "+tagName+" can not be an expression!",pe,TslPackage.PROPERTY,ISSUE_SHOULD_NOT_BE_EXPRESSION);
//					}
//				}
//			} else {
//				System.err.println("odd..");
//			}
//		}
//		for (String expr : mustBeExpression) {
//			PossibleExpression pe = getExpressionByName(expr, eList);
//			if(pe!=null && !pe.isExpression()) {
//				error("Attribute: "+expr+" of "+tagName+" must be an expression!",pe,TslPackage.PROPERTY,ISSUE_SHOULD_BE_EXPRESSION);
//			}
//		}
//		
//
//	}
//	
//	private PossibleExpression getExpressionByName(String name, EList<PossibleExpression> list) {
//		for (PossibleExpression attribute : list) {
//			if(attribute.getKey().equals(name)) {
//				return attribute;
//			}
//		}
//		return null;
//	}
//	
//	private boolean contains(String[] l, String e) {
//		for (String c : l) {
//			if(c.equals(e)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
}
