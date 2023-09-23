package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class CustomerMain{
	
	private static CustomerMain instance;

	Scene scene;
	
	BorderPane bPane;
	
	MenuBar menuBar;
	Menu userMenu, managementMenu;
	MenuItem logOutMI, buyToyMI, historyMI; 
	
	public static CustomerMain getInstance() {
		if (instance == null) {
			instance = new CustomerMain();
		}
		
		return instance;
	}
	
	public void initialize() {
		bPane = new BorderPane();
		
		menuBar = new MenuBar();
		userMenu = new Menu("User");
		managementMenu = new Menu("Management");
		logOutMI = new MenuItem("Logout");
		buyToyMI = new MenuItem("Buy Toy");
		historyMI = new MenuItem("History");
		
//		//add menu ke menubar
		menuBar.getMenus().add(userMenu);
		menuBar.getMenus().add(managementMenu);
		
//		//add menu item ke menu
		userMenu.getItems().add(logOutMI);
		managementMenu.getItems().add(buyToyMI);
		managementMenu.getItems().add(historyMI);
		
		bPane.setTop(menuBar);
		bPane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		scene = new Scene(bPane,1000,730);
		
//		BorderPane buyProductPage = new BuyProductForm();
	}
	
	public void showCustomerPage() {
		initialize();
		
		buyToyMI.setOnAction((event)->{
			BuyProductForm bpf = BuyProductForm.getInstance();
			bPane.setCenter(bpf.getBuyWindow());
		});
		
		historyMI.setOnAction((event) -> {
			History h = History.getInstance();
			bPane.setCenter(h.showHistoryWindow());
		});
		
		logOutMI.setOnAction((event)->{
			Login.setUser(null);
			Login login = Login.getInstance();
			login.showLogin();
		});
		
		Login.getInstance();
		System.out.println(Login.getUser().getUserID());
		
		Main.changeScene(scene, "Customer Main");
	}

	

}
