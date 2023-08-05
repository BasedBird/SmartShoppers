package UnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eecs3311_project.Item;
import eecs3311_project.RegisteredUser;
import eecs3311_project.RegularUser;
import eecs3311_project.ShoppingList;
import eecs3311_project.Util;

public class TestShoppingList {
	
	private List<RegisteredUser> users;
	
	@Before
	public void setUp() throws Exception {
		users = Util.readUsers();
	}

	@Test
	public void testCreateShoppingList() throws NumberFormatException, IOException {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.loadShoppingList();
		ShoppingList tempList = tempUser.getShoppingList();
		Assert.assertTrue(tempList != null);
	}
	
	@Test
	public void testAddAndRemoveFromList() throws NumberFormatException, IOException {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.loadShoppingList();
		ShoppingList tempList = tempUser.getShoppingList();
		Item tempItem = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		
		tempList.addItem(tempItem, 10);
		Assert.assertTrue(tempList.asMap().containsKey(tempItem));
		
		tempList.removeItem(tempItem);
		Assert.assertFalse(tempList.asMap().containsKey(tempItem));
	}
	
	@Test
	public void testAddMore() throws NumberFormatException, IOException {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.loadShoppingList();
		ShoppingList tempList = tempUser.getShoppingList();
		Item tempItem1 = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		tempList.addItem(tempItem1, 10);
		tempList.addItem(tempItem1, 10);
		
		Assert.assertTrue(tempList.asMap().get(tempItem1) == 20);
	}
	
	@Test
	public void testUpdateAmount() throws NumberFormatException, IOException {
		RegularUser tempUser = new RegularUser("A", "");
		tempUser.loadShoppingList();
		ShoppingList tempList = tempUser.getShoppingList();
		Item tempItem1 = new Item(123, "Pillow", "", "Home", 10, "No", 1);
		tempList.addItem(tempItem1, 10);
		
		Item tempItem2 = new Item(123, "Blanket", "", "Home", 2.5, "No", 1);
		tempList.addItem(tempItem2, 10);
		
		tempList.updateItemAmount(tempItem2, 1);
		Assert.assertTrue(tempList.getTotalPrice() == 102.5);	
	}
}
