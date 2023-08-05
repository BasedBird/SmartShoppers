package eecs3311_project;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

public class GUI_HumanResource extends JFrame implements ActionListener{

	private Administrator user;
	private JPanel contentPane;
	private JScrollPane managersScrollPane;
	private JTable managersTable;
	private JButton setStore;
	private JTable storesTable;
	private List<Manager> managers;
	private List<Store> stores;
	private String[][] arrManager;
	private String[][] arrStore;
	private Store selectedStore;
	private Manager selectedManager;
	private JButton selectStore;
	private AbstractButton removeManager;
	private JButton buttonA;
	private JScrollPane storesScrollPane;
	private JButton removeStore;
	private JButton addStore;
	
	GUI_HumanResource(Administrator user) throws IOException{
		this.user = user;
		readData();
		init();
	}

	private void readData() throws IOException {
		List<RegisteredUser> users = Util.readUsers();
		stores = Util.readStores();
		managers = new ArrayList<Manager>();
		for (RegisteredUser u : users) {
			if (u.getClass() == Manager.class) managers.add((Manager) u);
		}
	}

	private void init() {
		arrManager = new String[managers.size()][3];
		for (int i = 0; i < managers.size(); i++) {
			Manager temp = managers.get(i);
			arrManager[i][0] = temp.getName();
			arrManager[i][1] = temp.getStore().getAddress();
			arrManager[i][2] = "" + temp.getStore().getStoreID();
		}
		String[] labelsManager = {"Name", "Store", "StoreID"};
		
		arrStore = new String[stores.size()][3];
		for (int i = 0; i < stores.size(); i++) {
			Store temp = stores.get(i);
			arrStore[i][0] = temp.getAddress();
			arrStore[i][1] = "" + temp.getStoreID();
		}
		String[] labelsStore = {"Location", "StoreID"};
		
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
		
		JLabel HRLabel = new JLabel("Human Resource");
		HRLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		HRLabel.setBounds(10, 15, 285, 36);
		menubar.add(HRLabel);
		
		managersScrollPane = new JScrollPane();
		managersScrollPane.setBounds(10, 99, 341, 198);
		contentPane.add(managersScrollPane);
		
		managersTable = new JTable(arrManager, labelsManager);
		managersScrollPane.setViewportView(managersTable);
		
		setStore = new JButton("Set Store");
		setStore.setBounds(418, 204, 150, 83);
		setStore.addActionListener(this);
		contentPane.add(setStore);
		
		storesScrollPane = new JScrollPane();
		storesScrollPane.setBounds(10, 352, 341, 152);
		contentPane.add(storesScrollPane);
		
		storesTable = new JTable(arrStore, labelsStore) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		storesScrollPane.setViewportView(storesTable);
		
		removeManager = new JButton("Remove Manager");
		removeManager.addActionListener(this);
		removeManager.setBounds(418, 110, 150, 83);
		contentPane.add(removeManager);
		
		JLabel managerLabel = new JLabel("Managers");
		managerLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		managerLabel.setBounds(10, 74, 150, 23);
		contentPane.add(managerLabel);
		
		JLabel storesLabel = new JLabel("Stores");
		storesLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		storesLabel.setBounds(10, 325, 150, 23);
		contentPane.add(storesLabel);
		
		selectStore = new JButton("Manage Store");
		selectStore.addActionListener(this);
		selectStore.setBounds(418, 446, 150, 42);
		contentPane.add(selectStore);
		
		removeStore = new JButton("Remove Store");
		removeStore.addActionListener(this);
		removeStore.setBounds(418, 340, 150, 42);
		contentPane.add(removeStore);
		
		addStore = new JButton("Add Store");
		addStore.addActionListener(this);
		addStore.setBounds(418, 393, 150, 42);
		contentPane.add(addStore);
		
		setTableListeners();
	}

	private void setTableListeners() {
		managersTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = managersTable.rowAtPoint(evt.getPoint());
		        int col = managersTable.columnAtPoint(evt.getPoint());
		        String username = arrManager[row][0];
		        for (Manager m : managers) {
		        	if (m.getName().equals(username)) selectedManager = m;
		        }
		    }
		});	
		storesTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = storesTable.rowAtPoint(evt.getPoint());
		        int col = storesTable.columnAtPoint(evt.getPoint());
		        int id = Integer.parseInt(arrStore[row][1]);
		        for (Store s : stores) {
		        	if (s.getStoreID() == id) selectedStore = s;
		        }
		    }
		});	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == selectStore) {
			if (selectedStore != null) {
				try {
					GUI_Manager window = new GUI_Manager(selectedStore, this.user);
					window.setVisible(true);
				} catch (IOException e1) {e1.printStackTrace();}
			}
		}
		if (source == removeManager) {
			if (selectedManager != null) {
				try {
					selectedManager.deleteAccount();
					readData();
					updateManagerTable();
				} catch (IOException e1) {e1.printStackTrace();}
			}
		}
		if (source == setStore) {
			try {
				if (selectedManager != null && selectedStore != null) {
					List<RegisteredUser> temp = Util.readUsers();
					selectedManager.setStore(selectedStore);
					for (RegisteredUser u : temp) {
						if (u.getName().equals(selectedManager.getName())) {
							System.out.println(321);
							((Manager) u).setStore(selectedStore);
							break;
						}
					}
					Util.writeUsers(temp);
					updateManagerTable();
					System.out.println(123);
				}
			} catch (IOException e1) {e1.printStackTrace();}
			
		}
		if (source == buttonA) {
			GUI_UserProfile window = new GUI_UserProfile(user);
			window.setVisible(true);
		}
		if (source == this.removeStore) {
			stores.remove(this.selectedStore);
			try {
				stores.remove(this.selectedStore);
				Util.writeStore(stores);
				readData();
				updateStoreTable();
			} catch (IOException e1) {e1.printStackTrace();}
		}
		if (source == this.addStore) {
			GUI_NewStore window = new GUI_NewStore(this.user);
			window.setVisible(true);
			this.dispose();
		}
	}

	private void updateManagerTable() throws IOException {
		readData();
		arrManager = new String[managers.size()][3];
		for (int i = 0; i < managers.size(); i++) {
			Manager temp = managers.get(i);
			arrManager[i][0] = temp.getName();
			arrManager[i][1] = temp.getStore().getAddress();
			arrManager[i][2] = "" + temp.getStore().getStoreID();
		}
		String[] labelsManager = {"Name", "Store", "StoreID"};
		managersTable = new JTable(arrManager, labelsManager);
		managersScrollPane.setViewportView(managersTable);
		setTableListeners();
	}	
	
	private void updateStoreTable() throws IOException {
		readData();
		arrStore = new String[stores.size()][3];
		for (int i = 0; i < stores.size(); i++) {
			Store temp = stores.get(i);
			arrStore[i][0] = temp.getAddress();
			arrStore[i][1] = "" + temp.getStoreID();
		}
		String[] labelsStore = {"Location", "StoreID"};
		storesTable = new JTable(arrStore, labelsStore) {
			public boolean isCellEditable(int row, int column) {                
                return false;               
			};
		};
		storesScrollPane.setViewportView(storesTable);
		setTableListeners();
	}	
}
