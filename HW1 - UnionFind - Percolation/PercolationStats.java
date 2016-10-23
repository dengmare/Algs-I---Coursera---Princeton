package wk1; /**
 * Created by Wilm on 9/12/16.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private int sz;
    private double fractions[];


    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();

        this.trials = trials;
        this.sz = n;
        this.fractions = new double[this.trials];

        // for random experiment;
        int sites2fill[] = new int[sz * sz];

        for (int i = 0; i < this.trials; i++) {
            Percolation trail = new Percolation(sz);
            for (int j = 0; j < sites2fill.length; j++) {
                sites2fill[j] = j;
            }
            int fillLimit = sites2fill.length;
            while(!trail.percolates()) {
                int nextIndex2Fill = StdRandom.uniform(fillLimit);

                trail.open(sites2fill[nextIndex2Fill] / sz + 1, sites2fill[nextIndex2Fill] % sz + 1);
                fillLimit--;
                sites2fill[nextIndex2Fill] = sites2fill[fillLimit];
            }
            fractions[i] = (double) (sites2fill.length - fillLimit) / sites2fill.length;
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(fractions);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return (mean() - 1.96 * Math.sqrt(stddev())) / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return (mean() + 1.96 * Math.sqrt(stddev())) / Math.sqrt(trials);
    }

    public static void main(String[] args)    // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        System.out.println(n);System.out.println(trials);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
