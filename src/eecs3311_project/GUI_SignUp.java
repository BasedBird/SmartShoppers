package eecs3311_project;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI_SignUp extends JFrame implements ActionListener {

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField username;
	private JTextField password;
	private JButton submit;
	private JLabel error;

	private List<RegisteredUser> currentUsers;
	
	GUI_SignUp() throws IOException{
		currentUsers = Util.readUsers();
		init();
	}

	private void init() {
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(15, 14, 100, 14);
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(15, 45, 100, 14);
		username = new JTextField(10);
		username.setBounds(113, 11, 94, 20);
		password = new JTextField(10);
		password.setBounds(113, 42, 94, 20);
		submit = new JButton("Create Account");
		submit.setBounds(47, 82, 137, 23);

		submit.addActionListener(this);
		
		getContentPane().setLayout(null);

		getContentPane().add(usernameLabel);
		getContentPane().add(username);
		getContentPane().add(passwordLabel);
		getContentPane().add(password);
		getContentPane().add(submit);

		error = new JLabel("");
		error.setHorizontalAlignment(SwingConstants.CENTER);
		error.setForeground(Color.RED);
		error.setBounds(15, 82, 192, 20);
		getContentPane().add(error);
		this.setBounds(100,100, 250, 158);	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		boolean valid = true;
		if (source == submit) {
			int i = -1;
			try {
				i = RegisteredUser.createAccount(username.getText(), password.getText());
			} catch (IOException e2) {e2.printStackTrace();}
			if (i == 0) error.setText("Username taken");
			else if (i == 1) error.setText("Password field can not be empty");
			else if (i == 2) {
				try {
					currentUsers.add(new RegularUser(username.getText(), password.getText()));
					Util.writeUsers(currentUsers);
					GUI_Login window = new GUI_Login();
					window.setVisible(true);
					this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
				} catch (IOException e1) {e1.printStackTrace();}
			}
		}					
	}

}
