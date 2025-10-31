import java.io.Serializable;

public class Doctor implements Serializable {
    private String name;
    private String specialization;
    private String cnic; // New field for CNIC
    private String day; // New field for schedule day
    private String time; // New field for schedule time

    // Constructor with CNIC, date, and time
    public Doctor(String name, String specialization, String cnic, String day, String time) {
        this.name = name;
        this.specialization = specialization;
        this.cnic = cnic; // Initialize CNIC
        this.day = day; // Initialize date
        this.time = time; // Initialize time
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getCnic() {
        return cnic; // Getter for CNIC
    }

    public String getDay() {
        return day; // Getter for date
    }

    public String getTime() {
        return time; // Getter for time
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", cnic='" + cnic + '\'' + // Include CNIC in toString
                ", day='" + day + '\'' + // Include day in toString
                ", time='" + time + '\'' +// Include time in toString
                '}';
    }
}