package application;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadCategory {
	Alerts alerts = new Alerts();
	DoubleLinkedList category = Main.linkedList;
	CategoryDisplay categoryTable = Main.categoryDisplay;

	public void Display() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Category File");
		fc.setInitialDirectory(new File("C:\\Users\\HP\\eclipse-workspace\\FxStock"));
		Stage stage = new Stage();
		File f = fc.showOpenDialog(stage);
		if (f == null) {
			alerts.ErrorAlert("Error", "No file selected. Please select a file.");
			return;
		}

		try (Scanner scanner = new Scanner(f)) {

			// Clear any existing data before loading new categories
			category.clear();
			categoryTable.categoryList.clear();

			// Read the file line by line
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] data = line.split(",");

				try {
					// Parse each lin
					int categoryId = Integer.parseInt(data[0].trim());
					String categoryName = data[1].trim();
					String categoryDescription = data[2].trim();

					Category loadedCategory = new Category(categoryId, categoryName, categoryDescription);
					category.addLast(loadedCategory);
					categoryTable.categoryList.add(loadedCategory);

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
