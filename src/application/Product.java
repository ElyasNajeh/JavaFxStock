package application;

public class Product {
	private int productId;
	private int quantity;
	private String productName;
	private String status;
	private Category category;

	public Product() {
	}

	public Product(int productId, String productName, String status, Category category) {
		this.productId = productId;
		this.productName = productName;
		this.status = status;
		this.category = category;
		quantity = 0;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return productName + ", " + category;
	}

}
