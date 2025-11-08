package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProduct {
	IconButton backButton, updateButton, prevButton, nextButton;
	CustomLabel idLabel, nameLabel, categoryLabel, statusLabel;
	CustomTextField idField, nameField;
	ComboBox<Category> categoryBox;
	ToggleGroup statusGroup;
	RadioButton active, inActive;
	ProductDisplay productDisplay = Main.productDisplay;
	CategoryDisplay categoryDisplay = Main.categoryDisplay;
	ObservableList<Product> productList = productDisplay.productList;
	int index;
	Alerts alerts = new Alerts();
	Main m1;
	AddProduct addProduct = new AddProduct();
	Cursor cursor;
	int listIndex;

	// Constructor receives the index of the product and the list

	public UpdateProduct(ObservableList<Product> productList, int index) {
		this.productList = productList;
		this.index = index;
	}
	// Loads the current products details

	public void loadIndexDetails(CustomTextField idField, CustomTextField nameField, ComboBox<Category> categoryBox,
			ToggleGroup statusGroup) {

		Product currentProduct = productList.get(index);
		Category cat = currentProduct.getCategory();
		cursor = cat.getAcceptProduct();
		listIndex = cat.getListProduct();
		if (index >= 0 && index < productList.size()) {

			idField.setText(currentProduct.getProductId() + "");
			nameField.setText(currentProduct.getProductName());
			categoryBox.setValue(currentProduct.getCategory());
			String status = currentProduct.getStatus();
			if (status.equalsIgnoreCase("Active")) {
				statusGroup.selectToggle(active);
			} else if (status.equalsIgnoreCase("In Active")) {
				statusGroup.selectToggle(inActive);
			}

		}
		prevButton.setDisable(index <= 0);
		nextButton.setDisable(index >= productList.size() - 1);
	}

	public void updateButton() {

		Product currentProduct = productList.get(index);
		String Id = idField.getText();
		String newProductName = nameField.getText();

		Toggle selectedToggle = statusGroup.getSelectedToggle();
		String newStatus = ((RadioButton) selectedToggle).getText();

		Category newCategoryBox = categoryBox.getValue();

		if (Id == null || Id.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Product ID Cant be Empty");
			return;
		}
		if (!Id.matches("\\d+")) {
			alerts.ErrorAlert("Error", "Product ID Can be Only Numbers");
			return;

		}

		int newProductId = Integer.parseInt(Id);

		boolean isValid = validation(newProductId, newProductName);
		if (!isValid) {
			return;
		}

		boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
				"Are you sure you nedd to Update data for This Product ?");
		if (!confirmation) {
			return;
		}
		// If the category remains the same, update values directly
		if (currentProduct.getCategory().getCategoryId() == newCategoryBox.getCategoryId()) {

			currentProduct.setProductId(newProductId);
			currentProduct.setProductName(newProductName);
			currentProduct.setStatus(newStatus);
			currentProduct.setCategory(newCategoryBox);

			productList.set(index, currentProduct);
			alerts.InfoAlert("Success", "Product Data has been Updated Successfully");
		} else {
			// If the category is changed, remove from old category and add to new

			Category oldCategory = currentProduct.getCategory();
			oldCategory.removeProduct(currentProduct);

			currentProduct.setProductId(newProductId);
			currentProduct.setProductName(newProductName);
			currentProduct.setStatus(newStatus);
			currentProduct.setCategory(newCategoryBox);

			newCategoryBox.addProduct(currentProduct);
			productList.set(index, currentProduct);

			alerts.InfoAlert("Success", "Product Data has been Updated Successfully");
		}

	}
	// Validates the new ID and name

	public boolean validation(int id, String name) {
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
		return true;
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane updateProduct = new GridPane();
		updateProduct.setPadding(new Insets(50));
		updateProduct.setHgap(50);
		updateProduct.setVgap(40);

		idLabel = new CustomLabel("Product ID: ");
		idField = new CustomTextField();
		idField.setDisable(true);
		updateProduct.add(idLabel, 0, 0);
		updateProduct.add(idField, 1, 0);

		nameLabel = new CustomLabel("Product Name: ");
		nameField = new CustomTextField();
		updateProduct.add(nameLabel, 0, 1);
		updateProduct.add(nameField, 1, 1);

		categoryLabel = new CustomLabel("Product Category:");
		categoryBox = new ComboBox<>();
		categoryBox.setItems(FXCollections.observableArrayList(categoryDisplay.categoryList));
		categoryBox.setPromptText("Select a Category:");
		updateProduct.add(categoryLabel, 0, 2);
		updateProduct.add(categoryBox, 1, 2);

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
		updateProduct.add(statusLabel, 0, 3);
		updateProduct.add(statusBox, 1, 3);

		VBox allButtons = new VBox(20);
		HBox someButtons1 = new HBox(30);
		HBox someButtons2 = new HBox(30);

		prevButton = new IconButton("Previous", "/application/icons8-previous-48.png");
		prevButton.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadIndexDetails(idField, nameField, categoryBox, statusGroup);
			}
		});

		nextButton = new IconButton("Next", "/application/icons8-next-50.png");
		nextButton.setOnAction(x -> {
			if (index < productList.size() - 1) {
				index++;
				loadIndexDetails(idField, nameField, categoryBox, statusGroup);
			}
		});

		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});

		updateButton = new IconButton("Update", "/application/icons8-update-64.png");
		updateButton.setOnAction(x -> {

			updateButton();
		});

		loadIndexDetails(idField, nameField, categoryBox, statusGroup);

		someButtons1.getChildren().addAll(prevButton, nextButton);
		someButtons1.setAlignment(Pos.CENTER);

		someButtons2.getChildren().addAll(backButton, updateButton);
		someButtons2.setAlignment(Pos.CENTER);

		allButtons.getChildren().addAll(someButtons1, someButtons2);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane updateScreen = new BorderPane();
		m1 = new Main();
		m1.setbackGround(updateScreen);

		updateScreen.setTop(menuBar);
		updateScreen.setLeft(updateProduct);
		updateScreen.setBottom(allButtons);

		Scene scene = new Scene(updateScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}
}
