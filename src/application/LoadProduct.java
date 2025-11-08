package application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadProduct {
	Alerts alerts = new Alerts();
	Category category = Main.category;
	ProductDisplay productDisplay = Main.productDisplay;
	CategoryDisplay categoryTable = Main.categoryDisplay;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Product File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxStock"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			alerts.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			// Clear any existing data before loading new Products
			productDisplay.productList.clear();
			// Read the file line by line
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				try {
					// Parse each line
					int productId = Integer.parseInt(data[0].trim());
					String productName = data[1].trim();
					int categoryId = Integer.parseInt(data[2].trim());
					String status = data[3].trim();

					Category matchedCategory = null;
					for (int i = 0; i < categoryTable.categoryList.size(); i++) {
						Category c = categoryTable.categoryList.get(i);
						if (c.getCategoryId() == categoryId) {
							matchedCategory = c;
							break;
						}
					}

					// Search for category by ID from the existing list
					if (matchedCategory != null) {
						Product loadedProduct = new Product(productId, productName, status, matchedCategory);
						category.addProduct(loadedProduct);
						productDisplay.productList.add(loadedProduct);
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
