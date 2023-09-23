package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import model.DetailTransaction;
import model.Toy;

public class Report {
	private static Report instance;
	
	//Scene scene;
	BorderPane bPane;
	FlowPane fPane;
	GridPane gPane;
	
	Background bgL, bgL1;
	BackgroundFill bfL, bfL1;
	
	Label transactionDateLbl, totalLbl, customerLbl, customerNameLbl;
	Button backBtn;
	
	TableView<DetailTransaction> tableReport;

	Vector<DetailTransaction> transactionDetailList = new Vector<DetailTransaction>();
	Vector<Toy> toyList = new Vector<Toy>();
	
	Database db = Database.getConnection();
	
	Boolean kondisi = false;
	Window reportWindow;
	
	int transactionID = 0;
	
	public static Report getInstance() {
		if (instance == null) {
			instance = new Report();
		}
		
		return instance;
	}
	
	public void initialize() {
		bPane = new BorderPane();
		gPane = new GridPane();
		fPane = new FlowPane();
		
		bfL = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
		bfL1 = new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY);
		bgL = new Background(bfL);
		bgL1 = new Background(bfL1);
		
		History.getInstance();
		Login.getInstance();
		transactionDateLbl = new Label("Transaction Date: " + History.getTransaction().getTransactionDate());
		transactionDateLbl.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 22));
		totalLbl = new Label();
		totalLbl.setFont(Font.font("Inter", FontWeight.BOLD, 22));
		customerLbl = new Label("Customer ID: " + String.valueOf(Login.getUser().getUserID()));
		customerLbl.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 22));
		customerNameLbl = new Label("Customer Name: " + Login.getUser().getUserName());
		customerNameLbl.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 22));
		
		backBtn = new Button("Back");
		backBtn.setPrefWidth(150);
		backBtn.setPrefHeight(45);
		backBtn.setBackground(bgL1);
		backBtn.setFont(Font.font("Inter", FontWeight.BOLD,15));
		backBtn.setAlignment(Pos.CENTER);
				
		bPane.setBackground(bgL);
		reportWindow = new Window("Report");
		reportWindow.getRightIcons().add(new CloseIcon(reportWindow));
		reportWindow.getContentPane().getChildren().add(bPane);
		
				
	}
	
	public void arrangeComponent() {
		// Codingan untuk Table View Transaction Detail
		tableReport = new TableView<>();
		
		TableColumn<DetailTransaction, Integer> column1 = new TableColumn<>("Transaction ID");
		column1.setMinWidth(140);
		column1.setMaxWidth(140);
		column1.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("TransactionID"));
		
		TableColumn<DetailTransaction, Integer> column2 = new TableColumn<>("Toy ID");
		column2.setMinWidth(130);
		column2.setMaxWidth(130);
		column2.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("ToyID"));
		
		TableColumn<DetailTransaction, String> column3 = new TableColumn<DetailTransaction, String>("Toy Name");
		column3.setMinWidth(180);
		column3.setMaxWidth(180);
		column3.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("ToyName"));
		
		TableColumn<DetailTransaction, String> column4 = new TableColumn<DetailTransaction, String>("Toy Price");
		column4.setMinWidth(160);
		column4.setMaxWidth(160);
		column4.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("ToyPrice"));
		
		TableColumn<DetailTransaction, Integer> column5 = new TableColumn<DetailTransaction, Integer>("Quantity");
		column5.setMinWidth(120);
		column5.setMaxWidth(120);
		column5.setCellValueFactory(new PropertyValueFactory<DetailTransaction, Integer>("Quantity"));
				
		TableColumn<DetailTransaction, String> column6 = new TableColumn<DetailTransaction, String>("Sub Total");
		column6.setMinWidth(180);
		column6.setMaxWidth(180);
		column6.setCellValueFactory(new PropertyValueFactory<DetailTransaction, String>("SubTotal"));
		
		tableReport.getColumns().addAll(column1,column2,column3,column4,column5,column6); 
		
		// Biar column nya ga bisa di resize
		tableReport.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableReport.setPadding(new Insets(10, 10, 10, 10));
		tableReport.setMinHeight(450);
		tableReport.setMaxHeight(450);	
		
		gPane.add(customerLbl, 0, 0);
		gPane.add(totalLbl, 1, 0);
		gPane.add(customerNameLbl, 0, 1);
		gPane.add(transactionDateLbl, 1, 1);
		gPane.setHgap(200);
		gPane.setVgap(10);
		
		fPane.getChildren().add(backBtn);

		// Atur layout utama
		bPane.setPadding(new Insets(20));
		bPane.setTop(tableReport);
		bPane.setAlignment(tableReport, Pos.CENTER);
		bPane.setCenter(gPane);
		gPane.setPadding(new Insets(15, 0, 0, 0));
		gPane.setAlignment(Pos.CENTER);
		bPane.setBottom(fPane);
		fPane.setAlignment(Pos.CENTER);
	}
	
	public void getToyData() {
		String query = "SELECT * FROM `toy`";
		ResultSet rs = db.executeQuery(query);
		
		try {
			while(rs.next()) {
				int toyid = rs.getInt("ToyID");
				String toyname = rs.getString("ToyName");
				int toyprice = rs.getInt("ToyPrice");
				int toystock = rs.getInt("ToyStock");
				
				Toy toy = new Toy(toyid, toyname, toyprice, toystock);
				toyList.add(toy);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		transactionDetailList.clear();
		toyList.clear();
		getData();
	}
	
	public void getData() {
		getToyData();
		History.getInstance();
		int transactionID = History.getTransaction().getTransactionID();
		String query = "SELECT * FROM `detailtransaction` WHERE TransactionID = " + transactionID;
		ResultSet rs = db.executeQuery(query);
		
		int total = 0;
		try {
			while(rs.next()) {
				int toyID = rs.getInt("ToyID");
				int quantity = rs.getInt("Quantity");
				String toyName = null;
				String toyPrice = null;
				String subTotal = null;
				
				for (int i = 0; i<toyList.size(); i++) {
					if(toyList.get(i).getToyID() == toyID) {
						toyName = toyList.get(i).getToyName();
						toyPrice = "$" + Integer.toString(toyList.get(i).getToyPrice());
						subTotal = "$" + Integer.toString(toyList.get(i).getToyPrice() * quantity);
						total += toyList.get(i).getToyPrice() * quantity;
					}
				}
				String totalAll = "$" + String.valueOf(total);
				
				DetailTransaction detailTransaction = new DetailTransaction(transactionID, toyID, quantity, toyName, toyPrice, subTotal, totalAll);
				transactionDetailList.add(detailTransaction);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ObservableList<DetailTransaction> detailTransactionObs = FXCollections.observableArrayList(transactionDetailList);
		tableReport.setItems(detailTransactionObs);
		
		totalLbl.setText("Total: $" + String.valueOf(total));
	}
	
	public Window getReportWindow(){
		initialize();
		arrangeComponent();
		refreshTable();
		
		backBtn.setOnAction((event)->{
			reportWindow.close();
			CustomerMain customer = CustomerMain.getInstance();
			History h = History.getInstance();
			customer.bPane.setCenter(h.showHistoryWindow());
		});
				
		return reportWindow;
	}
}
