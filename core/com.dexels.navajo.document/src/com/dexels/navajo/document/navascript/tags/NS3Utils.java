package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.dexels.navajo.document.base.BaseNode;

public class NS3Utils {

	public static String generateIndent(int indent) {
		StringBuffer sb = new StringBuffer();
		for ( int i = 0; i < indent; i++ ) {
			sb.append(NS3Constants.INDENT_STEP);
		}
		return sb.toString();
	}

	public static boolean hasExpressionWithConstant(BaseNode n) {
				
		if ( n.getChildren().size() > 1 || n.getChildren().size() == 0) {
			return false;
		}
		if ( !(n.getChildren().get(0) instanceof ExpressionTag ) ) {
			return false;
		}
		ExpressionTag expression = (ExpressionTag) n.getChildren().get(0);		
		if ( expression.getValueTag() == null && expression.getConstant() != null ) {
			return true;
		}
		return false;
	}
	
	public static String removeParentAddressing(int levels, String s) {
	
		String result = s;
		for ( int i = 0; i < levels; i++ ) {
			result = result.replaceFirst("\\.\\.\\/", "");
		}
		return result;
	}
	
	public static String formatConditional(String condition) {
		
		StringBuffer sb = new StringBuffer();
		
		if ( condition != null && !"".equals(condition) ) {
			condition = condition.replaceAll("&gt;", ">");
			condition = condition.replaceAll("&lt;", "<");
			sb.append(NS3Constants.CONDITION_IF + condition + NS3Constants.CONDITION_THEN);
		} 
		
		return sb.toString();
		
	}
	
	public static String removeTrailingWhiteSpaces(int length, String s) {
		StringBuffer sb = new StringBuffer();
		
		String [] lines = s.split("\n");
		sb.append(lines[0] + "\n");
		for ( int j = 1; j < lines.length; j++ ) { // replace length space for all lines after the first
			String line = lines[j];
			for ( int i = 0; i < length; i++ ) { 
				line = line.replaceFirst(" ", "");
			}
			sb.append(line);
			if ( j < lines.length - 1 ) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public static String formatStringLiteral(int indent, String s) {
	
		s = s.trim();
		s = removeTrailingWhiteSpaces(generateIndent(indent+1).length(), s);
		s = s.replaceAll("\n", "\n" + generateIndent(indent+1));
		int newLineAt = s.length() - 1;
		if ( s.charAt(newLineAt) == '\n')  {
			s = s.substring(0, newLineAt);
			s = "\"" + s + "\"\n";
		} else {
			s = "\"" + s + "\"";
		}
		return s;
	}
	
	public static void writeConditionalExpressions(int indent, OutputStream w, List<? extends BaseNode> expressions) throws IOException {
		if ( expressions.size() == 1 ) {
			ExpressionTag et = (ExpressionTag) expressions.get(0);
			et.formatNS3(0, w);
		} else {
			w.write("\n".getBytes());
			int index = 0;
			for ( BaseNode e : expressions ) {
				ExpressionTag et = (ExpressionTag) e;
				index++;
				if ( index == expressions.size()) {
					w.write(NS3Utils.generateIndent(indent+1).getBytes());
					w.write((NS3Constants.CONDITION_ELSE + " ").getBytes());
					et.formatNS3(0, w);
				} else {
					et.formatNS3(indent+1, w);
					if ( index < expressions.size() ) {
						w.write("\n".getBytes());
					}
				}
			}
		}
	}

	public static void main(String [] args) throws Exception {

		String test = "   Aap\n  Noot\n            Mies\n     Vuur\n";
		
		System.err.println(NS3Utils.removeTrailingWhiteSpaces(4, test));
		
	}
}
