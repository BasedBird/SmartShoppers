package eecs3311_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI_Login extends JFrame implements ActionListener{

	/**
	 * The purpose of this class is to create the FrontEnd/GUI and provide listener functions.
	 * You cannot write/perform any database interaction functions/actions in this class.
	 * You can only invoke a suitable function from A02MiddleTier class on the click event of Submit button. 
	 */

	JLabel usernameLabel;
	JLabel passwordLabel;
	JTextField username;
	JTextField password;
	JButton submit;
	JButton guest;

	List<RegisteredUser> users;
	List<Store> stores;
	private JLabel error;
	private JLabel signUp;

	public GUI_Login() throws IOException {
		this.users = Util.readUsers();
		this.stores = Util.readStores();
		init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void init() {
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(15, 14, 88, 14);
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(15, 45, 88, 14);
		username = new JTextField(10);
		username.setBounds(113, 11, 94, 20);
		password = new JTextField(10);
		password.setBounds(113, 42, 94, 20);
		submit = new JButton("Submit");
		submit.setBounds(60, 119, 96, 23);
		guest = new JButton("Sign Up");
		guest.setBounds(60, 233, 103, 23);

		submit.addActionListener(this);
		guest.addActionListener(this);
		getContentPane().setLayout(null);

		getContentPane().add(usernameLabel);
		getContentPane().add(username);
		getContentPane().add(passwordLabel);
		getContentPane().add(password);
		getContentPane().add(submit);
		getContentPane().add(guest);

		error = new JLabel("");
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setBounds(15, 82, 192, 20);
		getContentPane().add(error);

		signUp = new JLabel("No Account? Sign up here!");
		signUp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		signUp.setHorizontalAlignment(SwingConstants.CENTER);
		signUp.setBounds(10, 202, 208, 20);
		getContentPane().add(signUp);
		this.setBounds(100,100, 239, 322);	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	/** Listens to the submit button click */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == submit) {
			for (RegisteredUser u : users) {

				if (u.signIn(this.username.getText(), this.password.getText())) {
					try {
						if (u.getClass() == RegularUser.class) {
							GUI_StoreLocationsRegistered window = new GUI_StoreLocationsRegistered(u);
							window.setVisible(true);
							this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
						}
						else if (u.getClass() == Manager.class) {
							Manager temp = (Manager) u;
							GUI_Manager window = new GUI_Manager(temp.getStore(), temp);
							window.setVisible(true);
							this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
						}	
						else if (u.getClass() == Administrator.class) {
							Administrator temp = (Administrator) u;
							GUI_HumanResource window = new GUI_HumanResource(temp);
							window.setVisible(true);
							this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
						}	
					} catch (IOException e1) { e1.printStackTrace(); }

				}
			}
			error.setText("Username or Password incorrect");
		}					
		if (source == guest) {
			try {
				GUI_SignUp window = new GUI_SignUp();
				window.setVisible(true);
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			} catch (IOException e1) { e1.printStackTrace();}
		}
	}
}
