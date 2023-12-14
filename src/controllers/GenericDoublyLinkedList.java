package controllers;

/**
 * Utility class for handling doubly linked list operations.
 */
public class GenericDoublyLinkedList<E> {
    /**
     * The first node in the doubly linked list.
     */
    private Node<E> firstNode;

    /**
     * The last node in the doubly linked list.
     */
    private Node<E> lastNode;

    /**
     * The current node in the doubly linked list.
     */
    private Node<E> currentNode;

    /**
     * The total number of elements in the doubly linked list.
     */
    private int totalElements;

    /**
     * Constructs an empty doubly linked list.
     */
    public GenericDoublyLinkedList() {
        // Initialize the doubly linked list with no elements.
        firstNode = null;
        lastNode = null;
        currentNode = null;
        totalElements = 0;
    }


    /**
     * Adds a new element to the doubly linked list with the specified data and ID.
     *
     * @param data the data to be added to the doubly linked list.
     * @param id   the ID associated with the data.
     */
    public void add(E data, int id) {
        // Create a new node with the given data and ID.
        Node<E> newNode = new Node<>(data, id);

        // If the list is empty, the new node becomes both the first and last node.
        if (firstNode == null) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            // If the list is not empty, set the new node's previous node to the current last node.
            newNode.setPreviousNode(lastNode);

            // Set the next node of the current last node to the new node.
            lastNode.setNextNode(newNode);

            // Update the last node to be the new node.
            lastNode = newNode;
        }
        // Increment the total number of elements in the list.
        totalElements++;
    }

    /**
     * Removes the current element from the doubly linked list.
     * If the current node is null, no action is taken.
     */
    public void remove() {
        // If the current node is null, there is nothing to remove.
        if (currentNode == null) {
            return;
        }

        // Retrieve the previous and next nodes of the current node.
        Node<E> previousNode = currentNode.getPreviousNode();
        Node<E> nextNode = currentNode.getNextNode();

        // If there is a previous node, update its next node to skip the current node.
        if (previousNode != null) {
            previousNode.setNextNode(nextNode);
        } else {
            // If there is no previous node, the current node is the first node,
            // so update the first node to be the next node.
            firstNode = nextNode;
        }

        // If there is a next node, update its previous node to skip the current node.
        if (nextNode != null) {
            nextNode.setPreviousNode(previousNode);
        } else {
            // If there is no next node, the current node is the last node,
            // so update the last node to be the previous node.
            lastNode = previousNode;
        }

        // Move the current node to the next node in the list.
        currentNode = nextNode;

        // Decrement the total number of elements in the list.
        totalElements--;
    }

    /**
     * Retrieves the data of the current element in the doubly linked list.
     *
     * @return the data of the current element, or null if the list is empty or the current element is null.
     */
    public E getCurrent() {
        // If the current node is not null, return the data of the current node.
        // Otherwise, return null indicating that there is no current element.
        return (currentNode != null) ? currentNode.getData() : null;
    }

    /**
     * Sets the current element of the doubly linked list based on the specified data.
     *
     * @param data the data to search for and set as the current element.
     */
    public void setCurrent(E data) {
        // Start from the first node in the list.
        Node<E> node = firstNode;

        // Iterate through the list until the end is reached or the data is found.
        while (node != null) {
            // If the data of the current node matches the specified data,
            // set the current node and exit the loop.
            if (node.getData().equals(data)) {
                currentNode = node;
                break;
            }

            // Move to the next node in the list.
            node = node.getNextNode();
        }
    }

    /**
     * Moves to the next element in the doubly linked list and retrieves its data.
     *
     * @return the data of the next element, or null if the list is empty or the end is reached.
     */
    public E next() {
        // If the current node is null, set it to the first node.
        if (currentNode == null) {
            currentNode = firstNode;
        } else {
            // Otherwise, move to the next node in the list.
            currentNode = currentNode.getNextNode();
        }

        // Return the data of the current element after the move.
        return getCurrent();
    }

    /**
     * Moves to the previous element in the doubly linked list and retrieves its data.
     *
     * @return the data of the previous element, or null if the list is empty or the beginning is reached.
     */
    public E previous() {
        // If the current node is null, set it to the last node.
        if (currentNode == null) {
            currentNode = lastNode;
        } else {
            // Otherwise, move to the previous node in the list.
            currentNode = currentNode.getPreviousNode();
        }

        // Return the data of the current element after the move.
        return getCurrent();
    }

    /**
     * Retrieves the total number of elements in the doubly linked list.
     *
     * @return the total number of elements.
     */
    public int getTotalElements() {
        return totalElements;
    }

    /**
     * Retrieves the current position of the current element in the doubly linked list.
     *
     * @return the current position of the current element, or -1 if the list is empty or the current element is null.
     */
    public int getCurrentPosition() {
        // If the current node is null, return -1 indicating that there is no current element.
        if (currentNode == null) {
            return -1;
        }

        // Start from the first node in the list.
        Node<E> node = firstNode;

        // Iterate through the list until the end is reached or the current node is found.
        int currentPosition = 0;
        while (node != null) {
            // If the current node is the same as the current node, return the current position.
            if (node == currentNode) {
                return currentPosition;
            }

            // Move to the next node in the list and increment the position.
            node = node.getNextNode();
            currentPosition++;
        }

        // If the current node is not found in the list, return -1.
        return -1;
    }


    /**
     * Sets the current node to the first node in the doubly linked list.
     */
    public void moveToFirst() {
        currentNode = firstNode;
    }

    /**
     * Sets the current node to the last node in the doubly linked list.
     */
    public void moveToLast() {
        currentNode = lastNode;
    }

    /**
     * Checks if there is a previous element in the doubly linked list.
     *
     * @return true if there is a previous element, false otherwise.
     */
    public boolean hasPrevious() {
        // Returns true if the current node is not null and it has a previous node.
        return currentNode != null && currentNode.getPreviousNode() != null;
    }

    /**
     * Checks if there is a next element in the doubly linked list.
     *
     * @return true if there is a next element, false otherwise.
     */
    public boolean hasNext() {
        // Returns true if the current node is not null and it has a next node.
        return currentNode != null && currentNode.getNextNode() != null;
    }

    /**
     * Checks if the current node is the first node in the doubly linked list.
     *
     * @return true if the current node is the first node, false otherwise.
     */
    public boolean isAtFirst() {
        return currentNode == firstNode;
    }

    /**
     * Checks if the current node is the last node in the doubly linked list.
     *
     * @return true if the current node is the last node, false otherwise.
     */
    public boolean isAtLast() {
        return currentNode == lastNode;
    }

    /**
     * Checks if an element with the specified ID exists in the doubly linked list.
     *
     * @param id the ID to search for in the doubly linked list.
     * @return true if an element with the specified ID exists, false otherwise.
     */
    public boolean exists(int id) {
        // Start from the first node in the list.
        Node<E> node = firstNode;

        // Iterate through the list until the end is reached or the element with the specified ID is found.
        while (node != null) {
            // If the ID of the current node matches the specified ID, return true.
            if (node.getId() == id) {
                return true;
            }

            // Move to the next node in the list.
            node = node.getNextNode();
        }

        // If no matching element is found, return false.
        return false;
    }

    /**
     * Sorts the doubly linked list in ascending order based on element IDs using the Bubble Sort algorithm.
     *
     * @return true if the list is successfully sorted, false if the list is empty or encounters an issue during sorting.
     */
    public boolean sort() {
        // Variable to track if any swaps occurred during a pass.
        boolean swapped;

        // Nodes used for traversal during sorting.
        Node<E> leftNode;
        Node<E> rightNode = null;

        // Check if the list is empty.
        if (firstNode == null) {
            return false; // Nothing to sort.
        }

        // Perform Bubble Sort until no more swaps are needed.
        do {
            swapped = false;
            leftNode = firstNode;

            // Traverse the list and perform comparisons and swaps as needed.
            while (leftNode.getNextNode() != rightNode) {
                if (leftNode.getId() > leftNode.getNextNode().getId()) {
                    // If the left node's ID is greater than the next node's ID, perform a swap.
                    interChange(leftNode, leftNode.getNextNode());
                    swapped = true;
                }
                leftNode = leftNode.getNextNode();
            }

            // Move the right node to the leftmost unsorted node.
            rightNode = leftNode;
        } while (swapped);

        // Return true indicating successful sorting.
        return true;
    }

    /**
     * Interchanges the data and ID between two nodes in the doubly linked list.
     *
     * @param node1 the first node to interchange data and ID.
     * @param node2 the second node to interchange data and ID.
     */
    public void interChange(Node<E> node1, Node<E> node2) {
        // Check if both nodes are not null before performing the interchange.
        if (node1 != null && node2 != null) {
            // Temporary variables to store ID and data of node1.
            int tempId = node1.getId();
            E tempData = node1.getData();

            // Set the ID and data of node1 to be the ID and data of node2.
            node1.setId(node2.getId());
            node1.setData(node2.getData());

            // Set the ID and data of node2 to be the temporary ID and data.
            node2.setId(tempId);
            node2.setData(tempData);
        }
    }

    /**
     * Finds and returns the node with the specified ID in the doubly linked list.
     *
     * @param id the ID to search for in the doubly linked list.
     * @return the node with the specified ID, or null if no such node is found.
     */
    private Node<E> findNodeById(int id) {
        // Start from the first node in the list.
        Node<E> node = firstNode;

        // Iterate through the list until the end is reached or the node with the specified ID is found.
        while (node != null) {
            // If the ID of the current node matches the specified ID, return the current node.
            if (node.getId() == id) {
                return node;
            }

            // Move to the next node in the list.
            node = node.getNextNode();
        }

        // If no matching node is found, return null.
        return null;
    }


    /**
     * Retrieves the data of the current element in the doubly linked list.
     *
     * @return the data of the current element, or null if the list is empty or the current element is null.
     */
    public E getData() {
        // If the current node is not null, return the data of the current node.
        // Otherwise, return null indicating that there is no current element.
        return (currentNode != null) ? currentNode.getData() : null;
    }

    /**
     * Retrieves the ID of the current element in the doubly linked list.
     *
     * @return the ID of the current element, or -1 if the list is empty or the current element is null.
     */
    public int getId() {
        // If the current node is not null, return the ID of the current node.
        // Otherwise, return -1 indicating that there is no current element.
        return (currentNode != null) ? currentNode.getId() : -1;
    }


    /**
     * Inner class representing a node in a doubly linked list.
     *
     * @param <T> the type of data stored in the node.
     */
    private class Node<T> {
        /**
         * Reference to the next node in the doubly linked list.
         */
        private Node<T> nextNode;

        /**
         * Reference to the previous node in the doubly linked list.
         */
        private Node<T> previousNode;

        /**
         * The data stored in the node.
         */
        private T data;

        /**
         * The ID associated with the data stored in the node.
         */
        private int id;

        /**
         * Constructs a node with the specified data and ID.
         *
         * @param data the data to be stored in the node.
         * @param id   the ID associated with the data.
         */
        public Node(T data, int id) {
            this.data = data;
            this.id = id;
            this.nextNode = null;
            this.previousNode = null;
        }

        /**
         * Retrieves the data stored in the node.
         *
         * @return the data stored in the node.
         */
        private T getData() {
            return data;
        }

        /**
         * Sets the data to be stored in the node.
         *
         * @param data the data to be stored in the node.
         */
        private void setData(T data) {
            this.data = data;
        }

        /**
         * Retrieves the ID associated with the data stored in the node.
         *
         * @return the ID associated with the data.
         */
        private int getId() {
            return id;
        }

        /**
         * Sets the ID associated with the data stored in the node.
         *
         * @param id the ID to be associated with the data.
         */
        private void setId(int id) {
            this.id = id;
        }

        /**
         * Retrieves the next node in the doubly linked list.
         *
         * @return the next node in the list.
         */
        private Node<T> getNextNode() {
            return nextNode;
        }

        /**
         * Retrieves the previous node in the doubly linked list.
         *
         * @return the previous node in the list.
         */
        private Node<T> getPreviousNode() {
            return previousNode;
        }

        /**
         * Sets the next node in the doubly linked list.
         *
         * @param nextNode the node to be set as the next node.
         */
        private void setNextNode(Node<T> nextNode) {
            this.nextNode = nextNode;
        }

        /**
         * Sets the previous node in the doubly linked list.
         *
         * @param previousNode the node to be set as the previous node.
         */
        private void setPreviousNode(Node<T> previousNode) {
            this.previousNode = previousNode;
        }
    }
}


