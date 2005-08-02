/*
 * Created on Aug 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.*;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.*;

import com.dexels.navajo.studio.script.plugin.*;

public class ScriptContentAssist implements IContentAssistProcessor {

    private ContextInformationValidator contextInfoValidator;

    private String error = "No error";

    public ScriptContentAssist() {
        contextInfoValidator = new ContextInformationValidator(this);
    }

    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
        // TODO Auto-generated method stub
        IDocument document = viewer.getDocument();
        int currOffset = offset - 1;
        try {
            String contents = document.get(0, document.getLength());
              ArrayList al = NavajoScriptPluginPlugin.getDefault().getScriptsStartingWith(contents);
            ICompletionProposal[] proposals = new ICompletionProposal[al.size()];
            for (int i = 0; i < al.size(); i++) {
                String current = (String) al.get(i);
                proposals[i] = new CompletionProposal(current, 0, contents.length(), current.length());
            }
            return proposals;
        } catch (BadLocationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
        // TODO Auto-generated method stub
        return null;
    }

    public char[] getCompletionProposalAutoActivationCharacters() {
        return null;
    }

    public char[] getContextInformationAutoActivationCharacters() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getErrorMessage() {

        return error;
    }

    public IContextInformationValidator getContextInformationValidator() {
        return contextInfoValidator;
    }

}
