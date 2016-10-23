/**
 * Created by Wilm on 9/17/16.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> s = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            s.enqueue(StdIn.readString());
        }

        Iterator<String> iterator = s.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
