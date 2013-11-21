package com.dexels.navajo.tipi.vaadin.components.impl;

import com.dexels.navajo.tipi.vaadin.components.TipiDynamicCell;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

/**
 * A message button which can be selected. Contains the sender, subject and a
 * shortened version of the body
 * 
 */
public class TableCell extends CssLayout {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

//	private static final String STYLENAME = "message-button";

	private Label bodyLabel;

	private Label subHeaderLabel;

	private Label tagLabel;

	private Label headerLabel;

//	private Integer index;

	private String stylePrefix;
//
//	private String headerPropertyName;
//
//	private String subHeaderPropertyName;
//
//	private String bodyPropertyName;
//
//	private String tagPropertyName;

	private final TipiDynamicCell tipiParent;

	public TableCell(TipiDynamicCell tc) {
		tipiParent = tc;
		setWidth("100%");
		initialize();
	}

	public void initialize() {
		headerLabel = new Label();
		headerLabel.setWidth("-1px");
		headerLabel.addStyleName(Reindeer.LABEL_H2);
		addComponent(headerLabel);

		tagLabel = new Label();
		tagLabel.setWidth("-1px");
		addComponent(tagLabel);

		subHeaderLabel = new Label();
		subHeaderLabel.setHeight("1.5em");
		subHeaderLabel.setWidth("-1px");
		addComponent(subHeaderLabel);

		bodyLabel = new Label();
		tagLabel.addStyleName(Reindeer.LABEL_SMALL);
		bodyLabel.setStyleName(Reindeer.LABEL_SMALL);
		addComponent(bodyLabel);

	}
	
	
	
	@Override
	public void attach() {
		super.attach();

		addListener(new LayoutClickListener() {
			private static final long serialVersionUID = -3200241305696535695L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				tipiParent.layoutClick();
			}
		});
	}

	public void setHeaderValue(String from) {
		headerLabel.setValue(from);
		if (from.length() > 25) {
			headerLabel.setValue(from.substring(0, 25) + "...");
		} else {
			headerLabel.setValue(from);
		}

	}

	public void setBodyValue(String body) {
		if(body==null) {
			bodyLabel.setValue("<stuff>");
			return;
		}
		if (body.length() > 80) {
			bodyLabel.setValue(body.replaceAll("\\<.*?\\>", "").substring(0, 80)
					+ "...");
		} else {
			bodyLabel.setValue(body.replaceAll("\\<.*?\\>", ""));
		}
	}

	public void setSubHeaderValue(String subject) {
		if (subject.length() > 40) {
			subHeaderLabel.setValue(subject.substring(0, 40) + "...");
		} else {
			subHeaderLabel.setValue(subject);
		}
	}


	public void setTagValue(String tag) {
		tagLabel.setValue(tag);
	}


	public void setStylePrefix(String string) {
		this.stylePrefix = string;
		setStyleName(stylePrefix);
		headerLabel.setStyleName(stylePrefix + "-header");
		subHeaderLabel.setStyleName(stylePrefix + "-subheader");
		bodyLabel.setStyleName(stylePrefix + "-body");
		tagLabel.setStyleName(stylePrefix + "-tag");
		
	}

}