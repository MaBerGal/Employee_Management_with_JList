package view;

// Import for the GenericDoublyLinkedList class in the controllers package.
import controllers.GenericDoublyLinkedList;

// Imports for the different employee-related classes in the models package, pertinent exceptions included.
import models.Analyst;
import models.InvalidDateException;
import models.Programmer;
import models.SalaryExceedsMaxException;

// For utilizing various Swing components in the javax.swing package.
import javax.swing.*;
// For utilizin various AWT components in the java.awt package.
import java.awt.*;

/**
 * Graphical User Interface class for employee management.
 *
 * @param <E> The type of elements in the GenericDoublyLinkedList.
 */
public class GUI<E> extends JFrame {
    /**
     * Model for the JList.
     */
    private DefaultListModel<E> listModel;

    /**
     * JList for displaying employees.
     */
    private JList<E> myList;

    /**
     * Label for displaying employee information.
     */
    private JLabel employeeInfoLabel;

    /**
     * Utility class for handling GUI interactions.
     */
    private GUITools<E> guiTools;

    /**
     * Button for navigating to the previous employee.
     */
    private JButton backButton;

    /**
     * Button for navigating to the next employee.
     */
    private JButton nextButton;

    /**
     * Button for navigating to the first employee.
     */
    private JButton firstButton;

    /**
     * Button for navigating to the last employee.
     */
    private JButton lastButton;

    /**
     * Button for sorting employees.
     */
    private JButton sortButton;

    /**
     * Button for loading data.
     */
    private JButton loadButton;

    /**
     * Button for saving data.
     */
    private JButton saveButton;

    /**
     * Button for creating a new employee.
     */
    private JButton createButton;

    /**
     * Button for creating multiple employees.
     */
    private JButton massivelyCreateButton;

    /**
     * Button for calculating employee-related information.
     */
    private JButton calculateButton;

    /**
     * Constructor for the GUI class.
     *
     * @param employees The GenericDoublyLinkedList containing employee data.
     */
    public GUI(GenericDoublyLinkedList<E> employees) {
        setTitle("Employee Management");
        setSize(840, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        myList = new JList<>(listModel);

        // Initialize GUI components.
        initComponents();

        // Create an instance of GUITools and pass the GenericDoublyLinkedList and JList.
        guiTools = new GUITools<>(this, employees, myList, employeeInfoLabel, listModel);

        // Set the buttons in GUITools.
        guiTools.setButtons(backButton, nextButton, firstButton, lastButton, calculateButton, loadButton, saveButton);

        // Populate the JList with data.
        guiTools.updateJList();
        // Update employee info label.
        guiTools.updateEmployeeInfo();

        // Update the enabled/disabled state of buttons initially.
        guiTools.updateButtonStates();
    }

    /**
     * Initialize the GUI components.
     */
    private void initComponents() {
        // Create and configure the JList and JScrollPane.
        JScrollPane listScrollPane = new JScrollPane(myList);

        employeeInfoLabel = new JLabel("<html>Sample Line 1<br>Sample Line 2</html>");
        employeeInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align to center
        employeeInfoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Add left and right margins

        // Create buttons.
        backButton = new JButton("◀");
        nextButton = new JButton("▶");
        firstButton = new JButton("◀◀");
        lastButton = new JButton("▶▶");
        sortButton = new JButton("Sort");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        createButton = new JButton("Create");
        massivelyCreateButton = new JButton("Massively Create");
        calculateButton = new JButton("Calculate");

        // Set background colors for buttons
        backButton.setBackground(new java.awt.Color(204, 247, 255)); // Pastel blue
        nextButton.setBackground(new java.awt.Color(255, 204, 229)); // Pastel pink
        firstButton.setBackground(new java.awt.Color(204, 255, 204)); // Pastel green
        lastButton.setBackground(new java.awt.Color(255, 255, 204)); // Pastel yellow
        sortButton.setBackground(new java.awt.Color(255, 204, 255)); // Pastel purple
        loadButton.setBackground(new java.awt.Color(204, 255, 255)); // Pastel cyan
        saveButton.setBackground(new java.awt.Color(255, 229, 204)); // Pastel orange
        createButton.setBackground(new java.awt.Color(204, 204, 255)); // Pastel lavender
        massivelyCreateButton.setBackground(new java.awt.Color(255, 204, 204)); // Pastel peach
        calculateButton.setBackground(new java.awt.Color(204, 204, 204)); // Pastel gray

        // Sets an icon for the window.
        Image icon = Toolkit.getDefaultToolkit().getImage("rulai.png");
        setIconImage(icon);

        // Load an image.
        ImageIcon imageIcon = new ImageIcon("logo.png");

        // Create a JLabel to display the image.
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setPreferredSize(new Dimension(100, 100));

        // Create a panel for buttons below the JList.
        JPanel listButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        listButtonPanel.add(firstButton);
        listButtonPanel.add(backButton);
        listButtonPanel.add(nextButton);
        listButtonPanel.add(lastButton);

        // Create a panel for the rest of the buttons with a GridLayout (2 rows, 3 columns).
        JPanel restButtonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        restButtonPanel.add(sortButton);
        restButtonPanel.add(loadButton);
        restButtonPanel.add(saveButton);
        restButtonPanel.add(createButton);
        restButtonPanel.add(massivelyCreateButton);
        restButtonPanel.add(calculateButton);
        // Add left and right margins.
        restButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create a panel for the rest of the buttons and the image.
        JPanel restButtonImagePanel = new JPanel(new BorderLayout());
        restButtonImagePanel.add(restButtonPanel, BorderLayout.CENTER);
        restButtonImagePanel.add(imageLabel, BorderLayout.SOUTH);

        // Create a main content panel with GridBagLayout.
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add the JList to the content panel (takes up the whole column and expands vertically).
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        contentPanel.add(listScrollPane, gbc);

        // Add the listButtonPanel directly below the JList.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        contentPanel.add(listButtonPanel, gbc);

        // Add the employeeInfoLabel to the right of the JList.
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        contentPanel.add(employeeInfoLabel, gbc);

        // Add the restButtonImagePanel below the employeeInfoLabel.
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        contentPanel.add(restButtonImagePanel, gbc);

        /**
         * Set a custom cell renderer for the JList.
         */
        myList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Check if the value is an instance of Analyst.
                if (value instanceof Analyst) {
                    Analyst analyst = (Analyst) value;
                    String name = (analyst.getName() != null) ? analyst.getName() : analyst.getMaskedName();
                    // Set the text to display the Analyst's name and employee number using getter methods.
                    setText("ANALYST - " + name + " (Number: " + analyst.getEmployeeNumber() + ")");
                }

                // Check if the value is an instance of Programmer.
                if (value instanceof Programmer) {
                    Programmer programmer = (Programmer) value;
                    String name = (programmer.getName() != null) ? programmer.getName() : programmer.getMaskedName();
                    // Set the text to display the Programmer's name and employee number using getter methods.
                    setText("PROGRAMMER - " + name + " (Number: " + programmer.getEmployeeNumber() + ")");
                }

                return this;
            }
        });

        // Add a ListSelectionListener to the JList in order to update the employee's information in real time.
        myList.addListSelectionListener(e -> {
            // The check for !e.getValueIsAdjusting() ensures that the listener responds only to the final selection,
            // preventing multiple updates during the process of adjusting the selection.
            if (!e.getValueIsAdjusting()) {
                guiTools.updateSelectedEmployee();
            }
        });

        // Add action listeners to buttons, catching thrown custom exceptions when necessary.
        backButton.addActionListener(e -> guiTools.backButtonClicked());
        nextButton.addActionListener(e -> guiTools.nextButtonClicked());
        firstButton.addActionListener(e -> guiTools.firstButtonClicked());
        lastButton.addActionListener(e -> guiTools.lastButtonClicked());
        calculateButton.addActionListener(e -> {
            try {
                guiTools.calculateButtonClicked();
            } catch (SalaryExceedsMaxException ex) {
                JOptionPane.showMessageDialog(this, "Salary cannot exceed max salary.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loadButton.addActionListener(e -> guiTools.loadButtonClicked());
        saveButton.addActionListener(e -> guiTools.saveButtonClicked());
        createButton.addActionListener(e -> {
            try {
                guiTools.createButtonClicked();
            } catch (SalaryExceedsMaxException ex) {
                JOptionPane.showMessageDialog(this, "Salary cannot exceed Max Salary.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidDateException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        massivelyCreateButton.addActionListener(e -> {
            try {
                guiTools.massiveCreateButtonClicked();
            } catch (SalaryExceedsMaxException ex) {
                JOptionPane.showMessageDialog(this, "Salary cannot exceed max salary.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidDateException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        sortButton.addActionListener(e -> guiTools.sortButtonClicked());

        // Finally, add the built content panel to the frame.
        add(contentPanel);
    }

    /**
     * Getter for the JList.
     *
     * @return The JList component.
     */
    public JList<E> getMyList() {
        return myList;
    }

    /**
     * Getter for the employeeInfoLabel.
     *
     * @return The JLabel displaying employee information.
     */
    public JLabel getEmployeeInfoLabel() {
        return employeeInfoLabel;
    }

    /**
     * Getter for the DefaultListModel.
     *
     * @return The DefaultListModel associated with the JList.
     */
    public DefaultListModel<E> getListModel() {
        return listModel;
    }
}
