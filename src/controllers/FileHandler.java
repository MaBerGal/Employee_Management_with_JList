package controllers;

// For reading and writing data.
import java.io.*;

import models.Analyst;
import models.Programmer;

/**
 * Utility class for handling file input and output operations.
 */
public class FileHandler {

    /**
     * Saves the data from a GenericDoublyLinkedList to a file using serialization.
     *
     * @param list     The GenericDoublyLinkedList to save to the file.
     * @param filename The name of the file to which the data will be saved.
     * @param <E>      The type of elements in the GenericDoublyLinkedList.
     */
    public static <E> void saveDataToFile(GenericDoublyLinkedList<E> list, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Iterate through the list and serialize each element to the file.
            list.moveToFirst();
            while (list.getCurrent() != null) {
                out.writeObject(list.getCurrent());
                list.next();
            }
            System.out.println("Data saved to file: " + filename);
        } catch (IOException e) {
            // Print the stack trace in case of an IOException.
            e.printStackTrace();
        }
    }

    /**
     * Loads data from a file into a GenericDoublyLinkedList using deserialization.
     *
     * @param filename The name of the file from which data will be loaded.
     * @param <E>      The type of elements in the GenericDoublyLinkedList.
     * @return A GenericDoublyLinkedList containing the loaded data.
     */
    public static <E> GenericDoublyLinkedList<E> loadDataFromFile(String filename) {
        GenericDoublyLinkedList<E> list = new GenericDoublyLinkedList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // Continuously read and deserialize objects from the file until the end is reached.
            while (true) {
                E data = (E) in.readObject();
                // Get the object's ID.
                int id = getIdForGenericList(data);
                // Add the deserialized data to the list.
                list.add(data, id);
            }
        } catch (EOFException e) {
            // End of file, do nothing.
        } catch (IOException | ClassNotFoundException e) {
            // Print the stack trace in case of an IOException or ClassNotFoundException.
            e.printStackTrace();
        }
        System.out.println("Data loaded from file: " + filename);
        return list;
    }

    /**
     * Helper method to get the employee number from an employee type object (in case it's one).
     *
     * @param employee The employee object.
     * @return The employee number.
     */
    private static <E> int getIdForGenericList(E employee) {
        if (employee instanceof Analyst) {
            return ((Analyst) employee).getEmployeeNumber();
        } else if (employee instanceof Programmer) {
            return ((Programmer) employee).getEmployeeNumber();
        } else {
            return 0;
        }
    }

}
