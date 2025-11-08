package application;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {

	// Constructor that applies custom styling to the TextField
	CustomTextField() {
		this.setStyle("-fx-background-color: white;" + "-fx-border-color: #0d1b58;" + "-fx-border-width: 2px;"
				+ "-fx-border-radius: 10px;" + "-fx-background-radius: 10px;" + "-fx-font-size: 18px;"
				+ "-fx-font-family: 'Segoe UI';" + "-fx-font-weight: bold;" + "-fx-text-fill: #0d1b58;"
				+ "-fx-padding: 10 12 10 18;");

	}
}
