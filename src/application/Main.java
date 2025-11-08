package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {

	public static DoubleLinkedList linkedList = new DoubleLinkedList();
	public static Category category = new Category();
	public static Product product = new Product();
	public static ProductDisplay productDisplay = new ProductDisplay();
	public static CategoryDisplay categoryDisplay = new CategoryDisplay();
	public static ShipmentDisplay shipmentDisplay = new ShipmentDisplay();
	static Alerts alerts = new Alerts();

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage stage) {
		BorderPane HomeScreen = new BorderPane();

		HomeScreen.setTop(createmenuBar(stage));
		HomeScreen.setCenter(welcomeVbox(stage));
		HomeScreen.setBottom(helpHbox(stage));
		setbackGround(HomeScreen);

		Scene scene = new Scene(HomeScreen, 400, 400);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}

	public static MenuBar createmenuBar(Stage stage) {
		MenuBar menuBar = new MenuBar();

		menuBar.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);" + "-fx-background-insets: 0;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);" + "-fx-padding: 5 10 5 10;"
				+ "-fx-background-radius: 10;");

		Menu displayStatistical1 = new Menu();
		styleMenu(displayStatistical1);
		MenuItem displayStatistical11 = new MenuItem("Display Statistical");
		displayStatistical11.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		displayStatistical1.getItems().add(displayStatistical11);
		Image i2 = new Image("/application/icons8-analytics-64.png");
		ImageView iv2 = new ImageView(i2);
		iv2.setFitHeight(50);
		iv2.setFitWidth(50);
		displayStatistical1.setGraphic(iv2);

		Menu empty2 = new Menu("|");
		styleMenu(empty2);
		empty2.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu loadItem1 = new Menu();
		styleMenu(loadItem1);
		MenuItem loadItem = new Menu("Load Category");
		loadItem.setOnAction(x -> {
			LoadCategory load = new LoadCategory();
			load.Display();
		});
		loadItem.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		MenuItem loadItem2 = new MenuItem("Load Products");
		loadItem2.setOnAction(x -> {
			if (categoryDisplay.categoryList == null || categoryDisplay.categoryList.isEmpty()) {
				alerts.ErrorAlert("Error", "Please Uploade the Category First");
				return;
			} else {
				LoadProduct loadProduct = new LoadProduct();
				loadProduct.Display();
			}
		});
		loadItem2.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		MenuItem loadItem3 = new MenuItem("Load Shipments");
		loadItem3.setOnAction(x -> {
			if (categoryDisplay.categoryList == null || categoryDisplay.categoryList.isEmpty()) {
				alerts.ErrorAlert("Error", "Please Uploade the Category First");
				return;
			}
			if (productDisplay.productList == null || productDisplay.productList.isEmpty()) {
				alerts.ErrorAlert("Error", "Please Uploade the Product First");
				return;
			}
			LoadShipment loadShipment = new LoadShipment();
			loadShipment.Display();
		});
		loadItem3.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
		loadItem1.getItems().addAll(loadItem, loadItem2, loadItem3);
		Image i3 = new Image("/application/icons8-load-from-file-48.png");
		ImageView iv3 = new ImageView(i3);
		iv3.setFitHeight(50);
		iv3.setFitWidth(50);
		loadItem1.setGraphic(iv3);

		Menu empty3 = new Menu("|");
		styleMenu(empty3);
		empty3.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu saveItem1 = new Menu();
		styleMenu(saveItem1);
		MenuItem saveItem2 = new MenuItem("Log Export");
		saveItem2.setOnAction(x -> {
			Shipment undo = new Shipment();
			LogExport logExport = new LogExport(undo);
			logExport.Display();
		});
		saveItem1.getItems().add(saveItem2);
		Image i4 = new Image("/application/icons8-save-50.png");
		ImageView iv4 = new ImageView(i4);
		saveItem1.setGraphic(iv4);

		Menu empty4 = new Menu("|");
		styleMenu(empty4);
		empty4.setStyle("-fx-font-size: 38px; -fx-text-fill: black;");

		Menu exit1 = new Menu();
		styleMenu(exit1);
		MenuItem exit2 = new MenuItem("Exit");
		exit2.setOnAction(x -> {
			stage.close();
		});
		exit1.getItems().add(exit2);
		Image i5 = new Image("/application/icons8-exit-50.png");
		ImageView iv5 = new ImageView(i5);
		exit1.setGraphic(iv5);

		menuBar.getMenus().addAll(displayStatistical1, empty2, loadItem1, empty3, saveItem1, empty4, exit1);
		return menuBar;
	}

	public static void styleMenu(Menu styleMenus) {
		styleMenus.setStyle("-fx-background-color: #0d1b58;" + "-fx-text-fill: white;" + "-fx-font-size: 26px;"
				+ "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;" + "-fx-padding: 18px 26px;"
				+ "-fx-border-radius: 6px;" + "-fx-background-radius: 6px;" + "-fx-border-color: transparent;"
				+ "-fx-cursor: hand;");
	}

	public static VBox welcomeVbox(Stage stage) {
		VBox welcomeVb = new VBox(50);
		HBox buttons = new HBox(30);
		CustomLabel WelcomeLabel = new CustomLabel("Welcome to Shipment Management System – COMP242");
		IconButton categoryButton = new IconButton("Categories", "/application/icons8-category-50.png");
		categoryButton.setOnAction(x -> {
			categoryDisplay.Display();
		});
		IconButton productButton = new IconButton("Products", "/application/icons8-products-50.png");
		productButton.setOnAction(x -> {
			productDisplay.Display();
		});
		IconButton shipmentButton = new IconButton("Shipments", "/application/icons8-shipment-50.png");
		shipmentButton.setOnAction(x -> {
			shipmentDisplay.Display();
		});
		buttons.getChildren().addAll(categoryButton, productButton, shipmentButton);
		buttons.setAlignment(Pos.CENTER);
		welcomeVb.getChildren().addAll(WelcomeLabel, buttons);
		welcomeVb.setAlignment(Pos.CENTER);

		return welcomeVb;

	}

	public static HBox helpHbox(Stage stage) {
		HBox helpHb = new HBox(25);

		CustomLabel helpLabel = new CustomLabel(
				"We are Always looking to Improve our Shipment Management System .. ! Share your Ideas with us At  --> :");
		CustomLabel emailLabel = new CustomLabel("Elyasnajeh5@gmail.com");
		helpHb.getChildren().addAll(helpLabel, emailLabel);
		helpHb.setAlignment(Pos.CENTER);

		return helpHb;

	}

	public void setbackGround(BorderPane HomeScreen) {

		HomeScreen.setStyle(
				"-fx-background-image: url('/application/HomeScreen.png');" + "-fx-background-repeat: no-repeat;"
						+ "-fx-background-position: center;" + "-fx-background-size: cover;");
	}
}
