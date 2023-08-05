package eecs3311_project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingList {
	
	Map<Item, Integer> shoppingList;
	
	ShoppingList(Map<Item, Integer> newList){
		this.shoppingList = newList;
	}
	
	public void addItem(Item item, int qty) {
		for (Item i : shoppingList.keySet()) {
			if (i.equals(item)) {
				shoppingList.put(i, shoppingList.get(i) + qty);
				return;
			}
		}
		shoppingList.put(item, qty);
	}
	
	public void updateItemAmount(Item item, int qty) {
		for (Item i : shoppingList.keySet()) {
			if (i.equals(item)) {
				shoppingList.put(i, qty);
				return;
			}
		}
		shoppingList.put(item, qty);
	}
	
	public void removeItem(Item item) {
		shoppingList.remove(item);
	}
	
	public Map<Item, Integer> asMap() {
		return this.shoppingList;
	}

	public static ShoppingList loadList(RegularUser regularUser) throws NumberFormatException, IOException {
		return new ShoppingList(Util.readShoppingList(regularUser));
	}

	public double getTotalPrice() {
		double total = 0;
		for (Item i : shoppingList.keySet()) {
			total += i.getPrice() * shoppingList.get(i);
		}
		return total;
	}

	public List<Item> generateOrder() {
		List<Item> order = new ArrayList<Item>();
		for (Item i : shoppingList.keySet()) {
			order.add(i);
		}
		Collections.shuffle(order);
		return order;
	}
}
