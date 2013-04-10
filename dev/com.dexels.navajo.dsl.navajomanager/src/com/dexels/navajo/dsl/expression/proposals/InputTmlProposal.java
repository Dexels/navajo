package com.dexels.navajo.dsl.expression.proposals;

import com.dexels.navajo.document.Property;

public class InputTmlProposal {
	private Property property;
	private boolean isAbsolute;
	
//	completionProposal = createCompletionProposal("[/Club/ClubName]", "[Club name] /Club/ClubName: De Schoof", null, context);

	
	public boolean isAbsolute() {
		return isAbsolute;
	}

	public void setAbsolute(boolean isAbsolute) {
		this.isAbsolute = isAbsolute;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getProposal() {
		return "[" + (isAbsolute()?getProperty().getFullPropertyName():getProperty().getName())+"]";
	}

	public String getProposalDescription() {
		StringBuffer sb = new StringBuffer();
		if(getProperty().getDescription()!=null) {
			sb.append(getProposal()+" ");
		}
		sb.append(getProperty().getName());
		sb.append(" (");
		if (Property.BINARY_PROPERTY.equals(getProperty().getType())) {
			sb.append("binary");
		} else {
			String val = getProperty().getValue();
			if(val!=null) {
				sb.append(val);
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
