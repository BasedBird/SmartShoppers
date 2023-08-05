package eecs3311_project;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Store {
	
	int storeID;
	String address;
	Map<Item, Integer> inventory;
	Map<Item, Integer> sales;
	List<Manager> managementTeam;
	String open;
	String close;
	
	public Store(int id, String address, String open, String close) throws IOException{
		this.storeID = id;
		this.address = address;
		this.open = open;
		this.close = close;
		this.inventory = readInventory(this);
	}
	
	public Store(Store store) {
		this.storeID = store.getStoreID();
		this.address = store.getAddress();
		this.open = store.getOpen();
		this.close = store.getClose();
	}
	
	private Map<Item, Integer> readInventory(Store store) throws IOException {
		return Util.readInventory(store);
	}
	
	public void updateInventory(Map<Item, Integer> newInv) {
		this.inventory = newInv;
	}

	public void addItem(Item item, int qty) {
		inventory.put(item, qty);
	}
	
	public int getStoreID() {
		return this.storeID;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public Map<Item, Integer> getInventory() {
		return inventory;
	}
	
	public void setOpen(String open) {
		this.open = open;
	}
	
	public String getOpen() {
		return this.open;
	}
	
	public void setClose(String close) {
		this.close = close;
	}
	
	public String getClose() {
		return this.close;
	}
}
