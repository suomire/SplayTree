import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SplayTreeTest {
    private List<Integer> list;
    private List<Integer> listIn2;
    private SortedSet<Integer> splayTree;
    private SortedSet<Integer> treeSet;

    void createRandomList() {
        list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < r.nextInt(100); i++) {
            list.add(r.nextInt(100));
        }

    }


    void init() {
        createRandomList();
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        for (Integer element : list) {
            splayTree.add(element);
            treeSet.add(element);
        }
    }

    @Test
    public void add() {
        init();
        assertTrue(splayTree.containsAll(list));
        splayTree.add(2231);
        assertTrue(splayTree.contains(2231));
        splayTree.add(1000000);
        assertTrue(splayTree.contains(1000000));
    }

    @Test
    public void remove() {
        for (int i = 0; i < 1000; i++) {
            init();
            Random r = new Random();
            if(!list.isEmpty()) {
                int toRemove = list.get(r.nextInt(list.size()));
                //System.out.println("removing " + toRemove + " from list : " + splayTree.toString());
                splayTree.remove(toRemove);
                treeSet.remove(toRemove);
                //System.out.println("After removing " + toRemove + " from list : " + splayTree.toString());
                assertTrue(treeSet.containsAll(splayTree));
            }
        }
    }

    @Test
    //ужас!!!!!
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
        splayTree.removeAll(list);
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
        boolean bool = splayTree.containsAll(list);
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
        assertFalse(splayTree.retainAll(list));
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
        splayTree.removeAll(list);
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
        treeSet.addAll(list);
        SortedSet<Integer> standart = treeSet.subSet(9, 12);
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
        treeSet.addAll(list);
        SortedSet<Integer> standart = treeSet.headSet(12);
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
        treeSet.addAll(list);
        SortedSet<Integer> standart = treeSet.tailSet(12);
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