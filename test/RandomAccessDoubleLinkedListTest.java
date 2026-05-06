import exceptions.InvalidAccessException;
import exceptions.ValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomAccessDoubleLinkedListTest {

    private RandomAccessDoubleLinkedList<Integer> empty;
    private RandomAccessDoubleLinkedList<Integer> list; // 1, 2, 3

    @BeforeEach
    void setUp() throws InvalidAccessException, ValueException {
        empty = new RandomAccessDoubleLinkedList<>();
        list = new RandomAccessDoubleLinkedList<>();
        list.insertAt(0, 1);
        list.insertAt(1, 2);
        list.insertAt(2, 3);
    }

    @Test
    void defaultConstructor() {
        RandomAccessDoubleLinkedList<Integer> l = new RandomAccessDoubleLinkedList<>();
        assertEquals(0, l.size());
    }

    @Test
    void copyConstructor() throws InvalidAccessException, ValueException {
        RandomAccessDoubleLinkedList<Integer> copy = new RandomAccessDoubleLinkedList<>(list);
        assertEquals(list.size(), copy.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.elementAt(i), copy.elementAt(i));
        }

        // mutating the copy must not affect the original
        copy.insertAt(0, 99);
        assertEquals(3, list.size());
        assertEquals(4, copy.size());

        // null other throws an exception
        RandomAccessDoubleLinkedList<Integer> fromNull;
        assertThrows(ValueException.class, () -> new RandomAccessDoubleLinkedList<>(null));
    }

    @Test
    void insertAt() throws InvalidAccessException, ValueException {
        // insert in the middle: existing element shifts right
        list.insertAt(1, 9);
        assertEquals(4, list.size());
        assertEquals(1, list.elementAt(0));
        assertEquals(9, list.elementAt(1));
        assertEquals(2, list.elementAt(2));
        assertEquals(3, list.elementAt(3));

        // index larger than size appends at the end
        list.insertAt(99, 7);
        assertEquals(5, list.size());
        assertEquals(7, list.elementAt(4));

        // insert at front
        list.insertAt(0, 0);
        assertEquals(0, list.elementAt(0));

        // insert into empty
        empty.insertAt(0, 42);
        assertEquals(1, empty.size());
        assertEquals(42, empty.elementAt(0));

        // negative index throws
        assertThrows(InvalidAccessException.class, () -> list.insertAt(-1, 5));
    }

    @Test
    void contains() throws InvalidAccessException {
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
        assertFalse(list.contains(99));
        assertFalse(empty.contains(1));
    }

    @Test
    void removeAt() throws InvalidAccessException {
        // remove from the middle
        list.removeAt(1);
        assertEquals(2, list.size());
        assertEquals(1, list.elementAt(0));
        assertEquals(3, list.elementAt(1));

        // remove from front
        list.removeAt(0);
        assertEquals(1, list.size());
        assertEquals(3, list.elementAt(0));

        // negative index or index out of bounds throws
        assertThrows(InvalidAccessException.class, () -> list.removeAt(-1));


        assertThrows(InvalidAccessException.class, () -> list.removeAt(99));


        assertThrows(InvalidAccessException.class, () -> empty.removeAt(0));

    }



    @Test
    void removeAll() throws InvalidAccessException, ValueException {
        // make a list with duplicates: 1, 2, 3, 2
        list.insertAt(99, 2);
        assertEquals(4, list.size());

        assertTrue(list.removeAll(2));
        assertEquals(2, list.size());
        assertEquals(1, list.elementAt(0));
        assertEquals(3, list.elementAt(1));

        // value not present returns false
        assertFalse(list.removeAll(99));
        assertFalse(empty.removeAll(1));
    }

    @Test
    void elementAt() throws InvalidAccessException {
        assertEquals(1, list.elementAt(0));
        assertEquals(2, list.elementAt(1));
        assertEquals(3, list.elementAt(2));

        assertThrows(InvalidAccessException.class, () -> list.elementAt(-1));
        assertThrows(InvalidAccessException.class, () -> list.elementAt(3));
        assertThrows(InvalidAccessException.class, () -> empty.elementAt(0));
    }

    @Test
    void insertAtNullValueThrows() {
        assertThrows(ValueException.class, () -> list.insertAt(0, null));
        assertThrows(ValueException.class, () -> empty.insertAt(0, null));
    }

    @Test
    void insertAtNegativeIndexThrowsBeforeNullCheck() {
        // negative index is checked first, so a negative index with null value
        // still surfaces as InvalidAccessException
        assertThrows(InvalidAccessException.class, () -> list.insertAt(-1, null));
    }

    @Test
    void containsNullThrows() {
        assertThrows(InvalidAccessException.class, () -> list.contains(null));
        assertThrows(InvalidAccessException.class, () -> empty.contains(null));
    }


    @Test
    void copyConstructorNullThrows() {
        assertThrows(ValueException.class, () -> new RandomAccessDoubleLinkedList<Integer>(null));
    }
}
