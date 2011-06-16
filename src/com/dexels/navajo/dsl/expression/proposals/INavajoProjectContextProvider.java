package com.dexels.navajo.dsl.expression.proposals;

import java.util.List;

import com.dexels.navajo.document.Navajo;

public interface INavajoProjectContextProvider {

	public abstract List<FunctionProposal> getFunctions();

	public abstract List<AdapterProposal> getAdapters();

	public abstract List<AdapterProposal> getAdapterProposals();
	public abstract AdapterProposal getAdapter(String mapName);

	public abstract List<InputTmlProposal> getTmlProposal();
	public void setNavajoResourceFinder(INavajoResourceFinder navajoResourceFinder);

	public INavajoResourceFinder getNavajoResourceFinder();

	public abstract void setInputNavajo(Navajo n);

}
