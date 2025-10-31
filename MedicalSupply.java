import java.io.Serializable;

public class MedicalSupply implements Serializable {
    private String supplyName;
    private int quantity;
    private String supplier;

    private String date; // New field for schedule date
    private String time; // New field for schedule time


    public MedicalSupply(String supplyName, int quantity, String supplier, String date, String time) {
        this.supplyName = supplyName;
        this.quantity = quantity;
        this.supplier = supplier;
        this.date = date; // Initialize date
        this.time = time; // Initialize time
    }

    // Getters and Setters
    public String getSupplyName() {
        return supplyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    // Added for consistency with HospitalInventory.update() method
    public String getName() {
        return supplyName;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {
        return time;
    }


    @Override
    public String toString() {
        return "MedicalSupply{" +
                "supplyName='" + supplyName + '\'' +
                ", quantity=" + quantity +
                ", supplier='" + supplier +
                ", date='" + date +
                ", time='" + time + '\'' +
                '}';
    }
}