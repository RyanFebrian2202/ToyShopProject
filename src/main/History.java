package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.Window;
import model.HeaderTransaction;

public class History {
	
	private static History instance;
	private static HeaderTransaction IdTransaction;
	
	//Scene scene;
	BorderPane bPane;
	FlowPane fPane, fPane2;
	
	Background bgL, bgL1;
	BackgroundFill bfL, bfL1;
	
	Label selectedTransactionLbl;
	
	Button showReportBtn;
	TableView<HeaderTransaction> transactionTable;
	
	Vector<HeaderTransaction> transactionList = new Vector<HeaderTransaction>();
	
	Database db = Database.getConnection();
	
	Boolean kondisi = false;
	Window manageWindow;
	
	int transactionID = 0;
	
	public static History getInstance() {
		if (instance == null) {
			instance = new History();
		}
		
		return instance;
	}
	
	public static HeaderTransaction getTransaction() {
		return IdTransaction;
	}
	
	public static void setTransaction(HeaderTransaction transactionSelected) {
		IdTransaction = transactionSelected;
	}
	
	public void initialize() {
		
		bPane = new BorderPane();
		fPane = new FlowPane();
		fPane2 = new FlowPane();
		
		bfL = new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY);
		bfL1 = new BackgroundFill(Color.DARKGRAY, new CornerRadii(10), Insets.EMPTY);
		bgL = new Background(bfL);
		bgL1 = new Background(bfL1);
		
		selectedTransactionLbl = new Label("Selected Transaction: None");
		manageWindow = new Window("Transaction History");
		
		// Button Area
		showReportBtn = new Button ("Show Report");
		showReportBtn.setPrefWidth(150);
		showReportBtn.setPrefHeight(45);
		showReportBtn.setBackground(bgL1);
		showReportBtn.setFont(Font.font("Inter", FontWeight.BOLD,15));
			
		bPane.setBackground(bgL);
		manageWindow.getRightIcons().add(new CloseIcon(manageWindow));
		manageWindow.getContentPane().getChildren().add(bPane);
		
	}
	
	public void arrangeComponent() {
		
		transactionTable = new TableView<>();

		transactionTable = new TableView<HeaderTransaction>();
		TableColumn<HeaderTransaction, Integer> column1 = new TableColumn<>("Transaction ID");
		column1.setMinWidth(130);
		column1.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Integer>("TransactionID"));

		TableColumn<HeaderTransaction, Integer> column2 = new TableColumn<>("User ID");
		column2.setMinWidth(120);
		column2.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Integer>("UserID"));

		TableColumn<HeaderTransaction, Date> column3 = new TableColumn<>("Transaction Date");
		column3.setMinWidth(170);
		column3.setCellValueFactory(new PropertyValueFactory<HeaderTransaction, Date>("TransactionDate"));

		transactionTable.getColumns().add(column1);
		transactionTable.getColumns().add(column2);
		transactionTable.getColumns().add(column3);
		
		transactionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Biar column nya ga bisa di resize
		transactionTable.setPadding(new Insets(10, 10, 10, 10));
		transactionTable.setMinHeight(450);
		transactionTable.setMaxHeight(450);	
		
		fPane2.setPadding(new Insets(10, 0, 10, 0));
		fPane2.getChildren().add(selectedTransactionLbl);
		fPane2.setAlignment(Pos.CENTER_LEFT);
		
		fPane.setPadding(new Insets(6, 0, 20, 0));
		fPane.setHgap(30);
		fPane.getChildren().addAll(showReportBtn);
		fPane.setAlignment(Pos.TOP_CENTER);
		
		bPane.setPadding(new Insets(20));
		bPane.setTop(transactionTable);
		bPane.setCenter(fPane2);
		bPane.setBottom(fPane);
	}
	
	public void getData() {
		Login.getInstance();
		int userID = Login.getUser().getUserID();
		String query = "SELECT * FROM `headertransaction` WHERE UserID = " + userID;
		ResultSet rs = db.executeQuery(query);
		
		try {
			while(rs.next()) {
				int transactionId = rs.getInt("TransactionID");
				Date transactionDate = new Date(); 
				transactionDate = rs.getDate("TransactionDate");
				
				HeaderTransaction transactionHead = new HeaderTransaction(transactionId, userID, transactionDate);						
				transactionList.add(transactionHead);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		transactionList.clear();
		getData();
		ObservableList<HeaderTransaction> transactionObs = FXCollections.observableArrayList(transactionList);
		transactionTable.setItems(transactionObs);
	}
	
	public void editTable() {
		transactionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HeaderTransaction>() {

			@Override
			public void changed(ObservableValue<? extends HeaderTransaction> observable, HeaderTransaction oldValue, HeaderTransaction newValue) {
				if (newValue != null) {
					kondisi = true;
					
					transactionID = newValue.getTransactionID();
					selectedTransactionLbl.setText("Selected Transaction: " + "Transaction " + transactionID);
					
					IdTransaction = new HeaderTransaction(transactionID, newValue.getUserID(), newValue.getTransactionDate());
					
					showReportBtn.setOnMouseClicked((event)->{
						manageWindow.close();
						CustomerMain customer = CustomerMain.getInstance();
						Report r = Report.getInstance();
						customer.bPane.setCenter(r.getReportWindow());
					});	
				}		
			} 	
		});
	}
	
	public void AlertError(String content) {
		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText("Error");
		error.setContentText(content);
		error.show();
	}	
	
	public Window showHistoryWindow() {	
		initialize();
		arrangeComponent();
		refreshTable();
		editTable();
		
		showReportBtn.setOnMouseClicked((event)->{
			AlertError("You must select a transaction from the table first!");
		});	
		
		return manageWindow;
	}
}


