package com.dexels.navajo.dsl.expression.proposals;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.dexels.navajo.document.Navajo;
import com.google.inject.ImplementedBy;

@ImplementedBy(NavajoGlobalContextProvider.class)
public interface INavajoContextProvider {

	public abstract List<FunctionProposal> getFunctions(IProject project);

	public abstract List<AdapterProposal> getAdapters(IProject project);

	public abstract List<AdapterProposal> getAdapterProposals(IProject project);
	public abstract AdapterProposal getAdapter(IProject project, String mapName);

//	public abstract List<InputTmlProposal> listPropertyPaths(IProject project, Navajo in);


	public abstract List<InputTmlProposal> getTmlProposal(IProject project);


	public abstract void processGetters(List<String> mapStack,
			List<String> proposals, StringBuffer stringBuffer);


	// ----
	
	public void setInputNavajo(IProject project,Navajo n);

	
}