package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddCategory {

	IconButton backButton, addButton, clearButton;
	CustomLabel idLabel, nameLabel, descripLabel;
	CustomTextField idField, nameField;
	TextArea descripField;
	Alerts alerts = new Alerts () ;
	Main m1;
	DoubleLinkedList linkedList = Main.linkedList;
	CategoryDisplay categoryDisplay = Main.categoryDisplay;
	
	// Clear all input fields
	public void clear() {
		idField.clear();
		nameField.clear();
		descripField.clear();
	}
	
	// Called when the user clicks the Add button
	public void addButton() {
		String Id = idField.getText();
		String categoryName = nameField.getText();
		String categoryDescrip = descripField.getText();
		if (Id == null || Id.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Category ID Cant be Empty");
			return;
		}
		if (!Id.matches("\\d+")) {
			alerts.ErrorAlert("Error", "Category ID Can be Only Numbers");
			return;

		}
		int categoryId = Integer.parseInt(Id);

		boolean isValid = validation(categoryId, categoryName, categoryDescrip);
		if (!isValid) {
			return;
		}

		boolean isUnique = uniqueId(categoryId);
		if (!isUnique) {
			return;
		}

		boolean confirmation = alerts.ConfiramtionAlert("Info", "Are You sure you Want to Add This Category ?");
		if (!confirmation) {
			return;
		}
		
		// If everything is good, create the category and add it to the list
		Category newCategory = new Category(categoryId, categoryName, categoryDescrip);
		linkedList.addLast(newCategory);
		categoryDisplay.categoryList.add(newCategory);
		alerts.InfoAlert("Success", "Category Added Succesfully,Thanks");
		clear();

	}

	// Validates the inputs before creating the category
	public boolean validation(int id, String name, String description) {
		if (id <= 0) {
			alerts.ErrorAlert("Error", "Category id Cant be zero or less");
			return false;
		}
		if (name == null || name.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Category Name Cant be Empty");
			return false;
		}
		if (!name.matches("^[a-zA-Z ]+$")) {
			alerts.ErrorAlert("Error", "Category Name must contain Only Characters");
			return false;
		}
		if (description == null || description.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Category Description Cant be Empty");
			return false;
		}
		if (!description.matches("[a-zA-Z0-9\\s.,!?()'-]+")) {
			alerts.ErrorAlert("Error", "Category Description must contain Only Letters and Numbers");
			return false;
		}

		return true;
	}
	
	// Checks if the ID is already used by another category

	public boolean uniqueId(int id) {
		for (Category category : categoryDisplay.categoryList) {
			if (category.getCategoryId() == id) {
				alerts.ErrorAlert("Error", "This Category ID is Already Exist, Sorry ..");
				return false;
			}
		}
		return true;
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane addCategory = new GridPane();
		addCategory.setPadding(new Insets(30));
		addCategory.setHgap(50);
		addCategory.setVgap(40);

		idLabel = new CustomLabel("Category ID: ");
		idField = new CustomTextField();
		addCategory.add(idLabel, 0, 0);
		addCategory.add(idField, 1, 0);

		nameLabel = new CustomLabel("Category Name: ");
		nameField = new CustomTextField();
		addCategory.add(nameLabel, 0, 1);
		addCategory.add(nameField, 1, 1);

		descripLabel = new CustomLabel("Category Description: ");
		descripField = new TextArea();
		descripField.setPrefWidth(400);
		descripField.setPrefHeight(180);
		descripField.setWrapText(true);
		descripField.setStyle("-fx-background-color: white;" + "-fx-border-color: #0d1b58;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 16px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #0d1b58;"
				+ "-fx-padding: 10;");
		addCategory.add(descripLabel, 0, 2);
		addCategory.add(descripField, 1, 2);

		HBox allButtons = new HBox(100);
		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});
		addButton = new IconButton("Add Category", "/application/icons8-add-50.png");
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
		addScreen.setLeft(addCategory);
		addScreen.setBottom(allButtons);

		Scene scene = new Scene(addScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}
}
