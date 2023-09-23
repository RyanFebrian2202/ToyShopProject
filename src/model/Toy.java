package model;

public class Toy {
	
	private int ToyID;
	private String ToyName;
	private int ToyPrice;
	private int ToyStock;
	
	public Toy(int toyID, String toyName, int toyPrice, int toyStock) {
		super();
		ToyID = toyID;
		ToyName = toyName;
		ToyPrice = toyPrice;
		ToyStock = toyStock;
	}

	public int getToyID() {
		return ToyID;
	}

	public void setToyID(int toyID) {
		ToyID = toyID;
	}
	
	public String getToyName() {
		return ToyName;
	}
	
	public void setToyName(String toyName) {
		ToyName = toyName;
	}
	
	public int getToyPrice() {
		return ToyPrice;
	}
	
	public void setToyPrice(int toyPrice) {
		ToyPrice = toyPrice;
	}
	
	public int getToyStock() {
		return ToyStock;
	}
	
	public void setToyStock(int toyStock) {
		ToyStock = toyStock;
	}
	
}
