package org.bgi.hibernate.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSecondLevelCache extends RestTestCase {

	private static final int NB_ITERATIONS = 3;
	
	private long id;
	
	@Before
	public void setUp2() throws Exception {
		Contact c = new Contact();
		c.setFirstName("First Name");
		c.setLastName("Last Name");
		c.setAddress("Address");
		c.setCountry("Country");
		c.setCity("City");
		c.setZipCode("ZIP");
		
		id = this.saveContact(c);
	}
	
	@Test
	public void testSecondLevelCache() throws Exception {
		for(int i=0; i<NB_ITERATIONS; i++){
			String newAddress = "NEW_ADRESS_" + i;
			
			Contact c = this.getContactById(id);
			Assert.assertNotNull(c);
			Assert.assertEquals(id, c.getId());
			
			c.setAddress(newAddress);
			
			this.updateContact(c);;
			
			Thread.sleep(100);
			
			c = getContactById(id);
			
			Assert.assertEquals(newAddress, c.getAddress());
		}
	}
}
