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

public class ShipmentDisplay {
	Category category = Main.category;
	Queue queue = category.getShipmentProduct();
	Stack stack = category.getUndo();
	IconButton approveButton, cancelButton, backButton, undoButton, addButton, redoButton;
	boolean columnsAdded = false;
	TableView<Shipment> shipmentTable = new TableView<>();
	ObservableList<Shipment> shipmentList = FXCollections.observableArrayList();
	Alerts alerts = new Alerts();

	public void Display() {

		Stage stage = new Stage();
		MenuBar menuBar = Main.createmenuBar(stage);

		// Add table columns only once

		if (!columnsAdded) {
			TableColumn<Shipment, Integer> idCol = new TableColumn<>("Shipment ID");
			idCol.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));

			TableColumn<Shipment, Integer> productCol = new TableColumn<>("Product ID");
			productCol.setCellValueFactory(new PropertyValueFactory<>("product"));

			TableColumn<Shipment, Integer> quantityCol = new TableColumn<>("Shipment Quantity");
			quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

			TableColumn<Shipment, String> dateCol = new TableColumn<>("Shipemnt Date");
			dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

			shipmentTable.getColumns().addAll(idCol, productCol, quantityCol, dateCol);
			shipmentTable.setStyle("-fx-background-color: white;" + "-fx-border-color: transparent;"
					+ "-fx-table-cell-border-color: transparent;" + "-fx-font-family: 'Segoe UI';"
					+ "-fx-font-size: 14px;");
			shipmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			shipmentTable.setItems(shipmentList);
			columnsAdded = true;

		}
		// Enable approve and cancel buttons when a shipment is selected
		shipmentTable.setOnMouseClicked(e -> {
			if (shipmentTable.getSelectionModel().getSelectedIndex() >= 0) {
				approveButton.setDisable(false);
				cancelButton.setDisable(false);
			}
		});
		VBox allButtons = new VBox(30);
		HBox someButtons1 = new HBox(30);
		HBox someButtons2 = new HBox(30);

		backButton = new IconButton("Back", "/application/icons8-back-50.png");
		backButton.setOnAction(x -> {
			stage.close();
		});

		addButton = new IconButton("Add Shipment", "/application/icons8-add-50.png");
		addButton.setOnAction(x -> {
			AddShipment addShipment = new AddShipment();
			addShipment.Display();
		});
		// Undo last shipment action (approve or cancel)
		undoButton = new IconButton("Undo", "/application/icons8-undo-48.png");
		undoButton.setOnAction(x -> {
			if (stack.isEmpty()) {
				alerts.ErrorAlert("Error", "No Action to Undo");
				return;
			}
			boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
					"Are you sure you want to Undo the last step you took ?");
			if (!confirmation) {
				return;
			}
			Shipment undo = (Shipment) stack.pop();
			// Re-add to queue
			category.addShipment(undo);
			shipmentList.add(undo);
			Product product = undo.getProduct();
			product.setQuantity(product.getQuantity() - undo.getQuantity());
			category.addToRedo(undo);
		});

		// Redo the last undone shipment
		redoButton = new IconButton("Redo", "/application/icons8-redo-48.png");
		redoButton.setDisable(false);
		redoButton.setOnAction(x -> {
			if (category.getRedo().isEmpty()) {
				alerts.ErrorAlert("Error", "No Action to Redo");
				return;
			}
			boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
					"Are you sure you want to Redo the last step you took ?");
			if (!confirmation) {
				return;
			}
			Shipment redo = (Shipment) category.getRedo().pop();

			Product product = redo.getProduct();
			int updatedQuantity = product.getQuantity() + redo.getQuantity();
			product.setQuantity(updatedQuantity);
			category.addShipment(redo);
			shipmentList.remove(redo);
			category.addToUndo(redo);
		});

		approveButton = new IconButton("Approve Shipment", "/application/icons8-accept-50.png");
		approveButton.setDisable(true);
		approveButton.setOnAction(x -> {
			boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
					"Are you sure you need to Approve this Shipment ?");
			if (!confirmation) {
				return;
			}

			Shipment undo = (Shipment) queue.deQueue();

			// Update product quantity
			Product productOfFrontShipment = undo.getProduct();
			int newQuantity = undo.getQuantity() + productOfFrontShipment.getQuantity();
			productOfFrontShipment.setQuantity(newQuantity);

			shipmentList.remove(undo);
			category.addToUndo(undo);
			alerts.InfoAlert("Success", "Shipment Approved and Product quantity updated Successfully, Thanks");

			undo.setActionType("Add Shipment");
			LogExport logExport = new LogExport(undo);
			logExport.Display();

		});

		cancelButton = new IconButton("Cancel Shipment", "/application/icons8-cancel-50.png");
		cancelButton.setDisable(true);
		cancelButton.setOnAction(x -> {
			boolean confirmation = alerts.ConfiramtionAlert("Confirmation",
					"Are you sure you need to Cancel this Shipment ?");
			if (!confirmation) {
				return;
			}
			Shipment undo = (Shipment) queue.deQueue();
			category.addToUndo(undo);
			shipmentList.remove(undo);
			category.addShipmentToCancelCursor(undo);
			alerts.InfoAlert("Success", "Shipment Canceled, Thanks");

			undo.setActionType("Cancel Shipment");
			LogExport logExport = new LogExport(undo);
			logExport.Display();
		});

		someButtons1.getChildren().addAll(backButton, approveButton, cancelButton);
		someButtons1.setAlignment(Pos.CENTER);

		someButtons2.getChildren().addAll(undoButton, addButton, redoButton);
		someButtons2.setAlignment(Pos.CENTER);

		allButtons.getChildren().addAll(someButtons2, someButtons1);
		allButtons.setAlignment(Pos.CENTER);

		BorderPane shipmentScreen = new BorderPane();
		shipmentScreen.setTop(menuBar);
		shipmentScreen.setCenter(shipmentTable);
		shipmentScreen.setBottom(allButtons);

		Scene scene = new Scene(shipmentScreen, 400, 300);
		stage.setScene(scene);
		stage.setTitle("Smart Warehouse");
		stage.setMaximized(true);
		stage.getIcons().add(new Image("/application/icons8-warehouse-64.png"));
		stage.show();

	}
}
