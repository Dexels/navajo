/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipi;

import java.io.Serializable;

import com.dexels.navajo.tipi.TipiContext;

import navajo.ExtensionDefinition;

public interface TipiExtension extends Comparable<TipiExtension>,
		ExtensionDefinition, Serializable {

	public void initialize(TipiContext tc);

	public void loadDescriptor();

}
