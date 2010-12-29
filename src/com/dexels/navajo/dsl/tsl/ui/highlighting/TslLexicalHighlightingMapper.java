package com.dexels.navajo.dsl.tsl.ui.highlighting;

import java.util.regex.Pattern;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.antlr.AbstractAntlrTokenToAttributeIdMapper;

public class TslLexicalHighlightingMapper extends AbstractAntlrTokenToAttributeIdMapper {

//	private static final Pattern QUOTED = Pattern.compile("(?:^'([^']*)'$)|(?:^\"([^\"]*)\")$", Pattern.MULTILINE);
	
//	private static final Pattern PUNCTUATION = Pattern.compile("\\p{Punct}*");
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		
		if("RULE_EXPRESSION_START".equals(tokenName)) {
			return TslHighlightingConfiguration.DEPRECATED_ID;
		}
		if("RULE_EXPRESSION_END".equals(tokenName)) {
			return TslHighlightingConfiguration.DEPRECATED_ID;
		}

		boolean encountered = valueForAnyInString(tokenName, new String[]{"RULE_ATTRIBUTESTRING","RULE_EMPTYSTRING","RULE_INT","RULE_LITERALSTRING"});
		if(encountered) {
			return TslHighlightingConfiguration.LITERAL_ID;
		}

		encountered = valueForAnyInString(tokenName, new String[]{"RULE_PROPERTY_END_TAG","RULE_MESSAGE_END_TAG","RULE_NAVASCRIPT_KEYWORD","'message'","'map'","'</map.'","'property'","RULE_MULTILINETAG_END","RULE_SINGLELINETAG_END","'AND'","'OR'","'FORALL'","RULE_TRUE","RULE_FALSE","RULE_NULL'","RULE_TODAY","'methods'","'method'","'required'","'validations'","'check'","'direction'","'name'","'value'","'type'","'length'","'option'","'field'","'include'","'break'"});
		if(encountered) {
			return TslHighlightingConfiguration.KEYWORD_ID;
		}

		
//		if(PUNCTUATION.matcher(tokenName).matches()) {
//			return DefaultHighlightingConfiguration.PUNCTUATION_ID;
//		}
//		if(QUOTED.matcher(tokenName).matches()) {
//			return DefaultHighlightingConfiguration.KEYWORD_ID;
//		}
		if("RULE_STRING".equals(tokenName)) {
			return DefaultHighlightingConfiguration.STRING_ID;
		}
		if("RULE_INT".equals(tokenName)) {
			return DefaultHighlightingConfiguration.NUMBER_ID;
		}
		encountered = valueForAnyInString(tokenName, new String[]{"RULE_XMLHead","RULE_XMLComment","RULE_CDATA","RULE_SL_COMMENT","RULE_ML_COMMENT"});
		if(encountered) { 
			System.err.println("COMMENT FOUND>>>>"+getId(tokenType)+" ttype: "+tokenType+" tokenname: "+tokenName);
			return TslHighlightingConfiguration.TSL_COMMENT_ID;
		}
		return DefaultHighlightingConfiguration.DEFAULT_ID;
	}

	private boolean valueForAnyInString(String tokenName, String[] tokenList) {
		for (String string : tokenList) {
			if(string.equals(tokenName)) {
				return true;
			}
		}
		return false;
	}
	
}