
package com.dexels.navajo.dsl.tsl.ui.quickfix;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.dexels.navajo.dsl.model.expression.Expression;
import com.dexels.navajo.dsl.model.expression.ExpressionFactory;
import com.dexels.navajo.dsl.model.expression.TopLevel;
import com.dexels.navajo.dsl.model.tsl.Element;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.TslFactory;
import com.dexels.navajo.dsl.model.tsl.util.TslAdapterFactory;
import com.dexels.navajo.dsl.tsl.ui.internal.TslActivator;
import com.dexels.navajo.dsl.tsl.validation.TslJavaValidator;

public class TslQuickfixProvider extends DefaultQuickfixProvider {

//	@Fix(MyJavaValidator.INVALID_NAME)
//	public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
//		acceptor.accept(issue, "Capitalize name", "Capitalize the name.", "upcase.png", new IModification() {
//			public void apply(IModificationContext context) throws BadLocationException {
//				IXtextDocument xtextDocument = context.getXtextDocument();
//				String firstLetter = xtextDocument.get(issue.getOffset(), 1);
//				xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
//			}
//		});
//	}

	@Fix(TslJavaValidator.ISSUE_ILLEGAL_ATTRIBUTE)
	public void removeIllegalAttribute(final Issue issue,IssueResolutionAcceptor acceptor) {
		
		acceptor.accept(issue, "Remove attribute", "Remove attribute", null , new IModification() {
		public void apply(IModificationContext context) throws BadLocationException {
			IXtextDocument xtextDocument = context.getXtextDocument();
			xtextDocument.replace(issue.getOffset(), issue.getLength(),"");
		}
	});
	}

	@Fix(TslJavaValidator.ISSUE_SHOULD_BE_EXPRESSION)
	public void convertToExpression(final Issue issue,IssueResolutionAcceptor acceptor) {
		
		acceptor.accept(issue, "Convert to expression", "Convert to expression (assume string)", null , new IModification() {
		public void apply(IModificationContext context) throws BadLocationException {
			IXtextDocument xtextDocument = context.getXtextDocument();
			String issuetext = xtextDocument.get(issue.getOffset(),issue.getLength());
			int start = issuetext.indexOf("\"");
			int end = issuetext.lastIndexOf("\"");
			xtextDocument.replace(issue.getOffset()+end,0,"';");
			xtextDocument.replace(issue.getOffset()+start+1, 0,"='");
		}
	});
	}

	@Fix(TslJavaValidator.ISSUE_SHOULD_BE_EXPRESSION)
	public void convertToExpressionFromLegacy(final Issue issue,IssueResolutionAcceptor acceptor) {
		
		acceptor.accept(issue, "Convert legacy expression", "Convert legacy expression (add '=' and ';')", null , new IModification() {
		public void apply(IModificationContext context) throws BadLocationException {
			IXtextDocument xtextDocument = context.getXtextDocument();
			String issuetext = xtextDocument.get(issue.getOffset(),issue.getLength());
			int start = issuetext.indexOf("\"");
			int end = issuetext.lastIndexOf("\"");
			xtextDocument.replace(issue.getOffset()+end,0,";");
			xtextDocument.replace(issue.getOffset()+start+1, 0,"=");
		}
	});
	}

//	@Fix(TslJavaValidator.ISSUE_MISSING_ATTRIBUTE)
//	public void insertMissingAttribute(final Issue issue,IssueResolutionAcceptor acceptor) {
//		acceptor.accept(issue, "Insert: "+issue.getData()[0]+" with \""+issue.getData()[1]+"\"", "Insert missing attribute", null , new ISemanticModification() {
//			
//			public void apply(EObject element, IModificationContext context)
//					throws Exception {
//				System.err.println("APPPPPLyING!!!!!!! "+issue.getData()[0]+" ::: "+issue.getData()[1]);
//				Element e = (Element)element;
//				PossibleExpression pe = TslFactory.eINSTANCE.createPossibleExpression();
//				pe.setKey(issue.getData()[0]);
//				pe.setValue("\""+issue.getData()[1]+"\"");
//				
//				e.getAttributes().add(pe);
//				
//			}
//		});
//	}	
	

	@Fix(TslJavaValidator.ISSUE_MISSING_ATTRIBUTE)
	public void insertMissingAttribute(final Issue issue,IssueResolutionAcceptor acceptor) {
		String value = TslJavaValidator.getDefaultValueForAttribute(issue.getData()[0], issue.getData()[1]);
		if(value==null) {
			value = "unknown";
		}
		acceptor.accept(issue, "Insert: "+issue.getData()[1]+" with \""+value+"\" to: "+issue.getData()[0], "Insert missing attribute", null , addAttributeToElement(issue, issue.getData()[1],value));
	}	

	@Fix(TslJavaValidator.ISSUE_SHOULD_NOT_BE_EXPRESSION)
	public void convertFromExpression(final Issue issue,IssueResolutionAcceptor acceptor) {
		
		acceptor.accept(issue, "Convert to string literal", "Convert to literal (assume string)", null , new IModification() {
		public void apply(IModificationContext context) throws BadLocationException {
			IXtextDocument xtextDocument = context.getXtextDocument();
			String issuetext = xtextDocument.get(issue.getOffset(),issue.getLength());
			int start = issuetext.indexOf("=\"");
			int end = issuetext.lastIndexOf(";\"");
			xtextDocument.replace(issue.getOffset()+end-1,2,"");
			xtextDocument.replace(issue.getOffset()+start+2, 2,"");
		}
	});
	}	
	
	protected IModification addAttributeToElement(final Issue issue, final String key, final String value) {
		IModification ii = new IModification() {
			public void apply(IModificationContext context) throws BadLocationException {
				IXtextDocument xtextDocument = context.getXtextDocument();
				String issuetext = xtextDocument.get(issue.getOffset(),issue.getLength());
				// find end of the first tag 
				int index = issuetext.indexOf(">");
				//int index = issuetext.lastIndexOf("/>");
				if(index == -1) {
					return;
				}
				if(issuetext.charAt(index-1)=='/') {
					index--;
				}

				String localKey = key;
				String atInsertionPoint = xtextDocument.get(issue.getOffset()+index-1, 1);
				System.err.println("AT: "+atInsertionPoint);
				// ensure leading whitespace
				if(!Character.isWhitespace(atInsertionPoint.charAt(0))) {
					localKey = " "+localKey;
				}
				xtextDocument.replace(issue.getOffset()+index, 0, localKey+"=\""+value+"\"");
//				int start = issuetext.indexOf("=\"");
//				int end = issuetext.lastIndexOf(";\"");
//				xtextDocument.replace(issue.getOffset()+end-1,2,"");
//				xtextDocument.replace(issue.getOffset()+start+2, 2,"");
			}
		};
		return ii;
	}
	
}
