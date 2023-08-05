package eecs3311_project;

public class Item {
	
	int ID;
	int aisle;
	double price;
	String description;
	private String category;
	private String name;
	private String recommended;
	
	public Item(int ID, String name, String description, String category, double price, String recommended, int aisle){
		setID(ID);
		setName(name);
		setDescription(description);
		setPrice(price);
		setCategory(category);
		this.aisle = aisle;
		this.recommended = recommended;
	}

	public int getID() {
		return this.ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String desc) {
		if (desc == null) {
			this.description = "";
		}
		else {
			this.description = desc;
		}
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}

	public String getRecommended() {
		return this.recommended;
	}
	
	public void setRecommended(String rec) {
		this.recommended = rec;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		Item other = (Item) obj;
		return this.getName().equals(other.getName());
	}

	public int getAisle() {
		return this.aisle;
	}
}
