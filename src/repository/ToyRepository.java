package repository;

import java.sql.ResultSet;

import main.Database;

public class ToyRepository {
	
	Database db = Database.getConnection();
	
	public void insert(String name, int price, int stock) {
		String query = String.format("INSERT INTO `toy`(`ToyName`, `ToyPrice`, `ToyStock`) VALUES ('%s','%d','%d')", name, price, stock);
		db.executeUpdate(query);
	};
	
	public ResultSet selectAll() {
		String query = "SELECT * FROM `toy`";
		ResultSet rs = db.executeQuery(query);
		
		return rs;
	}
	
	public void update(String name, int price, int stock, int id) {
		String query = String.format("UPDATE `toy` SET `ToyName`='%s',`ToyPrice`='%d',`ToyStock`='%d' WHERE `ToyID` = %d", name, price, stock, id);
		db.executeUpdate(query);
	}
	
	public void delete(int id) {
		String query = String.format("DELETE FROM `toy` WHERE `ToyID` = %d", id);
		db.executeUpdate(query);
	}
}
