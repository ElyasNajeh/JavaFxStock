package application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadShipment {
	Alerts alerts = new Alerts();
	Category category = Main.category;
	Product product = Main.product;
	ProductDisplay productDisplay = Main.productDisplay;
	CategoryDisplay categoryTable = Main.categoryDisplay;
	ShipmentDisplay shipmentDisplay = Main.shipmentDisplay;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Shipment File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxStock"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			alerts.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			shipmentDisplay.shipmentList.clear();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				try {
					// Parse line values
					int shipmentId = Integer.parseInt(data[0].trim());
					int productId = Integer.parseInt(data[1].trim());
					int amount = Integer.parseInt(data[2].trim());
					String date = data[3].trim();
					
					// Find the product matching the productId
					Product matchedProduct = null;
					for (int i = 0; i < productDisplay.productList.size(); i++) {
						Product p = productDisplay.productList.get(i);
						if (p.getProductId() == productId) {
							matchedProduct = p;
							break;
						}
					}
					// If the product exists, create and add the shipment

					if (matchedProduct != null) {
						Shipment loadedShipment = new Shipment(shipmentId, amount, date, matchedProduct);
						category.addShipment(loadedShipment);
						shipmentDisplay.shipmentList.add(loadedShipment);
					}

				} catch (Exception e) {
					alerts.ErrorAlert("Error", "Cannot read this line:" + line);
				}
			}

			alerts.InfoAlert("Success", "File read Successfully!");

		} catch (IOException e) {
			alerts.ErrorAlert("Error", "Error while reading the file.");
		}
	}
}
