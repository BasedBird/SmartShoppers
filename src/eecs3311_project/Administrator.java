package eecs3311_project;

import java.io.IOException;
import java.util.List;

public class Administrator extends RegisteredUser {

	private int employeeNumber;
	
	public Administrator(String user, String pass, int employeeNum){
		super(user, pass);
		this.employeeNumber = employeeNum;
	}
	
	public void setStore(Manager manager, Store store) {
		manager.setStore(store);
	}
}
