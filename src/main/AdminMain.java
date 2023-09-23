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


public class AdminMain{

	private static AdminMain instance;
	
	Scene scene;
	BorderPane bPane;
	
	MenuBar menuBar;
	Menu userMenu, managementMenu;
	MenuItem logOutMI, manageProductMI; 
	
	public static AdminMain getInsance() {
		if (instance == null) {
			instance = new AdminMain();
		}
		return instance;
	}
	
	public void initialize() {
		bPane = new BorderPane();
		
		menuBar = new MenuBar();
		userMenu = new Menu("User");
		managementMenu = new Menu("Manage Shop");
		logOutMI = new MenuItem("Logout");
		manageProductMI = new MenuItem("Manage Items Stock");
		
//		//add menu ke menubar
		menuBar.getMenus().add(userMenu);
		menuBar.getMenus().add(managementMenu);
		
//		//add menu item ke menu
		userMenu.getItems().add(logOutMI);
		managementMenu.getItems().add(manageProductMI);
		
		
		bPane.setTop(menuBar);
		bPane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		
		scene = new Scene(bPane,800,690);
		
	}
	
	public void showAdminPage() {
		initialize();
		
		manageProductMI.setOnAction((event)->{
			ManageProductForm managaProduct = ManageProductForm.getInstance();
			bPane.setCenter(managaProduct.showManageProductWindow());
		});
				
		logOutMI.setOnAction((event) -> {
			Login.setUser(null);
			Login login = Login.getInstance();
			login.showLogin();
		});
		
		Main.changeScene(scene, "Admin Main");
	}
}
