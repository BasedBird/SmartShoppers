package eecs3311_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GUI_StoreLocations extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	protected JButton buttonA;
	protected JPanel menubar;
	String storeFilePath = "";
	List<Store> stores;
	protected JPanel locations;
	
	public GUI_StoreLocations() throws IOException {
		stores = Util.readStores();
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		menubar = new JPanel();
		menubar.setBounds(0, 0, 647, 62);
		contentPane.add(menubar);
		menubar.setLayout(null);
		
		buttonA = new JButton();
		buttonA.setBounds(548, 11, 89, 23);
		menubar.add(buttonA);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 58, 647, 457);
		contentPane.add(scrollPane);
		
		locations = new JPanel();
		scrollPane.setViewportView(locations);
		locations.setLayout(new BoxLayout(locations, BoxLayout.Y_AXIS));

	}
	
	/** Listens to the submit button click */
    public void actionPerformed(ActionEvent e) {
    	 Object source = e.getSource();
    	 System.out.println(source + "!");
    }
}
