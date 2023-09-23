package main;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import model.Toy;
import repository.ToyRepository;

public class ManageProductForm {
	
	private static ManageProductForm instance;
	
	//Scene scene;
	BorderPane bPane;
	GridPane gPane;
	FlowPane fPane;
	
	Background bgL, bgL1;
	BackgroundFill bfL, bfL1;
	
	Label toyNameLbl, toyPriceLbl, toyStockLbl;
	Button insertToyBtn, updateToyBtn, deleteToyBtn;
	TextField toyNameTF, toyPriceTF;
	Spinner<Integer> toyStockSpn;
	TableView<Toy> toyTable;
	
	Vector<Toy> toyList = new Vector<Toy>();
	
	Database db = Database.getConnection();
	
	Boolean kondisi = false;
	Window manageWindow;
	
	ToyRepository repository;
	
	int toyID = 0;
	
	public static ManageProductForm getInstance() {
		if (instance == null) {
			instance = new ManageProductForm();
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
		
		manageWindow = new Window("Manage Items Stock");
		
		// Label Area
		toyNameLbl = new Label ("Toy Name: ");
		toyNameLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		toyPriceLbl = new Label ("Toy Price: ");
		toyPriceLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		toyStockLbl = new Label ("Toy Stock: ");
		toyStockLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
		
		// Button Area
		insertToyBtn = new Button ("Add new Toy");
		insertToyBtn.setPrefWidth(150);
		insertToyBtn.setPrefHeight(45);
		insertToyBtn.setBackground(bgL1);
		insertToyBtn.setFont(Font.font("Inter", FontWeight.BOLD,15));
//		insertToyBtn.setTextFill(Color.WHITE);
		
		updateToyBtn = new Button ("Update Toy");
		updateToyBtn.setPrefWidth(150);
		updateToyBtn.setPrefHeight(45);
		updateToyBtn.setBackground(bgL1);
		updateToyBtn.setFont(Font.font("Inter", FontWeight.BOLD,15));
//		updateToyBtn.setTextFill(Color.WHITE);
		
		deleteToyBtn = new Button ("Delete Toy");
		deleteToyBtn.setPrefWidth(150);
		deleteToyBtn.setPrefHeight(45);
		deleteToyBtn.setBackground(bgL1);
		deleteToyBtn.setFont(Font.font("Inter", FontWeight.BOLD,15));
//		deleteToyBtn.setTextFill(Color.WHITE);
		
		// Text Field Area
		toyNameTF = new TextField ();
		toyNameTF.setPromptText("Name");
		toyPriceTF = new TextField ();
		toyPriceTF.setPromptText("Price");
		
		// Spinner Area
		toyStockSpn = new Spinner<>(0, 1000, 0); // Angka pertama itu mulai dari brp, angka kedua itu sampai berapa, angka ketiga itu defaultnya.
		
		bPane.setBackground(bgL);
		manageWindow.getRightIcons().add(new CloseIcon(manageWindow));
		manageWindow.getContentPane().getChildren().add(bPane);
		
		// Repository
		repository = new ToyRepository();
	}
	
	public void arrangeComponent() {
		
		toyTable = new TableView<>();

		TableColumn<Toy, Integer> column1 = new TableColumn<>("Toy ID");
		column1.setCellValueFactory(new PropertyValueFactory<>("ToyID"));
		column1.setMinWidth(150);

		TableColumn<Toy, String> column2 = new TableColumn<>("Toy Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("ToyName"));
		column2.setMinWidth(150);

		TableColumn<Toy, Integer> column3 = new TableColumn<>("Toy Price");
		column3.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("ToyPrice"));
		column3.setMinWidth(150);

		TableColumn<Toy, Integer> column4 = new TableColumn<>("Toy Stock");
		column4.setCellValueFactory(new PropertyValueFactory<>("ToyStock"));
		column4.setMinWidth(150);
		
		toyTable.getColumns().addAll(column1,column2,column3,column4);
		toyTable.setMaxHeight(500);
		toyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		toyNameTF.setMinWidth(170);
		toyPriceTF.setMinWidth(170);
		
		gPane.add(toyNameLbl, 0, 0);
		gPane.add(toyStockLbl, 0, 1);
		gPane.add(toyNameTF, 1, 0);
		gPane.add(toyStockSpn, 1, 1);
		gPane.add(toyPriceLbl, 2, 0);
		gPane.add(toyPriceTF, 3, 0);
		gPane.setHgap(15);
		gPane.setVgap(20);
		gPane.setPadding(new Insets(10, 0, 0, 0));
		gPane.setAlignment(Pos.CENTER);
		
		fPane.setPadding(new Insets(6, 0, 20, 0));
		fPane.setHgap(30);
		fPane.getChildren().addAll(insertToyBtn, updateToyBtn, deleteToyBtn);
		fPane.setAlignment(Pos.TOP_CENTER);
		
		bPane.setPadding(new Insets(20));
		bPane.setTop(toyTable);
		bPane.setCenter(gPane);
		bPane.setBottom(fPane);
	}
	
	public void getData() {
//		BEFORE
//		String query = "SELECT * FROM `toy`";
//		ResultSet rs = db.executeQuery(query);
		ResultSet rs = repository.selectAll();
		
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
		toyList.clear();
		getData();
		ObservableList<Toy> toyObs = FXCollections.observableArrayList(toyList);
		toyTable.setItems(toyObs);
	}
	
	public void addToy() {
		insertToyBtn.setOnMouseClicked((event)->{
			
			if(toyNameTF.getText().equals("") || toyPriceTF.getText().equals("") || toyStockSpn.getValue() == 0) {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("Error");
				error.setContentText("Data needed not complete!");
				error.showAndWait();
			}
			
//			BEFORE
//			String query = String.format("INSERT INTO `toy`(`ToyName`, `ToyPrice`, `ToyStock`) VALUES ('%s','%d','%d')", toyNameTF.getText(),Integer.parseInt(toyPriceTF.getText()),toyStockSpn.getValue());
//			db.executeUpdate(query);
			repository.insert(toyNameTF.getText(),Integer.parseInt(toyPriceTF.getText()),toyStockSpn.getValue());
			toyNameTF.setText("");
			toyPriceTF.setText("");
			toyStockSpn.getValueFactory().setValue(0);
			AlertInformation("New toy successfully inserted!");
			refreshTable();
			
		});
	}
	
	public void editTable() {
		toyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Toy>() {

			@Override
			public void changed(ObservableValue<? extends Toy> observable, Toy oldValue, Toy newValue) {
				if (newValue != null) {
					kondisi = true;

					toyID = newValue.getToyID();
					toyNameTF.setText(newValue.getToyName());
					toyPriceTF.setText(Integer.toString(newValue.getToyPrice()));
					toyStockSpn.getValueFactory().setValue(newValue.getToyStock());
					
										
					updateToyBtn.setOnMouseClicked((event)->{
						if (toyID <= 0) {
							System.out.println("Test");
							AlertError("You must select a toy from the table first!");
						} else {
							
//							BEFORE
//							String query = String.format("UPDATE `toy` SET `ToyName`='%s',`ToyPrice`='%d',`ToyStock`='%d' WHERE `ToyID` = %d", toyNameTF.getText(),Integer.parseInt(toyPriceTF.getText()),toyStockSpn.getValue(),toyID);
//							db.executeUpdate(query);
							repository.update(toyNameTF.getText(),Integer.parseInt(toyPriceTF.getText()),toyStockSpn.getValue(),toyID);
							toyNameTF.setText("");
							toyPriceTF.setText("");
							toyStockSpn.getValueFactory().setValue(0);
							toyID = 0;
							AlertInformation("Toy successfully updated!");
							refreshTable();
						}
					});
						
					deleteToyBtn.setOnMouseClicked((event)->{
						if (toyID <= 0) {
							System.out.println("Test");
							AlertError("You must select a toy from the table first!");
						} else {
//							BEFORE
//							String query = String.format("DELETE FROM `toy` WHERE `ToyID` = %d", toyID);
//							db.executeUpdate(query);
							repository.delete(toyID);
							toyNameTF.setText("");
							toyPriceTF.setText("");
							toyStockSpn.getValueFactory().setValue(0);
							toyID = 0;
							AlertInformation("Toy successfully deleted!");
							refreshTable();
						}
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
	
	public void AlertInformation(String content) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setHeaderText("Message");
		info.setContentText(content);
		info.show();
	}
	
	
	public Window showManageProductWindow() {	
		initialize();
		arrangeComponent();
		refreshTable();
		addToy();
		editTable();
		
		updateToyBtn.setOnMouseClicked((event)->{
			AlertError("You must select a toy from the table first!");
		});
			
		deleteToyBtn.setOnMouseClicked((event)->{
			AlertError("You must select a toy from the table first!");
		});
		
		return manageWindow;
	}
}


