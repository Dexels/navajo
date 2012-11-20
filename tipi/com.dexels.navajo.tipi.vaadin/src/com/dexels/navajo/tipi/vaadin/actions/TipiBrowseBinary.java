/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Window;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiBrowseBinary extends TipiVaadinActionImpl {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBrowseBinary.class);
	private static final long serialVersionUID = -2225176680492876032L;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.
	 * tipi.internal.TipiEvent)
	 */
	
	private Binary outputBinary = null;

	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException {
		Operand value = getEvaluatedParameter("property", event);
		if (value == null) {
			throw new TipiException("TipiBrowseBinary: no value supplied");
		}
		if (value.value == null) {
			throw new TipiException("TipiBrowseBinary: null value supplied");
		}
		if (!(value.value instanceof Property)) {
			throw new TipiException(
					"TipiOpenBinary: Type of value is not Property, but: "
							+ value.value.getClass());
		}
		final Property pp = (Property) value.value;
		if (!pp.getType().equals(Property.BINARY_PROPERTY)) {
			throw new TipiException(
					"TipiOpenBinary: Property is not type binary , but: "
							+ pp.getType());
		}
		
		final Window w = new Window("Upload binary");
		w.setModal(true);
		w.setClosable(true);
		w.setWidth(320, Sizeable.UNITS_PIXELS);
		w.setHeight(70, Sizeable.UNITS_PIXELS);
		Upload u = new Upload("", new Upload.Receiver(){

			private static final long serialVersionUID = 1L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				outputBinary = new Binary();
				return outputBinary.getOutputStream();
			}});
        u.addListener(new Upload.SucceededListener() {
			
			private static final long serialVersionUID = 8118852698370774496L;

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				try {
					pp.setAnyValue(outputBinary);
					getApplication().getMainWindow().removeWindow(w);
					continueAction(getEvent());
				} catch (TipiBreakException e) {
				} catch (TipiSuspendException e) {
				} catch (TipiException e) {
					logger.error("Error: ",e);
				}
			}
		});
        u.addListener(new Upload.FailedListener() {
			
			private static final long serialVersionUID = 4791549173755572186L;

			@Override
			public void uploadFailed(FailedEvent event) {
				logger.error("Upload failed?");
				throw new TipiBreakException();
			}
		});

		w.addComponent(u);
		getApplication().getMainWindow().addWindow(w);
		suspend();
	}
}
