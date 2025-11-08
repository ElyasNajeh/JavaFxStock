package application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button {
	// Constructor: takes button text and path to icon image
	IconButton(String text, String imagePath) {
		super(text);
		Image icon = new Image(imagePath);
		ImageView im = new ImageView(icon);
		im.setFitWidth(40);
		im.setFitHeight(40);
		this.setGraphic(im);

		this.setStyle("-fx-background-color: #0d1b58;" + "-fx-text-fill: white;" + "-fx-font-size: 22px;"
				+ "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;" + "-fx-padding: 18px 26px;"
				+ "-fx-min-width: 200px;" + "-fx-min-height: 65px;" + "-fx-border-radius: 18px;"
				+ "-fx-background-radius: 18px;" + "-fx-border-color: transparent;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.3, 0, 2);");

		this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #0d1b58;" + "-fx-text-fill: white;"
				+ "-fx-font-size: 22px;" + "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;"
				+ "-fx-padding: 18px 26px;" + "-fx-min-width: 200px;" + "-fx-min-height: 65px;"
				+ "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-border-color: white;"
				+ "-fx-border-width: 2px;" + "-fx-cursor: hand;"
				+ "-fx-effect: dropshadow(gaussian, rgba(255,255,255,0.3), 10, 0.3, 0, 2);"));

		this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #0d1b58;" + "-fx-text-fill: white;"
				+ "-fx-font-size: 22px;" + "-fx-font-family: 'Segoe UI', sans-serif;" + "-fx-font-weight: bold;"
				+ "-fx-padding: 18px 26px;" + "-fx-min-width: 200px;" + "-fx-min-height: 65px;"
				+ "-fx-border-radius: 18px;" + "-fx-background-radius: 18px;" + "-fx-border-color: transparent;"
				+ "-fx-cursor: hand;" + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.3, 0, 2);"));

		this.setOnMousePressed(e -> {
			this.setScaleX(0.98);
			this.setScaleY(0.98);
		});

		this.setOnMouseReleased(e -> {
			this.setScaleX(1);
			this.setScaleY(1);
		});
	}

}
