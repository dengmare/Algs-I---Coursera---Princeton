import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by Wilm on 10/10/16.
 */
public class PointSET {
    private TreeSet<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new TreeSet<>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.isEmpty();
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        checkNullArgument(p);
        set.add(p);
    }

    private void checkNullArgument(Object p) {
        if (p == null) throw new NullPointerException();
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        checkNullArgument(p);
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setScale();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        set.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        checkNullArgument(rect);
        return set.stream().filter(rect::contains).collect(Collectors.toList());
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        checkNullArgument(p);

        Point2D nearestPoint = null;

        double minDis = Double.MAX_VALUE;
        for (Point2D thisP :
                set) {
            if (p.distanceTo(thisP) < minDis) {
                nearestPoint = thisP;
                minDis = p.distanceTo(thisP);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET set = new PointSET();
        set.insert(new Point2D(0.1, 0.2));
        set.insert(new Point2D(0.3, 0.4));
        set.insert(new Point2D(0.5, 0.6));
        set.draw();
    }
}
