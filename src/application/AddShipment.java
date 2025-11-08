package application;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddShipment {
	ShipmentDisplay shipmentDisplay = Main.shipmentDisplay;
	ProductDisplay productDisplay = Main.productDisplay;
	Category category = Main.category;
	IconButton backButton, addButton, addProduct, clearButton;
	CustomLabel idLabel, productLabel, quantityLabel, dateLabel;
	CustomTextField idField, quantityField;
	ComboBox<Product> productBox = new ComboBox<>();
	DatePicker dateField;
	Alerts alerts = new Alerts();
	Main m1;
	
	// Clears all input fields in the shipment form
	public void clear() {
		idField.clear();
		quantityField.clear();
		productBox.getSelectionModel().clearSelection();
		dateField.setValue(null);
	}

	// Called when user clicks "Add Shipment" button
	public void addButton() {
		String Id = idField.getText();
		String quantity = quantityField.getText();
		Product shipmentProduct = productBox.getSelectionModel().getSelectedItem();

		if (Id == null || Id.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Shipment ID Cant be Empty");
			return;
		}
		if (!Id.matches("\\d+")) {
			alerts.ErrorAlert("Error", "Shipemnt ID Can be Only Numbers");
			return;

		}
		if (quantity == null || quantity.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Shipment Quantity Cant be Empty");
			return;
		}
		if (!quantity.matches("\\d+")) {
			alerts.ErrorAlert("Error", "Shipment Quantity Can be Only Numbers");
			return;

		}
		if (dateField.getValue() == null) {
			alerts.ErrorAlert("Error", "Date can't be empty");
			return;
		}
		String selectedDate = dateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		int shipmentId = Integer.parseInt(Id);
		int shipmentQuantity = Integer.parseInt(quantity);

		boolean isValid = validation(shipmentId, shipmentQuantity, shipmentProduct, selectedDate);
		if (!isValid) {
			return;
		}

		boolean isUnique = uniqueId(shipmentId);
		if (!isUnique) {
			return;
		}

		boolean confirmation = alerts.ConfiramtionAlert("Info", "Are You sure you Want to Add This Shipemnt ?");
		if (!confirmation) {
			return;
		}

		// If all is good, create the shipment and add it
		Shipment newShipment = new Shipment(shipmentId, shipmentQuantity, selectedDate, shipmentProduct);
		category.addShipment(newShipment);
		shipmentDisplay.shipmentList.add(newShipment);
		alerts.InfoAlert("Success", "Shipment Added Succesfully,Thanks");
		clear();

	}
	// Validates shipment input data

	public boolean validation(int id, int quantity, Product productBox, String date) {
		if (id <= 0) {
			alerts.ErrorAlert("Error", "Shipment id Cant be zero or less");
			return false;
		}
		if (productBox == null) {
			alerts.ErrorAlert("Error", "Please select a Product for the Shipment");
			return false;
		}
		if (quantity <= 0) {
			alerts.ErrorAlert("Error", "Shipment quantity Cant be zero or less");
			return false;
		}
		String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		if (!date.equals(todayDate)) {
			alerts.ErrorAlert("Error", "Shipment date must be today Date Only");
			return false;
		}

		return true;
	}
	// Checks if the shipment ID already exists

	public boolean uniqueId(int id) {
		for (Shipment shipment : shipmentDisplay.shipmentList) {
			if (shipment.getShipmentId() == id) {
				alerts.ErrorAlert("Error", "This Shipment ID is Already Exist, Sorry ..");
				return false;
			}
		}
		return true;
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane addShipment = new GridPane();
		addShipment.setPadding(new Insets(30));
		addShipment.setHgap(50);
		addShipment.setVgap(40);

		idLabel = new CustomLabel("Shipment ID: ");
		idField = new CustomTextField();
		addShipment.add(idLabel, 0, 0);
		addShipment.add(idField, 1, 0);

		productLabel = new CustomLabel("Product: ");
		productBox.setItems(productDisplay.productList);

		productBox.setPromptText("Select a Product:");
		addShipment.add(productLabel, 0, 1);
		addShipment.add(productBox, 1, 1);

		quantityLabel = new CustomLabel("Product Quantity: ");
		quantityField = new CustomTextField();
		addShipment.add(quantityLabel, 0, 2);
		addShipment.add(quantityField, 1, 2);

		dateLabel = new CustomLabel("Date of Shipment:");
		dateField = new DatePicker();
		dateField.setStyle("-fx-background-color: white;" + "-fx-text-fill: #2b2b2b;" + "-fx-font-size: 13px;"
				+ "-fx-font-family: 'Segoe UI', 'Calibri';" + "-fx-border-color: #cccccc;" + "-fx-border-radius: 6;"
				+ "-fx-background-radius: 6;" + "-fx-padding: 6;" + "-fx-opacity: 0.98;");
		dateField.setMaxWidth(320);
		dateField.setMaxHeight(60);
		addShipment.add(dateLabel, 0, 3);
		addShipment.add(dateField, 1, 3);

		HBox allButtons = new HBox(100);
		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});
		addButton = new IconButton("Add Shipment", "/application/icons8-add-50.png");
		addButton.setOnAction(x -> {
			addButton();
		});
		addProduct = new IconButton("Add Product", "application/icons8-add-50.png");
		addProduct.setOnAction(x -> {
			alerts.InfoAlert("Info",
					"This Button only if you need to Add Shipment for a Product is not Available in the List");
			AddProduct addProduct = new AddProduct();
			addProduct.Display();

		});
		clearButton = new IconButton("Clear", "/application/icons8-clear-50.png");
		clearButton.setOnAction(x -> {
			clear();
		});
		allButtons.getChildren().addAll(backButton, addButton, addProduct, clearButton);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane addScreen = new BorderPane();
		m1 = new Main();
		m1.setbackGround(addScreen);

		addScreen.setTop(menuBar);
		addScreen.setLeft(addShipment);
		addScreen.setBottom(allButtons);

		Scene scene = new Scene(addScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}

}
