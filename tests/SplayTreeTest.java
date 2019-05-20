import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class SplayTreeTest {
    private List listIn1;
    private List listIn2;
    private SortedSet<Integer> splayTree;
    private SortedSet<Integer> treeSet;


    void init() {
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        listIn1 = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13);
        splayTree.addAll(listIn1);
    }

    @Test
    public void add() {
        init();
        splayTree.add(2);
        splayTree.add(76);
        splayTree.add(32);
        splayTree.add(0);
        assertTrue(splayTree.contains(2));
        assertTrue(splayTree.contains(76));
        assertTrue(splayTree.contains(32));
        assertTrue(splayTree.contains(0));
    }

    @Test
    public void remove() {
        init();
        splayTree.remove(14);
        assertFalse(splayTree.contains(14));
        assertEquals(listIn1.size() - 1, splayTree.size());
        splayTree.remove(10);
        assertFalse(splayTree.contains(10));
        assertEquals(listIn1.size() - 2, splayTree.size());
    }

    @Test
    public void addAll() {
        init();
        assertTrue(splayTree.contains(10));
        assertTrue(splayTree.contains(11));
        assertTrue(splayTree.contains(14));
        assertTrue(splayTree.contains(18));
        assertTrue(splayTree.contains(15));
        assertTrue(splayTree.contains(20));
        assertTrue(splayTree.contains(13));
        assertTrue(splayTree.contains(9));
        assertTrue(splayTree.contains(7));
        assertTrue(splayTree.contains(8));
        assertTrue(splayTree.contains(12));
    }

    @Test
    public void removeAll() {
    }

    @Test
    public void clear() {
        splayTree.clear();
    }

    @Test
    public void containsAll() {
        boolean bool = splayTree.containsAll(listIn1);
        assertTrue(bool);
    }

    @Test
    public void iterator() {
        init();
        Iterator iterator = splayTree.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertEquals(8, iterator.next());
        assertEquals(9, iterator.next());
        assertEquals(10, iterator.next());
        assertEquals(11, iterator.next());
        assertEquals(12, iterator.next());
        assertEquals(13, iterator.next());
        assertEquals(14, iterator.next());
        assertEquals(15, iterator.next());
        assertEquals(18, iterator.next());
        assertEquals(20, iterator.next());
    }

    @Test
    public void size() {
    }

    @Test
    public void toArray() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void comparator() {
    }

    @Test
    public void subSet() {
    }

    @Test
    public void headSet() {
    }

    @Test
    public void tailSet() {
    }

    @Test
    public void first() {
    }

    @Test
    public void last() {
    }

    @Test
    public void toString1() {
    }

    @Test
    public void retainAll() {
        splayTree.retainAll(listIn1);
        assertTrue(splayTree.containsAll(listIn1));
        assertFalse(splayTree.contains(10));
        assertFalse(splayTree.contains(11));
        assertFalse(splayTree.contains(14));
        assertFalse(splayTree.contains(18));
        assertFalse(splayTree.contains(15));
        splayTree.clear();
        splayTree.addAll(listIn1);
        assertFalse(splayTree.retainAll(listIn1));
    }
}