package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;

import com.dexels.navajo.tipi.TipiEmbedComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.embed.TipiEchoStandaloneContainer;
import com.dexels.navajo.tipi.components.echoimpl.embed.TipiEchoStandaloneToplevel;

public class TipiEchoEmbedComponent extends TipiEmbedComponent {


	private static final long serialVersionUID = -588381253551933691L;
	private TipiEchoStandaloneToplevel tet;

	public TipiEchoEmbedComponent() {
	}

	public Object createContainer() {
		stc = new TipiEchoStandaloneContainer((TipiEchoInstance) ((EchoTipiContext) getContext()).getInstance(),
				(EchoTipiContext) getContext());
		tet = (TipiEchoStandaloneToplevel) stc.getContext().getDefaultTopLevel();
		Component c = (Component) tet.getContainer();
		c.setBackground(new Color(255, 0, 0));
		return c;
		// return new ContentPane();
	}

	public void setComponentValue(String name, Object value) {

		if (name.equals("tipiCodeBase")) {
//			String tipiCodeBase = (String) value;
			stc.getContext().setTipiResourceLoader(getContext().getTipiResourceLoader());
			return;
		}
		if (name.equals("resourceCodeBase")) {
			// try {
//			String resourceCodeBase = (String) value;
			stc.getContext().setGenericResourceLoader(getContext().getGenericResourceLoader());
			return;
			// } catch (MalformedURLException e) {
			// e.printStackTrace();
			// }

		}
		super.setComponentValue(name, value);
	}

	protected void switchToDefinition(final String nameVal) {
		System.err.println("Switching to def: " + nameVal);
		try {
			stc.getContext().switchToDefinition(nameVal);
//			Component topLevel = (Component) stc.getContext().getTopLevel();
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
