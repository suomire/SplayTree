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
        if (contains(value)) return true;
        Node<T> preInsertPlace = null;
        Node<T> elementPlace = root;
        while (elementPlace != null) {
            preInsertPlace = elementPlace;
            if (value.compareTo(elementPlace.value) < 0) {
                elementPlace = elementPlace.left;
            } else if (value.compareTo(elementPlace.value) > 0) {
                elementPlace = elementPlace.right;
            }
        }
        Node<T> insertElem = new Node<>(value);
        insertElem.parent = preInsertPlace;
        if (preInsertPlace == null) root = insertElem;
        else if (insertElem.value.compareTo(preInsertPlace.value) < 0) {
            preInsertPlace.left = insertElem;
        } else if (insertElem.value.compareTo(preInsertPlace.value) > 0) {
            preInsertPlace.right = insertElem;
        }
        splay(insertElem);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || root == null) return false;
        @SuppressWarnings("unchecked")
        Node<T> t = find((T) o);
        if (t == null) return false;
        root = merge(t.left, t.right);
        size--;
        return true;
    }

    /*@Override
    public boolean addAll(Collection<? extends T> c) {
        int result = 0;
        for (T element : c) {
            if (add(element)) result++;
        }
        return (result > 0);
    }*/

    /*@Override
    @SuppressWarnings("unchecked")
    public boolean retainAll(Collection<?> c) {
        SplayTree<T> set = this;
        List<T> retained = new ArrayList<>();
        for (Object o : c) {
            if (contains(o)) retained.add((T) o);
        }
        clear();
        addAll(retained);
        return !equals(set);
    }*/

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (!remove(o)) return false;
        }
        return true;
    }

    /*@Override
    public void clear() {
        root = null;
    }*/

    /*@Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }*/

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
            tree1.parent = null;
            tree2.parent = tree1;
            return tree1;
        }
    }

    private void splay(Node<T> element) {
        while (!isRoot(element)) {
            if (element.parent == null) return;

            Node<T> parent = element.parent;
            Node<T> gparent = parent.parent;

            if (gparent == null) {
                if (parent.left == element) {
                    zig(parent, element);
                }
                if (parent.right == element) {
                    zag(parent, element);
                }
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
        if (isRoot(parent)) node.parent = null;
        else node.parent = parent.parent;
        setParent(parent, node);
        parent.parent = node;
        parent.left = right;
        if (right != null) right.parent = parent;
        if (node.parent == null) root = node;

    }

    private void zagZag(Node<T> parent, Node<T> node) {
        Node<T> left = node.left;
        node.left = parent;
        if (isRoot(parent)) node.parent = null;
        else node.parent = parent.parent;
        setParent(parent, node);
        parent.parent = node;
        parent.right = left;
        if (left != null) left.parent = parent;
        if (node.parent == null) root = node;

    }

    private void zigZag(Node<T> gparent, Node<T> parent, Node<T> node) {
        Node<T> left = node.left;
        gparent.left = node;
        node.parent = gparent;
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
        zagZag(gparent, node);
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
        return new SplayTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    /*@Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && closest.value.compareTo(t) == 0;
    }*/


    public class SplayTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        Node<T> next = findNext();

        private Node<T> findNext() {
            Node<T> point;
            if (root == null) {
                return null;
            }
            if (current == null) {
                return minimumItemInSubtree(root);
            } else {
                point = current;
            }
            if (point.right != null) {
                return minimumItemInSubtree(point.right);
            } else {
                Node<T> searchPoint = null;
                Node<T> ancestor = root;
                while (ancestor != point && ancestor != null) {
                    int comparison = point.value.compareTo(ancestor.value);
                    if (comparison > 0) {
                        ancestor = ancestor.right;
                    } else {
                        searchPoint = ancestor;
                        ancestor = ancestor.left;
                    }
                }
                return searchPoint;
            }
        }

        private Node<T> minimumItemInSubtree(Node<T> t) {
            if (t.left != null)
                return minimumItemInSubtree(t.left);
            return t;
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

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubSet<>(fromElement, toElement, this);

    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubSet<>(null, toElement, this);
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubSet<>(fromElement, null, this);
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

    private class SubSet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

        private T fromElement; //bottom border
        private T toElement; //up border
        private SplayTree<T> delegate;


        public SubSet(T fromElement, T toElement, SplayTree<T> delegate) {
            this.fromElement = fromElement;
            this.toElement = toElement;
            this.delegate = delegate;
        }

        @Override
        public Comparator<? super T> comparator() {
            return null;
        }

        @Override
        public SortedSet<T> subSet(T fromElement, T toElement) {
            if (checkBounds(fromElement) && checkBounds(toElement)) {
                return new SubSet<>(fromElement, toElement, delegate);
            } else throw new IndexOutOfBoundsException();
        }

        @Override
        public SortedSet<T> headSet(T toElement) {
            if (checkBounds(toElement)) {
                return new SubSet<>(this.fromElement, toElement, delegate);
            } else throw new IndexOutOfBoundsException();
        }

        @Override
        public SortedSet<T> tailSet(T fromElement) {
            if (checkBounds(fromElement)) {
                return new SubSet<>(this.fromElement, null, delegate);
            } else throw new IndexOutOfBoundsException();
        }

        @Override
        public T first() {
            Iterator<T> iterator = delegate.iterator();
            T currentFirst = null;
            while (iterator.hasNext() && currentFirst == null) {
                T nextElement = iterator.next();
                if (checkBounds(nextElement) && this.contains(nextElement)) {
                    currentFirst = nextElement;
                }
            }
            return currentFirst;
        }

        @Override
        public T last() {
            Iterator<T> iterator = delegate.iterator();
            T currentLast = null;
            while (iterator.hasNext()) {
                T nextElement = iterator.next();
                if (checkBounds(nextElement) && this.contains(nextElement)) {
                    currentLast = nextElement;
                }
            }
            return currentLast;
        }

        @Override
        public int size() {
            int size = 0;
            for (T next : delegate) {
                if (checkBounds(next)) size++;
            }
            return size;
        }

        private boolean checkBounds(T value) {
            if (fromElement != null && toElement != null)
                return (toElement.compareTo(value) > 0 && fromElement.compareTo(value) <= 0);
            else if (fromElement == null)
                return (toElement.compareTo(value) > 0);
            else return (fromElement.compareTo(value) <= 0);
        }

        @Override
        public boolean contains(Object o) {
            T value = (T) o;
            for (T t : this) {
                if (value.compareTo(t) == 0) return true;
            }
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return new SubSetIterator();
        }

        @Override
        public boolean add(T t) {
            delegate.add(t);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            T value = (T) o;

            if ((checkBounds(value)) && contains(value))
                delegate.remove(value);
            else throw new IllegalArgumentException();
            return true;
        }


        public class SubSetIterator implements Iterator<T> {

            Iterator<T> iterator = delegate.iterator();
            T current = null;
            T next = findNext();

            private T findNext() {
                if (fromElement != null) {
                    next = fromElement;
                }
                while (iterator.hasNext()) {
                    T nextElement = iterator.next();
                    if (checkBounds(nextElement)) {
                        next = nextElement;
                        return nextElement;
                    }
                }
                return null;
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
                return current;
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        }
    }
}
