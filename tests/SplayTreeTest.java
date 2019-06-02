import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SplayTreeTest {
    private List<Integer> list;
    private List<Integer> listIn2;
    private SortedSet<Integer> splayTree;
    private SortedSet<Integer> treeSet;

    private void createRandomList() {
        list = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < r.nextInt(100); i++) {
            list.add(r.nextInt(100));
        }

    }


    private void init() {
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
        for (int i = 0; i < 10000; i++) {
            init();
            Random r = new Random();
            int randomValue = r.nextInt(10) * 1000 + r.nextInt(999);
            splayTree.add(randomValue);
            treeSet.add(randomValue);
            assertTrue(splayTree.contains(randomValue));
            assertTrue(splayTree.containsAll(list));
            assertTrue(treeSet.containsAll(splayTree));
        }
    }

    @Test
    public void remove() {
        for (int i = 0; i < 10000; i++) {
            init();
            Random r = new Random();
            if (!list.isEmpty()) {
                int toRemove = list.get(r.nextInt(list.size()));
                //int toRemove = splayTree.last();
                System.out.println("removing " + toRemove + " from list : " + splayTree.toString());
                splayTree.remove(toRemove);
                treeSet.remove(toRemove);
                System.out.println("After removing " + toRemove + " from list : " + splayTree.toString());
                assertEquals(treeSet.size(), splayTree.size());
                assertTrue(treeSet.containsAll(splayTree));
                assertTrue(splayTree.containsAll(treeSet));
            }
        }
    }

    //донт понятно
    @Test
    public void removeAll() {
        for (int j = 0; j < 10; j++) {
            init();
            List<Integer> listForRemove = new ArrayList<>();
            for (int i = 0; i < list.size() / 2; i++) {
                listForRemove.add(list.get(i));
            }
            System.out.println("removing " + listForRemove.toString() + " from list : " + splayTree.toString());
            splayTree.removeAll(listForRemove);
            treeSet.removeAll(listForRemove);
            System.out.println("After removing "
                    + " from splaytree : " + splayTree.toString());
            System.out.println("After removing "
                    + " from treeset : " + treeSet.toString());
            assertEquals(treeSet.size(), splayTree.size());
            assertTrue(treeSet.containsAll(splayTree));
            assertTrue(splayTree.containsAll(treeSet));
        }
    }

    @Test
    public void containsAll() {
        for (int i = 0; i < 10000; i++) {
            init();
            assertTrue(treeSet.containsAll(splayTree));
            assertTrue(splayTree.containsAll(treeSet));
        }
    }

    @Test
    public void iterator() {
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        list = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13, 9, 7, 8, 12);
        splayTree.addAll(list);
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
    public void subSet() {
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        list = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13, 9, 7, 8, 12);
        splayTree.addAll(list);
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
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        list = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13, 9, 7, 8, 12);
        splayTree.addAll(list);
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
        splayTree = new SplayTree<>();
        treeSet = new TreeSet<>();
        list = Arrays.asList(10, 11, 14, 18, 15, 20, 13, 9, 7, 8, 12);
        listIn2 = Arrays.asList(20, 13, 9, 7, 8, 12);
        splayTree.addAll(list);
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


}