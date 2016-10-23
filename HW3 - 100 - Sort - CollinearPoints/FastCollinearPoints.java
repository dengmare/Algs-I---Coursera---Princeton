package wk3;

import java.util.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Wilm on 9/25/16.
 */
public class FastCollinearPoints {
    ArrayList<LineSegment> segmentArrayList = new ArrayList<>();

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        // check null
        if (points == null) throw new java.lang.NullPointerException();

        for (Point point : points) {
            if (point == null) throw new java.lang.NullPointerException();
        }
        // check duplicate
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();


        //Construct result.
        HashMap<Double, List<Point>> slopeMaptoLowPoint = new HashMap<>();// all used lines will be stored here.

        Point[] aux = Arrays.copyOf(points, points.length);

        for (Point point : points) {
            Arrays.sort(aux, point.slopeOrder());

            for (int i = 1; i < aux.length; ) {
                double slope = point.slopeTo(aux[i]);
                int count = 2;
                for (int j = i + count - 1; j < aux.length; j++) {
                    if (slope == point.slopeTo(aux[j]))
                        count++;
                    else break;
                }

                // contains more that 4 points
                if (count >= 4) {
                    Point pointSegMin = findSegMinPoint(aux, i, i + count - 1, point);
                    // get the origial list
                    slopeMaptoLowPoint.putIfAbsent(slope, new LinkedList<>());
                    List<Point> temp = slopeMaptoLowPoint.get(slope);
                    // check if already exist
                    if (!temp.contains(pointSegMin)) {
                        temp.add(pointSegMin);
                        segmentArrayList.add(new LineSegment(pointSegMin, findSegMaxPoint(aux, i, i + count - 1, point)));
                    }
                }
                i = i + count - 1;
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return segmentArrayList.size();
    }

    public LineSegment[] segments()                // the line segments
    {

        return segmentArrayList.toArray(new LineSegment[segmentArrayList.size()]);
    }

    private Point findSegMinPoint(Point[] aux, int fromInclusive, int toExclusive, Point startPoint) {
        Point segMin = startPoint;
        for (int j = fromInclusive; j < toExclusive; j++) {
            if (segMin.compareTo(aux[j]) > 0)
                segMin = aux[j];
        }
        return segMin;
    }

    private Point findSegMaxPoint(Point[] aux, int fromInclusive, int toExclusive, Point startPoint) {
        Point segMax = startPoint;
        for (int j = fromInclusive; j < toExclusive; j++) {
            if (segMax.compareTo(aux[j]) < 0)
                segMax = aux[j];
        }
        return segMax;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

//         draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
