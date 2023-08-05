package UnitTest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Item;
import eecs3311_project.Store;
import eecs3311_project.Util;

public class TestItemSearch {

	List<Store> stores;
	
	@Before
	public void setUp() throws Exception {
		stores = Util.readStores();
	}

	@Test
	public void testSearchName() {
		String findItem = "Shirt";
		Map<Item, Integer> inv = stores.get(0).getInventory();
		Item tempItem = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		inv.put(tempItem , 10);
		
		boolean flag = false;
		for (Item i : inv.keySet()) {
			if (i.getName().equals(findItem)) flag = true;
		}
		
		Assert.assertTrue(flag);
	}

	@Test
	public void testSearchCategory() {
		String findItem = "Clothing";
		Map<Item, Integer> inv = stores.get(0).getInventory();
		Item tempItem = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		inv.put(tempItem , 10);
		
		boolean flag = false;
		for (Item i : inv.keySet()) {
			if (i.getCategory().equals(findItem)) flag = true;
		}
		
		Assert.assertTrue(flag);
	}
}
