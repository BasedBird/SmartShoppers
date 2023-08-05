package UnitTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.RegisteredUser;
import eecs3311_project.RegularUser;
import eecs3311_project.Store;
import eecs3311_project.Util;

public class TestStorePreference {

	private List<RegisteredUser> users;
	private List<Store> stores;
	
	@Before
	public void setUp() throws Exception {
		stores = Util.readStores();
		users = Util.readUsers();
	}
	
	@Test
	public void testSaveStore() {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.setSavedStore(stores.get(0));
		
		Assert.assertEquals(stores.get(0), tempUser.getSavedStore());
	}
	
	@Test
	public void testChangeStore() {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.setSavedStore(stores.get(0));
		
		Assert.assertEquals(stores.get(0), tempUser.getSavedStore());
		
		tempUser.setSavedStore(stores.get(1));
		Assert.assertEquals(stores.get(1), tempUser.getSavedStore());
	}
}
