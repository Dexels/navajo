package com.dexels.navajo.dsl.tsl.ui.highlighting;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class TslHighlightingConfiguration implements IHighlightingConfiguration {

	public static final String KEYWORD_ID = "keyword";
	public static final String MAP_ID = "map";
	public static final String FUNCTION_CALL_ID = "functioncall";
	public static final String EXPR_DELIM_ID = "exprdelim";
	public static final String TML_INPUT = "tmlinput";
	public static final String DEPRECATED_ID = "deprecated";
	public static final String LITERAL_ID = "literal";
	public static final String TSL_COMMENT_ID = "tslcomment";

	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(MAP_ID, "Map", mapIdTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTION_CALL_ID, "Function Call",
				functionCallTextStyle());
		acceptor.acceptDefaultHighlighting(EXPR_DELIM_ID,
				"Expression delimiter", expressionDelimStyle());
		acceptor.acceptDefaultHighlighting(TML_INPUT, "TML Input",
				tmlInputStyle());
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword",
				keywordTextStyle());
		acceptor.acceptDefaultHighlighting(DEPRECATED_ID, "Deprecated",
				deprecatedStyle());
		acceptor.acceptDefaultHighlighting(LITERAL_ID, "Literal",
				literalStyle());
		acceptor.acceptDefaultHighlighting(TSL_COMMENT_ID, "Comment",
				commentTextStyle());
	}

	public TextStyle mapIdTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(255, 0, 0));
		// textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle functionCallTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(150, 0, 0));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle expressionDelimStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 0, 0));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle deprecatedStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	public TextStyle literalStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 0, 200));
		return textStyle;
	}

	public TextStyle tmlInputStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(0, 150, 0));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle keywordTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setColor(new RGB(127, 0, 85));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle commentTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(120, 180, 120));
		textStyle.setStyle(SWT.ITALIC);

		return textStyle;
	}

	public TextStyle defaultTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setBackgroundColor(new RGB(255, 255, 255));
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}
}
