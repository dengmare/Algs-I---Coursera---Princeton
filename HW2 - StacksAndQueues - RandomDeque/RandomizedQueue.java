import java.util.ArrayList;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Wilm on 9/13/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int count;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        count = 0;
        s = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item copy[] = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }


    public boolean isEmpty()                 // is the queue empty?
    {
        return count == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return count;
    }

    public void enqueue(Item item)           // add the item
    {
        if (count == s.length) resize(s.length * 2);
        s[count++] = item;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(count);
        Item item = s[index];
        s[index] = s[--count];

        if (count > 0 && count == s.length / 4)
            resize(s.length / 2);
        return item;
    }


    public Item sample()                     // return (but do not remove) a random item
    {
        int index = StdRandom.uniform(count);
        return s[index];
    }


    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new Iterator<Item>() {
            Item[] copy = shuffle();
            int cur = 0;

            @Override
            public boolean hasNext() {
                return cur < count;
            }

            @Override
            public Item next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();
                Item item = copy[cur];
                cur++;
                return item;
            }

            @Override
            public void remove() {
                throw new java.lang.UnsupportedOperationException();
            }
        };
    }

    private Item[] shuffle() {
        Item[] copy = s.clone();
        for (int i = 0; i < count; i++) {
            int index = StdRandom.uniform(i + 1);
            Item temp = copy[i];
            copy[i] = copy[index];
            copy[index] = temp;
        }
        return copy;
    }


    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> test = new RandomizedQueue<>();


        for (int i = 1; i <= 10; i++) {
            test.enqueue(Integer.toString(i));
        }

        for (int i = 0; i < 10; i++) {
            for (String item: test) {
                System.out.printf(item + " ");
            }
            System.out.println("");
        }
    }
}
