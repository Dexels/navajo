/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipipackage;

import tipi.TipiExtension;

public interface ITipiExtensionContainer {
	public void addOptionalInclude(TipiExtension te);

	public void setSystemProperty(String key, String value);

}
