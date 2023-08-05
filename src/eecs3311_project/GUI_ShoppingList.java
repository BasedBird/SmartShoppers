package eecs3311_project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
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

public class GUI_ShoppingList extends JFrame implements ActionListener {

	private RegularUser user;
	private Map<String, String> descriptions;
	private Map<Item, Integer> list;
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
	private Object[][] items;
	private JButton removeFromCart;
	private JTextPane textPane;
	private JButton shoppingOrder;
	
	public GUI_ShoppingList(RegularUser user) throws IOException{
		this.user = user;
		this.list = user.getShoppingList().asMap();
		this.descriptions = loadDescriptions(this.list);
		init();
	}

	private Map<String, String> loadDescriptions(Map<Item, Integer> list) {
		Map<String, String> temp = new HashMap<String, String>();
		for (Item itm : list.keySet()) {
			temp.put(itm.getName(), itm.getDescription());
		}
		return temp;
	}

	private void init() {
		items = new Object[list.size()][4];
		int i = 0;
		for (Item itm : list.keySet()) {
			Item temp = itm;
			items[i][0] = temp.getName();
			items[i][1] = temp.getCategory();
			items[i][2] = "" + temp.getPrice();
			items[i][3] = list.get(itm);
			i++;
		}
		String[] labels = {"Item Name", "Category",  "Price", "Amount"};
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 663, 554);
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 341, 431);
		contentPane.add(scrollPane);
		
		JLabel SLLabel = new JLabel("Shopping List");
		SLLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		SLLabel.setBounds(10, 15, 285, 36);
		menubar.add(SLLabel);
		
		table = new JTable(items, labels) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		scrollPane.setViewportView(table);	
		
		textPane = new JTextPane();
		textPane.setBounds(379, 327, 245, 177);
		contentPane.add(textPane);
		
		JLabel itemLabel = new JLabel("Selected Item:");
		itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		itemLabel.setBounds(379, 73, 101, 31);
		contentPane.add(itemLabel);
		
		itemLabel2 = new JLabel("None");
		itemLabel2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		itemLabel2.setBounds(490, 73, 126, 31);
		contentPane.add(itemLabel2);
		
		JLabel itemLabel3 = new JLabel("Select Amount:");
		itemLabel3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		itemLabel3.setBounds(379, 114, 101, 31);
		contentPane.add(itemLabel3);
		
		addToCart = new JButton("Update Amount");
		addToCart.setBounds(379, 162, 195, 36);
		addToCart.addActionListener(this);
		contentPane.add(addToCart);
		
		removeFromCart = new JButton("Remove From Cart");
		removeFromCart.setBounds(379, 210, 195, 36);
		removeFromCart.addActionListener(this);
		contentPane.add(removeFromCart);
		
		itemAmountField = new JTextField();
		itemAmountField.setBounds(488, 120, 86, 20);
		contentPane.add(itemAmountField);
		itemAmountField.setColumns(10);
		
		JLabel descLabel = new JLabel("Description");
		descLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		descLabel.setBounds(379, 304, 101, 20);
		contentPane.add(descLabel);
		
		shoppingOrder = new JButton("Generate Shopping Order");
		shoppingOrder.setBounds(379, 257, 245, 36);
		shoppingOrder.addActionListener(this);
		contentPane.add(shoppingOrder);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        textPane.setText(descriptions.get(items[row][0]));
		        for (Item itm : list.keySet()) {
		        	if (itm.getName() == items[row][0]) {
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
		else if (source == this.addToCart) {
			try {
				list.put(selectedItem, Integer.parseInt(this.itemAmountField.getText()));
				updateTable();
			} catch (Exception x ){}

		}
		else if (source == this.removeFromCart) {
			list.remove(selectedItem);
			updateTable();
		}
		else if (source == buttonA) {
			GUI_UserProfile window = new GUI_UserProfile(user);
			window.setVisible(true);
		}
		else if (source == shoppingOrder) {
			GUI_ShoppingOrder window = new GUI_ShoppingOrder(user.getShoppingList());
			window.setVisible(true);
		}
	}
	
	private void updateTable() {
		items = new Object[list.size()][4];
		int i = 0;
		for (Item itm : list.keySet()) {
			Item temp = itm;
			items[i][0] = temp.getName();
			items[i][1] = temp.getCategory();
			items[i][2] = "" + temp.getPrice();
			items[i][3] = list.get(itm);
			i++;
		}
		String[] labels = {"Item Name", "Category",  "Price", "Amount"};
		table = new JTable(items, labels) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		scrollPane.setViewportView(table);	
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        textPane.setText(descriptions.get(items[row][0]));
		        for (Item itm : list.keySet()) {
		        	if (itm.getName() == items[row][0]) {
		        		selectedItem = itm;
		        	}
		        }
		        if (selectedItem != null) {
		        	itemLabel2.setText(selectedItem.getName());
		        }
		    }
		});	
	}	
}