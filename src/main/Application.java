package main;

import main.EightPuzzle.State;

import java.lang.System;
import java.util.LinkedList;

public class Application {

    public static void main(String ...args) {

        int[][] start = { {7, 1, 2}, {3, 4, 5}, {6, 0, 8} };
        int[][] goal = { {0, 1, 2}, {3, 4, 5}, {6, 7, 8} };

        State initialState = new State(start, 2, 1);
        State goalState = new State(goal, 0, 0);

        System.out.println("-= Initial State =-\n" + initialState);
        System.out.println("-= Goal State =-\n" + goalState);

        LinkedList<State> path = BidirectionalBFS.search(initialState, goalState);

        if (path != null) {
            System.out.println("Found a solution!");
            for (State s : path) System.out.println(s.toString());
        } else System.out.println("No solution found.");
    }
}
