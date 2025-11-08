package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogExport {
	Alerts a = new Alerts();
	Shipment shipment;
	
	// Constructor: takes a shipment to be logged
	public LogExport(Shipment shipment) {
		this.shipment = shipment;
	}

	public void Display() {
		File file = new File("C:\\Users\\HP\\eclipse-workspace\\FxStock\\log.txt");
		try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
			String action = shipment.getActionType();

			if (shipment == null || shipment.getActionType() == null) {
				return;
			}

			String sign = action.equalsIgnoreCase("Cancel Shipment") ? "-" : "+";

			String logLine = shipment.getDate() + " | " + action + " | SHP" + shipment.getShipmentId() + " | P"
					+ shipment.getProduct().getProductId() + " | " + sign + shipment.getQuantity();


			// Write to the file
			writer.println(logLine);

		} catch (IOException e) {
			System.out.println("Error writing to log: " + e.getMessage());
		}
	}
}
