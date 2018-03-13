package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingExportSeparatorPanel;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingExportSortingPanel;


public class TipiExportDialog extends TipiDialog {

	private static final long serialVersionUID = 3039921708891626474L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiExportDialog.class);
	
	TipiSwingExportSortingPanel sp;
	TipiSwingExportSeparatorPanel sep;
	private String msgPath;
	GridBagLayout gridBagLayout1 = null;
	JButton proceedButton = null;
	JButton cancelButton = null;
	JButton backButton = null;
	JPanel container;
	private int current = 0;
	private Message data;

    private String proceed;
    private String proceed2;
    private String back;
    private String cancel;
    private String complete;

	private JToolBar myBar = null;

	public TipiExportDialog() {

	}

	@Override
	public Object createContainer() {
		final Object c = super.createContainer();
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				gridBagLayout1 = new GridBagLayout();
				setContainer(c);
				proceedButton = new JButton();
				cancelButton = new JButton();
				backButton = new JButton();
				myBar = new JToolBar();
				initialize();
			}
		});

		return c;
	}

	private final void initialize() {
		backButton.setEnabled(false);
		container = new JPanel();
		
		proceed = myContext.getLookupParser().lookup("exportDialogueProceedButton");
        proceed2 = myContext.getLookupParser().lookup("");
        back = myContext.getLookupParser().lookup("");
        cancel = myContext.getLookupParser().lookup("");
        complete = myContext.getLookupParser().lookup("");


		getSwingContainer().setLayout(gridBagLayout1);
		proceedButton.setText("Verder");
		proceedButton
				.addActionListener(new TipiExportDialog_proceedButton_actionAdapter(
						this));
		cancelButton.setText("Annuleren");
		cancelButton
				.addActionListener(new TipiExportDialog_cancelButton_actionAdapter(
						this));
		backButton.setText("Terug");
		backButton
				.addActionListener(new TipiExportDialog_backButton_actionAdapter(
						this));
		getSwingContainer().add(
				container,
				new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
		container.setLayout(new CardLayout());
		sp = new TipiSwingExportSortingPanel();
		sep = new TipiSwingExportSeparatorPanel();
		container.add(sp, "Sort");
		container.add(sep, "Separator");
		myBar.setFloatable(false);
		getSwingContainer().add(
				myBar,
				new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(0, 0, 0, 0), 0, 0));
		myBar.add(proceedButton);
		myBar.add(cancelButton);
		myBar.add(backButton);
		CardLayout c = (CardLayout) container.getLayout();
		c.first(container);
	}

	@Override
	public void setContainerLayout(Object layout) {
	}

	@Override
	public void setComponentValue(String name, Object value) {
		super.setComponentValue(name, value);
		if ("messagepath".equals(name)) {
			msgPath = (String) value;
			data = getNavajo().getMessage(msgPath);
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					sp.setMessage(data);
				}
			});
		}
	}

	@Override
	public Object getComponentValue(String name) {
		if ("messagepath".equals(name)) {
			return msgPath;
		}
		return super.getComponentValue(name);
	}

	void proceedButton_actionPerformed(ActionEvent e) {
		if (current == 1) {
			List<String> props = sp.getExportedPropertyNames();
			String[] filter = null;
			String separator = sep.getSelectedSeparator();
			exportData(props, filter, separator, sep.isHeaderSelected());
			// d.setVisible(false);
			myContext.disposeTipiComponent(this);
			return;
		}
		backButton.setEnabled(true);
		CardLayout c = (CardLayout) container.getLayout();
		c.next(container);
		current++;
		if (current == 1) {
			proceedButton.setText("Voltooien");
		} else {
			proceedButton.setText("Verder >>");
		}
	}

	private final void exportTitles(Message current, List<String> properties,
			String separator, Writer output) throws IOException {
		for (int j = 0; j < properties.size(); j++) {
			Property current_prop = current.getProperty(properties.get(j));
			String propName = current_prop.getDescription();
			if (propName == null) {
				propName = current_prop.getName();
			}
			output.write(propName);
			if (j < properties.size() - 1) {
				output.write(separator);
			}
		}
		output.write("\n");
	}

	private final void exportData(List<String> properties, String[] filter,
			String separator, boolean addTitles) {

		if (data != null) {
			JFileChooser fd = new JFileChooser("Opslaan");
			fd.showSaveDialog((Container) (this.getTipiParent().getContainer()));
			File out = fd.getSelectedFile();
			if (out == null) {
				return;
			}
			FileWriter fw = null;
			List<Message> subMsgs = data.getAllMessages();
			try {
				fw = new FileWriter(out);
				if (subMsgs.size() > 0 && addTitles) {
					Message first = subMsgs.get(0);
					exportTitles(first, properties, separator, fw);
				}
			} catch (IOException ex1) {
				logger.error("Error detected",ex1);
				return;
			}
			for (int i = 0; i < subMsgs.size(); i++) {
				Message current = subMsgs.get(i);
				if (current.getType() == Message.MSG_TYPE_DEFINITION) {
				    continue;
				}
				// ArrayList props = current.getAllProperties();
				boolean new_line = true;
				boolean line_complies_to_filter = false;
				String line = "";
				for (int j = 0; j < properties.size(); j++) {
					Property current_prop = current.getProperty(properties
							.get(j));
					String propValue = "";
					if (current_prop != null && current_prop.getType().equals(Property.DATE_PROPERTY)) {
						Date d = (Date) current_prop.getTypedValue();
						if (d == null) {
							propValue = "";
						} else {
							java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
									"yyyy-MM-dd");
							propValue = df.format(d);
						}
					} else if (current_prop != null) {
						propValue = (current_prop.getValue() == null) ? ""
								: current_prop.getValue();
					}
					if (current_prop == null || properties.contains(current_prop.getName())) {
						line_complies_to_filter = true;
						if (new_line) {
							line = line + "\"" + propValue + "\"";
							new_line = false;
						} else {
							line = line + separator + "\"" + propValue + "\"";
						}
					}
				}
				// Write the constructed line
				try {
					if (line_complies_to_filter) {
						fw.write(line + System.getProperty("line.separator"));
					}
				} catch (IOException ex) {
					logger.error("Error detected",ex);
				}
			}
			try {
				fw.flush();
				fw.close();
			} catch (IOException ex2) {
				logger.error("Error: ", ex2);
			}
		}
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		myContext.disposeTipiComponent(this);
	}

	void backButton_actionPerformed(ActionEvent e) {
		CardLayout c = (CardLayout) container.getLayout();
		c.previous(container);
		proceedButton.setEnabled(true);
		current--;
		if (current == 0) {
			backButton.setEnabled(false);
		}
		if (current == 1) {
			proceedButton.setText("Voltooien");
		} else {
			proceedButton.setText("Verder");
		}
	}
}

class TipiExportDialog_proceedButton_actionAdapter implements
		java.awt.event.ActionListener {
	TipiExportDialog adaptee;

	TipiExportDialog_proceedButton_actionAdapter(TipiExportDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.proceedButton_actionPerformed(e);
	}
}

class TipiExportDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	TipiExportDialog adaptee;

	TipiExportDialog_cancelButton_actionAdapter(TipiExportDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.cancelButton_actionPerformed(e);
	}
}

class TipiExportDialog_backButton_actionAdapter implements
		java.awt.event.ActionListener {
	TipiExportDialog adaptee;

	TipiExportDialog_backButton_actionAdapter(TipiExportDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.backButton_actionPerformed(e);
	}
}
