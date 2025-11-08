package application;

public class Shipment {
	private int shipmentId;
	private int quantity;
	private String date;
	private Product product;
	private String actionType;

	public Shipment() {
	}

	public Shipment(int shipmentId, int quantity, String date, Product product) {
		this.shipmentId = shipmentId;
		this.quantity = quantity;
		this.date = date;
		this.product = product;
	}

	public int getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return "Shipment [shipmentId=" + shipmentId + ", quantity=" + quantity + ", date=" + date + ", product="
				+ product + "]";
	}

}
