package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Item;
import eecs3311_project.Store;
import eecs3311_project.Util;

public class TestItemManagement {

	private List<Store> stores;
	
	@Before
	public void setUp() throws Exception {
		stores = Util.readStores();
	}

	@Test
	public void testAddItem() throws IOException {
		Store testStore = stores.get(0);
		boolean flag = true;
		Map<Item, Integer> testInventory = testStore.getInventory();
		for (Item i : testInventory.keySet()) {
			if (i.getName().equals("Pillow")) flag = false;
		}
		Item tempItem = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		if (flag) {
			testInventory.put(tempItem, 10);
			testStore.updateInventory(testInventory);
			Util.writeInventory(testStore);
		}
		
		stores = Util.readStores();
		Store newStore = stores.get(0);
		Map<Item, Integer> newInventory = newStore.getInventory();
		
		flag = false;
		for (Item i : newInventory.keySet()) {
			if (tempItem.getName().equals(i.getName())) {
				flag = true;
				tempItem = i;
				break;
			}
		}
		Assert.assertTrue(flag);
		
		newInventory.remove(tempItem);
		newStore.updateInventory(newInventory);	
		Util.writeInventory(newStore);
	}
	
	@Test
	public void testEditItem() throws IOException {
		Store testStore = stores.get(0);
		Map<Item, Integer> testInventory = testStore.getInventory();
		boolean flag = true;
		for (Item i : testInventory.keySet()) {
			if (i.getName().equals("Pillow")) flag = false;
		}
		Item tempItem = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		if (flag) {
			testInventory.put(tempItem, 10);
			tempItem.setPrice(12);
			tempItem.setCategory("Home");
			testStore.updateInventory(testInventory);
			Util.writeInventory(testStore);
		}
		
		stores = Util.readStores();
		Store newStore = stores.get(0);
		Map<Item, Integer> newInventory = newStore.getInventory();
		
		Item newItem = null;
		for (Item i : newInventory.keySet()) {
			if (tempItem.getName().equals(i.getName())) {
				flag = true;
				newItem = i;
				break;
			}
		}
		Assert.assertTrue(tempItem.getName().equals(newItem.getName()));
		Assert.assertTrue(newItem.getPrice() == 12);
		Assert.assertTrue(newItem.getCategory().equals("Home"));
		
		newInventory.remove(newItem);
		newStore.updateInventory(newInventory);	
		Util.writeInventory(newStore);
	}
}
