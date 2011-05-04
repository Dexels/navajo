package com.dexels.navajo.dsl.expression.proposals;

import java.io.IOException;
import java.util.List;


import com.dexels.navajo.document.Navajo;
import com.google.inject.ImplementedBy;

@ImplementedBy(NavajoContextProvider.class)
public interface INavajoContextProvider {

	public abstract List<FunctionProposal> getFunctions();

	public abstract List<AdapterProposal> getAdapters();

	public abstract List<AdapterProposal> getAdapterProposals();

	public abstract INavajoResourceFinder getNavajoResourceFinder();

	public abstract void setNavajoResourceFinder(
			INavajoResourceFinder navajoResourceFinder);

	public abstract List<InputTmlProposal> listPropertyPaths(Navajo in);

	public abstract void initialize(INavajoResourceFinder resourceFinder) throws IOException;

	public abstract List<InputTmlProposal> getTmlProposal();

	public abstract void processGetters(List<String> mapStack,
			List<String> proposals, StringBuffer stringBuffer);

	public abstract AdapterProposal getAdapter(String mapName);

}