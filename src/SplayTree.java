import java.util.*;

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
        Node<T> parentInsertPlace;
        Node<T> elementPlace = root;
        while (elementPlace != null) {
            parentInsertPlace = elementPlace;
            if (value.compareTo(elementPlace.value) < 0) {
                elementPlace = elementPlace.left;
            } else if (value.compareTo(elementPlace.value) > 0) {
                elementPlace = elementPlace.right;
            }
            Node<T> insertElem = new Node<>(value);
            insertElem.parent = parentInsertPlace;
            if (parentInsertPlace == null) root = insertElem;
            else if (insertElem.value.compareTo(parentInsertPlace.value) < 0) {
                parentInsertPlace.left = insertElem;
            } else if (insertElem.value.compareTo(parentInsertPlace.value) > 0) {
                parentInsertPlace.right = insertElem;
            }
            splay(insertElem);
            size++;
            return true;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        int result = 0;
        for (T element : c) {
            if (add(element)) result++;
        }
        return (result > 0);
    }

    //retain alll??????????????---------------------------
    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (!remove(o)) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    private Node<T> find(T value) {
        Node<T> current = root;
        while (current != null) {
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                splay(current);
                return current;
            }
        }
        return null;
    }

    private Node<T> merge(Node<T> tree1, Node<T> tree2) {
        if (tree1 == null || tree2 == null) {
            if (tree1 == null) {
                return tree2;
            }
            return tree1;
        } else {
            tree1 = find(maxNode(tree1).value);
            tree1.right = tree2;
            return tree1;
        }
    }

    private void splay(Node<T> element) {
        if (isRoot(element)) return;
        Node<T> parent = element.parent;
        Node<T> gparent = parent.parent;

        if (gparent == null) {
            if (parent.left == element) {
                zig(parent, element);
            }
            if (parent.right == element) {
                zag(parent, element);
            } else {
                if (gparent.left == parent && parent.left == element) {
                    zigZig(gparent, parent);
                    zigZig(parent, element);
                } else if (gparent.right == parent && parent.right == element) {
                    zagZag(gparent, parent);
                    zagZag(parent, element);
                } else if (gparent.right == parent && parent.left == element) {
                    zagZig(gparent, parent, element);
                } else if (gparent.left == parent && parent.right == element) {
                    zigZag(gparent, parent, element);
                }
            }
        }
    }

    private void zig(Node<T> parent, Node<T> node) {
        Node<T> right = node.right;
        node.right = parent;
        node.parent = null;
        root = node;
        parent.parent = node;
        parent.left = right;
        if (right != null) {
            right.parent = parent;
        }
    }

    private void zag(Node<T> parent, Node<T> node) {
        Node<T> left = node.left;
        node.left = parent;
        node.parent = null;
        root = node;
        parent.parent = node;
        parent.right = left;
        if (left != null) {
            left.parent = parent;
        }
    }

    private void zigZig(Node<T> parent, Node<T> node) {
        Node<T> right = node.right;
        node.right = parent;
        node.parent = parent.parent;
        //setparent??
        parent.parent = node;
        parent.left = right;
        if (right != null) right.parent = parent;
        if (node.parent == null) root = node;

    }

    private void zagZag(Node<T> parent, Node<T> node) {
        Node<T> left = node.left;
        node.left = parent;
        node.parent = parent.parent;
        setParent(parent, node);
        parent.parent = node;
        parent.left = left;
        if (left != null) left.parent = parent;
        if (node.parent == null) root = node;

    }

    private void zigZag(Node<T> gparent, Node<T> parent, Node<T> node) {
        Node<T> left = node.left;
        gparent.left = node;
        node.parent = gparent;
        setParent(parent, node);
        node.left = parent;
        parent.parent = node;
        parent.right = left;
        if (left != null) left.parent = parent;
        zigZig(gparent, node);
    }

    private void zagZig(Node<T> gparent, Node<T> parent, Node<T> node) {
        Node<T> right = node.right;
        gparent.right = node;
        node.parent = gparent;
        node.right = parent;
        parent.parent = node;
        parent.left = right;
        if (right != null) right.parent = parent;
        zigZig(gparent, node);
    }

    private void setParent(Node<T> previous, Node<T> current) {
        if (current.parent != null) {
            if (previous.parent.left == previous) previous.parent.left = current;
            else previous.parent.right = current;
        }
    }

    private Node<T> maxNode(Node<T> node) {
        if (node.right != null) {
            return maxNode(node.right);
        } else {
            return node;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
    /*
    @Override
    public void forEach(Consumer<? super T> action) {
    }*/

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
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && closest.value.compareTo(t) == 0;
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
        Iterator<T> iterator = new SplayTreeIterator();
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
