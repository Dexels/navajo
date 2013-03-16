package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TmlToPainMap implements Mappable {
	public XMLMap content = new XMLMap();
	Access access = null;
	public String rootPath = null;
	public String separator = null;
	Navajo document = null;
	PainStructureElement rootElement = null;

	final String targetNamespace = "urn:iso:std:iso:20022:tech:xsd:pain.008.001.02";
	final String targetSchemaInstance = "http://www.w3.org/2001/XMLSchema-instance";
	final String TYPE_PROPERTY = "Property";
	final String TYPE_MESSAGE = "Message";
	final String TYPE_ARRAYMESSAGE = "ArrayMessage";

	private final static Logger logger = LoggerFactory
			.getLogger(TmlToPainMap.class);

	private boolean hasBeenBuild = false;

	ArrayList<String[]> attributes = new ArrayList<String[]>();
	public String attributePath = "";
	public String attributeName = "";
	public String attributeValue = "";

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		this.access = access;
		generatePainStructure();
	}

	public void store() throws MappableException, UserException {
	}

	public void setBuildContent(boolean b) throws UserException {
		try {
			if (b) {
				if (document == null) {
					document = access.getOutputDoc();
				}
				content.setCompact(true);
				Message root = document.getMessage(rootPath);

				if (root == null) {
					logger.error("ERROR! Could not find message: " + rootPath);
					throw new UserException(1200,
							"Root message not found for TML2PAIN map");
				}
				content.setStart(root.getName());
				// Add the target namespace
				if (targetNamespace != null) {
					content.setAttributeName("xmlns");
					content.setAttributeText(targetNamespace);
				}
				// Add the target schema instance
				if (targetSchemaInstance != null) {
					content.setAttributeName("xmlns:xsi");
					content.setAttributeText(targetSchemaInstance);
				}
				loopStructureChildren(root, content, rootElement);

				hasBeenBuild = true;
			}
		} catch (Exception e) {
			throw new UserException(392, e.getMessage(),e);
		}
	}

	private void loopStructureChildren(Message m, TagMap child, PainStructureElement structureElement) throws UserException {
		for (PainStructureElement childStructureElement : structureElement
				.getChildren()) {
			if (TYPE_PROPERTY.equals(childStructureElement.getType())) {
				Property prop = m.getProperty(childStructureElement
						.getName());
				if (prop == null && childStructureElement.getRequired()) {
					throw new UserException(9999, "Required property "
							+ childStructureElement.getName()
							+ " not found in message " + m);
				}
				if (prop != null) {
					appendProperty(prop, child, childStructureElement);
				}
			} else if (TYPE_MESSAGE.equals(childStructureElement.getType())) {
				Message mess = m
						.getMessage(childStructureElement.getName());
				if (mess == null && childStructureElement.getRequired()) {
					throw new UserException(9999, "Required message "
							+ childStructureElement.getName()
							+ " not found in message " + m);
				}
				if (mess != null) {
					appendMessage(mess, child, childStructureElement);
				}
			} else if (TYPE_ARRAYMESSAGE.equals(childStructureElement
					.getType())) {
				Message mess = m
						.getMessage(childStructureElement.getName());
				if ((mess == null || mess.getArraySize() == 0)
						&& childStructureElement.getRequired()) {
					throw new UserException(9999, "Required message "
							+ childStructureElement.getName()
							+ " not found in message " + m);
				}
				logger.debug("Processing array, calling appendMessage for childStructureElement with name "
						+ childStructureElement.getName()
						+ " and message "
						+ mess);
				for (int i = 0; i < mess.getArraySize(); i++) {
					logger.debug("Grabbing message for this spot in the array: "
							+ mess.getMessage(i));
					appendMessage(mess.getMessage(i), child,
							childStructureElement);
				}
			}
		}
	}

	private final void appendMessage(Message m, TagMap parent,
			PainStructureElement structureElement) throws UserException {
		if (!m.getMode().equals(Message.MSG_MODE_IGNORE)) {
			// Check for array messages. Only append array children
			TagMap child;
			if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
				child = new TagMap();
				child.setCompact(true);
				child.setName(m.getName());
				parent.setChild(child);
			} else {
				child = parent;
			}

			loopStructureChildren(m, child, structureElement);
		}
	}

	private final void appendProperty(Property p, TagMap parent,
			PainStructureElement structureElement) throws UserException {
		String propertyValueUnsplit = p.getValue();
		String[] propertyValueSplit;
		if (separator == null) {
			propertyValueSplit = new String[]{propertyValueUnsplit};
		} else {
			propertyValueSplit = propertyValueUnsplit.split(separator);
		}
		for (String propertyValue : propertyValueSplit) {
			TagMap property = new TagMap();
			property.setCompact(true);
			property.setName(p.getName());
			property.setText(propertyValue);
			if (structureElement.getAttributeName() != null) {
				property.setAttributeName(structureElement.getAttributeName());
				property.setAttributeText(structureElement.getAttributeValue());
			}
			parent.setChild(property);
		}
	}

	public Binary getContent() throws UserException {
		if (!hasBeenBuild) {
			setBuildContent(true);
		}
		return content.getContent();
	}

	private void generatePainStructure() throws UserException {
		List<PainStructureElement> tmp = new ArrayList<PainStructureElement>();
		List<PainStructureElement> tmp2 = new ArrayList<PainStructureElement>();
		List<PainStructureElement> tmp3 = new ArrayList<PainStructureElement>();
		List<PainStructureElement> tmp4 = new ArrayList<PainStructureElement>();
		List<PainStructureElement> tmp5 = new ArrayList<PainStructureElement>();
		List<PainStructureElement> tmp6 = new ArrayList<PainStructureElement>();

		// Message Document, using tmp
		// Message CstmrDrctDbtInitn, using tmp3
		// Message GrpHdr, using tmp2
		tmp2.add(new PainStructureElement("MsgId", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("CreDtTm", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("NbOfTxs", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("CtrlSum", TYPE_PROPERTY, false));

		// Message InitgPty, using tmp
		tmp.add(new PainStructureElement("Nm", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("InitgPty", tmp, TYPE_MESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message InitgPty

		tmp3.add(new PainStructureElement("GrpHdr", tmp2, TYPE_MESSAGE));
		tmp2 = new ArrayList<PainStructureElement>();
		// End message GrpHdr

		// Message PmtInf, using tmp2
		tmp2.add(new PainStructureElement("PmtInfId", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("PmtMtd", TYPE_PROPERTY));
		tmp2.add(new PainStructureElement("NbOfTxs", TYPE_PROPERTY, false));
		tmp2.add(new PainStructureElement("CtrlSum", TYPE_PROPERTY, false));

		// Message PmtTpInf, using tmp
		// Message SvcLvl, using tmp4
		tmp4.add(new PainStructureElement("Cd", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("SvcLvl", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message SvcLvl

		// Message LclInstrm, using tmp4
		tmp4.add(new PainStructureElement("Cd", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("LclInstrm", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message LclInstrm

		tmp.add(new PainStructureElement("SeqTp", TYPE_PROPERTY));

		tmp2.add(new PainStructureElement("PmtTpInf", tmp, TYPE_MESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message PmtTpInf

		tmp2.add(new PainStructureElement("ReqdColltnDt", TYPE_PROPERTY));

		// Message Cdtr, using tmp
		tmp.add(new PainStructureElement("Nm", TYPE_PROPERTY));

		// Message PstlAdr, using tmp4
		tmp4.add(new PainStructureElement("Ctry", TYPE_PROPERTY));
		// TODO, how to do two AdrLine?
		tmp4.add(new PainStructureElement("AdrLine", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("PstlAdr", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message PstlAdr

		tmp2.add(new PainStructureElement("Cdtr", tmp, TYPE_MESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message Cdtr

		// Message CdtrAcct, using tmp
		// Message Id, using tmp4
		tmp4.add(new PainStructureElement("IBAN", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("Id", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message Id

		tmp.add(new PainStructureElement("Ccy", TYPE_PROPERTY, false));
		tmp2.add(new PainStructureElement("CdtrAcct", tmp, TYPE_MESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message CdtrAcct

		// Message CdtrAgt, using tmp
		// Message FinInstnId, using tmp4
		tmp4.add(new PainStructureElement("BIC", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("FinInstnId", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message FinInstnId

		tmp2.add(new PainStructureElement("CdtrAgt", tmp, TYPE_MESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message CdtrAgt

		tmp2.add(new PainStructureElement("ChrgBr", TYPE_PROPERTY, false));

		// Message DrctDbtTxInf, using tmp
		// Message PmtId, using tmp4
		tmp4.add(new PainStructureElement("InstrId", TYPE_PROPERTY, false));
		tmp4.add(new PainStructureElement("EndToEndId", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("PmtId", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message PmtId

		// TODO: does this have an attribute?
		tmp.add(new PainStructureElement("InstdAmt", TYPE_PROPERTY, "Ccy",
				"EUR"));

		// Message DrctDbtTx, using tmp4
		// Message MndtRltdInf, using tmp5
		tmp5.add(new PainStructureElement("MndtId", TYPE_PROPERTY));
		tmp5.add(new PainStructureElement("DtOfSgntr", TYPE_PROPERTY));
		tmp5.add(new PainStructureElement("AmdmntInd", TYPE_PROPERTY));
		tmp4.add(new PainStructureElement("MndtRltdInf", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message MndtRltdInf

		// Message CdtrSchmeId, using tmp5
		// Message Id, using tmp6
		// Message PrvtId, using tmp5
		// Message Othr, using tmp6
		tmp6.add(new PainStructureElement("Id", TYPE_PROPERTY));

		// Message SchmeNm, using tmp5
		tmp5.add(new PainStructureElement("Prtry", TYPE_PROPERTY));
		tmp6.add(new PainStructureElement("SchmeNm", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message SchmeNm

		tmp5.add(new PainStructureElement("Othr", tmp6, TYPE_MESSAGE));
		tmp6 = new ArrayList<PainStructureElement>();
		// End message Othr

		tmp6.add(new PainStructureElement("PrvtId", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message PrvtId

		tmp5.add(new PainStructureElement("Id", tmp6, TYPE_MESSAGE));
		tmp6 = new ArrayList<PainStructureElement>();
		// End message Id

		tmp4.add(new PainStructureElement("CdtrSchmeId", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message CdtrSchmeId

		tmp.add(new PainStructureElement("DrctDbtTx", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message DrctDbtTx

		// Message DbtrAgt, using tmp4
		// Message FinInstnId, using tmp5
		tmp5.add(new PainStructureElement("BIC", TYPE_PROPERTY));
		tmp4.add(new PainStructureElement("FinInstnId", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message FinInstnId

		tmp.add(new PainStructureElement("DbtrAgt", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message DbtrAgt

		// Message Dbtr, using tmp4
		tmp4.add(new PainStructureElement("Nm", TYPE_PROPERTY));

		// Message PstlAdr, using tmp5
		tmp5.add(new PainStructureElement("Ctry", TYPE_PROPERTY));
		// TODO, how to do two AdrLine?
		tmp5.add(new PainStructureElement("AdrLine", TYPE_PROPERTY));
		tmp4.add(new PainStructureElement("PstlAdr", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message PstlAdr

		tmp.add(new PainStructureElement("Dbtr", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message Dbtr

		// Message DbtrAcct, using tmp4
		// Message Id, using tmp5
		tmp5.add(new PainStructureElement("IBAN", TYPE_PROPERTY));
		tmp4.add(new PainStructureElement("Id", tmp5, TYPE_MESSAGE));
		tmp5 = new ArrayList<PainStructureElement>();
		// End message Id

		tmp.add(new PainStructureElement("DbtrAcct", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message DbtrAcct

		// Message Purp, using tmp4
		tmp4.add(new PainStructureElement("Cd", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("Purp", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message Purp

		// Message RmtInf, using tmp4
		tmp4.add(new PainStructureElement("Ustrd", TYPE_PROPERTY));
		tmp.add(new PainStructureElement("RmtInf", tmp4, TYPE_MESSAGE));
		tmp4 = new ArrayList<PainStructureElement>();
		// End message RmtInf

		tmp2.add(new PainStructureElement("DrctDbtTxInf", tmp,
				TYPE_ARRAYMESSAGE));
		tmp = new ArrayList<PainStructureElement>();
		// End message DrctDbtTxInf

		tmp3.add(new PainStructureElement("PmtInf", tmp2, TYPE_ARRAYMESSAGE));
		tmp2 = new ArrayList<PainStructureElement>();
		// End message PmtInf

		tmp.add(new PainStructureElement("CstmrDrctDbtInitn", tmp3,
				TYPE_MESSAGE));
		tmp3 = new ArrayList<PainStructureElement>();
		// End message CstmrDrctDbtInitn

		rootElement = new PainStructureElement("Document", tmp, TYPE_MESSAGE);
		tmp = new ArrayList<PainStructureElement>();
		// End message Document

		logger.debug("Just generated painStructure: " + rootElement);
	}

	class PainStructureElement {
		private String name;
		private List<PainStructureElement> children;
		private String type;
		private Boolean required;
		private String attributeName = null;
		private String attributeValue = null;

		PainStructureElement(String name,
				List<PainStructureElement> childrenElements, String type,
				Boolean required, String attributeName, String attributeValue)
				throws UserException {
			setName(name);
			setChildren(childrenElements);
			setType(type);
			setRequired(required);
			setAttributeName(attributeName);
			setAttributeValue(attributeValue);

			if (!TYPE_PROPERTY.equals(type) && childrenElements == null) {
				throw new UserException(-1,
						"Types other than property require a list of children elements!");
			}
		}

		PainStructureElement(String name,
				List<PainStructureElement> childrenElements, String type,
				String attributeName, String attributeValue)
				throws UserException {
			this(name, childrenElements, type, true, attributeName,
					attributeValue);
		}

		PainStructureElement(String name, String type, Boolean required,
				String attributeName, String attributeValue)
				throws UserException {
			this(name, null, type, required, attributeName, attributeValue);

		}

		PainStructureElement(String name, String type, String attributeName,
				String attributeValue) throws UserException {
			this(name, null, type, attributeName, attributeValue);
		}

		PainStructureElement(String name,
				List<PainStructureElement> childrenElements, String type,
				Boolean required) throws UserException {
			this(name, childrenElements, type, required, null, null);
		}

		PainStructureElement(String name,
				List<PainStructureElement> childrenElements, String type)
				throws UserException {
			this(name, childrenElements, type, true);
		}

		PainStructureElement(String name, String type, Boolean required)
				throws UserException {
			this(name, null, type, required);
		}

		PainStructureElement(String name, String type) throws UserException {
			this(name, type, true);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<PainStructureElement> getChildren() {
			return children;
		}

		public void setChildren(List<PainStructureElement> children) {
			this.children = children;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Boolean getRequired() {
			return required;
		}

		public void setRequired(Boolean required) {
			this.required = required;
		}

		public String getAttributeName() {
			return attributeName;
		}

		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}

		public String getAttributeValue() {
			return attributeValue;
		}

		public void setAttributeValue(String attributeValue) {
			this.attributeValue = attributeValue;
		}

		public String toString() {
			return "\nPainStructureElement:" + getName() + "/" + getChildren()
					+ "";
		}
	}
}
