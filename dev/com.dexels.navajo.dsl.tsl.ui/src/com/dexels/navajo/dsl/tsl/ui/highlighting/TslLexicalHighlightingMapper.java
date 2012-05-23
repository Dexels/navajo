package com.dexels.navajo.dsl.tsl.ui.highlighting;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;

public class TslLexicalHighlightingMapper extends AbstractAntlrTokenToAttributeIdMapper {

//	private static final Pattern QUOTED = Pattern.compile("(?:^'([^']*)'$)|(?:^\"([^\"]*)\")$", Pattern.MULTILINE);
	
//	private static final Pattern PUNCTUATION = Pattern.compile("\\p{Punct}*");
	
	@Override
	protected String calculateId(String tokenName, int tokenType) {
//		if(true) {
//			return TslHighlightingConfiguration.KEYWORD_ID;
//		}
//		if("RULE_EXPRESSION_START".equals(tokenName)) {
//			return TslHighlightingConfiguration.DEPRECATED_ID;
//		}
//		if("RULE_EXPRESSION_END".equals(tokenName)) {
//			return TslHighlightingConfiguration.DEPRECATED_ID;
//		}

		boolean encountered = valueForAnyInString(tokenName, new String[]{"RULE_ATTRIBUTESTRING","RULE_EMPTYSTRING","RULE_INT","RULE_LITERALSTRING"});
		if(encountered) {
			return TslHighlightingConfiguration.LITERAL_ID;
		}
		encountered = valueForAnyInString(tokenName, new String[]{"RULE_XMLHEAD","RULE_XMLCOMMENT","RULE_CDATA","RULE_SL_COMMENT","RULE_ML_COMMENT"});
		if(encountered) { 
//			System.err.println("COMMENT FOUND>>>>"+getId(tokenType)+" ttype: "+tokenType+" tokenname: "+tokenName);
			return TslHighlightingConfiguration.TSL_COMMENT_ID;
		}

		encountered = valueForAnyInString(tokenName, new String[]{"RULE_EXPRESSION_START_TAG","RULE_EXPRESSION_END_TAG","RULE_NAVASCRIPT_START","RULE_NAVASCRIPT_END","RULE_DEBUG_START_TAG","RULE_DEBUG_END_TAG","RULE_PROPERTY_END_TAG","RULE_MESSAGE_END_TAG","RULE_NAVASCRIPT_KEYWORD","RULE_MESSAGE_START_TAG","RULE_MESSAGE_END_TAG","RULE_PROPERTY_START_TAG","RULE_PROPERTY_END_TAG","RULE_MULTILINETAG_END","RULE_SINGLELINETAG_END","'AND'","'OR'","'FORALL'","RULE_TRUE","RULE_FALSE","RULE_NULL'","RULE_TODAY","RULE_METHODS_START_TAG","RULE_METHODS_END_TAG","RULE_METHOD_START_TAG","RULE_METHOD_END_TAG","RULE_REQUIRED_START_TAG","RULE_REQUIRED_END_TAG","RULE_VALIDATIONS_START_TAG","RULE_VALIDATIONS_END_TAG","RULE_CHECK_START_TAG","RULE_CHECK_END_TAG","'direction'","'name'","'value'","'type'","'length'","RULE_OPTION_START_TAG","RULE_OPTION_END_TAG","RULE_FIELD_START_TAG","RULE_FIELD_END_TAG","RULE_INCLUDE_START_TAG","RULE_INCLUDE_END_TAG","RULE_BREAK_START_TAG","RULE_BREAK_END_TAG","RULE_PARAM_START_TAG","RULE_PARAM_END_TAG"});
		if(encountered) {
			return TslHighlightingConfiguration.KEYWORD_ID;
		}

		encountered = valueForAnyInString(tokenName, new String[]{"RULE_QUOTEQ","RULE_SEMICOLONQUOTE"});
		if(encountered) {
			return TslHighlightingConfiguration.EXPR_DELIM_ID;
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