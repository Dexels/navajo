package com.dexels.navajo.dsl.expression.proposals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.dexels.navajo.document.Navajo;

@com.google.inject.Singleton
public class NavajoGlobalContextProvider implements INavajoContextProvider {

	private final Map<IProject,INavajoProjectContextProvider> navajoResourceFinderMap = new HashMap<IProject,INavajoProjectContextProvider>();

	private INavajoProjectContextProvider getProjectProvider(IProject p)  {
		INavajoProjectContextProvider res = navajoResourceFinderMap.get(p);
		if(res==null) {
			NavajoResourceFinder navajoResourceFinder = new NavajoResourceFinder();
			navajoResourceFinder.setCurrentProject(p);
			res = new NavajoContextProvider(navajoResourceFinder);
			navajoResourceFinderMap.put(p, res);
		}
		return res;
	}
	
	@Override
	public List<FunctionProposal> getFunctions(IProject project) {
		return getProjectProvider(project).getFunctions();
	
	}

	@Override
	public List<AdapterProposal> getAdapters(IProject project) {
		return getProjectProvider(project).getAdapters();
	}

	@Override
	public List<AdapterProposal> getAdapterProposals(IProject project) {
		return getProjectProvider(project).getAdapterProposals();
	}

	@Override
	public AdapterProposal getAdapter(IProject project, String mapName) {
		return getProjectProvider(project).getAdapter(mapName);
	}

//	@Override
//	public List<InputTmlProposal> listPropertyPaths(Navajo in) {
//		return getProjectProvider(project).getAdapter(mapName);
//		return null;
//	}

	@Override
	public List<InputTmlProposal> getTmlProposal(IProject project) {
		return getProjectProvider(project).getTmlProposal();
	}



	@Override
	public void processGetters(List<String> mapStack, List<String> proposals,
			StringBuffer stringBuffer) {

	}

	@Override
	public void setInputNavajo(IProject project, Navajo n) {
		 getProjectProvider(project).setInputNavajo(n);

	}

}
