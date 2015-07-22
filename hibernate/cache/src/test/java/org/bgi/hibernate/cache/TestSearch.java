package org.bgi.hibernate.cache;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSearch extends RestTestCase {

	private long bondId = 0;
	
	private long fatherId = 0;
	
	@Before
	public void setup2() throws Exception{
		Contact c1 = new Contact();
		c1.setFirstName("James");
		c1.setLastName("Bond");
		c1.setAddress("Somewhere");
		c1.setCity("London");
		c1.setCountry("Great Britain");
		c1.setZipCode("007 ;-)");
		
		bondId = this.saveContact(c1);
		
		Contact c2 = new Contact();
		c2.setFirstName("Andrew");
		c2.setLastName("Bond");
		c2.setAddress("Grave");
		c2.setCity("London");
		c2.setCountry("Great Britain");
		c2.setZipCode("007 ;-)");
		
		fatherId = this.saveContact(c2);
	}
	
	@Test
	public void testFindAll() throws Exception {
		
		Collection<? extends Contact> result = this.findAllContacts();
		
		Assert.assertEquals(2, result.size());
		Contact[] bondFamily = result.toArray(new Contact[result.size()]);
		Assert.assertEquals("Andrew", bondFamily[0].getFirstName());
		Assert.assertEquals(fatherId, bondFamily[0].getId());
		
		Assert.assertEquals("James", bondFamily[1].getFirstName());
		Assert.assertEquals(bondId, bondFamily[1].getId());
	}
	
	@Test
	public void findByFullText() throws Exception {
		this.reindex();
		Thread.sleep(2000);
		Collection<? extends Contact> result = this.findAllMatchingText("bond");
		Assert.assertEquals(2, result.size());
	}
}
