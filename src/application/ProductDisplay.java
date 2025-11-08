package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductDisplay {
	IconButton searchButton, refreshButton, backButton, updateButton, removeButton;
	ComboBox<String> sortBox;
	CustomTextField searchField;
	boolean columnsAdded = false;
	TableView<Product> productTable = new TableView<>();
	ObservableList<Product> productList = FXCollections.observableArrayList();
	Alerts alerts = new Alerts();
	AddProduct addProduct;
	UpdateProduct updateProduct;
	Category category = Main.category;

	public void Display() {

		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);
		
		// Add table columns once only

		if (!columnsAdded) {
			TableColumn<Product, Integer> idCol = new TableColumn<>("Product ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("productId"));

			TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
			nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

			TableColumn<Product, String> categoryCol = new TableColumn<>("Category Name");
			categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

			TableColumn<Product, String> statusCol = new TableColumn<>("Product Status");
			statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

			TableColumn<Product, Integer> quantityCol = new TableColumn<>("Product Quantity");
			quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

			productTable.getColumns().addAll(idCol, nameCol, categoryCol, statusCol, quantityCol);
			productTable.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
					+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
					+ "-fx-font-size: 14px;");
			productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			productTable.setItems(productList);
			columnsAdded = true;

		}
		// Enable update/remove/sort buttons once a row is selected
		productTable.setOnMouseClicked(e -> {
			if (productTable.getSelectionModel().getSelectedIndex() >= 0) {
				updateButton.setDisable(false);
				removeButton.setDisable(false);
				sortBox.setDisable(false);
			}
		});
		VBox allButtons = new VBox(30);
		HBox someButtons = new HBox(30);
		HBox searchButtons = new HBox(30);

		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});

		updateButton = new IconButton("Update Product", "/application/icons8-update-64.png");
		updateButton.setDisable(true);
		updateButton.setOnAction(x -> {
			int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
			updateProduct = new UpdateProduct(productList, selectedIndex);
			updateProduct.Display();
		});

		removeButton = new IconButton("Remove Product", "/application/icons8-remove-50.png");
		removeButton.setDisable(true);
		removeButton.setOnAction(x -> {
			int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
			Product removeProduct = productList.get(selectedIndex);
			if (selectedIndex >= 0) {
				boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
						"Are you sure you need to Delete this Product ? ");
				if (!confirmation) {
					return;
				}
				category.removeProduct(removeProduct);
				productList.remove(selectedIndex);
				alerts.InfoAlert("Success", "Product has been Deleted Successfully");
			}
		});

		refreshButton = new IconButton("Refresh", "/application/icons8-refresh-50.png");
		refreshButton.setOnAction(x -> {
			if (productList == null || productList.isEmpty()) {
				alerts.ErrorAlert("Error", "No Data in Table to Refresh,Sorry..");
				return;
			}
			productTable.setItems(productList);
		});

		searchButton = new IconButton("Search", "/application/icons8-search-50.png");
		searchButton.setOnAction(x -> {
			Cursor cursor = category.getAcceptProduct();
			int listIndex = category.getListProduct();
			ObservableList<Product> resultOfSearch = cursor.searchProduct(searchField.getText(), listIndex);
			if (resultOfSearch.isEmpty()) {
				productTable.setItems(productList);
			} else {
				productTable.setItems(resultOfSearch);
			}
		});
		searchField = new CustomTextField();
		searchField.setPromptText("Search here ..");
		
		// Sort box 

		sortBox = new ComboBox<>();
		sortBox.getItems().addAll("Name A --> Z : ", "Category : ", "Status : ");
		sortBox.setPromptText("Sort By :");
		sortBox.setStyle("-fx-font-size: 14px;" + "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;"
				+ "-fx-border-color: #0d1b58;" + "-fx-background-radius: 8px;" + "-fx-border-radius: 8px;"
				+ "-fx-padding: 6 12;" + "-fx-background-color: white;" + "-fx-text-fill: #0d1b58;");
		sortBox.setOnAction(x -> {
			String selected = sortBox.getValue();
			Cursor cursor = category.getAcceptProduct();
			int listIndex = category.getListProduct();
			if (selected.equals("Name A --> Z : ")) {
				cursor.sortProductsByName(listIndex);
				productTable.setItems(cursor.List(listIndex));
			} else if (selected.equals("Category : ")) {
				cursor.sortProductByCategory(listIndex);
				productTable.setItems(cursor.List(listIndex));
			} else if (selected.equals("Status : ")) {
				cursor.sortProductByStatus(listIndex);
				productTable.setItems(cursor.List(listIndex));
			}
		});
		sortBox.setDisable(true);

		searchButtons.getChildren().addAll(refreshButton, searchField, searchButton, sortBox);
		searchButtons.setAlignment(Pos.CENTER);

		someButtons.getChildren().addAll(backButton, updateButton, removeButton);
		someButtons.setAlignment(Pos.CENTER);

		allButtons.getChildren().addAll(searchButtons, someButtons);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane productScreen = new BorderPane();
		productScreen.setTop(menuBar);
		productScreen.setCenter(productTable);
		productScreen.setBottom(allButtons);

		Scene scene = new Scene(productScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();

	}

}
