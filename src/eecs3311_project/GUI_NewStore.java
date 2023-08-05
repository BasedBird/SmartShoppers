package eecs3311_project;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GUI_NewStore extends JFrame implements ActionListener{
	
	private Administrator user;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton;
	
	public GUI_NewStore(Administrator user) {
		this.user = user;
		init();
	}
	
	

	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label1 = new JLabel("Store ID");
		label1.setBounds(28, 22, 75, 14);
		contentPane.add(label1);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(28, 61, 75, 14);
		contentPane.add(lblAddress);
		
		JLabel lblOpen = new JLabel("Open");
		lblOpen.setBounds(28, 105, 75, 14);
		contentPane.add(lblOpen);
		
		JLabel lblClose = new JLabel("Close");
		lblClose.setBounds(28, 143, 75, 14);
		contentPane.add(lblClose);
		
		textField = new JTextField();
		textField.setBounds(127, 19, 149, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(127, 58, 149, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(127, 102, 149, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(127, 140, 149, 20);
		contentPane.add(textField_3);
		
		btnNewButton = new JButton("Create Store");
		btnNewButton.setBounds(133, 209, 143, 23);
		btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.btnNewButton) {
			try {
				Util.newInventory(Integer.parseInt(this.textField.getText()));
				Store newStore = new Store(Integer.parseInt(this.textField.getText()), this.textField_1.getText(), this.textField_2.getText(), this.textField_3.getText());
				Util.addStore(newStore);
				GUI_HumanResource window = new GUI_HumanResource(user);
				window.setVisible(true);
				this.dispose();
			} catch (NumberFormatException | IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
