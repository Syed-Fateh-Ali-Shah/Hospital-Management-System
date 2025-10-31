import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalGUI {
    private HospitalInventory<Patient> patientInventory;
    private HospitalInventory<Doctor> doctorInventory;
    private HospitalInventory<MedicalSupply> supplyInventory;
    private JFrame frame;
    private JTabbedPane tabbedPane;

    // Instance variables for text fields (to allow access from selection dialogs)
    private JTextField patientNameField;
    private JTextField patientAgeField;
    private JTextField patientDiseaseField;
    private JTextField patientDateField;
    private JTextField patientTimeField;
    private JTextField doctorNameField;
    private JTextField doctorSpecializationField;
    private JTextField doctorCnicField;
    private JTextField doctorDayField; // New for schedule date
    private JTextField doctorTimeField; // New for schedule time
    private JTextField supplyNameField;
    private JTextField supplyQuantityField;
    private JTextField supplySupplierField;
    private JTextField supplyDateField;
    private JTextField supplyTimeField;

    // Added for tracking original names during updates
    private String originalPatientName;
    private String originalDoctorName;
    private String originalSupplyName;

    public HospitalGUI() {
        patientInventory = new HospitalInventory<>();
        doctorInventory = new HospitalInventory<>();
        supplyInventory = new HospitalInventory<>();
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 450);  // Increased size for new tab
        frame.setLayout(new BorderLayout());

        // Show admin login first
        showAdminLogin();
        frame.setVisible(true);
    }

    private void showAdminLogin() {
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BorderLayout());
        adminPanel.setBackground(new Color(16, 62, 241));

        JLabel headingLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        headingLabel.setPreferredSize(new Dimension(250, 70));  // Corrected size aligned with labels
        headingLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        headingLabel.setForeground(Color.white);
        adminPanel.add(headingLabel, BorderLayout.NORTH);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        usernameField.setPreferredSize(new Dimension(270, 7));  // Corrected size aligned with labels
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        passwordField.setPreferredSize(new Dimension(250, 35));  // Corrected size aligned with labels
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Georgia", Font.BOLD, 25));  // Font size 22
        loginButton.setPreferredSize(new Dimension(130, 37));  // Smaller, standard size


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin")) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    frame.remove(adminPanel);
                    showMainTabs();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, 10));  // Corrected grid for username and password only
        inputPanel.setBorder(BorderFactory.createEmptyBorder(85, 80, 70, 85));  // Padding
        inputPanel.setBackground(new Color(173, 216, 230));


        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Georgia", Font.BOLD, 22));  // Bold, 22pt
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Georgia", Font.BOLD, 22));  // Bold, 22pt
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);



        adminPanel.add(inputPanel, BorderLayout.CENTER);


        // Button at bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));  // Padding
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.add(loginButton);
        adminPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(adminPanel, BorderLayout.CENTER);
    }

    private void showMainTabs() {
        tabbedPane = new JTabbedPane();

        JPanel patientPanel = createPatientPanel();
        tabbedPane.addTab("Patients", patientPanel);

        JPanel doctorPanel = createDoctorPanel();
        tabbedPane.addTab("Doctors", doctorPanel);

        JPanel supplyPanel = createMedicalSuppliesPanel();
        tabbedPane.addTab("Medical Supplies", supplyPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private JPanel createPatientPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(16, 62, 241));

        JLabel headingLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        headingLabel.setPreferredSize(new Dimension(250, 70));  // Corrected size aligned with labels
        headingLabel.setForeground(Color.white);
        panel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2));

        // Initialize instance variables for fields
        patientNameField = new JTextField();
        patientNameField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        patientAgeField = new JTextField();
        patientAgeField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        patientDiseaseField = new JTextField();
        patientDiseaseField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        patientDateField = new JTextField();
        patientDateField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        patientTimeField = new JTextField();
        patientTimeField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22


        JButton addButton = new JButton("Add Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton readButton = new JButton("Read Patients");
        JButton updateButton = new JButton("Update Patient");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = patientNameField.getText();
                String ageText = patientAgeField.getText();
                String disease = patientDiseaseField.getText();
                String patientDate = patientDateField.getText();
                String patientTime = patientTimeField.getText();

                if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty() || patientDate.isEmpty() || patientTime.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int age = Integer.parseInt(ageText);
                    patientInventory.add(new Patient(name, age, disease, patientDate, patientTime));
                    JOptionPane.showMessageDialog(frame, "Patient added successfully.", "Add Patient", JOptionPane.INFORMATION_MESSAGE);
                    clearFields(patientNameField, patientAgeField, patientDiseaseField, patientDateField, patientTimeField);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = patientNameField.getText();
                patientInventory.delete(name);
                clearFields(patientNameField, patientAgeField, patientDiseaseField, patientDateField, patientTimeField);
                JOptionPane.showMessageDialog(frame, "Patient deleted successfully.", "Delete Patient", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = patientNameField.getText();
                String ageText = patientAgeField.getText();
                String disease = patientDiseaseField.getText();
                String patientDate = patientDateField.getText();
                String patientTime = patientTimeField.getText();

                if (name.isEmpty() || ageText.isEmpty() || disease.isEmpty() || patientDate.isEmpty() || patientTime.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int age = Integer.parseInt(ageText);
                    patientInventory.update(originalPatientName, new Patient(name, age, disease, patientDate, patientTime));
                    clearFields(patientNameField, patientAgeField, patientDiseaseField, patientDateField, patientTimeField);
                    JOptionPane.showMessageDialog(frame, "Patient updated successfully.", "Update Patient", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.setLayout(new GridLayout(5, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 41, 10, 41)); // Padding added
        formPanel.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Georgia", Font.BOLD, 22);
        Font buttonFont = new Font("Georgia", Font.PLAIN, 22);

// Create labels separately so we can style them
        JLabel nameLabel = new JLabel("Patient Name:");
        nameLabel.setFont(labelFont);

        JLabel ageLabel = new JLabel("Patient Age:");
        ageLabel.setFont(labelFont);

        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setFont(labelFont);

        JLabel appointmentDateLabel = new JLabel("Appointment Date:");
        appointmentDateLabel.setFont(labelFont);

        JLabel appointmentTimeLabel = new JLabel("Appointment Time:");
        appointmentTimeLabel.setFont(labelFont);

// Now add to form panel
        formPanel.add(nameLabel);
        formPanel.add(patientNameField);
        formPanel.add(ageLabel);
        formPanel.add(patientAgeField);
        formPanel.add(diseaseLabel);
        formPanel.add(patientDiseaseField);
        formPanel.add(appointmentDateLabel);
        formPanel.add(patientDateField);
        formPanel.add(appointmentTimeLabel);
        formPanel.add(patientTimeField);

        // ✅ Create Button Panel for bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(00, 40, 10, 40)); // Padding added
        buttonPanel.setBackground(new Color(173, 216, 230));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        // Buttons with font
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        readButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        // Add panels to main Patient Panel
        panel.add(formPanel, BorderLayout.CENTER); // Fields in center
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at bottom
        return panel;
    }

    private JPanel createDoctorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(16, 62, 241));

        JLabel headingLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        headingLabel.setPreferredSize(new Dimension(250, 70));  // Corrected size aligned with labels
        headingLabel.setForeground(Color.white);
        panel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2));  // Increased rows for new fields

        // Initialize instance variables for fields
        doctorNameField = new JTextField();
        doctorNameField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        doctorSpecializationField = new JTextField();
        doctorSpecializationField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        doctorCnicField = new JTextField();
        doctorCnicField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        doctorDayField = new JTextField();  // New for schedule date
        doctorDayField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        doctorTimeField = new JTextField();  // New for schedule time
        doctorTimeField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22

        JButton addButton = new JButton("Add Doctor");
        JButton deleteButton = new JButton("Delete Doctor");
        JButton readButton = new JButton("Read Doctors");
        JButton updateButton = new JButton("Update Doctor");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = doctorNameField.getText();
                String specialization = doctorSpecializationField.getText();
                String cnic = doctorCnicField.getText();
                String day = doctorDayField.getText();
                String time = doctorTimeField.getText();

                if (name.isEmpty() || specialization.isEmpty() || cnic.isEmpty() || day.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                doctorInventory.add(new Doctor(name, specialization, cnic, day, time));
                clearFields(doctorNameField, doctorSpecializationField, doctorCnicField, doctorDayField, doctorTimeField);
                JOptionPane.showMessageDialog(frame, "Doctor added successfully.", "Add Doctor", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = doctorNameField.getText();
                doctorInventory.delete(name);
                clearFields(doctorNameField, doctorSpecializationField, doctorCnicField, doctorDayField, doctorTimeField);
                JOptionPane.showMessageDialog(frame, "Doctor deleted successfully.", "Delete Doctor", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDoctorTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = doctorNameField.getText();
                String specialization = doctorSpecializationField.getText();
                String cnic = doctorCnicField.getText();
                String date = doctorDayField.getText();
                String time = doctorTimeField.getText();

                if (name.isEmpty() || specialization.isEmpty() || cnic.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                doctorInventory.update(originalDoctorName, new Doctor(name, specialization, cnic, date, time));
                clearFields(doctorNameField, doctorSpecializationField, doctorCnicField, doctorDayField, doctorTimeField);
                JOptionPane.showMessageDialog(frame, "Doctor updated successfully.", "Update Doctor", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        formPanel.setLayout(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 41, -10, 41)); // Padding added
        formPanel.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Georgia", Font.BOLD, 22);
        Font buttonFont = new Font("Georgia", Font.PLAIN, 22);

// Create labels separately so we can style them
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);

        JLabel specializationLabel = new JLabel("Specialization:");
        specializationLabel.setFont(labelFont);

        JLabel cnicLabel = new JLabel("CNIC:");
        cnicLabel.setFont(labelFont);

        JLabel dateLabel = new JLabel("Available Days:");
        dateLabel.setFont(labelFont);

        JLabel timeLabel = new JLabel("Consultation Hours:");
        timeLabel.setFont(labelFont);

// Now add to form panel
        formPanel.add(nameLabel);
        formPanel.add(doctorNameField);
        formPanel.add(specializationLabel);
        formPanel.add(doctorSpecializationField);
        formPanel.add(cnicLabel);
        formPanel.add(doctorCnicField);
        formPanel.add(dateLabel);
        formPanel.add(doctorDayField);
        formPanel.add(timeLabel);
        formPanel.add(doctorTimeField);


        // ✅ Create Button Panel for bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(00, 40, 10, 40)); // Padding added
        buttonPanel.setBackground(new Color(173, 216, 230));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        // Buttons with font
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        readButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        // Add panels to main Patient Panel
        panel.add(formPanel, BorderLayout.CENTER); // Fields in center
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at bottom

        return panel;
    }

    private JPanel createMedicalSuppliesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(16, 62, 241));  // Light green background

        JLabel headingLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        headingLabel.setPreferredSize(new Dimension(250, 70));  // Corrected size aligned with labels
        headingLabel.setForeground(Color.white);
        panel.add(headingLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2));

        // Initialize instance variables for fields
        supplyNameField = new JTextField();
        supplyNameField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        supplyQuantityField = new JTextField();
        supplyQuantityField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        supplySupplierField = new JTextField();
        supplySupplierField.setFont(new Font("Georgia", Font.PLAIN, 18));  // Font size 22
        supplyDateField = new JTextField();
        supplyDateField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22
        supplyTimeField = new JTextField();
        supplyTimeField.setFont(new Font("Arial", Font.PLAIN, 18));  // Font size 22


        JButton addButton = new JButton("Add Supply");
        JButton deleteButton = new JButton("Delete Supply");
        JButton readButton = new JButton("Read Supplies");
        JButton updateButton = new JButton("Update Supply");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplyName = supplyNameField.getText();
                String quantityText = supplyQuantityField.getText();
                String supplier = supplySupplierField.getText();
                String supplyDate = supplyDateField.getText();
                String supplyTime = supplyTimeField.getText();


                if (supplyName.isEmpty() || quantityText.isEmpty() || supplier.isEmpty() || supplyDate.isEmpty() || supplyTime.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityText);
                    supplyInventory.add(new MedicalSupply(supplyName, quantity, supplier, supplyDate, supplyTime));
                    clearFields(supplyNameField, supplyQuantityField, supplySupplierField, supplyDateField, supplyTimeField);
                    JOptionPane.showMessageDialog(frame, "Supply added successfully.", "Add Supply", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplyName = supplyNameField.getText();
                supplyInventory.delete(supplyName);
                clearFields(supplyNameField, supplyQuantityField, supplySupplierField, supplyDateField, supplyTimeField);
                JOptionPane.showMessageDialog(frame, "Supply deleted successfully.", "Delete Supply", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSupplyTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String supplyName = supplyNameField.getText();
                String quantityText = supplyQuantityField.getText();
                String supplier = supplySupplierField.getText();
                String supplyDate = supplyDateField.getText();
                String supplyTime = supplyTimeField.getText();


                if (supplyName.isEmpty() || quantityText.isEmpty() || supplier.isEmpty() || supplyDate.isEmpty() || supplyTime.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityText);  // Parse string to int
                    supplyInventory.update(originalSupplyName, new MedicalSupply(supplyName, quantity, supplier, supplyDate, supplyTime));
                    clearFields(supplyNameField, supplyQuantityField, supplySupplierField, supplyDateField, supplyTimeField);
                    JOptionPane.showMessageDialog(frame, "Supply updated successfully.", "Update Supply", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formPanel.setLayout(new GridLayout(5, 2));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 41, 10, 41)); // Padding added
        formPanel.setBackground(new Color(173, 216, 230));

        Font labelFont = new Font("Georgia", Font.BOLD, 22);
        Font buttonFont = new Font("Georgia", Font.PLAIN, 22);

// Create labels separately so we can style them
        JLabel nameLabel = new JLabel("Supply Name:");
        nameLabel.setFont(labelFont);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);

        JLabel supplierLabel = new JLabel("Supplier Name:");
        supplierLabel.setFont(labelFont);

        JLabel supplyDateLabel = new JLabel("Arrival Date:");
        supplyDateLabel.setFont(labelFont);

        JLabel supplyTimeLabel = new JLabel("Arrival Time:");
        supplyTimeLabel.setFont(labelFont);

// Now add to form panel
        formPanel.add(nameLabel);
        formPanel.add(supplyNameField);
        formPanel.add(quantityLabel);
        formPanel.add(supplyQuantityField);
        formPanel.add(supplierLabel);
        formPanel.add(supplySupplierField);
        formPanel.add(supplyDateLabel);
        formPanel.add(supplyDateField);
        formPanel.add(supplyTimeLabel);
        formPanel.add(supplyTimeField);

        // ✅ Create Button Panel for bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 rows, 2 columns, with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(00, 40, 10, 40)); // Padding added
        buttonPanel.setBackground(new Color(173, 216, 230));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(readButton);
        buttonPanel.add(updateButton);
        // Buttons with font
        addButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);
        readButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        // Add panels to main Patient Panel
        panel.add(formPanel, BorderLayout.CENTER); // Fields in center
        panel.add(buttonPanel, BorderLayout.SOUTH); // Buttons at bottom

        return panel;
    }

    private void showPatientTable() {
        String[] columnNames = {"Patient Name", "Patient Age", "Disease", "Appointment Date", "Appointment Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Patient patient : patientInventory.getAll()) {
            model.addRow(new Object[]{patient.getName(), patient.getAge(), patient.getDisease(), patient.getDate(), patient.getTime()});
        }

        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JDialog dialog = new JDialog(frame, "Select Patient", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                originalPatientName = (String) model.getValueAt(selectedRow, 0);
                patientNameField.setText(originalPatientName);
                patientAgeField.setText(String.valueOf(model.getValueAt(selectedRow, 1)));
                patientDiseaseField.setText((String) model.getValueAt(selectedRow, 2));
                patientDateField.setText((String) model.getValueAt(selectedRow, 3));
                patientTimeField.setText((String) model.getValueAt(selectedRow, 4));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a row.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showDoctorTable() {
        String[] columnNames = {"Name", "Specialization", "CNIC", "Available Days", "Consultation Hours"};  // Updated for new fields
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Doctor doctor : doctorInventory.getAll()) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSpecialization(), doctor.getCnic(), doctor.getDay(), doctor.getTime()});
        }

        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JDialog dialog = new JDialog(frame, "Select Doctor", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                originalDoctorName = (String) model.getValueAt(selectedRow, 0);
                doctorNameField.setText(originalDoctorName);
                doctorSpecializationField.setText((String) model.getValueAt(selectedRow, 1));
                doctorCnicField.setText((String) model.getValueAt(selectedRow, 2));
                doctorDayField.setText((String) model.getValueAt(selectedRow, 3));
                doctorTimeField.setText((String) model.getValueAt(selectedRow, 4));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a row.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showSupplyTable() {
        String[] columnNames = {"Supply Name", "Quantity", "Supplier Name", "Arrival Date", "Arrival Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (MedicalSupply supply : supplyInventory.getAll()) {
            model.addRow(new Object[]{supply.getSupplyName(), supply.getQuantity(), supply.getSupplier(), supply.getDate(), supply.getTime() });
        }

        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JDialog dialog = new JDialog(frame, "Select Supply", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                originalSupplyName = (String) model.getValueAt(selectedRow, 0);
                supplyNameField.setText(originalSupplyName);
                supplyQuantityField.setText(String.valueOf(model.getValueAt(selectedRow, 1)));
                supplySupplierField.setText((String) model.getValueAt(selectedRow, 2));
                supplyDateField.setText((String) model.getValueAt(selectedRow, 3));
                supplyTimeField.setText((String) model.getValueAt(selectedRow, 4));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a row.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(selectButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        new HospitalGUI();
    }

}
