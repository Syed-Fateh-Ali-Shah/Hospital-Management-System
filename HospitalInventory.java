
//HospitalInventory controls all the nodes
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalInventory<T> {
    private Node<T> head;  // first node of the list

    public HospitalInventory() {
        this.head = null;
    }

    // Add a patient or doctor (Push)
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    // Delete a patient or doctor (Pop)
    public void delete(String name) {
        if (head == null) return;

        if (head.data instanceof Patient && ((Patient) head.data).getName().equals(name)) {
            head = head.next;
            return;
        } else if (head.data instanceof Doctor && ((Doctor) head.data).getName().equals(name)) {
            head = head.next;
            return;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data instanceof Patient && ((Patient) current.next.data).getName().equals(name)) {
                current.next = current.next.next;
                return;
            } else if (current.next.data instanceof Doctor && ((Doctor) current.next.data).getName().equals(name)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }

    // Read all patients or doctors
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    // Updated update method to include MedicalSupply
    public void update(String name, T newData) {
        Node<T> current = head;
        while (current != null) {
            if (current.data instanceof Patient && ((Patient) current.data).getName().equals(name)) {
                current.data = newData;
                return;
            } else if (current.data instanceof Doctor && ((Doctor) current.data).getName().equals(name)) {
                current.data = newData;
                return;
            } else if (current.data instanceof MedicalSupply && ((MedicalSupply) current.data).getName().equals(name)) {
                current.data = newData;
                return;
            }
            current = current.next;
        }
    }

    // Save data to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<T> dataList = new ArrayList<>();
            Node<T> current = head;
            while (current != null) {
                dataList.add(current.data);
                current = current.next;
            }
            oos.writeObject(dataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from a file
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<T> dataList = (List<T>) ois.readObject();
            for (T data : dataList) {
                add(data);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
