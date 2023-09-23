package model;

public class DetailTransaction {
	private int TransactionID, ToyID, Quantity;
	private String ToyName, ToyBrand, SubTotal, ToyPrice, Total;
	
	public DetailTransaction(int transactionID, int toyID, int quantity, String toyName, String toyPrice, String subTotal, String total) {
		super();
		TransactionID = transactionID;
		ToyID = toyID;
		Quantity = quantity;
		ToyName = toyName;
		ToyPrice = toyPrice;
		SubTotal = subTotal;
		Total = total;
	}

	public int getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
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

	public String getToyName() {
		return ToyName;
	}

	public void setToyName(String toyName) {
		ToyName = toyName;
	}

	public String getToyBrand() {
		return ToyBrand;
	}

	public void setToyBrand(String toyBrand) {
		ToyBrand = toyBrand;
	}

	public String getToyPrice() {
		return ToyPrice;
	}

	public void setToyPrice(String toyPrice) {
		ToyPrice = toyPrice;
	}

	public String getSubTotal() {
		return SubTotal;
	}

	public void setSubTotal(String subTotal) {
		SubTotal = subTotal;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}
}
