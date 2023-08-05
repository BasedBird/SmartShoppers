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

public class TestRecommendation {
	
	private List<Store> stores;
	
	@Before
	public void setUp() throws Exception {
		stores = Util.readStores();
	}

	@Test
	public void testSetRecommendation() {
		Map<Item, Integer> inv = stores.get(0).getInventory();
		for (Item i : inv.keySet()) {
			i.setRecommended("YES");
		}
		
		boolean flag = true;
		for (Item i : inv.keySet()) {
			if (!i.getRecommended().equals("YES")) flag = false;
		}
		Assert.assertTrue(flag);
	}
	
	@Test
	public void testRecommendation() {
		Map<Item, Integer> inv = stores.get(0).getInventory();
		for (Item i : inv.keySet()) {
			i.setRecommended("YES");
		}
		
		boolean flag = true;
		for (Item i : inv.keySet()) {
			if (!i.getRecommended().equals("YES")) flag = false;
		}
		Assert.assertTrue(flag);
	}
}
