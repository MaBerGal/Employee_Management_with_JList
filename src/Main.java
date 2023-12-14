import controllers.*;
import models.*;
import view.*;

import javax.swing.*;

public class Main {

    public static <E> void main(String... args) {
        // Create Swing events on the EDT (Event Dispatch Thread) to ensure thread safety.
        SwingUtilities.invokeLater(() -> {
            // Create a GenericDoublyLinkedList for Analyst objects.
            GenericDoublyLinkedList<E> genericList = new GenericDoublyLinkedList<>();

            try {
                // Create some Analyst objects and add them to the list.
                Analyst analyst1 = new Analyst(1, "John", "01-01-2021", 5000, 5100, 200.0, "Certified Analyst");
                Analyst analyst2 = new Analyst(2, "Alice", "02-15-2022", 4800, 5900, 400.0, "Experienced Analyst");
                Programmer programmer3 = new Programmer(4, "Bob", "11-08-2023", 5500, 6100, 12.0, "Senior Analyst");
                Programmer programmer4 = new Programmer(3, "Sasque", "14-08-2023", 5000, 100000, 100, "Based Analyst");

                genericList.add((E) analyst1, analyst1.getEmployeeNumber());
                genericList.add((E) analyst2, analyst2.getEmployeeNumber());
                genericList.add((E) programmer3, programmer3.getEmployeeNumber());
                genericList.add((E) programmer4, programmer4.getEmployeeNumber());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create a new GUI instance.
            GUI<E> gui = new GUI<>(genericList);

            // Display the GUI.
            gui.setVisible(true);
        });
    }
}
