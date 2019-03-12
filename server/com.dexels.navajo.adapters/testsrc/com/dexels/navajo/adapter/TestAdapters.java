package com.dexels.navajo.adapter;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.adapter.filemap.FileLineMap;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestAdapters {

	@Test
	public void testFileMap() throws IOException, MappableException, UserException {
		FileMap fm = new FileMap();
		FileLineMap[] flm = new FileLineMap[2];
		flm[0] = new FileLineMap();
		flm[0].setLine("apenoot");
		flm[1] = new FileLineMap();
		flm[1].setLine("kibbeling");
		fm.setLines(flm);
		File f = File.createTempFile("test", ".txt");
		fm.setFileName(f.getAbsolutePath());
		fm.store();
		Assert.assertEquals(18, f.length());
		f.deleteOnExit();
	}
}
