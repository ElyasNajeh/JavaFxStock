package application;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateCategory {
	IconButton backButton, updateButton, prevButton, nextButton;
	CustomLabel idLabel, nameLabel, descripLabel;
	CustomTextField idField, nameField;
	TextArea descripField;
	CategoryDisplay categoryDisplay = Main.categoryDisplay;
	ObservableList<Category> categoryList = categoryDisplay.categoryList;
	DoubleLinkedList linkedList = Main.linkedList;
	AddCategory toValidation = new AddCategory();
	Alerts alerts = new Alerts();
	Main m1;
	int index;

	// Constructor to receive which category to update

	public UpdateCategory(int index, ObservableList<Category> categoryList) {
		this.index = index;
		this.categoryList = categoryList;
	}

	// Load data of the category at the current index into the input fields
	public void loadIndexDetails(CustomTextField idField, CustomTextField nameField, TextArea descripField) {
		if (index >= 0 && index < linkedList.getSize()) {
			Category infoCategory = categoryList.get(index);
			idField.setText(infoCategory.getCategoryId() + "");
			nameField.setText(infoCategory.getCategoryName());
			descripField.setText(infoCategory.getCategoryDescription());
		}
		// Disable navigation buttons when at the beginning or end of the list

		prevButton.setDisable(index <= 0);
		nextButton.setDisable(index >= linkedList.getSize() - 1);
	}

	// This method is called when the update button is clicked
	public void updateButton() {
		Category infoCategory = categoryList.get(index);
		int newCategoryId = Integer.parseInt(idField.getText());
		String newCategoryName = nameField.getText();
		String newCategorydescrip = descripField.getText();

		boolean isValid = toValidation.validation(newCategoryId, newCategoryName, newCategorydescrip);
		if (!isValid) {
			return;
		}
		boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
				"Are you sure you need to Update data for This Category ?");
		if (!confirmation) {
			return;
		}
		// Apply the updates

		infoCategory.setCategoryId(newCategoryId);
		infoCategory.setCategoryName(newCategoryName);
		infoCategory.setCategoryDescription(newCategorydescrip);

		categoryList.set(index, infoCategory);
		alerts.InfoAlert("Success", "Category Data has been Updated Successfully");
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane updateCategory = new GridPane();
		updateCategory.setPadding(new Insets(30));
		updateCategory.setHgap(50);
		updateCategory.setVgap(40);

		idLabel = new CustomLabel("Category ID: ");
		idField = new CustomTextField();
		idField.setDisable(true);
		updateCategory.add(idLabel, 0, 0);
		updateCategory.add(idField, 1, 0);

		nameLabel = new CustomLabel("Category Name: ");
		nameField = new CustomTextField();
		updateCategory.add(nameLabel, 0, 1);
		updateCategory.add(nameField, 1, 1);

		descripLabel = new CustomLabel("Category Description: ");
		descripField = new TextArea();
		descripField.setPrefWidth(400);
		descripField.setPrefHeight(180);
		descripField.setWrapText(true);
		descripField.setStyle("-fx-background-color: white;" + "-fx-border-color: #0d1b58;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 16px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #0d1b58;"
				+ "-fx-padding: 10;");
		updateCategory.add(descripLabel, 0, 2);
		updateCategory.add(descripField, 1, 2);

		VBox allButtons = new VBox(20);
		HBox someButtons1 = new HBox(30);
		HBox someButtons2 = new HBox(30);

		// Go to previous category

		prevButton = new IconButton("Previous", "/application/icons8-previous-48.png");
		prevButton.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadIndexDetails(idField, nameField, descripField);
			}

		});
		// Go to Next category

		nextButton = new IconButton("Next", "/application/icons8-next-50.png");
		nextButton.setOnAction(x -> {
			if (index < linkedList.getSize() - 1) {
				index++;
				loadIndexDetails(idField, nameField, descripField);
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

		loadIndexDetails(idField, nameField, descripField);

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
		updateScreen.setLeft(updateCategory);
		updateScreen.setBottom(allButtons);

		Scene scene = new Scene(updateScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}
}
