package com.dexels.navajo.dsl.expression.proposals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.dsl.model.expression.Expression;
import com.google.inject.Inject;

public class NavajoContextProvider implements INavajoContextProvider {
	protected List<FunctionProposal> functions = new ArrayList<FunctionProposal>();
	protected List<AdapterProposal> adapters = new ArrayList<AdapterProposal>();
	protected java.util.Map<String, AdapterProposal> adapterMap = new HashMap<String, AdapterProposal>();
	protected List<InputTmlProposal> tmlProposal = new ArrayList<InputTmlProposal>();
	private INavajoResourceFinder navajoResourceFinder= null;

	@Inject
	public NavajoContextProvider(INavajoResourceFinder navajoResourceFinder) throws IOException {
		initialize(navajoResourceFinder);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#getFunctions()
	 */
	@Override
	public List<FunctionProposal> getFunctions() {
		return functions;
	}


	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#getAdapters()
	 */
	@Override
	public List<AdapterProposal> getAdapters() {
		return adapters;
	}


	public List<InputTmlProposal> getTmlProposal() {
		return tmlProposal;
	}


	public void initialize(INavajoResourceFinder navajoResourceFinder)
			throws IOException {
		//InputStream is = this.getClass().getClassLoader().getResource("com/dexels/navajo/dsl/ui/functions.xml").openStream();
		setNavajoResourceFinder(navajoResourceFinder);
		initializeFunctions(navajoResourceFinder);
		initializeAdapters(navajoResourceFinder);
		initializeInput(navajoResourceFinder);
		System.err.println("Initialization complete. functions: "+functions.size()+" adapters: "+adapters.size()+" tml: "+tmlProposal.size());
	}


	private void initializeInput(INavajoResourceFinder navajoResourceFinder) {
		try {
			Navajo inputNavajo = navajoResourceFinder.getInputNavajo();
			if(inputNavajo==null) {
				return;
			}
			tmlProposal =  listPropertyPaths(inputNavajo);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeAdapters(INavajoResourceFinder navajoResourceFinder)
			throws IOException {
		XMLElement adaptersList = getNavajoResourceFinder().getAdapters();
		if(adaptersList==null) {
			return;
		}

		for (XMLElement x : adaptersList.getChildren()) {
			AdapterProposal fr = new AdapterProposal();
			fr.setTagName(x.getElementByTagName("tagname").getContent());
			XMLElement valueList = x.getElementByTagName("values");
			if(valueList!=null) {
				List<XMLElement> valueElements = valueList.getChildren();
				for (XMLElement value : valueElements) {
					AdapterValueEntry ave = new AdapterValueEntry();
					ave.load(value);
					fr.addValueEntry(ave);
				}
			}

			XMLElement methodList = x.getElementByTagName("methods");
			if(methodList!=null) {
				List<XMLElement> methodElements = methodList.getChildren();
				for (XMLElement method : methodElements) {
					AdapterMethodEntry ave = new AdapterMethodEntry();
					ave.load(method);
					fr.addMethodEntry(ave);
				}
			}

			
			
			adapters.add(fr);
			adapterMap.put(fr.getTagName(), fr);
		}
		Collections.sort(adapters);
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#getAdapterProposals()
	 */
	@Override
	public List<AdapterProposal> getAdapterProposals() {
		return adapters;
	}
	
	public AdapterProposal getAdapter(String name) {
		System.err.println("Available adapters: "+adapterMap.keySet());
		return adapterMap.get(name);
	}

	private void initializeFunctions(INavajoResourceFinder navajoResourceFinder)
			throws IOException {
		XMLElement functionList = getNavajoResourceFinder().getFunctions();
		if(functionList==null) {
			return;
		}
		for (XMLElement x : functionList.getChildren()) {
			String input = x.getElementByTagName("input").getContent();
			List<List<String>> alternatives = parseAlternatives(input);
			for (List<String> inputList : alternatives) {
				FunctionProposal fr = new FunctionProposal();
				fr.setName(x.getStringAttribute("name"));
				fr.setDescription(x.getElementByTagName("description")
						.getContent());
				fr.setInput(inputList);
				fr.setResult(x.getElementByTagName("result").getContent());
				functions.add(fr);
			}
		}
		Collections.sort(functions);
	}

	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#getNavajoResourceFinder()
	 */
	@Override
	public INavajoResourceFinder getNavajoResourceFinder() {
		return navajoResourceFinder;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#setNavajoResourceFinder(com.dexels.navajo.dsl.expression.proposals.INavajoResourceFinder)
	 */
	@Override
	public void setNavajoResourceFinder(INavajoResourceFinder navajoResourceFinder) {
		this.navajoResourceFinder = navajoResourceFinder;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.dsl.expression.proposals.INavajoContextProvider#listPropertyPaths(com.dexels.navajo.document.Navajo)
	 */
	@Override
	public List<InputTmlProposal> listPropertyPaths(Navajo in) {
		List<InputTmlProposal> result = new ArrayList<InputTmlProposal>();
		List<Message> m;
		try {
			m = in.getAllMessages();
			for (Message message : m) {
				listPropertyPaths(message,result);
			}
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	private void listPropertyPaths(Message m, List<InputTmlProposal> result) throws NavajoException {
		List<Property> pl = m.getAllProperties();
		for (Property property : pl) {
			InputTmlProposal fp = new InputTmlProposal();
			fp.setProperty(property);
			fp.setAbsolute(false);
			result.add(fp);
			InputTmlProposal fpabs = new InputTmlProposal();
			fpabs.setProperty(property);
			fpabs.setAbsolute(true);
			result.add(fpabs);
		}
		List<Message> ml = m.getAllMessages();
		for (Message message : ml) {
			if (message.getType().equals(Message.MSG_TYPE_ARRAY)) {
				if(message.getArraySize()>0) {
					listPropertyPaths(message.getMessage(0),result);
				}
			} else {
				listPropertyPaths(message,result);
			}
		}

		
	}

	private List<List<String>> parseAlternatives(String input) {
		List<String> base = new ArrayList<String>();
		List<List<String>> result = new ArrayList<List<String>>();
		String[] params = input.split(",");
		for (String string : params) {
			base.add(string);
		}
		result.add(base);
		while (true) {
			boolean found = false;
			for (List<String> alternative : result) {
				int index = 0;
				for (String element : alternative) {
					if (element.indexOf("|") != -1) {
						found = true;
						break;
					}
					index++;
				}
				if (found) {

					String currentExample = alternative.get(index);
					String[] alts = currentExample.split("\\|");
					for (String currentAlt : alts) {
						List<String> newAlt = new ArrayList<String>(alternative);
						newAlt.set(index , currentAlt);
						result.add(newAlt);
					}
					result.remove(alternative);
					break;
				}

			}
			if (!found) {
				break;
			}
		}
		for (List<String> propose : result) {
			stripTrailingEmpties(propose);
		}
		List<List<String>> invalid = new ArrayList<List<String>>();
		for (List<String> propose : result) {
			if (!isLegal(propose)) {
				invalid.add(propose);
			}
		}
		result.removeAll(invalid);
		return result;
	}

	private void stripTrailingEmpties(List<String> propose) {
		for (int i = propose.size()-1; i>=0; i--) {
			if(propose.get(i).equals("empty")) {
				propose.remove(i);
			}
		}
		
	}

//	private List<FunctionProposal> findFunctionByName(String name) {
//		List<FunctionProposal> result = new ArrayList<FunctionProposal>();
//		for (FunctionProposal f :functions) {
//			if(f.getName().equals(name)) {
//				result.add(f);
//			}
//		}
//		return result;
//	}
	
	private boolean isLegal(List<String> propose) {
		int index=0;
		for (String r : propose) {
			if(r.equals("empty") && index!=propose.size()-1) {
				return false;
			}
			index++;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
//		System.setProperty("testmode", "true");
//		NavajoContextProvider npp = new NavajoContextProvider();
//		System.err.println("Parse test of: string,integer,string|empty,boolean|empty");
//		System.err.println("result: " + npp.parseAlternatives("string,integer,string|empty,boolean|empty"));
//		System.err.println("Function count: " + npp.functions.size());
//		for (FunctionProposal f : npp.functions) {
//			System.err.println("Function: " + f.getProposalDescription());
//		}
//
//		Navajo input = npp.getNavajoResourceFinder().getInputNavajo();
//		List<InputTmlProposal> l =  npp.listPropertyPaths(input);
//		for (InputTmlProposal tmlProposal : l) {
//			System.err.println("Proposal: "+tmlProposal.getProposal()+" ---- "+tmlProposal.getProposalDescription());
//		}
//		for (AdapterProposal f : npp.adapters) {
//			System.err.println("Adapter: " + f.getTagName());
//		}
//		List<String> stack = new ArrayList<String>();
//		stack.add("ftp");
//		stack.add("sqlquery");
//		
//		List<String> proposals = new ArrayList<String>();
//		StringBuffer prefixBuffer = new StringBuffer();
//		npp.processGetters(stack, proposals, prefixBuffer);		
//		for (String string : proposals) {
//			System.err.println("Proposal: "+string);
//		}
	}
	
	
	public void processGetters(List<String> mapStack,List<String> proposals, StringBuffer prefix ) {
		if(mapStack.isEmpty()) {
			return;
		}
		String mapName = mapStack.get(0);
		AdapterProposal ap = adapterMap.get(mapName);
		if(ap==null) {
			System.err.println("Warning: map: "+mapName+" not found!");
		}
		List<String> apGetters = ap.getGetters();
		for (String getter : apGetters) {
			proposals.add("$"+prefix.toString()+getter);
		}
		mapStack.remove(0);
		prefix.append("../");
		processGetters(mapStack, proposals, prefix);
	}
	
	public void debugExpression(Expression e) {
		if(e==null) {
			return;
		}
		Expression c = e;
		while(c.getParent()!=null) {
			System.err.println("Parent:: "+c.getParent().getClass()+" getParams: "+c.getParameters()+" >> "+c.getElements());
			c = c.getParent();
		}
		System.err.println("== finished debugexpression ==");
	}


}
