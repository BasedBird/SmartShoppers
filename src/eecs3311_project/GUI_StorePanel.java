package eecs3311_project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GUI_StorePanel extends JPanel implements ActionListener{

	private Store store;
	private RegularUser user;

	GUI_StorePanel(Store store, RegularUser user){
		this.store = store;
		this.user = user;
		List<JPanel> storePanels = new ArrayList<JPanel>();
		JButton select = new JButton("Select");
		select.addActionListener(this);
		this.add(new JLabel(String.valueOf(store.getStoreID()) + ". "));
		this.add(new JLabel(store.getAddress()));
		this.add(select);
		this.setBounds(40,80,200,200);
	}
	
	private JFrame getRoot() {
		return (JFrame) this.getTopLevelAncestor();
	}
	
	private void close() {
		JFrame test = (JFrame) this.getTopLevelAncestor();
		test.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			GUI_Store window = new GUI_Store(store, user);
			window.setVisible(true);
		} catch (IOException x) {x.printStackTrace();}
        close();	
	}
}
