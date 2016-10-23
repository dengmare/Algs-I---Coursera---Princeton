import edu.princeton.cs.algs4.*;
//import edu.princeton.cs.algs4.Draw;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wilm on 10/10/16.
 */
@SuppressWarnings("Duplicates")
public class KdTree {
    private static final RectHV SQUARE = new RectHV(0.0, 0.0, 1.0, 1.0);

    private class Node {
        private Point2D p;      // the point
        private Node lb; //left bottom
        private Node rt; //right top
        private boolean vertical;

        private Node(Point2D p, Node lb, Node rt, boolean vertical) {
            this.p = p;
            lb = null;  // left bottom
            rt = null;  // right top
            this.vertical = vertical; // true for vertical line, false for horizontal line
        }
    }

    private Node root;
    private int size = 0;


    public KdTree()                               // construct an empty set of points
    {

    }

    public boolean isEmpty()                      // is the set empty?
    {
        return size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        checkNullArgument(p);
        root = insert(root, p, true);
    }

    private Node insert(Node x, Point2D p, boolean vertical) {
        if (x == null) {
            x = new Node(p, null, null, vertical);
            return x;
        }

        // duplicate
        if (p.equals(x.p))
            return x;

        if (isLeftBottom(p, x)) {
            x.lb = insert(x.lb, p, !vertical);
        } else {
            x.rt = insert(x.rt, p, !vertical);
        }
        return x;

    }

    // return if p located on the lb side of x
    private boolean isLeftBottom(Point2D p, Node x) {
        int cmp = 0;
        if (x.vertical) {
            cmp = Point2D.X_ORDER.compare(p, x.p);
        } else {
            cmp = Point2D.Y_ORDER.compare(p, x.p);
        }
        return cmp < 0;
    }


    public boolean contains(Point2D p)            // does the set contain point p?
    {
        checkNullArgument(p);
        Node x = root;

        while (x != null) {
            if (p.equals(x.p)) return true;
            if (isLeftBottom(p, x)) x = x.lb;
            else x = x.rt;
        }
        return false;
    }

    public void draw()                         // draw all points to standard draw
    {
        StdDraw.setScale();
        draw(root, SQUARE);
    }

    private void draw(Node x, RectHV square) {
        if (x == null) return;

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();

        // draw line
        StdDraw.setPenRadius();
        if (x.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), square.ymin(), x.p.x(), square.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(square.xmin(), x.p.y(), square.xmax(), x.p.y());
        }

        // recursive draw
        draw(x.lb, findLeftBottomSubRec(x, square)); // left or bottom
        draw(x.rt, findRightTopSubRec(x, square));   // right or top

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
    }

    // x as the current node, xRec is x's search rect
    private RectHV findLeftBottomSubRec(Node x, RectHV xRec) {
//        if (x == null || xRec == null) return null;
        if (x.vertical) //left
            return new RectHV(xRec.xmin(), xRec.ymin(), x.p.x(), xRec.ymax());
        else    // bottom
            return new RectHV(xRec.xmin(), xRec.ymin(), xRec.xmax(), x.p.y());
    }

    private RectHV findRightTopSubRec(Node x, RectHV xRec) {
        if (x.vertical) //right
            return new RectHV(x.p.x(), xRec.ymin(), xRec.xmax(), xRec.ymax());
        else    // top
            return new RectHV(xRec.xmin(), x.p.y(), xRec.xmax(), xRec.ymax());
    }

    // To find all points contained in a given query rectangle, start at the root and recursively search for
    // points in both subtrees using the following pruning rule:
    // if the query rectangle does not intersect the rectangle corresponding to a node,
    // there is no need to explore that node (or its subtrees).
    // A subtree is searched only if it might contain a point contained in the query rectangle.
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        checkNullArgument(rect);
        List<Point2D> res = new LinkedList<>();
        range(root, SQUARE, rect, res);
        return res;
    }

    private void range(Node x, RectHV xRec, RectHV qRec, List<Point2D> res) {
        if (x == null) return;
        if (!xRec.intersects(qRec)) return;
        if (qRec.contains(x.p))
            res.add(x.p);
        range(x.lb, findLeftBottomSubRec(x, xRec), qRec, res);
        range(x.rt, findRightTopSubRec(x, xRec), qRec, res);
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        checkNullArgument(p);
        return nearest(root, SQUARE, p, root.p);
    }

    private Point2D nearest2(Node x, RectHV xRec, Point2D qP, Point2D nearestSoFar) {
        if (x == null) return nearestSoFar;

        if (xRec.distanceSquaredTo(qP) >= nearestSoFar.distanceSquaredTo(qP))
            return nearestSoFar;

        if (nearestSoFar.distanceSquaredTo(qP) > x.p.distanceSquaredTo(qP))
            nearestSoFar = x.p;

        // next parse
        double lbDis = Double.MAX_VALUE;
        double rtDis = Double.MAX_VALUE;
        RectHV lbRect = null, rtRect = null;
        if (x.lb != null) {
            lbRect = findLeftBottomSubRec(x, xRec);
            lbDis = lbRect.distanceSquaredTo(qP);
        }
        if (x.rt != null) {
            rtRect = findRightTopSubRec(x, xRec);
            rtDis = rtRect.distanceSquaredTo(qP);
        }
        if (lbDis < rtDis) { // start left bottom first
            return nearest(x.rt, rtRect, qP, nearest(x.lb, lbRect, qP, nearestSoFar));
        } else {            // start right top first
            return nearest(x.lb, lbRect, qP, nearest(x.rt, rtRect, qP, nearestSoFar));
        }
    }

    private Point2D nearest(Node x, RectHV xRec, Point2D qP, Point2D nearestSoFar) {
        if (x == null) return nearestSoFar;

        if (xRec.distanceSquaredTo(qP) >= nearestSoFar.distanceSquaredTo(qP))
            return nearestSoFar;

        if (nearestSoFar.distanceSquaredTo(qP) > x.p.distanceSquaredTo(qP))
            nearestSoFar = x.p;

        // next parse
        RectHV lbRect = null, rtRect = null;
        if (x.lb != null) {
            lbRect = findLeftBottomSubRec(x, xRec);
        }
        if (x.rt != null) {
            rtRect = findRightTopSubRec(x, xRec);
        }
        if (isLeftBottom(qP, x)) { // start left bottom first
            return nearest(x.rt, rtRect, qP, nearest(x.lb, lbRect, qP, nearestSoFar));
        } else {            // start right top first
            return nearest(x.lb, lbRect, qP, nearest(x.rt, rtRect, qP, nearestSoFar));
        }
    }

    private void checkNullArgument(Object p) {
        if (p == null) throw new NullPointerException();
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        KdTree test = new KdTree();
        test.insert(new Point2D(0.5, 0.5));
        test.insert(new Point2D(0.500000, 1.000000));
        test.insert(new Point2D(0.25, 0.5));
        test.insert(new Point2D(0.75, 0.5));
        test.insert(new Point2D(0.25, 0.75));
        System.out.println('s');
    }

}
