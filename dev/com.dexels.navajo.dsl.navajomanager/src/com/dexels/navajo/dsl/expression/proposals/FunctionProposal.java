package com.dexels.navajo.dsl.expression.proposals;

import java.util.List;


public class FunctionProposal implements Comparable<FunctionProposal> {
	
	private String name;
	private String description;
	private List<String> input;
	private String result;

	public final static String WHATEVER = "...";
	
	public FunctionProposal() {
	}

	public String getProposal(boolean instantiate) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append("(");
		sb.append(getOperandProposal(instantiate));
		sb.append(")");
		return sb.toString();
	}

	public String getOperandProposal(boolean instantiate) {
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (String e : input) {
			if(count!=0) {
				sb.append(", ");
			}
			count++;
			if (instantiate) {
				sb.append(instantiateDataType(e));
			} else {
				sb.append(e);
			}
		}
		return sb.toString();
	}

	private String instantiateDataType(String e) {
		if(e.equals("string")) {
			return "''";
		}
		if(e.equals("integer")) {
			return "0";
		}
		if(e.equals("float")) {
			return "0.0";
		}
		if(e.equals("boolean")) {
			return "FALSE";
		}
		
		if(e.equals("list")) {
			return "{}";
		}
		if(e.equals("date")) {
			return "TODAY";
		}
		
		return "NULL";
	}

	public String getProposalDescription() {
		StringBuffer b = new StringBuffer();
		if(result!=null && !result.isEmpty()) {
			b.append("("+result+") ");
		}
		b.append(getProposal(false));
		if(description!=null) {
			b.append( " - "+description);
		}
		return b.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getInput() {
		return input;
	}

	public void setInput(List<String> input) {
		this.input = input;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	@Override
	public int compareTo(FunctionProposal o) {
		return name.compareTo(o.name);
	}
	
}
