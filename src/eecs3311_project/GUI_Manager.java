package eecs3311_project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUI_Manager extends JFrame implements ActionListener{
	
	Store store;
	RegisteredUser user;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private AbstractButton confirm;
	private Map<Item, Integer> inventory;
	private JTextField newOpen;
	private JTextField newClose;
	private JButton updateHours;
	private JLabel open;
	private JLabel close;
	private Item selectedItem;
	protected Map<String, String> descriptions;
	private JButton buttonA;
	
	GUI_Manager(Store store, Manager user) throws IOException{
		this.store = store;
		this.user = user;
		this.inventory = store.getInventory();
		descriptions = loadDescriptions(inventory);
		init();
	}
	
	GUI_Manager(Store store, Administrator user) throws IOException{
		this.store = store;
		this.user = user;
		this.inventory = store.getInventory();
		descriptions = loadDescriptions(inventory);
		init();
	}
	
	private Map<String, String> loadDescriptions(Map<Item, Integer> inventory) {
		Map<String, String> temp = new HashMap<String, String>();
		for (Item itm : inventory.keySet()) {
			temp.put(itm.getName(), itm.getDescription());
		}
		return temp;
	}
	
	private void init(){
		Object[][] rows = new Object[inventory.size() + 100][6];
		int i = 0;
		for (Item itm : inventory.keySet()) {
			Item temp = itm;
			rows[i][0] = temp.getID();
			rows[i][1] = temp.getName();
			rows[i][2] = temp.getCategory();
			rows[i][3] = "" + temp.getPrice();
			rows[i][4] = inventory.get(itm);
			rows[i][5] = "" + temp.getAisle();
			i++;
		}
		String[] labels = {"ID", "Item Name", "Category",  "Price", "In Stock", "Aisle"};
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel menubar = new JPanel();
		menubar.setBounds(0, 0, 784, 62);
		contentPane.add(menubar);
		menubar.setLayout(null);
		
		JLabel storeLabel = new JLabel(this.store.getAddress());
		storeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		storeLabel.setBounds(10, 11, 228, 36);
		menubar.add(storeLabel);
		
		buttonA = new JButton("Account");
		buttonA.setBounds(685, 11, 89, 23);
		buttonA.addActionListener(this);
		menubar.add(buttonA);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 506, 431);
		contentPane.add(scrollPane);
		
		table = new JTable(rows, labels);
		scrollPane.setViewportView(table);	
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(539, 327, 245, 177);
		contentPane.add(textPane);
		
		confirm = new JButton("Confirm Changes");
		confirm.addActionListener(this);
		confirm.setBounds(539, 293, 150, 23);
		contentPane.add(confirm);
		
		JPanel hoursPanel = new JPanel();
		hoursPanel.setBounds(526, 73, 258, 189);
		contentPane.add(hoursPanel);
		hoursPanel.setLayout(null);
		
		JLabel hoursLabel = new JLabel("Hours");
		hoursLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		hoursLabel.setHorizontalAlignment(SwingConstants.CENTER);
		hoursLabel.setBounds(83, 11, 79, 20);
		hoursPanel.add(hoursLabel);
		
		newOpen = new JTextField(store.getOpen());
		newOpen.setBounds(23, 76, 86, 20);
		hoursPanel.add(newOpen);
		newOpen.setColumns(10);
		
		newClose = new JTextField(store.getClose());
		newClose.setBounds(136, 76, 86, 20);
		hoursPanel.add(newClose);
		newClose.setColumns(10);
		
		JLabel colon = new JLabel(":");
		colon.setFont(new Font("Tahoma", Font.PLAIN, 15));
		colon.setHorizontalAlignment(SwingConstants.CENTER);
		colon.setBounds(112, 65, 25, 37);
		hoursPanel.add(colon);
		
		updateHours = new JButton("Update Hours");
		updateHours.setBounds(62, 107, 128, 23);
		updateHours.addActionListener(this);
		hoursPanel.add(updateHours);
		
		open = new JLabel(store.getOpen());
		open.setFont(new Font("Tahoma", Font.BOLD, 13));
		open.setHorizontalAlignment(SwingConstants.CENTER);
		open.setBounds(40, 51, 46, 14);
		hoursPanel.add(open);
		
		close = new JLabel(store.getClose());
		close.setFont(new Font("Tahoma", Font.BOLD, 13));
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.setBounds(159, 51, 46, 14);
		hoursPanel.add(close);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        textPane.setText(descriptions.get(rows[row][0]));
		        for (Item itm : inventory.keySet()) {
		        	if (itm.getName() == rows[row][1]) {
		        		selectedItem = itm;
		        	}
		        }
		        if (selectedItem != null) {
		        	textPane.setText(selectedItem.getName());
		        }
		        System.out.println(selectedItem);
		    }
		});	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == confirm) {
			this.inventory = buildInventory();
			store.updateInventory(inventory);
			Util.writeInventory(store);
		}
		if (source == updateHours) {
			try {
				open.setText(newOpen.getText());
				close.setText(newClose.getText());
				this.store.setOpen(newOpen.getText());
				this.store.setClose(newClose.getText());
				Util.writeStore(this.store);
			} catch (IOException e1) {e1.printStackTrace();}
		}
		if (source == buttonA) {
			GUI_UserProfile window = new GUI_UserProfile(user);
			window.setVisible(true);
		}
	}

	private Map<Item, Integer> buildInventory() {
		Map<Item, Integer> tempInv = new HashMap<Item, Integer>(); 
		for (int x = 0; x < table.getRowCount(); x++){
			if (table.getValueAt(x,0) == null) {
				continue;
			}
			Item tempItem = new Item(Integer.parseInt(table.getValueAt(x,0).toString()), table.getValueAt(x,1).toString(), "",
					table.getValueAt(x,2).toString(),
					Float.parseFloat(table.getValueAt(x,3).toString()),
					"No",
					Integer.parseInt(table.getValueAt(x, 5).toString()));
			for (Item oldItm : inventory.keySet()) if (oldItm.getID() == tempItem.getID()) tempItem.setDescription(oldItm.getDescription());
			System.out.println(table.getValueAt(x, 4).toString());
			Integer qty = Integer.parseInt(table.getValueAt(x, 4).toString());
			tempInv.put(tempItem, qty);
		}
		return tempInv;
	}
}
