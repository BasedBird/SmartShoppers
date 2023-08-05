package eecs3311_project;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class GUI_Store extends JFrame implements ActionListener{
	
	private Store store;
	private RegularUser user;
	private Map<Item, Integer> inventory;
	private Map<String, String> descriptions;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton buttonA;
	private JTextField itemAmountField;
	private JButton viewCart;
	private JButton saveStore;
	private JButton addToCart;
	private Item selectedItem;
	private JLabel itemLabel2;
	private JButton backButton;
	
	public GUI_Store(Store store, RegularUser user) throws IOException{
		this.store = store;
		this.user = user;
		this.user.loadShoppingList();
		this.inventory = Util.readInventory(store);
		this.descriptions = loadDescriptions(inventory);
		init();
	}

	private Map<String, String> loadDescriptions(Map<Item, Integer> inventory) {
		Map<String, String> temp = new HashMap<String, String>();
		for (Item itm : inventory.keySet()) {
			temp.put(itm.getName(), itm.getDescription());
		}
		return temp;
	}

	private void init() {
		Object[][] rows = new Object[inventory.size()][6];
		int i = 0;
		for (Item itm : inventory.keySet()) {
			Item temp = itm;
			rows[i][0] = temp.getName();
			rows[i][1] = temp.getCategory();
			rows[i][2] = "" + temp.getPrice();
			rows[i][3] = inventory.get(itm);
			rows[i][4] = temp.getRecommended();
			rows[i][5] = "" + temp.getAisle();
			i++;
		}
		String[] labels = {"Item Name", "Category",  "Price", "In Stock", "Recommended", "Aisle"};
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 882, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel menubar = new JPanel();
		menubar.setBounds(0, 0, 647, 62);
		contentPane.add(menubar);
		menubar.setLayout(null);
		
		buttonA = new JButton("Account");
		buttonA.setBounds(548, 11, 89, 23);
		buttonA.addActionListener(this);
		menubar.add(buttonA);
		
		viewCart = new JButton("View Cart");
		viewCart.setBounds(449, 11, 89, 23);
		viewCart.addActionListener(this);
		menubar.add(viewCart);
		
		saveStore = new JButton("Save Store");
		saveStore.setBounds(311, 11, 128, 23);
		saveStore.addActionListener(this);
		menubar.add(saveStore);
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);
		backButton.setBounds(237, 11, 64, 23);
		menubar.add(backButton);
		
		JLabel storeLabel = new JLabel(this.store.getAddress());
		storeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		storeLabel.setBounds(10, 11, 228, 36);
		menubar.add(storeLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 552, 431);
		contentPane.add(scrollPane);
		
		table = new JTable(rows, labels) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		scrollPane.setViewportView(table);	
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(611, 327, 245, 177);
		contentPane.add(textPane);
		
		JLabel itemLabel = new JLabel("Selected Item:");
		itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		itemLabel.setBounds(611, 89, 101, 31);
		contentPane.add(itemLabel);
		
		itemLabel2 = new JLabel("None");
		itemLabel2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemLabel2.setBounds(722, 90, 126, 31);
		contentPane.add(itemLabel2);
		
		JLabel itemLabel3 = new JLabel("Select Amount:");
		itemLabel3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		itemLabel3.setBounds(611, 131, 101, 31);
		contentPane.add(itemLabel3);
		
		addToCart = new JButton("Add To Cart");
		addToCart.setBounds(611, 173, 195, 36);
		addToCart.addActionListener(this);
		contentPane.add(addToCart);
		
		itemAmountField = new JTextField();
		itemAmountField.setBounds(722, 138, 86, 20);
		contentPane.add(itemAmountField);
		itemAmountField.setColumns(10);
		
		JLabel descLabel = new JLabel("Description");
		descLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		descLabel.setBounds(611, 296, 101, 20);
		contentPane.add(descLabel);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        textPane.setText(descriptions.get(rows[row][0]));
		        for (Item itm : inventory.keySet()) {
		        	if (itm.getName() == rows[row][0]) {
		        		selectedItem = itm;
		        	}
		        }
		        if (selectedItem != null) {
		        	itemLabel2.setText(selectedItem.getName());
		        }
		    }
		});	
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == buttonA) {
			GUI_UserProfile window = new GUI_UserProfile(user);
			window.setVisible(true);
		}
		else if (source == this.saveStore) {
			try {
				this.user.setSavedStore(store);
				List<RegisteredUser> users = Util.readUsers();
				for (RegisteredUser u : users) {
					if (u.equals(this.user)) {
						((RegularUser) u).setSavedStore(store);
						break;
					}
				}
				Util.writeUsers(users);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (source == this.addToCart) {
			this.user.getShoppingList().addItem(selectedItem, Integer.parseInt(this.itemAmountField.getText()));
		}
		else if (source == this.viewCart) {
			GUI_ShoppingList window;
			try {
				window = new GUI_ShoppingList(user);
				window.setVisible(true);
			} catch (IOException e1) {e1.printStackTrace();}
		}
		else if (source == backButton) {
			GUI_StoreLocationsRegistered window;
			try {
				window = new GUI_StoreLocationsRegistered(this.user);
				window.setVisible(true);
				this.dispose();
			} catch (IOException e1) {e1.printStackTrace();}
		}
	}
}
