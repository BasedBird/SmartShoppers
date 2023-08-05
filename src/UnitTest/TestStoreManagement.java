package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Item;
import eecs3311_project.Store;
import eecs3311_project.Util;

public class TestStoreManagement {

	private List<Store> stores;
	
	@Before
	public void setUp() throws Exception {
		stores = Util.readStores();
	}

	@Test
	public void testSetHours() throws IOException {
		Store testStore = new Store(stores.get(0));
		String oldOpen = new String(testStore.getOpen());
		String oldClose = new String(testStore.getClose());
		
		testStore.setOpen("9:00");
		testStore.setClose("11:00");
		Util.writeStore(testStore);
		
		stores = Util.readStores();
		testStore = new Store(stores.get(0));
		Assert.assertEquals(testStore.getOpen(), "9:00");
		Assert.assertEquals(testStore.getClose(), "11:00");
		
		testStore = new Store(stores.get(0));
		testStore.setOpen(oldOpen);
		testStore.setClose(oldClose);
		Util.writeStore(testStore);
		
		stores = Util.readStores();
		testStore = new Store(stores.get(0));
		Assert.assertEquals(testStore.getOpen(), oldOpen);
		Assert.assertEquals(testStore.getClose(), oldClose);
	}
	
	@Test
	public void testAddAndRemoveStore() throws IOException {
		int id = -1;
		Util.newInventory(id);
		Store tempStore = new Store(id, "New Store", "12:00", "1:00");
		Util.addStore(tempStore);
		
		boolean flag = false;
		stores = Util.readStores();
		for (Store s : stores) {
			if (s.getStoreID() == tempStore.getStoreID()) flag = true;
		}
		
		Assert.assertTrue(flag);
		for (Store s : stores) {
			if (s.getStoreID() == tempStore.getStoreID()) {
				stores.remove(s);
				break;
			}
		}
		Util.writeStore(stores);
		stores = Util.readStores();
		
		flag = true;
		for (Store s : stores) {
			if (s.getStoreID() == tempStore.getStoreID()) flag = false;
		}
		Assert.assertTrue(flag);
	}
}
