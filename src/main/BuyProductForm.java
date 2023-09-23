package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.Cart;
import model.Toy;

public class BuyProductForm{
	
	private static BuyProductForm instance;


	Scene scene;
	BorderPane bPane1, bPanequan;
	GridPane gPane;
	FlowPane bottomBtn;
	Window buyproductWindow;
	
	Background bgL, bgL1;
	BackgroundFill bfL, bfL1;
	
	Label selectToyLbl, quantityLbl, toyNameLbl;
	Button addToyToCartBtn, clearCartBtn, checkOutBtn;
	Spinner<Integer> quantitySp;
	
	TableView<Toy> toyTable;
	TableView<Cart> cartTable;
	Vector<Toy> toylist;
	Vector<Cart> cartlist;
	Database db = Database.getConnection();
	
	int toyId;
	int userID = 0;
	
	public static BuyProductForm getInstance() {
		if (instance == null) {
			instance = new BuyProductForm();
		}
		return instance;
	}
	
	public void setTableToy() {
		toyTable = new TableView<>();
		toylist = new Vector<>();
		TableColumn<Toy, Integer> col1 = new TableColumn<Toy, Integer>("Toy ID");
		TableColumn<Toy, String> col2 = new TableColumn<Toy, String>("Toy Name");
		TableColumn<Toy, Integer> col3 = new TableColumn<Toy, Integer>("Toy Price");
		TableColumn<Toy, Integer> col4 = new TableColumn<Toy, Integer>("Toy Stock");
		
		col1.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("ToyID"));
		col2.setCellValueFactory(new PropertyValueFactory<Toy, String>("ToyName"));
		col3.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("ToyPrice"));
		col4.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("ToyStock"));
		
		toyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);	
		toyTable.setMaxHeight(250);
		toyTable.setMinHeight(250);
//		set minimal ukuran kolom
		col1.setMinWidth(150);
		col2.setMinWidth(150);
		col3.setMinWidth(150);
		col4.setMinWidth(150);
		
		//add ke table pakai colom
		toyTable.getColumns().addAll(col1, col2, col3, col4);
		
		bPane1.setTop(toyTable);
		bPane1.setAlignment(toyTable, Pos.TOP_CENTER);
		
		col1.getCellData(0);
	}
	
	public void setTableCart() {
		cartTable = new TableView<>();
		cartlist = new Vector<>();
		TableColumn<Cart, Integer> col1 = new TableColumn<Cart, Integer>("User ID");
		TableColumn<Cart, Integer> col2 = new TableColumn<Cart, Integer>("Toy ID");
		TableColumn<Cart, Integer> col3 = new TableColumn<Cart, Integer>("Quantity");

		
		col1.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("UserID"));
		col2.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("ToyID"));
		col3.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("Quantity"));
		
//		cartTable.setMaxSize(650, 250);
		cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		col1.setMinWidth(200);
		col2.setMinWidth(200);
		col3.setMinWidth(200);
		
		cartTable.setMaxHeight(250);
		cartTable.setMinHeight(250);
		
		//add ke table pakai colom
		cartTable.getColumns().addAll(col1,col2,col3);
	}
	
	public void init() {
		bPane1 = new BorderPane();
		bPanequan = new BorderPane();
		
		gPane = new GridPane();
		bottomBtn = new FlowPane();
		
		bfL = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
		bfL1 = new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY);
		bgL = new Background(bfL);
		bgL1 = new Background(bfL1);

		buyproductWindow = new Window("Buy Product");
		
		setTableToy();
		setTableCart();
				
		selectToyLbl = new Label("Selected Toy: None");
		selectToyLbl.setFont(Font.font("Inter", FontWeight.BOLD, 15));
		
		quantityLbl = new Label("Quantity: ");
		quantityLbl.setFont(Font.font("Inter", FontWeight.BOLD, 15));
		quantitySp = new Spinner<>(0, 100, 0, 1);

		addToyToCartBtn = new Button("Add Toy To Cart");
		addToyToCartBtn.setFont(Font.font("Inter", FontWeight.BOLD, 15));
		addToyToCartBtn.setBackground(bgL1);
		
		gPane.setAlignment(Pos.CENTER);
		
		clearCartBtn = new Button("Clear Cart");
		clearCartBtn.setFont(Font.font("Inter", FontWeight.BOLD, 15));
		clearCartBtn.setBackground(bgL1);
		bottomBtn.getChildren().add(clearCartBtn);
		
		checkOutBtn = new Button("Checkout");
		checkOutBtn.setFont(Font.font("Inter", FontWeight.BOLD, 15));
		checkOutBtn.setBackground(bgL1);
		bottomBtn.getChildren().add(checkOutBtn);
		
		bottomBtn.setHgap(15);
		bottomBtn.setPadding(new Insets(8, 0, 0, 0));
		
		gPane.add(selectToyLbl, 0, 1);
		gPane.add(quantityLbl, 1, 2);
		gPane.add(quantitySp, 2, 2);
		gPane.add(addToyToCartBtn, 3, 2);
		gPane.setHgap(7);

//		gPane.add(QuanPane, 1, 2);
		
		bPanequan.setCenter(gPane);
		
		bPanequan.setBottom(cartTable);
		bPanequan.setAlignment(cartTable, Pos.CENTER);
		
		bPane1.setCenter(bPanequan);
		bPane1.setBottom(bottomBtn);
		bPane1.setPadding(new Insets(8));
		bPane1.setBackground(bgL);

		
		bottomBtn.setAlignment(Pos.BOTTOM_CENTER);

		buyproductWindow.getRightIcons().add(new CloseIcon(buyproductWindow));
		buyproductWindow.getContentPane().getChildren().add(bPane1);

//		scene = new Scene(buyproductWindow, 800, 700);
		
	}
	
	public void getData() {
		String query = "SELECT * FROM `toy`";
		ResultSet rs = db.executeQuery(query);
		
		
		try {
			while(rs.next()) {
				int toyid = rs.getInt("ToyID");
				String toyname = rs.getString("ToyName");
				int toyprice = rs.getInt("ToyPrice");
				int toystock = rs.getInt("ToyStock");
								
				Toy toy = new Toy(toyid, toyname, toyprice, toystock);
				toylist.add(toy);
			}
			
			rs = db.executeQuery("SELECT * FROM `cart` WHERE UserID = " + userID);
			while(rs.next()) {
				int toyid = rs.getInt("ToyID");
				int quantity = rs.getInt("Quantity");
				
				Cart cart = new Cart(userID,toyid,quantity);
				cartlist.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectTable() {
		toyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Toy>() {

			@Override
			public void changed(ObservableValue<? extends Toy> observable, Toy oldValue, Toy newValue) {
				if (newValue != null) {
					selectToyLbl.setText("Selected Toy: " + newValue.getToyName());
					
					toyId = newValue.getToyID();
				}
			}
		});
	}
	
	public void refreshTable() {
		toylist.clear();
		cartlist.clear();
		getData();
		ObservableList<Toy> toyObs = FXCollections.observableArrayList(toylist);
		toyTable.setItems(toyObs);
		ObservableList<Cart> cartObs = FXCollections.observableArrayList(cartlist);
		cartTable.setItems(cartObs);
	}
	
	public void addToy() {
		addToyToCartBtn.setOnMouseClicked((event)->{
			if (toyId < 1) {
				AlertError("You must select the product!");
			}else if (quantitySp.getValue() == 0) {
				AlertError("You must input quantity more than 0!");
			}else {
				String query = String.format("INSERT INTO `cart`(`UserID`, `ToyID`, `Quantity`) VALUES ('%d','%d','%d')",userID,toyId,quantitySp.getValue());
				db.executeUpdate(query);
				quantitySp.getValueFactory().setValue(0);
				selectToyLbl.setText("Selected Toy: None");
				toyId = 0;
				refreshTable();
			}
		});
	}
	
	public void AlertInformation(String content) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setHeaderText("Message");
		info.setContentText(content);
		info.showAndWait();
	}
	
	public void AlertError(String content) {

		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Error");
		error.setContentText(content);
		error.showAndWait();
	}
	
	public void Checkout() {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		now = Calendar.getInstance().getTime();
		sdf.format(now);
		java.sql.Date sqlDate = new java.sql.Date(now.getTime());
//		System.out.println(now);
		
		String query = "INSERT INTO `headertransaction` (`UserID`, `TransactionDate`) VALUES (?,?)";
		PreparedStatement ps = db.prepareStatement(query);
		try {
			ps.setInt(1, Login.getUser().getUserID());
			ps.setDate(2, sqlDate);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int transactionID = 0;
		String transactionQuery = String.format("SELECT TransactionID FROM `headertransaction` WHERE UserID = %d ORDER BY TransactionID DESC LIMIT 1",userID);
		ResultSet rs = db.executeQuery(transactionQuery);
		try {
			while(rs.next()){
				transactionID = rs.getInt("TransactionID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
		
		for(int i = 0; i<cartlist.size();i++) {
			
			int toyStock = 0;
			
			String queryDetail = String.format("INSERT INTO `detailtransaction` " + "(`TransactionID`, `ToyID`, `Quantity`) " + "VALUES ('%d','%d','%d')",transactionID,cartlist.get(i).getToyID(),cartlist.get(i).getQuantity() );
			db.executeUpdate(queryDetail);
			
			String queryGetToy = String.format("SELECT ToyStock FROM `toy` WHERE ToyID = %d", cartlist.get(i).getToyID());
			rs = db.executeQuery(queryGetToy);
			try {
				while(rs.next()){
					toyStock = rs.getInt("ToyStock");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String queryUpdateStock = String.format("UPDATE `toy` SET `ToyStock`='%d' WHERE `ToyID`= %d", toyStock-cartlist.get(i).getQuantity(), cartlist.get(i).getToyID());
			db.executeUpdate(queryUpdateStock);
		}
		
		String queryDelete = "DELETE FROM `cart` WHERE UserID = " + userID;
		db.executeUpdate(queryDelete);
	}
		
	public Window getBuyWindow() {
		userID = Login.getUser().getUserID();
		
		init();
		refreshTable();
		selectTable();
		addToy();
		
		clearCartBtn.setOnMouseClicked(e -> {
			Alert conforclear = new Alert(AlertType.CONFIRMATION);
			conforclear.setContentText("Are you sure to clear cart?");
			conforclear.showAndWait().ifPresent(respone -> {
				if (respone == ButtonType.OK) {
					String queryDelete = "DELETE FROM `cart` WHERE UserID = " + userID;
					db.executeUpdate(queryDelete);
					refreshTable();
				}
			});
			
		});
		
		checkOutBtn.setOnMouseClicked(e -> {
			Alert conforcheckout = new Alert(AlertType.CONFIRMATION);
			conforcheckout.setContentText("Are you sure want to checkout?");
			conforcheckout.showAndWait().ifPresent(respone -> {
				if (respone == ButtonType.OK) {
					Alert info = new Alert(AlertType.INFORMATION);
					info.setHeaderText("Message");
					info.setContentText("Checkout successful!");
					
//					Masukin data cart ke transaction
					Checkout();
					
//					hapus cart
					refreshTable();
					info.showAndWait();
				}
			});
		});
		
		return buyproductWindow;
	}
}
