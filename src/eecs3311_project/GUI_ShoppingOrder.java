package eecs3311_project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

public class GUI_ShoppingOrder extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private List<Item> order;
	private Map<Item, Integer> list;

	/**
	 * Create the frame.
	 */
	public GUI_ShoppingOrder(ShoppingList list) {
		this.list = list.asMap();
		order = list.generateOrder();
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Object[][]items = new Object[list.size()][5];
		int i = 0;
		for (Item itm : order) {
			Item temp = itm;
			items[i][0] = "" + (i + 1);
			items[i][1] = temp.getName();
			items[i][2] = temp.getCategory();
			items[i][3] = "" + temp.getPrice();
			items[i][4] = list.get(itm);
			i++;
		}
		String[] labels = {"Priority", "Item Name", "Category",  "Price", "Amount"};
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 261);
		contentPane.add(scrollPane);
		
		table = new JTable(items, labels);
		scrollPane.setViewportView(table);
	}
}

