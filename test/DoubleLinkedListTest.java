import exceptions.InvalidAccessException;
import exceptions.ValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> empty;
    private DoubleLinkedList<Integer> list; // 1, 2, 3

    @BeforeEach
    void setUp() throws ValueException {
        empty = new DoubleLinkedList<>();
        list = new DoubleLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
    }

    @Test
    void defaultConstructor() {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<>();
        assertEquals(0, l.size());
    }

    @Test
    void copyConstructor() throws Exception {
        DoubleLinkedList<Integer> copy = new DoubleLinkedList<>(list);
        assertEquals(3, copy.size());
        assertEquals(1, copy.get(0));
        assertEquals(2, copy.get(1));
        assertEquals(3, copy.get(2));

        // mutating the copy must not affect the original
        copy.append(99);
        assertEquals(3, list.size());

        // null other throws
        assertThrows(ValueException.class, () -> new DoubleLinkedList<Integer>(null));
    }

    @Test
    void clear() throws Exception {
        list.clear();
        assertEquals(0, list.size());
        assertThrows(InvalidAccessException.class, () -> list.peekFront());
        assertThrows(InvalidAccessException.class, () -> list.peekBack());

        // list still usable after clear
        list.append(42);
        assertEquals(1, list.size());
        assertEquals(42, list.peekFront());
    }

    @Test
    void prepend() throws Exception {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<>();
        l.prepend(2);
        l.prepend(1);
        assertEquals(2, l.size());
        assertEquals(1, l.peekFront());
        assertEquals(2, l.peekBack());

        assertThrows(ValueException.class, () -> empty.prepend((Integer) null));
    }

    @Test
    void append() throws Exception {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<>();
        l.append(1);
        l.append(2);
        assertEquals(2, l.size());
        assertEquals(1, l.peekFront());
        assertEquals(2, l.peekBack());

        assertThrows(ValueException.class, () -> empty.append((Integer) null));
    }

    @Test
    void get() throws Exception {
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));

        assertThrows(InvalidAccessException.class, () -> list.get(-1));
        assertThrows(InvalidAccessException.class, () -> list.get(3));
        assertThrows(InvalidAccessException.class, () -> empty.get(0));
    }

    @Test
    void popFront() throws Exception {
        assertEquals(1, list.popFront());
        assertEquals(2, list.size());
        assertEquals(2, list.peekFront());

        // pop until empty
        list.popFront();
        list.popFront();
        assertEquals(0, list.size());

        assertThrows(InvalidAccessException.class, () -> empty.popFront());
        assertThrows(InvalidAccessException.class, () -> list.popFront());
    }

    @Test
    void peekFront() throws Exception {
        assertEquals(1, list.peekFront());
        assertEquals(3, list.size()); // peek must not remove

        assertThrows(InvalidAccessException.class, () -> empty.peekFront());
    }

    @Test
    void popBack() throws Exception {
        assertEquals(3, list.popBack());
        assertEquals(2, list.size());
        assertEquals(2, list.peekBack());

        // pop until empty
        list.popBack();
        list.popBack();
        assertEquals(0, list.size());

        assertThrows(InvalidAccessException.class, () -> empty.popBack());
        assertThrows(InvalidAccessException.class, () -> list.popBack());
    }

    @Test
    void peekBack() throws Exception {
        assertEquals(3, list.peekBack());
        assertEquals(3, list.size()); // peek must not remove

        assertThrows(InvalidAccessException.class, () -> empty.peekBack());
    }

    @Test
    void size() throws Exception {
        assertEquals(0, empty.size());
        assertEquals(3, list.size());

        list.append(4);
        assertEquals(4, list.size());

        list.popFront();
        assertEquals(3, list.size());

        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void reverse() throws Exception {
        list.reverse();
        assertEquals(3, list.peekFront());
        assertEquals(1, list.peekBack());
        assertEquals(3, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(1, list.get(2));

        // reversing twice yields the original order
        list.reverse();
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));

        // empty reverse is a no-op
        empty.reverse();
        assertEquals(0, empty.size());

        // single-element reverse is a no-op
        DoubleLinkedList<Integer> single = new DoubleLinkedList<>();
        single.append(42);
        single.reverse();
        assertEquals(42, single.peekFront());
        assertEquals(42, single.peekBack());
        assertEquals(1, single.size());
    }

    @Test
    void testPrepend() throws Exception {
        DoubleLinkedList<Integer> other = new DoubleLinkedList<>();
        other.append(10);
        other.append(20);

        list.prepend(other);
        assertEquals(5, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(1, list.get(2));
        assertEquals(2, list.get(3));
        assertEquals(3, list.get(4));

        // other must not be mutated
        assertEquals(2, other.size());

        // null throws
        assertThrows(ValueException.class, () -> list.prepend((DoubleLinkedList<Integer>) null));

        // self-prepend must terminate (no infinite loop) and double the list
        DoubleLinkedList<Integer> self = new DoubleLinkedList<>();
        self.append(1);
        self.append(2);
        self.prepend(self);
        assertEquals(4, self.size());
        assertEquals(1, self.get(0));
        assertEquals(2, self.get(1));
        assertEquals(1, self.get(2));
        assertEquals(2, self.get(3));
    }

    @Test
    void testAppend() throws Exception {
        DoubleLinkedList<Integer> other = new DoubleLinkedList<>();
        other.append(10);
        other.append(20);

        list.append(other);
        assertEquals(5, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(10, list.get(3));
        assertEquals(20, list.get(4));

        // other must not be mutated
        assertEquals(2, other.size());

        // null throws
        assertThrows(ValueException.class, () -> list.append((DoubleLinkedList<Integer>) null));

        // self-append must terminate (no infinite loop) and double the list
        DoubleLinkedList<Integer> self = new DoubleLinkedList<>();
        self.append(1);
        self.append(2);
        self.append(self);
        assertEquals(4, self.size());
        assertEquals(1, self.get(0));
        assertEquals(2, self.get(1));
        assertEquals(1, self.get(2));
        assertEquals(2, self.get(3));
    }

    @Test
    void testClone() throws Exception {
        DoubleLinkedList<Integer> copy = list.clone();
        assertNotSame(list, copy);
        assertEquals(list.size(), copy.size());
        assertTrue(list.equals(copy));

        // mutating the clone must not affect the original
        copy.append(99);
        assertEquals(3, list.size());
        assertEquals(4, copy.size());
    }

    @Test
    void testEquals() throws Exception {
        DoubleLinkedList<Integer> sameContents = new DoubleLinkedList<>();
        sameContents.append(1);
        sameContents.append(2);
        sameContents.append(3);
        assertTrue(list.equals(sameContents));

        DoubleLinkedList<Integer> shorter = new DoubleLinkedList<>();
        shorter.append(1);
        shorter.append(2);
        assertFalse(list.equals(shorter));

        DoubleLinkedList<Integer> sameSizeDifferent = new DoubleLinkedList<>();
        sameSizeDifferent.append(1);
        sameSizeDifferent.append(9);
        sameSizeDifferent.append(3);
        assertFalse(list.equals(sameSizeDifferent));

        // two empty lists are equal
        DoubleLinkedList<Integer> empty2 = new DoubleLinkedList<>();
        assertTrue(empty.equals(empty2));

        // a list equals itself
        assertTrue(list.equals(list));

        // null throws
        assertThrows(ValueException.class, () -> list.equals((DoubleLinkedList<Integer>) null));
    }

    @Test
    void testToString() {
        assertEquals("1<->2<->3", list.toString());
        assertEquals("", empty.toString());
    }

    @Test
    void search() throws Exception {
        assertTrue(list.search(1));
        assertTrue(list.search(2));
        assertTrue(list.search(3));
        assertFalse(list.search(99));
        assertFalse(empty.search(1));

        assertThrows(InvalidAccessException.class, () -> list.search(null));
    }

    @Test
    void copyConstructorNullThrows() {
        assertThrows(ValueException.class, () -> new DoubleLinkedList<Integer>(null));
    }

    @Test
    void prependNullValueThrows() {
        assertThrows(ValueException.class, () -> empty.prepend((Integer) null));
        assertThrows(ValueException.class, () -> list.prepend((Integer) null));
    }

    @Test
    void appendNullValueThrows() {
        assertThrows(ValueException.class, () -> empty.append((Integer) null));
        assertThrows(ValueException.class, () -> list.append((Integer) null));
    }

    @Test
    void prependOtherNullThrows() {
        assertThrows(ValueException.class, () -> list.prepend((DoubleLinkedList<Integer>) null));
        assertThrows(ValueException.class, () -> empty.prepend((DoubleLinkedList<Integer>) null));
    }

    @Test
    void appendOtherNullThrows() {
        assertThrows(ValueException.class, () -> list.append((DoubleLinkedList<Integer>) null));
        assertThrows(ValueException.class, () -> empty.append((DoubleLinkedList<Integer>) null));
    }

    @Test
    void getInvalidIndexThrows() {
        assertThrows(InvalidAccessException.class, () -> list.get(-1));
        assertThrows(InvalidAccessException.class, () -> list.get(3));   // == size
        assertThrows(InvalidAccessException.class, () -> list.get(99));  // > size
        assertThrows(InvalidAccessException.class, () -> empty.get(0));
    }

    @Test
    void popFrontEmptyThrows() {
        assertThrows(InvalidAccessException.class, () -> empty.popFront());
    }

    @Test
    void peekFrontEmptyThrows() {
        assertThrows(InvalidAccessException.class, () -> empty.peekFront());
    }

    @Test
    void popBackEmptyThrows() {
        assertThrows(InvalidAccessException.class, () -> empty.popBack());
    }

    @Test
    void peekBackEmptyThrows() {
        assertThrows(InvalidAccessException.class, () -> empty.peekBack());
    }

    @Test
    void equalsNullThrows() {
        assertThrows(ValueException.class, () -> list.equals((DoubleLinkedList<Integer>) null));
        assertThrows(ValueException.class, () -> empty.equals((DoubleLinkedList<Integer>) null));
    }

    @Test
    void searchNullThrows() {
        assertThrows(InvalidAccessException.class, () -> list.search(null));
        assertThrows(InvalidAccessException.class, () -> empty.search(null));
    }
}
