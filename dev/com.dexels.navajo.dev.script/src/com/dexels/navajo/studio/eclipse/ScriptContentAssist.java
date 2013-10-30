/*
 * Created on Aug 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.studio.eclipse;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformationValidator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.studio.script.plugin.NavajoScriptPluginPlugin;

public class ScriptContentAssist implements IContentAssistProcessor {

    private ContextInformationValidator contextInfoValidator;
    
	private final static Logger logger = LoggerFactory
			.getLogger(ScriptContentAssist.class);
	
    private String error = "No error";

    public ScriptContentAssist() {
        contextInfoValidator = new ContextInformationValidator(this);
    }

    @Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
        IDocument document = viewer.getDocument();
//        int currOffset = offset - 1;
        try {
            String contents = document.get(0, document.getLength());
              List<String> al = NavajoScriptPluginPlugin.getDefault().getScriptsStartingWith(contents);
            ICompletionProposal[] proposals = new ICompletionProposal[al.size()];
            for (int i = 0; i < al.size(); i++) {
                String current = al.get(i);
                proposals[i] = new CompletionProposal(current, 0, contents.length(), current.length());
            }
            return proposals;
        } catch (BadLocationException e) {
        	logger.error("Error: ", e);
        	return null;
        }
    }

    @Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
        return null;
    }

    @Override
	public char[] getCompletionProposalAutoActivationCharacters() {
        return null;
    }

    @Override
	public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    @Override
	public String getErrorMessage() {

        return error;
    }

    @Override
	public IContextInformationValidator getContextInformationValidator() {
        return contextInfoValidator;
    }

}
