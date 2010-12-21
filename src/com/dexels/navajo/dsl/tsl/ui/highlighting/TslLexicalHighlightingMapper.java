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

		encountered = valueForAnyInString(tokenName, new String[]{"RULE_PROPERTY_END_TAG","RULE_MESSAGE_END_TAG","ROLE_NAVASCRIPT_KEYWORD","'message'","'map'","'</map.'","'property'","RULE_MULTILINETAG_END","RULE_SINGLELINETAG_END","'AND'","'OR'","'FORALL'","'TRUE'","'FALSE'","'NULL'","'TODAY'","'methods'","'method'","'required'","'validations'","'check'","'direction'","'name'","'value'","'type'","'length'"});
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
		if("RULE_ML_COMMENT".equals(tokenName) | "RULE_SL_COMMENT".equals(tokenName) | "CDATA".equals(tokenName)) {
			return DefaultHighlightingConfiguration.COMMENT_ID;
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