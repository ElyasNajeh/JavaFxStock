package application;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	// This method shows a confirmation dialog and returns true if user clicks OK
	public boolean ConfiramtionAlert(String title, String messege) {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setTitle(title);
		a.setContentText(messege);
		Optional<ButtonType> res = a.showAndWait();
		return res.isPresent() && res.get() == ButtonType.OK;
	}
	// This method shows an error alert with the given title and message
	public void ErrorAlert(String title, String messege) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle(title);
		a.setContentText(messege);
		a.showAndWait();
	}
	// This method shows a simple information alert 

	public void InfoAlert(String title, String messege) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setTitle(title);
		a.setContentText(messege);
		a.showAndWait();

	}
}
