import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SplayTreeTest {
    private List listIn1;
    private List listIn2;
    private SortedSet<Integer> splayTree;
    private SortedSet<Integer> set;


    void init() {
        splayTree = new SplayTree<>();
        set = new TreeSet<>();
        listIn1 = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13, 9, 7, 8, 12);
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
        splayTree.remove(11);
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
        init();
        splayTree.removeAll(listIn1);
        assertFalse(splayTree.contains(10));
        assertFalse(splayTree.contains(11));
        assertFalse(splayTree.contains(14));
        assertFalse(splayTree.contains(18));
        assertFalse(splayTree.contains(15));
        assertFalse(splayTree.contains(20));
        assertFalse(splayTree.contains(13));
        assertFalse(splayTree.contains(9));
        assertFalse(splayTree.contains(7));
        assertFalse(splayTree.contains(8));
        assertFalse(splayTree.contains(12));
    }

    @Test
    public void containsAll() {
        init();
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
    public void retainAll() {
        init();
        splayTree.retainAll(listIn2);
        assertTrue(splayTree.containsAll(listIn2));
        assertFalse(splayTree.contains(10));
        assertFalse(splayTree.contains(11));
        assertFalse(splayTree.contains(14));
        assertFalse(splayTree.contains(18));
        assertFalse(splayTree.contains(15));
        splayTree.clear();
        splayTree.addAll(listIn2);
        assertFalse(splayTree.retainAll(listIn1));
    }

    @Test
    public void size() {
    }

    @Test
    public void toArray() {
    }

    @Test
    public void isEmpty() {
        init();
        assertFalse(splayTree.isEmpty());
        splayTree.removeAll(listIn1);
        assertTrue(splayTree.isEmpty());
    }

    @Test
    public void subSet() {
        init();
        SortedSet<Integer> subset = splayTree.subSet(9, 12);
        assertEquals(Optional.of(11), Optional.of(subset.last()));
        assertEquals(Optional.of(9), Optional.of(subset.first()));
        assertEquals(3, subset.size());
        //Inclusive
        assertTrue(subset.contains(9));
        //Exclusive
        assertFalse(subset.contains(12));

        assertFalse(subset.contains(14));
        assertFalse(subset.contains(18));
        assertFalse(subset.contains(15));
        assertFalse(subset.contains(20));
        assertFalse(subset.contains(13));
        assertTrue(subset.contains(10));
        assertTrue(subset.contains(11));
        assertFalse(subset.contains(7));
        assertFalse(subset.contains(8));

        //Сравнение со стандартным классом
        set.addAll(listIn1);
        SortedSet<Integer> standart = set.subSet(9, 12);
        assertEquals(standart, subset);
    }

    @Test
    public void headSet() {
        init();
        SortedSet<Integer> subset = splayTree.headSet(12);
        assertEquals(5, subset.size());
        //Exclusive
        assertFalse(subset.contains(12));

        assertFalse(subset.contains(14));
        assertFalse(subset.contains(18));
        assertFalse(subset.contains(15));
        assertFalse(subset.contains(20));
        assertFalse(subset.contains(13));
        assertTrue(subset.contains(9));
        assertTrue(subset.contains(10));
        assertTrue(subset.contains(11));
        assertTrue(subset.contains(7));
        assertTrue(subset.contains(8));


        //Сравнение со стандартным классом
        set.addAll(listIn1);
        SortedSet<Integer> standart = set.headSet(12);
        assertEquals(standart, subset);
    }

    @Test
    public void tailSet() {
        init();
        SortedSet<Integer> subset = splayTree.tailSet(12);
        //Inclusive
        assertTrue(subset.contains(12));
        //Inclusive
        assertTrue(subset.contains(20));

        assertTrue(subset.contains(14));
        assertTrue(subset.contains(18));
        assertTrue(subset.contains(15));
        assertTrue(subset.contains(13));
        assertFalse(subset.contains(9));
        assertFalse(subset.contains(10));
        assertFalse(subset.contains(11));
        assertFalse(subset.contains(7));
        assertFalse(subset.contains(8));

        //Сравнение со стандартным классом
        set.addAll(listIn1);
        SortedSet<Integer> standart = set.tailSet(12);
        assertEquals(standart, subset);
    }

    @Test
    public void first() {
        init();
        assertEquals(Optional.of(20), Optional.of(splayTree.last()));

    }

    @Test
    public void last() {
        init();
        assertEquals(Optional.of(7), Optional.of(splayTree.first()));
    }

    @Test
    public void toString1() {
        init();
        assertEquals(
                "7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 20",
                splayTree.toString()
        );
    }

}