package wk4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Wilm on 10/3/16.
 */
public class Board {
    private int[] storeArray;
    private int N;

    // (where blocks[i][j] = block in row i, column j)
    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length <= 1) throw new java.lang.IllegalArgumentException();
        N = blocks.length;
        storeArray = new int[N * N];

        int b = 0;
        for (int[] block :
                blocks) {
            System.arraycopy(block, 0, storeArray, b * N, N);
            b++;
        }
    }

    public int dimension()                 // board dimension n
    {
        return N;
    }

    public int hamming()                   // number of blocks out of place
    {
        int hamming = 0;
        for (int i = 0; i < N * N; i++) {
            if (storeArray[i] == 0) continue;
            if (storeArray[i] != i + 1) hamming++;
        }
        return hamming;
    }

    private int getX(int index) {
        return index / N;
    }

    private int getY(int index) {
        return index % N;
    }

    private int geBytXY(int x, int y) {
        return x * N + y;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int dis = 0;
        for (int i = 0; i < N * N; i++) {
            if (storeArray[i] == i + 1 || storeArray[i] == 0) continue;
            dis += Math.abs(getX(i) - getX(storeArray[i] - 1)) + Math.abs(getY(i) - getY(storeArray[i] - 1));
        }
        return dis;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int i, j;
        do {
            i = StdRandom.uniform(N * N);
        } while (storeArray[i] == 0);

        do {
            j = StdRandom.uniform(N * N);
        } while (j == i || storeArray[j] == 0);

//        System.out.println("i=" + i + " j=" + j);
//        wk4.Board twin = new wk4.Board(new int[N][N]);
//        System.arraycopy(this.storeArray, 0, twin.storeArray, 0, N * N);
//        // swap
//        twin.storeArray[i] = this.storeArray[j];
//        twin.storeArray[j] = this.storeArray[i];

        return copySwap(i, j);
    }

    private Board copySwap(int i, int j) {
        Board replica = new Board(new int[N][N]);
        System.arraycopy(this.storeArray, 0, replica.storeArray, 0, N * N);
        // swap
        replica.storeArray[i] = this.storeArray[j];
        replica.storeArray[j] = this.storeArray[i];

        return replica;
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return board.N == this.N && Arrays.equals(this.storeArray, board.storeArray);
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> neighbors = new LinkedList<>();
        int flatIndex = -1, x = -1, y = -1;
        for (int i = 0; i < N * N; i++) {
            if (storeArray[i] == 0) {
                x = getX(i);
                y = getY(i);
                flatIndex = i;
            }
        }
        // up
        if (x > 0)
            neighbors.add(copySwap(flatIndex, flatIndex - N));
        //down
        if (x < N - 1)
            neighbors.add(copySwap(flatIndex, flatIndex + N));
        //left
        if (y > 0)
            neighbors.add(copySwap(flatIndex, flatIndex - 1));
        //right
        if (y < N - 1)
            neighbors.add(copySwap(flatIndex, flatIndex + 1));
        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N);
        for (int i = 0; i < storeArray.length; i++) {
            if (i % (N) == 0) s.append('\n');
            s.append(String.format("%2d ", storeArray[i]));
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
//        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
//        wk4.Board b = new wk4.Board(arr);
//        System.out.println(b.toString());
//        System.out.println(b.isGoal());
//        System.out.println("manhattan 0? = " + b.manhattan());
//        System.out.println("hamming 0? = " + b.hamming());

        // goal board construct
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        int i = 1;
        while (!initial.equals(initial.twin())){
            System.out.println(i++);
        }

//        System.out.println("false? = " + b2.isGoal());
//        System.out.println();
//        System.out.println(b2.neighbors());
//        System.out.println();
//        System.out.println("manhattan 7? = " + b2.manhattan());
//        System.out.println("hamming 4? = " + b2.hamming());
//
//        wk4.Board b3 = b2.twin();
//        System.out.println("twin!!!");
//        System.out.println(b3.toString());
    }
}