import java.io.Serializable;

public class Patient implements Serializable {
    private String name;
    private int age;
    private String disease;
    private String date; // New field for schedule date
    private String time; // New field for schedule time


    public Patient(String name, int age, String disease, String date, String time) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.date = date; // Initialize date
        this.time = time; // Initialize time
    }

    // Getters and Setters
    public String getName() {

        return name;
    }

    public int getAge() {

        return age;
    }

    public String getDisease() {
        return disease;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {
        return time;
    }



    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", disease='" + disease +
                ", date='" + date +
                ", time='" + time + '\'' +
                '}';
    }
}
