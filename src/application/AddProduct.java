package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddProduct {
	IconButton backButton, addButton, clearButton;
	CustomLabel idLabel, nameLabel, categoryLabel, statusLabel;
	CustomTextField idField, nameField;
	Category category = Main.category;
	CategoryDisplay categoryDisplay = Main.categoryDisplay;
	ProductDisplay productDisplay = Main.productDisplay;
	ComboBox<Category> categoryBox =  new ComboBox<>();
	ToggleGroup statusGroup;
	RadioButton active, inActive;
	Alerts alerts = new Alerts();
	Main m1;
	
	// Getter and setter for ComboBox
	
	public ComboBox<Category> getCategoryBox() {
		return categoryBox;
	}

	public void setCategoryBox(ComboBox<Category> categoryBox) {
		this.categoryBox = categoryBox;
	}


	// Clear all input fields 
	public void clear() {
		idField.clear();
		nameField.clear();
		categoryBox.getSelectionModel().clearSelection();
		statusGroup.selectToggle(null);
	}
	
	// Called when user clicks the Add Product button
	public void addButton() {
		String id = idField.getText();
		String productName = nameField.getText();
		Category productCategory = categoryBox.getSelectionModel().getSelectedItem();
		Toggle selectedToggle = statusGroup.getSelectedToggle();

		if (id == null || id.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Product ID Cant be Empty");
			return;
		}
		if (!id.matches("\\d+")) {
			alerts.ErrorAlert("Error", "Product ID Can be Only Numbers");
			return;

		}

		int productId = Integer.parseInt(id);

		boolean isValid = validation(productId, productName, productCategory, selectedToggle);
		if (!isValid) {
			return;
		}
		String productStatus = ((RadioButton) selectedToggle).getText();
		boolean isUnique = uniqueId(productId);
		if (!isUnique) {
			return;
		}

		boolean confirmation = alerts.ConfiramtionAlert("Info", "Are You sure you Want to Add This Product ?");
		if (!confirmation) {
			return;
		}
		// Create and add the product to the system
		Product newProduct = new Product(productId, productName, productStatus, productCategory);
		category.addProduct(newProduct);
		productDisplay.productList.add(newProduct);
		alerts.InfoAlert("Success", "Product Added Succesfully,Thanks");
		clear();
	}
	
	// Validates product inputs before adding

	public boolean validation(int id, String name, Category categoryBox, Toggle statusGroup) {
		if (id <= 0) {
			alerts.ErrorAlert("Error", "Product id Cant be zero or less");
			return false;
		}
		if (name == null || name.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Product Name Cant be Empty");
			return false;
		}
		if (!name.matches("^[a-zA-Z ]+$")) {
			alerts.ErrorAlert("Error", "Product Name must contain Only Characters");
			return false;
		}
		if (categoryBox == null) {
			alerts.ErrorAlert("Error", "Please select a Category for the Product");
			return false;
		}
		if (statusGroup == null) {
			alerts.ErrorAlert("Error", "Please select the Status of the product (Active or Inactive)");
			return false;
		}
		return true;
	}
	// Checks if product ID is unique

	public boolean uniqueId(int id) {
		for (Product product : productDisplay.productList) {
			if (product.getProductId() == id) {
				alerts.ErrorAlert("Error", "This Product ID is Already Exist, Sorry ..");
				return false;
			}
		}
		return true;
	}


	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane addProduct = new GridPane();
		addProduct.setPadding(new Insets(50));
		addProduct.setHgap(50);
		addProduct.setVgap(40);

		idLabel = new CustomLabel("Product ID: ");
		idField = new CustomTextField();
		addProduct.add(idLabel, 0, 0);
		addProduct.add(idField, 1, 0);

		nameLabel = new CustomLabel("Product Name: ");
		nameField = new CustomTextField();
		addProduct.add(nameLabel, 0, 1);
		addProduct.add(nameField, 1, 1);

		categoryLabel = new CustomLabel("Product Category:");
		categoryBox.setItems(categoryDisplay.categoryList);
		
		categoryBox.setPromptText("Select a Category:");
		addProduct.add(categoryLabel, 0, 2);
		addProduct.add(categoryBox, 1, 2);

		statusLabel = new CustomLabel("Status Product: ");
		active = new RadioButton("Active");
		inActive = new RadioButton("In Active");
		statusGroup = new ToggleGroup();
		active.setToggleGroup(statusGroup);
		active.setStyle("-fx-text-fill: #0d1b58;" + "-fx-font-size: 18px;" + "-fx-font-weight: bold;"
				+ "-fx-font-family: 'Segoe UI';");

		inActive.setToggleGroup(statusGroup);
		inActive.setStyle("-fx-text-fill: #0d1b58;" + "-fx-font-size: 18px;" + "-fx-font-weight: bold;"
				+ "-fx-font-family: 'Segoe UI';");
		HBox statusBox = new HBox(35, active, inActive);
		addProduct.add(statusLabel, 0, 3);
		addProduct.add(statusBox, 1, 3);

		HBox allButtons = new HBox(100);
		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});
		addButton = new IconButton("Add Product", "/application/icons8-add-50.png");
		addButton.setOnAction(x -> {
			addButton();
		});
		clearButton = new IconButton("Clear", "/application/icons8-clear-50.png");
		clearButton.setOnAction(x -> {
			clear();
		});

		allButtons.getChildren().addAll(backButton, addButton, clearButton);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane addScreen = new BorderPane();
		m1 = new Main();
		m1.setbackGround(addScreen);

		addScreen.setTop(menuBar);
		addScreen.setLeft(addProduct);
		addScreen.setBottom(allButtons);

		Scene scene = new Scene(addScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}
}
