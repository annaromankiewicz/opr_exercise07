import exceptions.InvalidAccessException;
import exceptions.ValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortableListTest {

    private SortableList<Integer> empty;
    private SortableList<Integer> list; // 5, 2, 8, 1 (unsorted)

    @BeforeEach
    void setUp() throws InvalidAccessException, ValueException {
        // Requires a default constructor on SortableList: public SortableList() { super(); }
        empty = new SortableList<>();
        list = new SortableList<>();
        list.insertAt(0, 5);
        list.insertAt(1, 2);
        list.insertAt(2, 8);
        list.insertAt(3, 1);
    }

    @Test
    void copyConstructor() throws InvalidAccessException, ValueException {
        SortableList<Integer> copy = new SortableList<>(list);
        assertEquals(list.size(), copy.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.elementAt(i), copy.elementAt(i));
        }

        // mutating the copy must not affect the original
        copy.insertAt(0, 99);
        assertEquals(4, list.size());
        assertEquals(5, copy.size());
    }

    @Test
    void insertSortedAscendingBuildsSortedList() throws Exception {
        SortableList<Integer> l = new SortableList<>();
        l.insertSorted(3, true);
        l.insertSorted(1, true);
        l.insertSorted(4, true);
        l.insertSorted(2, true);
        l.insertSorted(5, true);
        l.insertSorted(0, true);

        assertEquals(6, l.size());
        assertEquals(0, l.elementAt(0));
        assertEquals(1, l.elementAt(1));
        assertEquals(2, l.elementAt(2));
        assertEquals(3, l.elementAt(3));
        assertEquals(4, l.elementAt(4));
        assertEquals(5, l.elementAt(5));
    }

    @Test
    void insertSortedDescendingBuildsSortedList() throws Exception {
        SortableList<Integer> l = new SortableList<>();
        l.insertSorted(3, false);
        l.insertSorted(1, false);
        l.insertSorted(4, false);
        l.insertSorted(2, false);
        l.insertSorted(5, false);

        assertEquals(5, l.size());
        assertEquals(5, l.elementAt(0));
        assertEquals(4, l.elementAt(1));
        assertEquals(3, l.elementAt(2));
        assertEquals(2, l.elementAt(3));
        assertEquals(1, l.elementAt(4));
    }

    @Test
    void insertSortedIntoEmpty() throws Exception {
        empty.insertSorted(42, true);
        assertEquals(1, empty.size());
        assertEquals(42, empty.elementAt(0));
    }

    @Test
    void insertSortedHandlesDuplicates() throws Exception {
        SortableList<Integer> l = new SortableList<>();
        l.insertSorted(2, true);
        l.insertSorted(2, true);
        l.insertSorted(2, true);
        assertEquals(3, l.size());
        assertEquals(2, l.elementAt(0));
        assertEquals(2, l.elementAt(1));
        assertEquals(2, l.elementAt(2));
    }

    @Test
    void insertSortedNullThrows() {
        assertThrows(ValueException.class, () -> empty.insertSorted(null, true));
        assertThrows(ValueException.class, () -> empty.insertSorted(null, false));
    }

    @Test
    void sortAscending() throws Exception {
        Sortable<Integer> sorted = list.sortAscending();
        assertNotNull(sorted);

        SortableList<Integer> result = (SortableList<Integer>) sorted;
        assertEquals(4, result.size());
        assertEquals(1, result.elementAt(0));
        assertEquals(2, result.elementAt(1));
        assertEquals(5, result.elementAt(2));
        assertEquals(8, result.elementAt(3));

        // contract says "Create a new Sortable" — original must not be mutated
        assertEquals(5, list.elementAt(0));
        assertEquals(2, list.elementAt(1));
        assertEquals(8, list.elementAt(2));
        assertEquals(1, list.elementAt(3));
    }

    @Test
    void sortDescending() throws Exception {
        Sortable<Integer> sorted = list.sortDescending();
        assertNotNull(sorted);

        SortableList<Integer> result = (SortableList<Integer>) sorted;
        assertEquals(4, result.size());
        assertEquals(8, result.elementAt(0));
        assertEquals(5, result.elementAt(1));
        assertEquals(2, result.elementAt(2));
        assertEquals(1, result.elementAt(3));

        // original unchanged
        assertEquals(5, list.elementAt(0));
        assertEquals(1, list.elementAt(3));
    }

    @Test
    void sortEmptyList() throws Exception {
        Sortable<Integer> sortedAsc = empty.sortAscending();
        Sortable<Integer> sortedDesc = empty.sortDescending();
        assertEquals(0, ((SortableList<Integer>) sortedAsc).size());
        assertEquals(0, ((SortableList<Integer>) sortedDesc).size());
    }

    @Test
    void sortAlreadySortedList() throws Exception {
        SortableList<Integer> l = new SortableList<>();
        l.insertAt(0, 1);
        l.insertAt(1, 2);
        l.insertAt(2, 3);

        SortableList<Integer> asc = (SortableList<Integer>) l.sortAscending();
        assertEquals(1, asc.elementAt(0));
        assertEquals(2, asc.elementAt(1));
        assertEquals(3, asc.elementAt(2));
    }

    @Test
    void copyConstructorNullThrows() {
        assertThrows(ValueException.class, () -> new SortableList<Integer>(null));
    }

    @Test
    void insertSortedNullValueAscendingThrows() {
        assertThrows(ValueException.class, () -> empty.insertSorted(null, true));
        assertThrows(ValueException.class, () -> list.insertSorted(null, true));
    }

    @Test
    void insertSortedNullValueDescendingThrows() {
        assertThrows(ValueException.class, () -> empty.insertSorted(null, false));
        assertThrows(ValueException.class, () -> list.insertSorted(null, false));
    }

    @Test
    void insert() {
        SortableList<Integer> l = new SortableList<Integer>();
        l.add(42); // First element
        l.add(5); // Second element
        try {
            l.insertSorted(3, true); // Will be the first element, before 42
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(l.toString());

        Sortable sl = l.sortAscending();
        System.out.println(sl.toString());

    }

}
