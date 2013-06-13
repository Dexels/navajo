import junit.framework.Assert;

import org.junit.Test;

import com.dexels.navajo.entity.Key;


public class TestKey {

	@Test
	public void testGetKeyId() {
		
		String id = Key.getKeyId("auto,id=noot,optional");
		Assert.assertEquals("noot", id);
		
	}
	
	@Test
	public void testGetKeyId2() {
		
		String id = Key.getKeyId("auto,optional");
		Assert.assertNull(id);
		
	}
	
	@Test
	public void testGetKeyId3() {
		
		String id = Key.getKeyId("");
		Assert.assertNull(id);
		
	}
	
	@Test
	public void testGetKeyId4() {
		
		String id = Key.getKeyId("id=noot");
		Assert.assertEquals("noot", id);
		
	}
}
