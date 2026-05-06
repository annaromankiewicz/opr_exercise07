import exceptions.InvalidAccessException;
import exceptions.ValueException;

import java.util.Comparator;

// answer to question which toString() method is used: inherited Method of java.util.AbstractCollection
// "Returns a string representation of this collection. The string representation consists of a list
// of the collection's elements in the order they are returned by its iterator, enclosed in square brackets ("[]").
// Adjacent elements are separated by the characters ", " (comma and space).
// Elements are converted to strings as by String.valueOf(Object)." (docs.oracle)


public class SortableList<T extends Comparable<T>> extends
        RandomAccessDoubleLinkedList<T> implements Sortable<T> {

    public SortableList() {
        super();
    }

    public SortableList(SortableList<T> other) throws ValueException {
        super(other);
    }

    // Insert one element into the Sortable and do so either in ascending or descending fashion
    @Override
    public void insertSorted (T value, boolean ascending) throws ValueException, InvalidAccessException {
        if (value == null) throw new ValueException();

        for (T elem: this) {
            if (ascending) {
                if (value.compareTo(elem) <= 0) {
                    super.insertAt(indexOf(elem), value);
                    return;
                }
            } else {
                if (value.compareTo(elem) > 0) {
                    super.insertAt(indexOf(elem), value);
                    return;
                }
            }
        }
        super.addLast(value);
    }

    @Override
    public Sortable<T> sortAscending() {
        try {
            SortableList <T> sl = new SortableList<>(this);
            sl.sort(Comparator.naturalOrder());
            return sl;
        } catch (ValueException e) {
            throw new AssertionError("unreachable: copy constructor cannot fail on a valid list", e);   // unreachable code, but exception which is thrown in insert at and constructer has to be catched
        }

    }

    @Override
    public Sortable<T> sortDescending() {
        try {
            SortableList <T> sl = new SortableList<>(this);
            sl.sort(Comparator.reverseOrder());
            return sl;
        } catch (ValueException e) {
            throw new AssertionError("unreachable: copy constructor cannot fail on a valid list", e);
        }

    }
}