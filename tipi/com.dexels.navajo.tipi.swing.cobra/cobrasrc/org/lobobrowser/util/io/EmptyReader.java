/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.util.io;

import java.io.IOException;
import java.io.Reader;

public class EmptyReader extends Reader {
	public void close() throws IOException {
	}

	public int read(char[] cbuf, int off, int len) throws IOException {
		return 0;
	}
}
