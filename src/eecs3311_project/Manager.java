package eecs3311_project;

public class Manager extends RegisteredUser{
	
	private int employeeNumber;
	private Store store;
	
	Manager(String user, String pass, int employeeNum, Store store){
		super(user, pass);
		this.employeeNumber = employeeNum;
		this.store = store;
	}
	
	public Manager(String user, String pass, int employeeNum){
		super(user, pass);
		this.employeeNumber = employeeNum;
		this.store = null;
	}
	public int getEmployeeNumber() {
		return this.employeeNumber;
	}
	
	public void setStore(Store store) {
		this.store = store;
	}
	
	public Store getStore() {
		return this.store;
	}
}
