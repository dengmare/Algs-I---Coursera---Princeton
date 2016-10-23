package wk4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Wilm on 10/3/16.
 */
public class Solver {
    private boolean isSolvable;
    private LinkedList<Board> solution = null;
    private int moves = -1;


    private class State {
        Board board;
        int moves;
        State prev;

        Board getBoard() {
            return board;
        }

        State(Board board, State prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }

        int getHamming() {
            return board.hamming();
        }
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) {
            throw new NullPointerException("null is passed");
        }

        MinPQ<State> boardsPQ = new MinPQ<State>(getComparator());
        MinPQ<State> twinBdPQ = new MinPQ<State>(getComparator());

        boardsPQ.insert(new State(initial, null, 0));
        twinBdPQ.insert(new State(initial.twin(), null, 0));

        State current;
        while (true) {
//            System.out.println("boards " + boardsPQ.size());
//            System.out.println("twin " + twinBdPQ.size());

            current = boardsPQ.delMin();
            State twincur = twinBdPQ.delMin();

            if (current.getBoard().isGoal()) {
                isSolvable = true;
                break;
            }
            if (twincur.getBoard().isGoal()) {
                isSolvable = false;
                break;
            }

            addDistinctNeigh(boardsPQ, current);
            addDistinctNeigh(twinBdPQ, twincur);
        }
        if (isSolvable)
            makeSolution(current);
    }

    private void addDistinctNeigh(MinPQ<State> pq, State state) {
        Board board = state.board;
        Board prev = state.prev == null ? null : state.prev.board;
        for (Board b : board.neighbors()) {
            if (prev == null || !prev.equals(b))
                pq.insert(new State(b, state, state.moves + 1));
        }
    }

    private Comparator<State> getComparator() {
//        return (o1, o2) -> Integer.compare(o1.getHamming(), o2.getHamming());
        return new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                final int compare = Integer.compare(o1.board.manhattan() + o1.moves, o2.board.manhattan() + o2.moves);
                if (compare != 0) {
                    return compare;
                } else {
                    return Integer.compare(o1.board.hamming() + o1.moves, o2.board.hamming() + o2.moves);
                }
            }
        };
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return isSolvable;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }

    private void makeSolution(State s) {
        moves = s.moves;
        State cur = s;
        solution = new LinkedList<>();
        while (cur != null) {
            solution.addFirst(cur.board);
            cur = cur.prev;
        }
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
//        if (!isSolvable)
//            return null;
        return solution;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
