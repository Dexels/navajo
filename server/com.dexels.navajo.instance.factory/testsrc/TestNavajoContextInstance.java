/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.context.NavajoContextInstanceFactory;


public class TestNavajoContextInstance {

	private NavajoContextInstanceFactory context; 

	
	@Before
	public void setUp() throws Exception {
		context = new NavajoContextInstanceFactory();
	}

	@Test
	@Ignore // fails due to non-existing Repository
	public void test() throws FileNotFoundException {
		context.activate(null);
		
	}

}
