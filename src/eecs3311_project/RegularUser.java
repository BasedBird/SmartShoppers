package eecs3311_project;

import java.io.IOException;

public class RegularUser extends RegisteredUser {

	Store savedStore;
	ShoppingList shoppingList;
	
	public RegularUser(String user, String pass){
		super(user, pass);
		this.savedStore = null;
		this.shoppingList = null;
	}
	
	public void setSavedStore(Store store) {
		this.savedStore = store;
	}
	
	public ShoppingList getShoppingList() {
		return this.shoppingList;
	}
	
	public Store getSavedStore() {
		return this.savedStore;
	}

	public void loadShoppingList() throws NumberFormatException, IOException {
		this.shoppingList = ShoppingList.loadList(this);
	}
}
