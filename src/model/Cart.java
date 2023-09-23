package model;

public class Cart {

	protected int UserID, ToyID, Quantity;

	public Cart(int userID, int toyID, int quantity) {
		super();
		UserID = userID;
		ToyID = toyID;
		Quantity = quantity;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public int getToyID() {
		return ToyID;
	}

	public void setToyID(int toyID) {
		ToyID = toyID;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	
}