package application;

public class Category {
	private int categoryId;
	private String categoryName;
	private String categoryDescription;
	private Queue shipmentProduct = new Queue(); // of shipment
	private Cursor acceptProduct = new Cursor(100); // of product
	private Cursor cancelShipment = new Cursor(100); // of shipment
	private Stack undo = new Stack(); // of shipment
	private Stack redo = new Stack(); // of shipment
	public int ListProduct = acceptProduct.createList();
	public int ListCancelShipment = cancelShipment.createList();

	public Category() {

	}
	
	// Constructor with all details

	public Category(int categoryId, String categoryName, String categoryDescription) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;

	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Queue getShipmentProduct() {
		return shipmentProduct;
	}

	public void setShipmentProduct(Queue shipmentProduct) {
		this.shipmentProduct = shipmentProduct;
	}

	public Cursor getAcceptProduct() {
		return acceptProduct;
	}

	public void setAcceptProduct(Cursor acceptProduct) {
		this.acceptProduct = acceptProduct;
	}

	public Cursor getCancelShipment() {
		return cancelShipment;
	}

	public void setCancelShipment(Cursor cancelShipment) {
		this.cancelShipment = cancelShipment;
	}

	public Stack getUndo() {
		return undo;
	}

	public void setUndo(Stack undo) {
		this.undo = undo;
	}

	public Stack getRedo() {
		return redo;
	}

	public void setRedo(Stack redo) {
		this.redo = redo;
	}

	public int getListProduct() {
		return ListProduct;
	}

	public void setListProduct(int listProduct) {
		ListProduct = listProduct;
	}
	// Adds a product to the accepted product list
	public void addProduct(Product product) {
		this.acceptProduct.insertAtBack(product, ListProduct);
	}
	
	// Removes a product from the accepted product list
	public void removeProduct(Product product) {
		this.acceptProduct.remove(product, ListProduct);
	}
	
	// Adds a shipment to the queue (pending shipments)
	public void addShipment(Shipment shipment) {
		this.shipmentProduct.enQueue(shipment);
	}
	// Adds a canceled shipment to the cancel shipment cursor list

	public void addShipmentToCancelCursor(Shipment shipment) {
		this.cancelShipment.insertAtBack(shipment, ListCancelShipment);
	}
	
	// Adds a shipment action to the undo stack
	public void addToUndo(Shipment shipment) {
		this.undo.push(shipment);
	}
	
	// Adds a shipment action to the redo stack
	public void addToRedo(Shipment shipment) {
		this.redo.push(shipment);
	}

	@Override
	public String toString() {
		return categoryName;
	}

}
