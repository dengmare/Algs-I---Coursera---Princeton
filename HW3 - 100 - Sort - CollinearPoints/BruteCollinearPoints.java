package wk3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Wilm on 9/25/16.
 */
public class BruteCollinearPoints {
    private Point[] points;
    ArrayList<LineSegment> segmentArrayList = new ArrayList<>();


    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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

        // compute
        int len = points.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                    for (int l = k + 1; l < len; l++) {
                        if (points[i].slopeTo(points[l]) == points[i].slopeTo(points[j])) {
                            Point[] tempP = createSorted4(points[i], points[j], points[k], points[l]);
                            segmentArrayList.add(new LineSegment(tempP[0], tempP[3]));
                        }
                    }
                }
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

    private Point[] createSorted4(Point point, Point point1, Point point2, Point point3) {
        Point[] temp = {point, point1, point2, point3};
        Arrays.sort(temp);
        return temp;
    }

//    public static void main(String[] args) {
//
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        wk3.Point[] points = new wk3.Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new wk3.Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (wk3.Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        wk3.BruteCollinearPoints collinear = new wk3.BruteCollinearPoints(points);
//        for (wk3.LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
}
