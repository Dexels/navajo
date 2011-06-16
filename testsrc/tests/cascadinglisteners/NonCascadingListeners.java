package tests.cascadinglisteners;

import java.io.File;

import com.dexels.navajo.tipi.testimpl.AbstractTipiTest;

public class NonCascadingListeners extends AbstractTipiTest {
	public NonCascadingListeners() {
		super("testTipi");
	}

	protected void setUp() throws Exception {
		super.setUp();
		// new String[]{"-DcascadingLoading=aap"}

		setContext("init", new File("testsrc/tests/cascadinglisteners"),
				new String[] { "-DnoCascadedLoading=true" });
	}

	public void testTipi() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		String xx = getContext().getInfoBuffer();
		assertEquals("", xx);

	}

}
