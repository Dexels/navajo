/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
*/
package org.lobobrowser.html.style;

import org.lobobrowser.html.domimpl.HTMLElementImpl;

public class PreRenderState extends BlockRenderState {
	public PreRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	public int getWhiteSpace() {
		Integer ws = this.iWhiteSpace;
		if(ws != null) {
			return ws.intValue();
		}
		AbstractCSS2Properties props = this.getCssProperties();
		String whiteSpaceText = props == null ? null : props.getWhiteSpace();
		int wsValue;
		if(whiteSpaceText == null) {
			wsValue = WS_PRE;
		}
		else {
			String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
			if("nowrap".equals(whiteSpaceTextTL)) {
				wsValue = WS_NOWRAP;
			}
			else if("normal".equals(whiteSpaceTextTL)) {
				wsValue = WS_NORMAL;
			}
			else {
				wsValue = WS_PRE;
			}
		}
		this.iWhiteSpace = new Integer(wsValue);
		return wsValue;
	}
}
