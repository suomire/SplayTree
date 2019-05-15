import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SplayTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {


    private static class Node<T> {
        final T value;
        Node<T> left = null;
        Node<T> right = null;
        Node<T> parent = null;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root;
    private int size = 0;

    public boolean isRoot(Node<T> node) {
        return node == root;
    }


    public boolean add(T value) {
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return true;
    }

    private Node<T> find(T value) {
        return null;
    }

    private Node<T> merge(Node<T> tree1, Node<T> tree2) {
        return null;
    }

    private void splay(Node<T> element) {
    }

    private void rightMoveToRoot(Node<T> parent, Node<T> node) {
    }

    private void leftMoveToRoot(Node<T> parent, Node<T> node) {
    }

    private void rotateLeft(Node<T> parent, Node<T> node) {
    }

    private void rotateRight(Node<T> parent, Node<T> node) {
    }

    private void rightZigZag(Node<T> gparent, Node<T> parent, Node<T> node) {
    }

    private void leftZigZag(Node<T> gparent, Node<T> parent, Node<T> node) {
    }

    private void setParent(Node<T> previous, Node<T> current) {

    }

    private Node<T> maxNode(Node<T> vertex) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Iterator<T> iterator = this.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            array[i] = iterator.next();
            i++;
        }
        return array;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object o) {
        return true;
    }


    public class SplayTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        Node<T> next = findNext();

        private Node<T> findNext() {
            current = next;
            if (next == null) {
                next = first();
                return next;
            }

            if (next.right != null) {
                next = next.right;
                while (next.left != null) next = next.left;
                return next;
            }

            while (next.parent != null) {
                if (next.parent.left == next) {
                    next = next.parent;
                    return next;
                }
                next = next.parent;
            }

            return null;

        }

        private Node<T> first() {
            if (root == null) throw new NoSuchElementException();
            Node<T> result = root;
            while (result.left != null) {
                result = result.left;
            }
            return result;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (next == null) throw new NoSuchElementException();
            current = next;
            next = findNext();
            return current.value;
        }

        @Override
        public void remove() {
            SplayTree.this.remove(current.value);
        }
    }


    /*@Override
    public boolean removeIf(Predicate<? super T> filter) {
        return false;
    }

     */

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }


    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    @Override
    public String toString() {
        Iterator iterator = new SplayTreeIterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
