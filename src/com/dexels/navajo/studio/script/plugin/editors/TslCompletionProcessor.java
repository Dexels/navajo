/*
 * Created on Jun 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.script.plugin.editors;

import java.util.*;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TslCompletionProcessor implements IContentAssistProcessor {

    private final IContextInformation[] NO_CONTEXTS = 
        new IContextInformation[0];
     private final char[] PROPOSAL_ACTIVATION_CHARS = 
        new char[] { 's','f','p','n','m', };
     private ICompletionProposal[] NO_COMPLETIONS = 
        new ICompletionProposal[0];

     public ICompletionProposal[] computeCompletionProposals(
        ITextViewer viewer, int offset) {
        try {
           IDocument document = viewer.getDocument();
           ArrayList result = new ArrayList();
           String prefix = lastWord(document, offset);
           String indent = lastIndent(document, offset);
//           EscriptModel model = 
//                       EscriptModel.getModel(document, null);
//           model.getContentProposals(prefix, indent, 
//                                            offset, result);
           return (ICompletionProposal[]) result.toArray(
              new ICompletionProposal[result.size()]);
        } catch (Exception e) {
           // ... log the exception ...
           return NO_COMPLETIONS;
        }
     }
     private String lastWord(IDocument doc, int offset) {
        try {
           for (int n = offset-1; n >= 0; n--) {
             char c = doc.getChar(n);
             if (!Character.isJavaIdentifierPart(c))
               return doc.get(n + 1, offset-n-1);
           }
        } catch (BadLocationException e) {
           // ... log the exception ...
        }
        return "";
     }
     private String lastIndent(IDocument doc, int offset) {
        try {
           int start = offset-1; 
           while (start >= 0 && 
              doc.getChar(start)!= '\n') start--;
           int end = start;
           while (end < offset && 
              Character.isSpaceChar(doc.getChar(end))) end++;
           return doc.get(start+1, end-start-1);
        } catch (BadLocationException e) {
           e.printStackTrace();
        }
        return "";
     }
     public IContextInformation[] computeContextInformation(
        ITextViewer viewer, int offset) { 
        return NO_CONTEXTS;
     }
     public char[] getCompletionProposalAutoActivationCharacters() {
        return PROPOSAL_ACTIVATION_CHARS;
     }
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
     */
    public char[] getContextInformationAutoActivationCharacters() {
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
     */
    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
     */
    public IContextInformationValidator getContextInformationValidator() {
        // TODO Auto-generated method stub
        return null;
    }

}
