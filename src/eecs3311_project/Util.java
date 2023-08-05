package eecs3311_project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
	public static final String UTF8_BOM = "0xEF";
	
	public static String removeUTF8BOM(String s) {
	    if (s.startsWith(UTF8_BOM)) {
	        s = s.substring(1);
	    }
	    return s;
	}
	
	public static List<RegisteredUser> readUsers() throws IOException {
		List<Store> temp = readStores();
		List<RegisteredUser> userList = new ArrayList<RegisteredUser>();
		BufferedReader br = new BufferedReader(new FileReader("Users.csv"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] parseLine = line.split(",");
			RegisteredUser newUser = new RegisteredUser("temp", "temp");
			switch (parseLine[2]) {
				case "R":
					newUser = new RegularUser(parseLine[0], parseLine[1]);
					if (parseLine.length > 3) ((RegularUser) newUser).setSavedStore(getStore(temp, parseLine[3]));
					userList.add(newUser);		
					break;
				case "M":
					newUser = new Manager(parseLine[0], parseLine[1], 0, getStore(temp, parseLine[3]));
					userList.add(newUser);
					break;
				case "A":
					newUser = new Administrator(parseLine[0], parseLine[1], 0);
					userList.add(newUser);
					break;	
			}	
			try {
				newUser.setEmail(parseLine[4]);
			} catch (Exception e) {}
		}
		br.close();
		return userList;
	}
	
	private static Store getStore(List<Store> stores, String str) {
		int id = Integer.parseInt(str);
		for (Store s : stores) {
			if ( s.getStoreID() == id ) {
				return s;	
			}
		}
		return null;
	}
	
	public static void writeUsers(List<RegisteredUser> users) {
		try (PrintWriter writer = new PrintWriter("Users.csv")) {
			for (RegisteredUser user : users) {
				StringBuilder sb = new StringBuilder();
				sb.append(user.getName());
				sb.append(',');
				sb.append(user.getPassword());
				sb.append(',');
				if (user.getClass() == RegularUser.class) {
					sb.append('R');
					if (((RegularUser) user).getSavedStore() != null) {
						sb.append(','); 
						sb.append(((RegularUser) user).getSavedStore().getStoreID());
						sb.append(',');
					}
				}
				else if (user.getClass() == Manager.class) {
					sb.append('M'); 
					sb.append(',');
					sb.append(((Manager) user).getStore().getStoreID());
					sb.append(',');
				}
				else if (user.getClass() == Administrator.class) {
					sb.append('A');
					sb.append(',');
					sb.append(',');
				}
				try {
					sb.append(user.getEmail());
				} catch (Exception e) {}
				sb.append('\n');
				writer.write(sb.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Map<Item, Integer> readInventory(Store store) throws IOException {
		Map<Item, Integer> itemList = new HashMap<Item, Integer>();
		BufferedReader br = new BufferedReader(new FileReader("Inventory_" + store.storeID + ".csv"));
		String line;
		while ((line = br.readLine()) != null) {
			line = Util.removeUTF8BOM(line);
			String[] parseLine = line.split(",");
			Item tempItem = new Item(Integer.parseInt(parseLine[0]), parseLine[1], parseLine[2], parseLine[3], Float.parseFloat(parseLine[4]), 
					parseLine[6], Integer.parseInt(parseLine[7]));
			Integer tempInt = Integer.parseInt(parseLine[5]);
			itemList.put(tempItem, tempInt);
		}
		br.close();
		return itemList;
	}
	
	public static List<Store> readStores() throws IOException {
		List<Store> storeList = new ArrayList<Store>();
		BufferedReader br = new BufferedReader(new FileReader("Stores.csv"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] parseLine = line.split(",");
			storeList.add(new Store(Integer.parseInt(parseLine[0]), parseLine[1], parseLine[2], parseLine[3]));
		}
		br.close();
		return storeList;
	}
	
	public static void writeInventory(Store store) {
		Map<Item, Integer> inv = store.getInventory();
		try (PrintWriter writer = new PrintWriter("Inventory_" + store.getStoreID() + ".csv")) {
			for (Item itm : inv.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(itm.getID());
				sb.append(',');
				sb.append(itm.getName());
				sb.append(',');
				sb.append(itm.getDescription());
				sb.append(',');
				sb.append(itm.getCategory());
				sb.append(',');
				sb.append(itm.getPrice());
				sb.append(',');
				sb.append(inv.get(itm));
				sb.append(',');
				sb.append(itm.getRecommended());
				sb.append(',');
				sb.append(itm.getAisle());
				
				sb.append('\n');
				writer.write(sb.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void newInventory(int id) throws IOException {
		Map<Item, Integer> inv = new HashMap<Item, Integer>();
		Item tempItem = new Item(-1, "dummy", "dummy", "dummy", -1, "dummy", -1);
		inv.put(tempItem, -1);
		try (PrintWriter writer = new PrintWriter("Inventory_" + id + ".csv")) {
			for (Item itm : inv.keySet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(itm.getID());
				sb.append(',');
				sb.append(itm.getName());
				sb.append(',');
				sb.append(itm.getDescription());
				sb.append(',');
				sb.append(itm.getCategory());
				sb.append(',');
				sb.append(itm.getPrice());
				sb.append(',');
				sb.append(inv.get(itm));
				sb.append(',');
				sb.append(itm.getRecommended());
				sb.append(',');
				sb.append(itm.getAisle());
				
				sb.append('\n');
				writer.write(sb.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void addStore(Store store) throws IOException {
		List<Store> storeList = readStores();
		storeList.add(store);
		writeStore(storeList);
	}
	
	public static void writeStore(List<Store> storeList) throws IOException {
		try (PrintWriter writer = new PrintWriter("Stores.csv")) {
			for (Store s : storeList) {
				StringBuilder sb = new StringBuilder();
				sb.append(s.getStoreID());
				sb.append(',');
				sb.append(s.getAddress());
				sb.append(',');
				sb.append(s.getOpen());
				sb.append(',');
				sb.append(s.getClose());
				
				sb.append('\n');
				writer.write(sb.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void writeStore(Store store) throws IOException {
		List<Store> storeList = readStores();
		storeList = updateStoreList(storeList, store);
		try (PrintWriter writer = new PrintWriter("Stores.csv")) {
			for (Store s : storeList) {
				StringBuilder sb = new StringBuilder();
				sb.append(s.getStoreID());
				sb.append(',');
				sb.append(s.getAddress());
				sb.append(',');
				sb.append(s.getOpen());
				sb.append(',');
				sb.append(s.getClose());
				
				sb.append('\n');
				writer.write(sb.toString());
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static List<Store> updateStoreList(List<Store> storeList, Store store) {
		for (Store s : storeList) {
			if (s.getStoreID() == store.getStoreID()) {
				s.setOpen(store.getOpen());
				s.setClose(store.getClose());
			}
		}	
		return storeList;
	}

	public static Map<Item, Integer> readShoppingList(RegularUser regularUser) throws NumberFormatException, IOException {
		Map<Item, Integer> itemList = new HashMap<Item, Integer>();
		BufferedReader br = new BufferedReader(new FileReader("ShoppingList.csv"));
		String line;
		while ((line = br.readLine()) != null) {
			line = Util.removeUTF8BOM(line);
			String[] parseLine = line.split(",");
			if (parseLine[7].equals(regularUser.getName())) {
				Item tempItem = new Item(Integer.parseInt(parseLine[0]), parseLine[1], parseLine[2], parseLine[3], Float.parseFloat(parseLine[4]), 
						parseLine[6], Integer.parseInt(parseLine[7]));
				Integer tempInt = Integer.parseInt(parseLine[5]);
				itemList.put(tempItem, tempInt);
			}
		}
		br.close();
		return itemList;
	}
}
