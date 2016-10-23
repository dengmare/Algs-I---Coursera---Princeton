package wk1; /**
 * Created by Wilm on 9/12/16.
 */

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;

public class Percolation {
    private WeightedQuickUnionUF wquf;
    private int top = 0;
    private int bottom;
    private int sz;
    private boolean sites[];


    public Percolation(int n)               // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        wquf = new WeightedQuickUnionUF(n * n + 2);
        sz = n;
        sites = new boolean[n * n + 1];
        bottom = n * n + 1;
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        if (!isValidate(i, j))
            throw new java.lang.IndexOutOfBoundsException();

        if (isOpen(i, j)) return;

        sites[co2in(i, j)] = true;

        if (i == 1)
            wquf.union(top, co2in(i, j));
        if (i == sz)
            wquf.union(bottom, co2in(i, j));

        // up
        if (isValidate(i - 1, j) && isOpen(i - 1, j))
            wquf.union(co2in(i - 1, j), co2in(i, j));

        // down
        if (isValidate(i + 1, j) && isOpen(i + 1, j))
            wquf.union(co2in(i + 1, j), co2in(i, j));

        // left
        if (isValidate(i, j - 1) && isOpen(i, j - 1))
            wquf.union(co2in(i, j - 1), co2in(i, j));

        //right
        if (isValidate(i, j + 1) && isOpen(i, j + 1))
            wquf.union(co2in(i, j + 1), co2in(i, j));
    }

    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        if (!isValidate(i, j))
            throw new java.lang.IndexOutOfBoundsException();
        return sites[co2in(i, j)];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        if (!isValidate(i, j))
            throw new java.lang.IndexOutOfBoundsException();
        return wquf.connected(top, co2in(i, j));
    }

    public boolean percolates()             // does the system percolate?
    {
        return wquf.connected(top, bottom);
    }

    private boolean isValidate(int i, int j) {
        if (i > 0 && j > 0 && i <= sz && j <= sz) {
            return true;
        } else {
            return false;
        }
    }

    private int co2in(int i, int j) {
        return (i - 1) * sz + j;
    }

    public static void main(String[] args)  // test client (optional)
    {
//        Percolation perc = new Percolation(1);
//        StdOut.println(percolation.percolates());
//
//        percolation.open(1, 1);
//        StdOut.println(percolation.percolates());
//
//        Percolation percolation2 = new Percolation(2);
//        StdOut.println(percolation2.percolates());
//
//        percolation2.open(1, 1);
//        StdOut.println(percolation2.percolates());
//
//        percolation2.open(2, 1);
//        StdOut.println(percolation2.percolates());
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
    }
}
