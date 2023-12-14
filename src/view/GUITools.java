package view;

import controllers.*;
import models.*;

// For JDatePicker functionalities.
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

// For GUI functionalities.
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

// For read/write functionalities.
import java.io.File;

// For working with dates.
import java.text.SimpleDateFormat;
import java.util.*;

// For FileChooser functionalities.
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

// Collection to generate during sorting to compare to the doubly linked list's sorting time.
import java.util.ArrayList;

/**
 * Utility class for handling various GUI interactions and actions in the employee management system.
 *
 * @param <E> Type parameter for the elements stored in the GenericDoublyLinkedList.
 */
public class GUITools<E> {
    /**
     * The main JFrame for the application.
     */
    private JFrame frame;

    /**
     * Reference to the GenericDoublyLinkedList managing employee data.
     */
    private GenericDoublyLinkedList<E> employeeList;

    /**
     * Reference to the JList component displaying employee data.
     */
    private JList<E> jList;

    /**
     * Reference to the JLabel displaying detailed information about the selected employee.
     */
    private JLabel employeeInfoLabel;

    /**
     * Reference to the DefaultListModel associated with the JList for managing employee data.
     */
    private DefaultListModel<E> listModel;

    /**
     * Button to navigate to the previous employee in the list.
     */
    private JButton backButton;

    /**
     * Button to navigate to the next employee in the list.
     */
    private JButton nextButton;

    /**
     * Button to navigate to the first employee in the list.
     */
    private JButton firstButton;

    /**
     * Button to navigate to the last employee in the list.
     */
    private JButton lastButton;

    /**
     * Button to calculate bonuses or extras for the selected employee.
     */
    private JButton calculateButton;

    /**
     * Button to load employee data from a file.
     */
    private JButton loadButton;

    /**
     * Button to save employee data to a file.
     */
    private JButton saveButton;

    /**
     * ArrayList to store created employees during mass creation.
     */
    private ArrayList createdEmployees = new ArrayList();

    /*
     * Uses a custom date controller to create and format gregorian calendars.
     */
    DateController dateController = new DateController();

    /**
     * Constructor for GUITools class.
     *
     * @param frame             The JFrame associated with the GUI.
     * @param employeeList      Reference to the GenericDoublyLinkedList containing employee data.
     * @param jList             Reference to the JList component displaying employee data.
     * @param employeeInfoLabel Reference to the JLabel displaying detailed information about the selected employee.
     * @param listModel         Reference to the DefaultListModel used for the JList.
     */
    public GUITools(JFrame frame, GenericDoublyLinkedList<E> employeeList, JList<E> jList, JLabel employeeInfoLabel, DefaultListModel<E> listModel) {
        this.employeeList = employeeList;
        this.jList = jList;
        this.employeeInfoLabel = employeeInfoLabel;
        this.listModel = listModel;
    }

    /**
     * Sets the buttons used in the GUI to be managed by GUITools.
     *
     * @param backButton       JButton for moving to the previous employee.
     * @param nextButton       JButton for moving to the next employee.
     * @param firstButton      JButton for moving to the first employee.
     * @param lastButton       JButton for moving to the last employee.
     * @param calculateButton  JButton for calculating salary modifications.
     * @param loadButton       JButton for loading employee data from a file.
     * @param saveButton       JButton for saving employee data to a file.
     */
    public void setButtons(JButton backButton, JButton nextButton, JButton firstButton, JButton lastButton, JButton calculateButton,
                           JButton loadButton, JButton saveButton) {
        this.backButton = backButton;
        this.nextButton = nextButton;
        this.firstButton = firstButton;
        this.lastButton = lastButton;
        this.calculateButton = calculateButton;
        this.loadButton = loadButton;
        this.saveButton = saveButton;
    }

    /**
     * Handles the action when the back button is clicked, moving to the previous employee.
     */
    public void backButtonClicked() {
        // Call the previous method to move to the previous item
        employeeList.previous();
        updateEmployeeInfo();
        // Update the enabled/disabled state of buttons
        updateButtonStates();
        setJListSelection();
    }

    /**
     * Handles the action when the next button is clicked, moving to the next employee.
     */
    public void nextButtonClicked() {
        // Call the next method to move to the next item
        employeeList.next();
       // updateJListAndEmployeeInfo();
        updateEmployeeInfo();
        // Update the enabled/disabled state of buttons
        updateButtonStates();
        setJListSelection();
    }

    /**
     * Handles the action when the first button is clicked, moving to the first employee.
     */
    public void firstButtonClicked() {
        // Call the moveToFirst method to move to the first item
        employeeList.moveToFirst();
        updateEmployeeInfo();
        // Update the enabled/disabled state of buttons
        updateButtonStates();
        setJListSelection();
    }

    /**
     * Handles the action when the last button is clicked, moving to the last employee.
     */
    public void lastButtonClicked() {
        // Call the moveToLast method to move to the last item
        employeeList.moveToLast();
        updateEmployeeInfo();
        // Update the enabled/disabled state of buttons
        updateButtonStates();
        setJListSelection();
    }

    /*public void calculateButtonClicked() throws SalaryExceedsMaxException {
        // Get the currently selected employee
        E currentEmployee = employeeList.getCurrent();

        // Check the type of the current employee
        if (currentEmployee instanceof Programmer) {
            Programmer programmer = (Programmer) currentEmployee;

            // Calculate the total monthly extras
            double totalMonthlyExtras = calculateMonthlyExtra(programmer);

            // If adding the total monthly extras would exceed the max salary, adjust the extras
            if (programmer.getSalary() + totalMonthlyExtras > programmer.getMaxSalary()) {
                totalMonthlyExtras = programmer.getMaxSalary() - programmer.getSalary();

                // Display a message informing the user that max salary limit is reached
                JOptionPane.showMessageDialog(frame, "Adding monthly extras exceeds max salary. Adjusted to fit within the limit.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            // Add the adjusted total monthly extras to the salary
            programmer.setSalary(programmer.getSalary() + totalMonthlyExtras);
            programmer.setMonthlyExtraCalculable(false);
        } else if (currentEmployee instanceof Analyst) {
            Analyst analyst = (Analyst) currentEmployee;
            double totalAnnualBonuses = calculateAnnualBonus(analyst);

            if (analyst.getSalary() + totalAnnualBonuses > analyst.getMaxSalary()) {
                totalAnnualBonuses = analyst.getMaxSalary() - analyst.getSalary();

                // Display a message informing the user that max salary limit is reached
                JOptionPane.showMessageDialog(frame, "Adding annual bonuses exceeds max salary. Adjusted to fit within the limit.", "Warning", JOptionPane.WARNING_MESSAGE);
             }

            // Add the adjusted annual bonuses to the salary
            analyst.setSalary(analyst.getSalary() + totalAnnualBonuses);
            analyst.setAnnualBonusCalculable(false);
        }

        // After calculating, update the JList and employee information
        updateJListAndEmployeeInfo();

        // Update the button states
        updateButtonStates();

        JOptionPane.showMessageDialog(frame, "Salary has been modified.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }*/

    /**
     * Handles the action when the calculate button is clicked, calculating salary modifications.
     *
     * @throws SalaryExceedsMaxException If the calculated salary exceeds the maximum allowed.
     */
    public void calculateButtonClicked() throws SalaryExceedsMaxException {
        // Get the currently selected employee
        E currentEmployee = employeeList.getCurrent();

        // Check the type of the current employee
        if (currentEmployee instanceof Programmer) {
            Programmer programmer = (Programmer) currentEmployee;

            // Calculate the total monthly extras
            double totalMonthlyExtras = calculateMonthlyExtra(programmer);

            // If adding the total monthly extras would exceed the max salary, throw an exception
            if (programmer.getSalary() + totalMonthlyExtras > programmer.getMaxSalary()) {
                throw new SalaryExceedsMaxException("Adding monthly extras exceeds max salary.");
            }

            // Add the total monthly extras to the salary
            programmer.setSalary(programmer.getSalary() + totalMonthlyExtras);
            programmer.setMonthlyExtraCalculable(false);
        } else if (currentEmployee instanceof Analyst) {
            Analyst analyst = (Analyst) currentEmployee;
            double totalAnnualBonuses = calculateAnnualBonus(analyst);

            // If adding the total annual bonuses would exceed the max salary, throw an exception
            if (analyst.getSalary() + totalAnnualBonuses > analyst.getMaxSalary()) {
                throw new SalaryExceedsMaxException("Adding annual bonuses exceeds max salary.");
            }

            // Add the total annual bonuses to the salary
            analyst.setSalary(analyst.getSalary() + totalAnnualBonuses);
            analyst.setAnnualBonusCalculable(false);
        }

        // After calculating, update the JList and employee information
        updateEmployeeInfo();

        // Update the button states
        updateButtonStates();

        JOptionPane.showMessageDialog(frame, "Salary has been modified.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the action when the load button is clicked, loading employee data from a file.
     */
    public void loadButtonClicked() {
        // Create a file chooser dialog.
        JFileChooser fileChooser = new JFileChooser();

        // Set the file filter to only show data files with the .ser extension.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Data Files", "ser");
        fileChooser.setFileFilter(filter);

        // Show the file chooser dialog and get the user's selection.
        int returnValue = fileChooser.showOpenDialog(frame);

        // If the user selected a file.
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Load employee data from the selected file.
            employeeList = FileHandler.loadDataFromFile(selectedFile.getPath());

            // Update the JList and employee information display.
            updateJListAndEmployeeInfo();
        } else {
            // If the user canceled or closed the dialog, print a message.
            System.out.println("Load operation canceled or closed by the user.");
        }
    }

    /**
     * Handles the action when the save button is clicked, saving employee data to a file.
     */
    public void saveButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Data Files", "ser");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showSaveDialog(frame);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            FileHandler.saveDataToFile(employeeList, selectedFile.getPath());
        } else {
            System.out.println("Save operation canceled or closed by the user.");
        }
    }

    /**
     * Handles the action when the create button is clicked, creating a new employee.
     *
     * @throws SalaryExceedsMaxException If the entered salary exceeds the maximum allowed.
     * @throws InvalidDateException      If the entered date is invalid or in the future.
     */
    public void createButtonClicked() throws SalaryExceedsMaxException, InvalidDateException {
        boolean employeeTypeSelected = false;

        do {
            // Create a panel with radio buttons for Programmer and Analyst.
            JPanel panel = new JPanel();
            JRadioButton programmerRadioButton = new JRadioButton("Programmer");
            JRadioButton analystRadioButton = new JRadioButton("Analyst");

            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(programmerRadioButton);
            buttonGroup.add(analystRadioButton);

            panel.add(programmerRadioButton);
            panel.add(analystRadioButton);

            // Show the message dialog with radio buttons.
            int result = JOptionPane.showConfirmDialog(frame, panel,
                    "Select Employee Type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            // Based on the selection, perform the corresponding action.
            if (result == JOptionPane.OK_OPTION) {
                if (programmerRadioButton.isSelected()) {
                    // User selected Programmer.
                    createProgrammerDialog(null);
                    employeeTypeSelected = true;
                } else if (analystRadioButton.isSelected()) {
                    // User selected Analyst.
                    createAnalystDialog(null);
                    employeeTypeSelected = true;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No employee type is selected.",
                            "Message",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // User clicked "Cancel" or closed the dialog.
                employeeTypeSelected = true; // Exit the loop.
            }
        } while (!employeeTypeSelected);
    }

    /**
     * Defines a class to hold the programmer data to allow data permanence between message dialog changes.
     */
    class ProgrammerData {
        private String employeeNumber;
        private String name;
        private String hireDate;
        private String salary;
        private String maxSalary;
        private String monthlyExtra;
        private String mainLanguage;
    }

    /**
     * Creates a dialog to input programmer data.
     *
     * @param previousData ProgrammerData containing previous data, if any.
     * @return ProgrammerData containing the entered data or previous data if canceled.
     * @throws SalaryExceedsMaxException if salary exceeds max salary.
     * @throws InvalidDateException      if the date is invalid or in the future.
     */
    private ProgrammerData createProgrammerDialog(ProgrammerData previousData) throws SalaryExceedsMaxException, InvalidDateException {
        // Create a JPanel with a 7x2 grid layout for organizing input fields.
        JPanel panel = new JPanel(new GridLayout(7, 2));

        // Add a label and text field for Employee Number.
        panel.add(new JLabel("Employee Number:"));
        String employeeNumberPlaceholder = "#######";
        // Create a text field with the previous data or a placeholder, and apply filters.
        JTextField programmerEmployeeNumberField = new JTextField(previousData != null
                && !previousData.employeeNumber.equals(employeeNumberPlaceholder) ? previousData.employeeNumber : "");
        // Add a placeholder if the text field is empty.
        if (programmerEmployeeNumberField.getText().isEmpty()) {
            addPlaceholder(programmerEmployeeNumberField, employeeNumberPlaceholder);
        }
        // Add a size filter to limit the input length.
        addMaxSizeFilter(programmerEmployeeNumberField, 7);
        // Add the text field to the panel.
        panel.add(programmerEmployeeNumberField);

        // Add a label and text field for Name.
        panel.add(new JLabel("Name:"));
        String namePlaceholder = "Enter name here";
        // Create a text field with the previous data or a placeholder, and apply filters.
        JTextField programmerNameField = new JTextField(previousData != null
                && !previousData.name.equals(namePlaceholder) ? previousData.name : "");
        // Add a placeholder if the text field is empty.
        if (programmerNameField.getText().isEmpty()) {
            addPlaceholder(programmerNameField, namePlaceholder);
        }
        // Add a size filter to limit the input length.
        addMaxSizeFilter(programmerNameField, 20);
        // Add the text field to the panel.
        panel.add(programmerNameField);

        // Add a label and date picker for Hire Date.
        panel.add(new JLabel("Hire Date (yyyy-MM-dd):"));
        // Create a UtilDateModel for handling the date.
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        // Customize the display text for the date picker.
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        // Create a date panel and a date picker with a custom formatter.
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) {
                return null;
            }

            @Override
            public String valueToString(Object value) {
                if (value != null) {
                    Calendar calendar = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    // Format the selected date and return it as a string.
                    String date = format.format(calendar.getTime());
                    return date;
                }
                // Return an empty string if no date is selected.
                return "";
            }
        });
        // Add the date picker to the panel.
        panel.add(datePicker);

        // Add text fields and a combo box for Salary, Max Salary, Monthly Extra, and Main Language.
        panel.add(new JLabel("Salary:"));
        // Set a money placeholder for the salary field.
        String moneyPlaceholder = "0000.00";
        JTextField programmerSalaryField = new JTextField(previousData != null
                && !previousData.salary.equals(moneyPlaceholder) ? previousData.salary : "");
        // Add a placeholder and size filter to the salary field.
        if (programmerSalaryField.getText().isEmpty()) {
            addPlaceholder(programmerSalaryField, moneyPlaceholder);
        }
        addMaxSizeFilter(programmerSalaryField, 9);
        // Add the salary field to the panel.
        panel.add(programmerSalaryField);

        // Add a label and text field for Max Salary.
        panel.add(new JLabel("Max Salary:"));
        JTextField programmerMaxSalaryField = new JTextField(previousData != null
                && !previousData.maxSalary.equals(moneyPlaceholder) ? previousData.maxSalary : "");
        // Add a placeholder and size filter to the Max Salary field.
        if (programmerMaxSalaryField.getText().isEmpty()) {
            addPlaceholder(programmerMaxSalaryField, moneyPlaceholder);
        }
        addMaxSizeFilter(programmerMaxSalaryField, 9);
        // Add the Max Salary field to the panel.
        panel.add(programmerMaxSalaryField);

        // Add a label and text field for Monthly Extra.
        panel.add(new JLabel("Monthly Extra:"));
        // Set a placeholder for the Monthly Extra field.
        String monthlyExtraPlaceholder = "X%";
        JTextField programmerMonthlyExtraField = new JTextField(previousData != null
                && !previousData.monthlyExtra.equals(monthlyExtraPlaceholder) ? previousData.monthlyExtra : "");
        // Add a placeholder and size filter to the Monthly Extra field.
        if (programmerMonthlyExtraField.getText().isEmpty()) {
            addPlaceholder(programmerMonthlyExtraField, monthlyExtraPlaceholder);
        }
        addMaxSizeFilter(programmerMonthlyExtraField, 9);
        // Add the Monthly Extra field to the panel.
        panel.add(programmerMonthlyExtraField);

        // Add a label and combo box for Main Language.
        panel.add(new JLabel("Main Language:"));
        ProgrammerLanguages[] languages = ProgrammerLanguages.values();
        // Convert the enum values to an array of strings.
        String[] languageNames = new String[languages.length];
        for (int i = 0; i < languages.length; i++) {
            languageNames[i] = languages[i].toString();
        }
        JComboBox<String> programmerLanguageComboBox = new JComboBox<>(languageNames);
        // Add the combo box to the panel.
        panel.add(programmerLanguageComboBox);

        if (previousData != null && previousData.mainLanguage != null) {
            // Set the selected language if it was previously selected.
            programmerLanguageComboBox.setSelectedItem(previousData.mainLanguage);
        }

        // Show a confirmation dialog with the input panel.
        int result = JOptionPane.showConfirmDialog(frame, panel, "Create Programmer", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked "OK".
        if (result == JOptionPane.OK_OPTION) {
            // Create a new ProgrammerData object to store entered data.
            ProgrammerData enteredData = new ProgrammerData();

            // Set the entered data from the text fields and combo box.
            enteredData.employeeNumber = programmerEmployeeNumberField.getText();
            enteredData.name = programmerNameField.getText();
            enteredData.hireDate = datePicker.getJFormattedTextField().getText();
            enteredData.salary = programmerSalaryField.getText();
            enteredData.maxSalary = programmerMaxSalaryField.getText();
            enteredData.monthlyExtra = programmerMonthlyExtraField.getText();
            enteredData.mainLanguage = (String) programmerLanguageComboBox.getSelectedItem();

            // Validate the entered employee number.
            String enteredEmployeeNumber = enteredData.employeeNumber;
            try {
                int employeeNumber = Integer.parseInt(enteredEmployeeNumber);

                // Check if the employee number is within the desired range (1 to 2000).
                if (employeeNumber < 1 || employeeNumber > 2000) {
                    JOptionPane.showOptionDialog(frame, "Employee Number must be between 1 and 2000.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createProgrammerDialog(enteredData);
                }

                // Check if the employee number already exists in the list.
                if (employeeList.exists(employeeNumber)) {
                    JOptionPane.showOptionDialog(frame, "Employee Number already exists. Please choose a different number.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createProgrammerDialog(enteredData);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showOptionDialog(frame, "Employee Number must be a valid number.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return createProgrammerDialog(enteredData);
            }

            // Validate all entered fields.
            if (!validateProgrammerFields(enteredData)) {
                // Show an error message without clearing the values.
                JOptionPane.showOptionDialog(frame, "Please fill in all fields.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                // Recall the method with the entered values.
                return createProgrammerDialog(enteredData);
            }

            try {
                // Convert employee number to int.
                int employeeNumber = Integer.parseInt(programmerEmployeeNumberField.getText());

                // Convert salary, maxSalary, and monthlyExtra to double.
                String name = programmerNameField.getText();
                String hireDate = datePicker.getJFormattedTextField().getText();
                double salary = Double.parseDouble(programmerSalaryField.getText());
                double maxSalary = Double.parseDouble(programmerMaxSalaryField.getText());
                double monthlyExtra = Double.parseDouble(programmerMonthlyExtraField.getText());
                String mainLanguage = (String) programmerLanguageComboBox.getSelectedItem();

                try {
                    // Create a new Programmer object and add it to the employee list.
                    Programmer programmer = new Programmer(employeeNumber, name, hireDate, salary, maxSalary, monthlyExtra, mainLanguage);
                    employeeList.add((E) programmer, programmer.getEmployeeNumber());
                } catch (SalaryExceedsMaxException salex) {
                    JOptionPane.showOptionDialog(frame, "Max Salary cannot exceed Salary.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createProgrammerDialog(enteredData);
                } catch (InvalidDateException ide) {
                    JOptionPane.showOptionDialog(frame, "Invalid hire date. The date cannot be in the future.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createProgrammerDialog(enteredData);
                }

                // Update the JList and employee information.
                updateJListAndEmployeeInfo();

            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer or double.
                JOptionPane.showOptionDialog(frame, "Employee Number, Salary, Max Salary, and Monthly Extra must be valid numbers.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return createProgrammerDialog(enteredData);
            }

            // Return the entered data.
            return enteredData;

        } else {
            // User clicked "Cancel" or closed the dialog, return previous data.
            return previousData;
        }
    }

    /**
     * Validates programmer fields.
     *
     * @param data ProgrammerData to validate.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean validateProgrammerFields(ProgrammerData data) {
        return !data.employeeNumber.isEmpty() &&
                !data.name.isEmpty() &&
                !data.hireDate.isEmpty() &&
                !data.salary.isEmpty() &&
                !data.maxSalary.isEmpty() &&
                !data.monthlyExtra.isEmpty();
    }

    /**
     * Defines a class to hold analyst data for data permanence between message dialog changes.
     */
    public class AnalystData {
        private String employeeNumber;
        private String name;
        private String hireDate;
        private String salary;
        private String maxSalary;
        private String annualBonus;
        private String additionalQualification;

    }

    /**
     * Creates a dialog to input analyst data.
     *
     * @param previousData AnalystData containing previous data, if any.
     * @return AnalystData containing the entered data or previous data if canceled.
     * @throws SalaryExceedsMaxException if salary exceeds max salary.
     * @throws InvalidDateException      if the date is invalid or in the future.
     */
    private AnalystData createAnalystDialog(AnalystData previousData) throws SalaryExceedsMaxException, InvalidDateException {
        // Create a panel with a grid layout for Analyst input.
        JPanel panel = new JPanel(new GridLayout(8, 2));

        // Add a label and text field for Employee Number.
        panel.add(new JLabel("Employee Number:"));
        String employeeNumberPlaceholder = "#######";
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystEmployeeNumberField = new JTextField(previousData != null
                && !previousData.employeeNumber.equals(employeeNumberPlaceholder) ? previousData.employeeNumber : "");
        // Add a placeholder and max size filter for the text field.
        if (analystEmployeeNumberField.getText().isEmpty()) {
            addPlaceholder(analystEmployeeNumberField, employeeNumberPlaceholder);
        }
        addMaxSizeFilter(analystEmployeeNumberField, 7);
        panel.add(analystEmployeeNumberField);

        // Add a label and text field for Name.
        panel.add(new JLabel("Name:"));
        String namePlaceholder = "Enter name here";
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystNameField = new JTextField(previousData != null
                && !previousData.name.equals(namePlaceholder) ? previousData.name : "");
        // Add a placeholder and max size filter for the text field.
        if (analystNameField.getText().isEmpty()) {
            addPlaceholder(analystNameField, namePlaceholder);
        }
        addMaxSizeFilter(analystNameField, 20);
        panel.add(analystNameField);

        // Add a label and date picker for Hire Date.
        panel.add(new JLabel("Hire Date (yyyy-MM-dd):"));
        // Set up the date picker components.
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) {
                return null;
            }

            @Override
            public String valueToString(Object value) {
                // Convert the selected date to a formatted string.
                if (value != null) {
                    Calendar calendar = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String date = format.format(calendar.getTime());

                    return date;
                }
                return "";
            }
        });
        panel.add(datePicker);

        // Add a label and text field for Salary.
        panel.add(new JLabel("Salary:"));
        String moneyPlaceholder = "0000.00";
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystSalaryField = new JTextField(previousData != null
                && !previousData.salary.equals(moneyPlaceholder) ? previousData.salary : "");
        // Add a placeholder and max size filter for the text field.
        if (analystSalaryField.getText().isEmpty()) {
            addPlaceholder(analystSalaryField, moneyPlaceholder);
        }
        addMaxSizeFilter(analystSalaryField, 9);
        panel.add(analystSalaryField);

        // Add a label and text field for Max Salary.
        panel.add(new JLabel("Max Salary:"));
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystMaxSalaryField = new JTextField(previousData != null
                && !previousData.maxSalary.equals(moneyPlaceholder) ? previousData.maxSalary : "");
        // Add a placeholder and max size filter for the text field.
        if (analystMaxSalaryField.getText().isEmpty()) {
            addPlaceholder(analystMaxSalaryField, moneyPlaceholder);
        }
        addMaxSizeFilter(analystMaxSalaryField, 9);
        panel.add(analystMaxSalaryField);

        // Add a label and text field for Annual Bonus.
        panel.add(new JLabel("Annual Bonus:"));
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystAnnualBonusField = new JTextField(previousData != null
                && !previousData.annualBonus.equals(moneyPlaceholder) ? previousData.annualBonus : "");
        // Add a placeholder and max size filter for the text field.
        if (analystAnnualBonusField.getText().isEmpty()) {
            addPlaceholder(analystAnnualBonusField, moneyPlaceholder);
        }
        addMaxSizeFilter(analystAnnualBonusField, 9);
        panel.add(analystAnnualBonusField);

        // Add a label and text field for Additional Qualification.
        panel.add(new JLabel("Additional Qualification:"));
        String qualificationPlaceholder = "Enter any qualification";
        // Initialize the text field with the previous data or a placeholder.
        JTextField analystQualificationField = new JTextField(previousData != null
                && !previousData.additionalQualification.equals(qualificationPlaceholder) ? previousData.additionalQualification : "");
        // Add a placeholder and max size filter for the text field.
        if (analystQualificationField.getText().isEmpty()) {
            addPlaceholder(analystQualificationField, qualificationPlaceholder);
        }
        addMaxSizeFilter(analystQualificationField, 30);
        panel.add(analystQualificationField);

        // Show a dialog to create an Analyst with the input panel.
        int result = JOptionPane.showConfirmDialog(frame, panel, "Create Analyst", JOptionPane.OK_CANCEL_OPTION);

        // If the user clicks "OK".
        if (result == JOptionPane.OK_OPTION) {
            // Create an AnalystData object to store the entered data.
            AnalystData enteredData = new AnalystData();
            // Populate AnalystData fields with user input.
            enteredData.employeeNumber = analystEmployeeNumberField.getText();
            enteredData.name = analystNameField.getText();
            enteredData.hireDate = datePicker.getJFormattedTextField().getText();
            enteredData.salary = analystSalaryField.getText();
            enteredData.maxSalary = analystMaxSalaryField.getText();
            enteredData.annualBonus = analystAnnualBonusField.getText();
            enteredData.additionalQualification = analystQualificationField.getText();

            // Validate employeeNumber.
            String enteredEmployeeNumber = enteredData.employeeNumber;
            try {
                int employeeNumber = Integer.parseInt(enteredEmployeeNumber);

                // Check if the employeeNumber is within the desired range (1 to 2000).
                if (employeeNumber < 1 || employeeNumber > 2000) {
                    // Show an error message and recall the method with entered data.
                    JOptionPane.showOptionDialog(frame, "Employee Number must be between 1 and 2000.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createAnalystDialog(enteredData);
                }

                // Check if the employeeNumber already exists in the list.
                if (employeeList.exists(employeeNumber)) {
                    // Show an error message and recall the method with entered data.
                    JOptionPane.showOptionDialog(frame, "Employee Number already exists. Please choose a different number.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createAnalystDialog(enteredData);
                }

            } catch (NumberFormatException e) {
                // Show an error message for invalid employeeNumber and recall the method with entered data.
                JOptionPane.showOptionDialog(frame, "Employee Number must be a valid number.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return createAnalystDialog(enteredData);
            }

            // Validate all AnalystData fields.
            if (!validateAnalystFields(enteredData)) {
                // Show an error message without clearing the values and recall the method with entered data.
                JOptionPane.showOptionDialog(frame, "Please fill in all fields.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return createAnalystDialog(enteredData);
            }

            try {
                // Convert employeeNumber to int.
                int employeeNumber = Integer.parseInt(analystEmployeeNumberField.getText());

                // Convert salary, maxSalary, and annualBonus to double.
                String name = analystNameField.getText();
                String hireDate = datePicker.getJFormattedTextField().getText();
                double salary = Double.parseDouble(analystSalaryField.getText());
                double maxSalary = Double.parseDouble(analystMaxSalaryField.getText());
                double annualBonus = Double.parseDouble(analystAnnualBonusField.getText());
                String additionalQualifications = analystQualificationField.getText();

                try {
                    // Create an Analyst object and add it to the employeeList.
                    Analyst analyst = new Analyst(employeeNumber, name, hireDate, salary, maxSalary, annualBonus, additionalQualifications);
                    employeeList.add((E) analyst, analyst.getEmployeeNumber());
                } catch (SalaryExceedsMaxException salex) {
                    // Show an error message and recall the method with entered data if Salary exceeds Max Salary.
                    JOptionPane.showOptionDialog(frame, "Salary cannot exceed Max Salary.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createAnalystDialog(enteredData);
                } catch (InvalidDateException ide) {
                    // Show an error message and recall the method with entered data for an invalid hire date.
                    JOptionPane.showOptionDialog(frame, "Invalid hire date. The date cannot be in the future.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                    return createAnalystDialog(enteredData);
                }
                // Update the JList and employee information.
                updateJListAndEmployeeInfo();

            } catch (NumberFormatException e) {
                // Show an error message for invalid input and recall the method with entered data.
                JOptionPane.showOptionDialog(frame, "Employee Number, Salary, Max Salary, and Annual Bonus must be valid numbers.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return createAnalystDialog(enteredData);
            }

            // Return the entered data if everything is successful.
            return enteredData;
        } else {
            // User clicked "Cancel" or closed the dialog, return the previous data.
            return previousData;
        }
    }

    /**
     * Validates the fields of an AnalystData instance.
     *
     * @param data AnalystData to validate.
     * @return true if all fields are valid, false otherwise.
     */
    private boolean validateAnalystFields(AnalystData data) {
        return !data.employeeNumber.isEmpty() &&
                !data.name.isEmpty() &&
                !data.hireDate.isEmpty() &&
                !data.salary.isEmpty() &&
                !data.maxSalary.isEmpty() &&
                !data.annualBonus.isEmpty() &&
                !data.additionalQualification.isEmpty();
    }

    /**
     * Adds a maximum size filter to a JTextField.
     *
     * @param textField The JTextField to which the filter is applied.
     * @param maxSize   The maximum allowed size of the text.
     */
    private void addMaxSizeFilter(JTextField textField, int maxSize) {
        // Create a DocumentFilter to limit the size of the text field.
        DocumentFilter filter = new DocumentFilter() {
            // Override insertString method to filter inserted text.
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                // Check if inserting the new string would exceed the maxSize.
                if ((fb.getDocument().getLength() + string.length()) <= maxSize) {
                    // If not, allow the insertion.
                    super.insertString(fb, offset, string, attr);
                }
            }

            // Override replace method to filter replaced text.
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                // Check if replacing the text would exceed the maxSize.
                if ((fb.getDocument().getLength() + text.length() - length) <= maxSize) {
                    // If not, allow the replacement.
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        // Apply the DocumentFilter to the text field's document.
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
    }

    /**
     * Adds a placeholder to a JTextField by setting its initial text and text color.
     *
     * @param field The JTextField to which the placeholder is added.
     * @param placeholder The text to be used as a placeholder.
     */
    public void addPlaceholder(JTextField field, String placeholder) {
        // Set the initial text and text color for the placeholder.
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        // Add a FocusListener to handle placeholder behavior.
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // When the field gains focus, check if its text is the placeholder.
                if (field.getText().equals(placeholder) || field.getText().equals("")) {
                    // Clear the placeholder text and set the text color to black.
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // When the field loses focus, check if it's empty.
                if (field.getText().isEmpty()) {
                    // Restore the placeholder text and set the text color to gray.
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Creates a specified number of random employees and adds them to the employee list.
     *
     * @throws SalaryExceedsMaxException if salary exceeds max salary.
     * @throws InvalidDateException      if the date is invalid or in the future.
     */
    public void massiveCreateButtonClicked() throws SalaryExceedsMaxException, InvalidDateException {
        Random random = new Random();

        // Create 10.000 employees with random employee numbers between 2.001 and 1.000.000.
        for (int i = 0; i < 10000; i++) {
            // Ensure the employee number is unique.
            int randomEmployeeNumber = random.nextInt(999000) + 1001;
            while (employeeList.exists(randomEmployeeNumber)) {
                randomEmployeeNumber = random.nextInt(999000) + 1001;
            }
            createRandomEmployee(randomEmployeeNumber);
        }

        // Create 20 employees with numbers between 1 and 2.000.
        for (int i = 0; i < 20; i++) {
            int randomEmployeeNumber = random.nextInt(2000) + 1;
            while (employeeList.exists(randomEmployeeNumber)) {
                randomEmployeeNumber = random.nextInt(2000) + 1;
            }
            createRandomEmployee(randomEmployeeNumber);
        }

        // After creating, update the JList and employee information.
        updateJListAndEmployeeInfo();

        // Update the button states.
        updateButtonStates();

        // Inform the user via a dialog.
        JOptionPane.showMessageDialog(frame, "Massive employee creation completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates a random employee with the given employee number and adds it to the employee list.
     *
     * @param employeeNumber The employee number for the random employee.
     * @throws SalaryExceedsMaxException if salary exceeds max salary.
     * @throws InvalidDateException      if the date is invalid or in the future.
     */
    private void createRandomEmployee(int employeeNumber) throws SalaryExceedsMaxException, InvalidDateException {
        E employee;

        // Check if the employee number is within the range of programmers (1 to 2.000).
        if (employeeNumber <= 2000) {
            // Create a dummy Programmer with default values.
            Programmer programmer = new Programmer(employeeNumber, "ProgrammerDummy", "10-10-1910", 0, 0, 0, "None");
            programmer.setEmployeeNumber(employeeNumber);

            // Add the dummy Programmer to the employee list.
            employeeList.add((E) programmer, programmer.getEmployeeNumber());

            // Set the employee variable to the created Programmer.
            employee = (E) programmer;
        } else {
            // Create a dummy Analyst with default values.
            Analyst analyst = new Analyst(employeeNumber, "AnalystDummy", "10-10-1910", 0, 0, 0, "None");
            analyst.setEmployeeNumber(employeeNumber);

            // Add the dummy Analyst to the employee list.
            employeeList.add((E) analyst, analyst.getEmployeeNumber());

            // Set the employee variable to the created Analyst.
            employee = (E) analyst;
        }

        // Add the created employee to the collection of created employees.
        createdEmployees.add(employee);
    }


    /**
     * Handles the sorting of employees and measures the time it takes for both the list and the collection.
     */
    public void sortButtonClicked() {
        long startTime, endTime;

        // Clear the collection of created employees.
        createdEmployees.clear();

        // Iterate through the linked list and add each element to the collection.
        employeeList.moveToFirst();
        while (employeeList.getCurrent() != null) {
            E currentEmployee = employeeList.getCurrent();
            createdEmployees.add(currentEmployee);
            employeeList.next();
        }

        // Measure the sorting time for the list using employeeList.sort().
        startTime = System.currentTimeMillis();
        employeeList.sort();
        endTime = System.currentTimeMillis();
        long listSortingTime = endTime - startTime;

        // Measure the sorting time for the collection using Collections.sort().
        startTime = System.currentTimeMillis();
        Collections.sort(createdEmployees);
        endTime = System.currentTimeMillis();
        long collectionSortingTime = endTime - startTime;

        // Show the sorting timers in a JOptionPane.
        String message = "List sorting time: " + listSortingTime + " ms\n"
                + "Collection sorting time: " + collectionSortingTime + " ms";

        JOptionPane.showMessageDialog(frame, message, "Sorting Times", JOptionPane.INFORMATION_MESSAGE);

        // Show the first 100 employees from the collection via console.
        System.out.println("First 100 employees:");
        int count = 0;
        for (Object employee : createdEmployees) {
            if (count < 100) {
                if (employee instanceof Programmer) {
                    Programmer programmer = (Programmer) employee;
                    System.out.println("Programmer - " + programmer.getName() + " (Employee number: " +  programmer.getEmployeeNumber() + ")");
                } else if (employee instanceof Analyst) {
                    Analyst analyst = (Analyst) employee;
                    System.out.println("Analyst - " + analyst.getName() + " (Employee number: " +  analyst.getEmployeeNumber() + ")");
                }
                count++;
            } else {
                break;
            }
        }

        // Debugging: Print the size of the createdEmployees collection.
        System.out.println("Created Employees Collection Size: " + createdEmployees.size());

        // Update the JList and employee information.
        updateJListAndEmployeeInfo();
    }

    /**
     * Updates the JList and employee information display one after another.
     */
    private void updateJListAndEmployeeInfo() {
        updateJList();
        updateEmployeeInfo();
    }

    /**
     * Updates the JList component with the current list of employees.
     */
    public void updateJList() {
        // Remember the currently selected employee to maintain selection after the update.
        E rememberedEmployee = employeeList.getCurrent();

        // Create a new DefaultListModel to update the JList.
        DefaultListModel<E> newListModel = new DefaultListModel<>();
        jList.setModel(newListModel);

        // Move the iterator to the beginning of the linked list.
        employeeList.moveToFirst();

        // Iterate through the linked list and add each employee to the new list model.
        while (employeeList.getCurrent() != null) {
            E currentEmployee = employeeList.getCurrent();
            newListModel.addElement(currentEmployee);
            employeeList.next();
        }

        // Ensure that the remembered employee is in the list model.
        if (rememberedEmployee != null && !newListModel.contains(rememberedEmployee)) {
            newListModel.addElement(rememberedEmployee);
        }

        // Set the iterator back to the remembered employee.
        employeeList.setCurrent(rememberedEmployee);

        // Update the button states based on the current state of the linked list.
        updateButtonStates();
    }


    /**
     * Updates the selected employee information.
     */
    public void updateSelectedEmployee() {
        // Get the currently selected employee from the JList.
        E selectedEmployee = jList.getSelectedValue();

        // Check if a valid employee is selected.
        if (selectedEmployee != null) {
            // Set the iterator's current position to the selected employee.
            employeeList.setCurrent(selectedEmployee);

            // Update the employee information display.
            updateEmployeeInfo();
        }
    }

    /**
     * Sets the JList selection to the current employee in the list.
     */
    private void setJListSelection() {
        // Get the current employee from the iterator.
        E currentEmployee = employeeList.getCurrent();

        // Set the JList selection to the current employee.
        // The second parameter (true) indicates that the selection should be scrolled into view.
        jList.setSelectedValue(currentEmployee, true);
    }

    /**
     * Updates the displayed information for the current employee in the employeeInfoLabel.
     */
    public void updateEmployeeInfo() {
        // System.out.println("Update Employee Info called");

        // Retrieve the current employee from the linked list.
        E currentEmployee = employeeList.getCurrent();

        // Check if there is a current employee to display information.
        if (currentEmployee != null) {
            // Get the current position and total elements in the list.
            int currentPosition = employeeList.getCurrentPosition() + 1; // Adding 1 to convert from 0-based to 1-based index.
            int totalElements = employeeList.getTotalElements();

            // Display the current position and total elements in the list.
            String positionText = "Employee #" + currentPosition + " of " + totalElements;

            // Update the employeeInfoLabel based on the type of employee (Programmer or Analyst).
            if (currentEmployee instanceof Programmer) {
                Programmer programmer = (Programmer) currentEmployee;
                System.out.println("Updating with Programmer data");

                // Determine the name to display, considering masking if necessary.
                String name = (programmer.getName() != null) ? programmer.getName() : programmer.getMaskedName();

                // Construct the HTML-formatted label text for Programmer information.
                String labelText = "<html><style>" +
                        "body { font-size: 12px; margin: 10px; padding: 10px; background-color: #E6F7FF; border: 2px solid #CCCCCC; }" +
                        "b { color: #336699; }" +
                        "</style>" +
                        "<center><b>" + positionText + "</b></center><br>" +
                        "<b>Name:</b> " + name +
                        "<br><b>Employee Number:</b> " + programmer.getEmployeeNumber() +
                        "<br><b>Hire Date:</b> " + dateController.calendarToString(programmer.getHireDate()) +
                        "<br><b>Salary:</b> " + programmer.getSalary() +
                        "<br><b>Max Salary:</b> " + programmer.getMaxSalary() +
                        "<br><b>Monthly Extra:</b> " + programmer.getMonthlyExtra() +
                        "<br><b>Main Language:</b> " + programmer.getMainLanguage() + "</html>";

                // Set the label text in the employeeInfoLabel.
                employeeInfoLabel.setText(labelText);
            } else if (currentEmployee instanceof Analyst) {
                Analyst analyst = (Analyst) currentEmployee;
                System.out.println("Updating with Analyst data");

                // Determine the name to display, considering masking if necessary.
                String name = (analyst.getName() != null) ? analyst.getName() : analyst.getMaskedName();

                // Construct the HTML-formatted label text for Analyst information.
                String labelText = "<html><style>" +
                        "body { font-size: 12px; margin: 10px; padding: 10px; background-color: #FFE6F7; border: 2px solid #CCCCCC; }" +
                        "b { color: #CC3366; }" +
                        "</style>" +
                        "<center><b>" + positionText + "</b></center><br>" +
                        "<b>Name:</b> " + name +
                        "<br><b>Employee Number:</b> " + analyst.getEmployeeNumber() +
                        "<br><b>Hire Date:</b> " + dateController.calendarToString(analyst.getHireDate()) +
                        "<br><b>Salary:</b> " + analyst.getSalary() +
                        "<br><b>Max Salary:</b> " + analyst.getMaxSalary() +
                        "<br><b>Annual Bonus:</b> " + analyst.getAnnualBonus() +
                        "<br><b>Additional Qualification:</b> " + analyst.getAdditionalQualification() + "</html>";

                // Set the label text in the employeeInfoLabel.
                employeeInfoLabel.setText(labelText);
            }
        } else {
            // Clear the employeeInfoLabel if there is no current employee.
            System.out.println("No current employee");
            employeeInfoLabel.setText("");
        }

        // Update the state of buttons based on the current employee.
        updateButtonStates();
    }

    /**
     * Calculates the annual bonus for an Analyst based on the elapsed years since their hire date.
     *
     * @param analyst The Analyst for whom the annual bonus is calculated.
     * @return The calculated annual bonus.
     */
    private double calculateAnnualBonus(Analyst analyst) {
        // Get the hire date and current date for the Analyst.
        GregorianCalendar hireDate = analyst.getHireDate();
        GregorianCalendar currentDate = new GregorianCalendar();

        // Calculate the elapsed years since the hire date.
        int elapsedYears = currentDate.get(GregorianCalendar.YEAR) - hireDate.get(GregorianCalendar.YEAR);

        // Adjust elapsed years if the current date is before the anniversary of the hire date.
        if (currentDate.get(GregorianCalendar.MONTH) < hireDate.get(GregorianCalendar.MONTH) ||
                (currentDate.get(GregorianCalendar.MONTH) == hireDate.get(GregorianCalendar.MONTH) &&
                        currentDate.get(GregorianCalendar.DAY_OF_MONTH) < hireDate.get(GregorianCalendar.DAY_OF_MONTH))) {
            elapsedYears--;
        }

        // Ensure elapsed years is not negative.
        elapsedYears = Math.max(0, elapsedYears);

        // Multiply the elapsed years by the annual bonus to calculate the total annual bonus.
        double annualBonus = elapsedYears * analyst.getAnnualBonus();

        return annualBonus;
    }

    /**
     * Calculates the monthly extra for a Programmer based on the elapsed months since their hire date.
     *
     * @param programmer The Programmer for whom the monthly extra is calculated.
     * @return The calculated monthly extra.
     */
    private double calculateMonthlyExtra(Programmer programmer) {
        // Get the hire date and current date for the Programmer.
        GregorianCalendar hireDate = programmer.getHireDate();
        GregorianCalendar currentDate = new GregorianCalendar();

        // Calculate the elapsed months since the hire date.
        int elapsedMonths = ((currentDate.get(GregorianCalendar.YEAR) - hireDate.get(GregorianCalendar.YEAR)) * 12) +
                (currentDate.get(GregorianCalendar.MONTH) - hireDate.get(GregorianCalendar.MONTH));

        // Adjust elapsed months if the current day is before the anniversary of the hire date.
        if (currentDate.get(GregorianCalendar.DAY_OF_MONTH) < hireDate.get(GregorianCalendar.DAY_OF_MONTH)) {
            elapsedMonths--;
        }

        // Ensure elapsed months is not negative.
        elapsedMonths = Math.max(0, elapsedMonths);

        // Calculate the monthly extra as a percentage of the salary.
        double monthlyExtraPercentage = programmer.getMonthlyExtra() / 100.0;
        double totalMonthlyExtras = Math.max(0, elapsedMonths) * (programmer.getSalary() * monthlyExtraPercentage);

        System.out.println("Elapsed Months: " + elapsedMonths);
        System.out.println("Monthly Extra Percentage: " + monthlyExtraPercentage);
        System.out.println("Total Monthly Extras: " + totalMonthlyExtras);

        return totalMonthlyExtras;
    }


    /**
     * Checks whether the "Calculate" button should be enabled or disabled based on the current employee type and time conditions.
     *
     * @return true if the button should be enabled, false otherwise.
     */
    private boolean canCalculate() {
        // Get the current employee from the employee list.
        E currentEmployee = employeeList.getCurrent();

        // Check if the current employee is an instance of Analyst.
        if (currentEmployee instanceof Analyst) {
            // Cast the current employee to Analyst type.
            Analyst analyst = (Analyst) currentEmployee;

            // Return true if the Analyst has passed the required years.
            return analyst.haveYearsPassed();

        } else if (currentEmployee instanceof Programmer) {
            // Check if the current employee is an instance of Programmer.
            // Cast the current employee to Programmer type.
            Programmer programmer = (Programmer) currentEmployee;

            // Return true if the Programmer has passed the required months.
            return programmer.haveMonthsPassed();
        }

        // Return false if the current employee type is neither Analyst nor Programmer.
        return false;
    }


    /**
     * Checks whether bonus calculations can be performed for the current employee.
     *
     * @return true if bonus calculations can be performed, false otherwise.
     */
    private boolean checkBonusCalculations() {
        // Get the current employee from the employee list.
        E currentEmployee = employeeList.getCurrent();

        // Check if the current employee is an instance of Analyst.
        if (currentEmployee instanceof Analyst) {
            // Cast the current employee to Analyst type.
            Analyst analyst = (Analyst) currentEmployee;

            // Return true if bonus calculations can be performed for the Analyst.
            return analyst.getAnnualBonusCalculable();

        } else if (currentEmployee instanceof Programmer) {
            // Check if the current employee is an instance of Programmer.
            // Cast the current employee to Programmer type.
            Programmer programmer = (Programmer) currentEmployee;

            // Return true if bonus calculations can be performed for the Programmer.
            return programmer.getMonthlyExtraCalculable();

        } else {
            // Return false if the current employee type is neither Analyst nor Programmer.
            return false;
        }
    }


    /**
     * Updates the enabled/disabled state of buttons based on the current state of the employee list.
     */
    public void updateButtonStates() {
        // Update the enabled/disabled state of navigation buttons based on list conditions.
        backButton.setEnabled(employeeList.hasPrevious());
        nextButton.setEnabled(employeeList.hasNext());
        firstButton.setEnabled(employeeList.hasPrevious());
        lastButton.setEnabled(employeeList.hasNext());

        // Enable the 'Calculate' button if both conditions are met:
        // 1. The current employee type allows bonus calculations (checkBonusCalculations()).
        // 2. The time conditions are satisfied for either Analyst (years passed) or Programmer (months passed) (canCalculate()).
        calculateButton.setEnabled(canCalculate() && checkBonusCalculations());
    }


}