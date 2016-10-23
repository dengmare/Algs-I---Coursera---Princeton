package wk3;

import java.util.Comparator;

import edu.princeton.cs.algs4.*;

/**
 * Created by Wilm on 9/25/16.
 */
public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    //    public int x;
    private final int y;     // y-coordinate of this point

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw()                               // draws this point
    {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that)                   // draws the line segment from this point to that point
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString()                           // string representation
    {
        return "(" + x + ", " + y + ")";
    }


    public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    {
        int yCompare = this.y - that.y;
        return yCompare == 0 ? this.x - that.x : yCompare;

//        if (this.y > that.y) return 1;
//        else if (this.y < that.y) return -1;
//        else if (this.x > that.x) return 1;
//        else if (this.x < that.x) return -1;
//        else return 0;
    }

    public double slopeTo(Point that)       // the slope between this point and that point
    {
        if (this.y == that.y)
            if (this.x == that.x)
                return Double.NEGATIVE_INFINITY;
            else
                return 0;
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
    {
        return (o1, o2) ->
                Double.compare(this.slopeTo(o1), this.slopeTo(o2));
    }
}
