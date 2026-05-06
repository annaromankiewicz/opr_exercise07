import exceptions.InvalidAccessException;
import exceptions.ValueException;

import java.util.ArrayList;

public class RandomAccessDoubleLinkedList<T extends Comparable<T>> extends ArrayList<T> {
    /**
     * Initializes an empty list.
     */
    public RandomAccessDoubleLinkedList() {
        super();
    }


    /**
     * Copy constructor which initializes the list with another list.
     * This constructor must COPY all elements of the other list.
     */
    public RandomAccessDoubleLinkedList(RandomAccessDoubleLinkedList<T> other) throws ValueException {
        super();
        if (other == null) throw new ValueException("other is null");
        int size = other.size();
        try {
            for (int index = 0; index < size; index++) {
                super.add(other.elementAt(index));
            }
        } catch (InvalidAccessException e) {            // unreachable error - InvalidAccessException has to be handled here
            throw new AssertionError("unreachable: copy constructor cannot fail on a valid list", e);
        }


    }

    /**
     * Inserts a new element with value val at the given index. If the
     * index is larger than the current size, the element is added at the
     * last position in the list. Should index be < 0, then do nothing.
     */
    public void insertAt(int index, T val) throws ValueException, InvalidAccessException { // if a value is already in list, at given index the list moves this value one position to the right
        if (index < 0) throw new InvalidAccessException("Index is negative");
        if (val == null) throw new ValueException("val is null");
        if (index < super.size()) {
            super.add(index, val);
        } else {
            super.addLast(val);
        }

    }


    /**
     * Returns true if an element with the given value exists, false
     * otherwise. However, true is returned upon the first occurrence of
     * val.
     */
    public boolean contains(T val) throws InvalidAccessException {
        if (val == null) throw new InvalidAccessException("val is null");
        return super.contains(val);
    }


    /**
     * Removes the element at the given index and
     * throws an InvalidAccessException if index < 0
     * or index > list's size
     */
    public void removeAt(int index) throws InvalidAccessException {     // signature changed to void because it either works or throws an exception
        if (index < 0) {
            throw new InvalidAccessException("Index is negative", index);
        }
        if (index >= super.size()) {
            throw new InvalidAccessException("Index is out of bounds", index);
        }
        super.remove(index);
    }


    /**
     * Removes all elements with the given value. False if
     * val was not found.
     */
    public boolean removeAll(T val) throws InvalidAccessException {
        boolean removed = false;
        while (contains(val)) { super.remove(val); removed = true; }
        return removed;
    }

    /**
     * Returns the integer value at the given index. If index > list’s
     * size, Integer.MIN_VALUE is returned.
     */
    public T elementAt(int index) throws InvalidAccessException {
        if (index < 0 || index >= super.size()) throw new InvalidAccessException("Index is negative", index);
        return super.get(index);
    }
}