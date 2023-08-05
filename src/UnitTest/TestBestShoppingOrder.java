package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Item;
import eecs3311_project.RegisteredUser;
import eecs3311_project.RegularUser;
import eecs3311_project.ShoppingList;
import eecs3311_project.Util;

public class TestBestShoppingOrder {

	private List<RegisteredUser> users;

	@Before
	public void setup() throws IOException {
		users = Util.readUsers();
	}

	@Test
	public void testShoppingOrder() throws NumberFormatException, IOException {
		RegularUser temp = new RegularUser("temp", "temp");
		for (RegisteredUser u : users) {
			if (u.getName().equals("Jim")) {
				temp = (RegularUser) u;
			}
		}
		temp.loadShoppingList();
		Set<Item> list1 = temp.getShoppingList().asMap().keySet();
		List<Item> list2 = temp.getShoppingList().generateOrder();
		Assert.assertTrue(list1.size() == list2.size());
	}

	@Test
	public void test2() throws NumberFormatException, IOException {
		RegularUser temp = new RegularUser("temp", "temp");
		for (RegisteredUser u : users) {
			if (u.getName().equals("Jim")) {
				temp = (RegularUser) u;
			}
		}
		temp.loadShoppingList();
		Set<Item> list1 = temp.getShoppingList().asMap().keySet();
		List<Item> list2 = temp.getShoppingList().generateOrder();
		Assert.assertTrue(list1.size() == list2.size());
	}
}
