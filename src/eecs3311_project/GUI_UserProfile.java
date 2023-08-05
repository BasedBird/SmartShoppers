package eecs3311_project;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUI_UserProfile extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 8170865348933630772L;
	
	private JPanel contentPane;
	private JButton confirm;
	
	private RegisteredUser user;
	private JTextField newUserField;

	private JTextField newPassField;
	private JButton delete;

	private JTextField emailTextField;

	private JButton updateEmailButton;

	private JLabel savedEmailLabel;

	private JLabel saveStore;

	private JButton removeStore;

	GUI_UserProfile(RegisteredUser user){
		this.user = user;
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 523, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel newUsername = new JLabel("New Username");
		newUsername.setBounds(205, 80, 97, 14);
		contentPane.add(newUsername);
		
		JLabel newPassword = new JLabel("New Password");
		newPassword.setBounds(205, 123, 97, 14);
		contentPane.add(newPassword);
		
		newUserField = new JTextField();
		newUserField.setBounds(312, 77, 107, 20);
		contentPane.add(newUserField);
		newUserField.setColumns(10);
		
		newPassField = new JTextField();
		newPassField.setColumns(10);
		newPassField.setBounds(312, 120, 107, 20);
		contentPane.add(newPassField);
		
		confirm = new JButton("Apply Changes");
		confirm.setBounds(250, 168, 124, 23);
		confirm.addActionListener(this);
		contentPane.add(confirm);
		
		delete = new JButton("Delete Account");
		delete.setBounds(363, 407, 134, 23);
		delete.addActionListener(this);
		contentPane.add(delete);
		
		JLabel label1 = new JLabel("User Profile");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label1.setBounds(112, 11, 262, 29);
		contentPane.add(label1);
		
		JLabel label2 = new JLabel("<html>Change Username or Password</html>");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		label2.setBounds(59, 67, 75, 95);
		contentPane.add(label2);
		
		JLabel lblSavedStore = new JLabel("Saved Store:");
		lblSavedStore.setHorizontalAlignment(SwingConstants.CENTER);
		lblSavedStore.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSavedStore.setBounds(31, 221, 134, 29);
		contentPane.add(lblSavedStore);
		
		saveStore = new JLabel("None");
		if (user.getClass() == RegularUser.class) {
			RegularUser temp = (RegularUser) user;
			if (temp.getSavedStore() != null) saveStore.setText(temp.getSavedStore().getAddress());
		}
		saveStore.setHorizontalAlignment(SwingConstants.LEFT);
		saveStore.setFont(new Font("Tahoma", Font.PLAIN, 12));
		saveStore.setBounds(168, 223, 224, 29);
		contentPane.add(saveStore);
		
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		emailLabel.setBounds(10, 259, 134, 29);
		contentPane.add(emailLabel);
		
		savedEmailLabel = new JLabel("None");
		if (!this.user.getEmail().equals("")) {
			savedEmailLabel.setText(this.user.getEmail());
		}
		savedEmailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		savedEmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		savedEmailLabel.setBounds(168, 261, 134, 29);
		contentPane.add(savedEmailLabel);
		
		JLabel updateEmailLabel = new JLabel("Update Email");
		updateEmailLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		updateEmailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		updateEmailLabel.setBounds(31, 327, 124, 23);
		contentPane.add(updateEmailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(55, 361, 169, 20);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		updateEmailButton = new JButton("Update");
		updateEmailButton.setBounds(250, 342, 89, 35);
		updateEmailButton.addActionListener(this);
		contentPane.add(updateEmailButton);
		
		removeStore = new JButton("Remove Store");
		removeStore.addActionListener(this);
		removeStore.setBounds(363, 342, 124, 35);
		contentPane.add(removeStore);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if (source == confirm) {
				List<RegisteredUser> users = Util.readUsers();
				for (RegisteredUser u : users) {
					if (u.equals(this.user)) {
						u.setUsername(this.newUserField.getText());
						u.setPassword(this.newPassField.getText());
					}
				}
				Util.writeUsers(users);
			}
			if (source == this.delete) {
				user.deleteAccount();
			}	
			if (source == this.updateEmailButton) {
				this.savedEmailLabel.setText(this.emailTextField.getText());
				this.user.setEmail(this.emailTextField.getText());
				List<RegisteredUser> users = Util.readUsers();
				for (RegisteredUser u : users) {
					if (u.equals(this.user)) {
						u.setEmail(this.emailTextField.getText());
					}
				}
				Util.writeUsers(users);
			}
			if (source == this.removeStore) {
				List<RegisteredUser> users = Util.readUsers();
				for (RegisteredUser u : users) {
					if (u.equals(this.user)) {
						this.saveStore.setText("None");
						if (this.user.getClass() == RegularUser.class) {
							((RegularUser) this.user).setSavedStore(null);
						}
					}
				}
				Util.writeUsers(users);
			}
		} catch (IOException e1) {e1.printStackTrace();}
	}
} 
