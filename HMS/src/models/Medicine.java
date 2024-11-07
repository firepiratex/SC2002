package models;
/**
 * Represents a medicine in the inventory, including its name, current stock,
 * and the low stock alert threshold.
 */
public class Medicine {

    private final String medicineName;
    private int stock;
    private int lowStockAlert;
    /**
     * Constructs a Medicine object with the specified name, stock, and low stock alert level.
     *
     * @param medicineName  the name of the medicine
     * @param stock         the current stock level of the medicine
     * @param lowStockAlert the stock level at which a low stock alert is triggered
     */
    public Medicine(String medicineName, int stock, int lowStockAlert) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockAlert = lowStockAlert;
    }
    /**
     * Returns the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getMedicineName() {
        return medicineName;
    }
    /**
     * Returns the current stock level of the medicine.
     *
     * @return the current stock level
     */
    public int getStock() {
        return stock;
    }
    /**
     * Returns the low stock alert level for the medicine.
     *
     * @return the low stock alert level
     */
    public int getLowStockAlert() {
        return lowStockAlert;
    }
    /**
     * Decreases the stock level by 1.
     */
    public void minusStock() {
        this.stock--;  // Reduce by 1
    }
    /**
     * Decreases the stock level by the specified quantity.
     *
     * @param quantity the amount by which to reduce the stock
     */
    public void minusStock(int quantity) {
        this.stock -= quantity;  // Reduce by specified quantity
    }
    /**
     * Increases the stock level by 1.
     */
    public void addStock() {
        this.stock++;  // Increment by 1 (fixed the decrement error)
    }
    /**
     * Increases the stock level by the specified quantity.
     *
     * @param quantity the amount by which to increase the stock
     */
    public void addStock(int quantity) {
        this.stock += quantity;  // Increment by specified quantity
    }
    /**
     * Sets the low stock alert level for the medicine.
     *
     * @param lowStockAlert the new low stock alert level
     */
    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }
    /**
     * Returns a string representation of the medicine, displaying its name and current stock level.
     *
     * @return a string in the format "MedicineName: StockLevel"
     */
    @Override
    public String toString() {
        return getMedicineName() + ": " + getStock();
    }

}
