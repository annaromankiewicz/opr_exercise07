import exceptions.InvalidAccessException;
import exceptions.ValueException;

public class DoubleLinkedList<T extends Comparable<T>> {

    public class Node {
        private Node next;
        private Node previous;
        private T value;

        public Node() {
            value = null;
        }

        public Node(T val) throws ValueException {
            if (val == null) throw new ValueException("Value is null");
            value = val;
        }

        public void flipNextPrev() {
            Node oldPrev = this.previous;
            this.previous = next;
            next = oldPrev;
        }

    }

    /**
     * Pointer to the first and last element of the list
     */
    private Node head, tail;

    /**
     * Saves the number of elements in the list
     */
    private int count;


    /**
     * Constructor initializes an empty list.
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        this.count = 0;
    }

    /**
     * Copy constructor initializes list with another list.
     * This constructor must COPY all elements of the other list.
     * The elements of the other list must NOT be changed!
     */
    public DoubleLinkedList(DoubleLinkedList<T> other) throws ValueException {
        if (other == null) throw new ValueException();
        Node p = other.head;
        while (p != null) {
            append(p.value);
            p = p.next;
        }
    }

    /**
     * Clears all elements from the linked list
     */
    public void clear() {
        head = null;
        tail = null;
        this.count = 0;
    }


    /**
     * Adds an element at the front of the linked list.
     */
    public void prepend(T elem) throws ValueException {
        if (elem == null) throw new ValueException();
        Node n = new Node();
        n.value = elem;
        if (head == null) {
            head = n;
            tail = n;
        } else {
            n.next = head;
            head.previous = n;
            head = n;
        }
        this.count++;
    }


    /**
     * Adds an element at the back of the linked list.
     */
    public void append(T elem) throws ValueException {
        if (elem == null) throw new ValueException();
        Node n = new Node();
        n.value = elem;
        if (head == null) {
            head = n;
            tail = n;
        } else {
            n.previous = tail;
            tail.next = n;
            tail = n;
        }
        this.count++;

    }

    /**
     * Returns the element at position ‘index’. Throws an exception
     * if ‘index’ is invalid.
     */
    public T get(int index) throws InvalidAccessException {
        Node p = head;
        for (int i = 0; i < count; i++) {
            if (i == index) {
                return p.value;
            }
            p = p.next;
        }
        throw new InvalidAccessException();
    }

    /**
     * Removes and returns the front element of the linked list. Throws an exception if empty
     */
    public T popFront() throws InvalidAccessException {
        if (head == null) throw new InvalidAccessException();
        Node p = head;
        if (head == tail) { // last element in list
            head = null;
            tail = null;
        } else {
            head = p.next;
            head.previous = null;
        }
        this.count--;
        return p.value;
    }

    /**
     * Returns the front element of the list without removing it.
     * Throws an exception if empty
     */
    public T peekFront() throws InvalidAccessException {
        if (head == null) throw new InvalidAccessException();
        return head.value;
    }

    /**
     * Removes and returns the element from the back of the linked list.
     * Throws an exception if empty
     */
    public T popBack() throws InvalidAccessException {
        if (tail == null) throw new InvalidAccessException();
        Node p = tail; // old tail
        if (tail != head) { // list has 2 or more elements
            tail = tail.previous;
            tail.next = null;
        } else {        // pop last element in list
            head = null;
            tail = null;
        }
        this.count--;
        return p.value;
    }

    /**
     * Returns the element at the back of the list without removing it.
     * Throws an exception if empty
     */
    public T peekBack() throws InvalidAccessException {
        if (head == null) throw new InvalidAccessException();
        return tail.value;
    }

    /**
     * Returns the number of elements in the double linked list
     */
    public int size() {
        return this.count;
    }

    /**
     * Reverses the order of all elements in the list. “He who is first,
     * shall be last!”
     */
    public void reverse() {
        if (head != null) {
            Node newTail = head;
            Node newHead = tail;
            Node current = head;
            Node next = head.next;
            while (current != null) {
                current.flipNextPrev();    //  helperReverse() method of Node to reverse the references next and prev
                current = next;
                if (current != null) {
                    next = current.next;
                }
            }
            head = newHead;
            tail = newTail;
        }
    }


    /**
     * Adds all elements from another list at the front of this linked list.
     */
    public void prepend(DoubleLinkedList<T> other) throws ValueException {
        if (other == null) throw new ValueException();
        int n = other.count;
        Node p = other.tail;
        for (int i = 0; i < n; i++) {
            prepend(p.value);
            p = p.previous;
        }
    }

    /**
     * Adds all elements from another list at the back of this linked list.
     */
    public void append(DoubleLinkedList<T> other) throws ValueException {
        if (other == null) throw new ValueException();
        int n = other.count;
        Node p = other.head;
        for (int i = 0; i < n; i++) {
            append(p.value);
            p = p.next;
        }
    }


    /**
     * Clones this DoubleLinkedList instance and returns an exact COPY.
     */
    public DoubleLinkedList <T> clone() {
        try {
            return new DoubleLinkedList<>(this);
        } catch (ValueException e) {        // unreachable: this list cannot be null, but constructor throws exception
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns true if the other list is equal to this one, false otherwise.
     * The contents of the two lists must not be changed!
     */
    public boolean equals(DoubleLinkedList<T> other) throws ValueException {
        if (other == null) throw new ValueException();
        if (this.count != other.count) return false;
        if (this.head == null && other.head == null) return true;
        boolean allElementsEqual = false;
        Node current = this.head;
        Node currentOther = other.head;
        while (current != null) {
            allElementsEqual = current.value.compareTo(currentOther.value)==0;
            if (!allElementsEqual) return false;
            current = current.next;
            currentOther = currentOther.next;
        }
        return allElementsEqual;
    }


    /**
     * Returns a string representation of the list. Example:
     * List of size 5: 1 -> 2 -> 3 -> 4 -> 5
     */
    public String toString() {
        Node p = head;
        StringBuilder s = new StringBuilder();
        while (p != null) {
            s.append(p.value);
            if (p.next != null) {
                s.append("<->");
            }
            p = p.next;
        }
        return s.toString();
    }

    /**
     * Returns true if the element val exists in the list, false otherwise.
     */
    public boolean search(T elem) throws InvalidAccessException {
        if (elem == null) throw new InvalidAccessException();
        Node p = head;
        while (p != null) {
            if (p.value.compareTo(elem)==0) return true;
            p = p.next;
        }
        return false;
    }


}