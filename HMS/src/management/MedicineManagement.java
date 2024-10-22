package management;

public class MedicineManagement {
	private String medicineName;
	private int stock;
	private int lowStockAlert;
	
	public MedicineManagement(String medicineName, int stock, int lowStockAlert) {
		this.medicineName = medicineName;
		this.stock = stock;
		this.lowStockAlert = lowStockAlert;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public int getStock() {
		return stock;
	}

	public int getLowStockAlert() {
		return lowStockAlert;
	}
	
	public void minusStock() {
		this.stock--;  // Reduce by 1
	}
	
	public void minusStock(int quantity) {
		this.stock -= quantity;  // Reduce by specified quantity
	}
	
	public void addStock() {
		this.stock++;  // Increment by 1 (fixed the decrement error)
	}
	
	public void addStock(int quantity) {
		this.stock += quantity;  // Increment by specified quantity
	}
	
	public void setLowStockAlert(int lowStockAlert) {
		this.lowStockAlert = lowStockAlert;
	}

	public String toString() {
		return getMedicineName() + ": " + getStock(); 
	}

}
