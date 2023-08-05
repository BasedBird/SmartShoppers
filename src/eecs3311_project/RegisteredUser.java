package eecs3311_project;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class RegisteredUser {

	private String username;
	private String password;
	private String email;

	public RegisteredUser(String user, String pass){
		setPassword(pass);
		setUsername(user);
		setEmail("");
	}

	public String getName() {
		return this.username;
	}

	public void setUsername(String user) {
		this.username = user;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}

	public boolean signIn(String user, String pass) {
		return (this.getName().toLowerCase().equals(user.toLowerCase()) && this.getPassword().toLowerCase().equals(pass.toLowerCase()));
	}

	public boolean usernameTaken(String username) {
		return this.getName().toLowerCase().equals(username.toLowerCase());
	}

	public static int createAccount(String username, String password) throws IOException {
		List<RegisteredUser> users = Util.readUsers();
		for (RegisteredUser u : users) {
			if (u.usernameTaken(username)) {
				return 0;
			}	
			if (password.equals("")) {
				return 1;
			}	
		}
		users.add(new RegularUser(username, password));
		Util.writeUsers(users);
		return 2;
	}

	public void deleteAccount() throws IOException {
		List<RegisteredUser> users = Util.readUsers();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(this)) {
				users.remove(i);
				break;
			}
		}
		Util.writeUsers(users);
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		RegisteredUser other = (RegisteredUser) obj;
		return this.username.equals(other.getName()) && this.password.equals(other.getPassword());
	}
}
