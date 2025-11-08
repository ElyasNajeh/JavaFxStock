package application;

import javafx.scene.control.Label;

public class CustomLabel extends Label {
	
	// Constructor that creates a styled label with custom
	CustomLabel(String text) {
		super(text);
		this.setStyle("-fx-text-fill: white;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;"
				+ "-fx-font-family: 'Segoe UI Semibold';" + "-fx-background-color: #0d1b58;" + "-fx-padding: 6 18;"
				+ "-fx-background-radius: 12px;" + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 4, 0, 0, 1);");
	}
}
