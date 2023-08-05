package eecs3311_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI_StoreLocationsRegistered extends GUI_StoreLocations implements ActionListener{

	RegisteredUser user;

	public GUI_StoreLocationsRegistered(RegisteredUser user) throws IOException {
		super();
		this.user = user;
		init();
	}
	
	private List<JPanel> createStorePanels() {
		List<JPanel> storePanels = new ArrayList<JPanel>();
		int i = 1;
		for (Store store : stores) {
			JPanel panel = new GUI_StorePanel(store, (RegularUser) user);
			storePanels.add(panel);
		}
		return storePanels;
	}

	private void init() {
		JLabel welcome = new JLabel("Welcome " + user.getName() + "!");
		welcome.setBounds(22, 15, 300, 14);
		super.menubar.add(welcome);
		super.buttonA.setText("Account");
		super.buttonA.addActionListener(this);
		
		List<JPanel> panels = createStorePanels();
        for(JPanel panel : panels) {
            super.locations.add(panel);
        }
	}
	
	/** Listens to the submit button click */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == super.buttonA) {
			GUI_UserProfile window = new GUI_UserProfile(user);
			window.setVisible(true);
		}
	}
}
