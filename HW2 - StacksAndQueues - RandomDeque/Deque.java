import java.util.Iterator;

/**
 * Created by Wilm on 9/13/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item val;
        Node prev;
        Node next;

        public Node(Item val) {
            this.val = val;
        }
    }

    private int count = 0;
    private Node first = null;
    private Node last = null;

//    public Deque()                           // construct an empty deque
//    {
//    }

    public boolean isEmpty()                 // is the deque empty?
    {
        return count == 0;
    }

    public int size()                        // return the number of items on the deque
    {
        return count;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        checkIfValidate(item);

        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (oldFirst == null)
            last = first;
        else
            oldFirst.prev = first;
        count++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        checkIfValidate(item);

        Node oldLast = last;
        last = new Node(item);
        last.prev = oldLast;
        if (oldLast == null)
            first = last;
        else {
            oldLast.next = last;
        }
        count++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        Item item = first.val;
        first = first.next;
        count--;
        if (isEmpty()) last = null;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        Item item = last.val;
        last = last.prev;
        count--;
        if (isEmpty()) first = null;
        return item;
    }

    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new Iterator<Item>() {

            private Node cur = first;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                Item item = cur.val;
                cur = cur.next;
                return item;
            }

            @Override
            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
        };
    }

    private void checkIfValidate(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
    }

//    @Override
//    public String toString() {
//        StringBuilder out = new StringBuilder();
//        for (Item item: this){
//            out.append(item + " ");
//        }
//        return out.toString().trim();
//    }

    public static void main(String[] args)   // unit testing
    {
        Deque<String> test = new Deque<>();
        test.addFirst("2");
        test.addFirst("1");
        test.addLast("3");
        test.addLast("4");
//        System.out.println(test.toString()); //1 2 3 4

        test.removeFirst();
        test.removeLast();
//        System.out.println(test.toString()); //2 3
    }
}
