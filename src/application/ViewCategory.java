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

public class ViewCategory {
	IconButton backButton, prevButton, nextButton;
	CustomLabel idLabel, nameLabel, descripLabel;
	CustomTextField idField, nameField;
	TextArea descripField;
	CategoryDisplay categoryDisplay = Main.categoryDisplay;
	ObservableList<Category> categoryList = categoryDisplay.categoryList;
	int index;
	DoubleLinkedList linkedList = Main.linkedList;
	Main m1;

	public ViewCategory(int index, ObservableList<Category> categoryList) {
		this.index = index;
		this.categoryList = categoryList;
	}

	public void loadIndexDetails(CustomTextField idField, CustomTextField nameField, TextArea descripField) {
		if (index >= 0 && index < linkedList.getSize()) {
			Category infoCategory = categoryList.get(index);
			idField.setText(infoCategory.getCategoryId() + "");
			nameField.setText(infoCategory.getCategoryName());
			descripField.setText(infoCategory.getCategoryDescription());
		}
		prevButton.setDisable(index <= 0);
		nextButton.setDisable(index >= linkedList.getSize() - 1);
	}

	public void Display() {
		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		GridPane viewCategory = new GridPane();
		viewCategory.setPadding(new Insets(30));
		viewCategory.setHgap(50);
		viewCategory.setVgap(40);

		idLabel = new CustomLabel("Category ID: ");
		idField = new CustomTextField();
		idField.setDisable(true);
		viewCategory.add(idLabel, 0, 0);
		viewCategory.add(idField, 1, 0);

		nameLabel = new CustomLabel("Category Name: ");
		nameField = new CustomTextField();
		nameField.setDisable(true);
		viewCategory.add(nameLabel, 0, 1);
		viewCategory.add(nameField, 1, 1);

		descripLabel = new CustomLabel("Category Description: ");
		descripField = new TextArea();
		descripField.setDisable(true);
		descripField.setPrefWidth(400);
		descripField.setPrefHeight(180);
		descripField.setWrapText(true);
		descripField.setStyle("-fx-background-color: white;" + "-fx-border-color: #0d1b58;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 16px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #0d1b58;"
				+ "-fx-padding: 10;");
		viewCategory.add(descripLabel, 0, 2);
		viewCategory.add(descripField, 1, 2);

		VBox allButtons = new VBox(20);
		HBox someButtons1 = new HBox(30);
		HBox someButtons2 = new HBox(30);

		prevButton = new IconButton("Previous", "/application/icons8-previous-48.png");
		prevButton.setOnAction(x -> {
			if (index > 0) {
				index--;
				loadIndexDetails(idField, nameField, descripField);
			}

		});

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

		loadIndexDetails(idField, nameField, descripField);

		someButtons1.getChildren().addAll(prevButton, nextButton);
		someButtons1.setAlignment(Pos.CENTER);

		someButtons2.getChildren().addAll(backButton);
		someButtons2.setAlignment(Pos.CENTER);

		allButtons.getChildren().addAll(someButtons1, someButtons2);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane viewScreen = new BorderPane();
		m1 = new Main();
		m1.setbackGround(viewScreen);

		viewScreen.setTop(menuBar);
		viewScreen.setLeft(viewCategory);
		viewScreen.setBottom(allButtons);

		Scene scene = new Scene(viewScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();
	}

}
