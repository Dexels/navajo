package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JScrollPane;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiDefinitionListener;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingTextArea;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSourceViewer extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 967187890066389401L;
	private TipiSwingTextArea myTextArea;
	private TipiDefinitionListener myTipiListener;

	private String currentDefinition = null;

	public TipiSourceViewer() {
	}

	@Override
	public Object createContainer() {
		myTextArea = new TipiSwingTextArea();
		// myTextArea.setEditable(false);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		JScrollPane jsp = new JScrollPane(myTextArea);
		addHelper(th);
		myTipiListener = new TipiDefinitionListener() {

			private static final long serialVersionUID = -5461187048768338542L;

			@Override
			public void definitionLoaded(String definitionName,
					XMLElement definition) {
				if (definitionName.equals(currentDefinition)) {
					setDefinition(definitionName);
				}
			}
		};
		myContext.addDefinitionListener(myTipiListener);
		return jsp;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("definition")) {
			setDefinition((String) object);
			return;
		}
		super.setComponentValue(name, object);
	}

	private void setDefinition(final String object) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				String definition = object;
				XMLElement xe = myContext.getTipiDefinition(definition);
				currentDefinition = definition;
				// This can happen, in this case, it will be picked up when the
				// defininition gets loaded
				if (xe != null) {
					myTextArea.setText(xe.toString());
				} else {
					myTextArea.setText("Component: " + definition
							+ " is not loaded.");
				}
			}
		});
	}

	@Override
	public void disposeComponent() {
		myContext.removeDefinitionListener(myTipiListener);
		super.disposeComponent();
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myTextArea.getText();
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		if (name.equals("save")) {
			XMLElement xe = new CaseSensitiveXMLElement();
			xe.parseString(myTextArea.getText());
			myContext.addTipiDefinition(xe);
		}

	}
}
