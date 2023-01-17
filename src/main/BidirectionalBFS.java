package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

import main.EightPuzzle.State;

public class BidirectionalBFS {

    /**
     * Function to find a path from the start state to the goal state
     */
    public static LinkedList<State> search(State start, State goal) {

        /**
         * Queues for start and goal states
         */
        Queue<State> startQueue = new LinkedList<>();
        Queue<State> goalQueue = new LinkedList<>();

        /**
         * Keep track of the visited states
         */
        HashSet<State> visitedStart = new HashSet<>();
        HashSet<State> visitedGoal = new HashSet<>();

        /**
         * Keep track of the parent of each state as the algorithm progresses
         */
        HashMap<State, State> parentStart = new HashMap<>();
        HashMap<State, State> parentGoal = new HashMap<>();

        /**
         * Add the states to the queue
         */
        startQueue.add(start);
        visitedStart.add(start);

        goalQueue.add(goal);
        visitedGoal.add(goal);

        parentStart.put(start, null);
        parentGoal.put(goal, null);

        /**
         * While there are still states to be visited from both directions,
         * generate the next possible states and return the found path
         */
        while (!startQueue.isEmpty() && !goalQueue.isEmpty()) {

            State currentStart = startQueue.poll();
            for (State next : currentStart.nextStates()) {
                if (visitedGoal.contains(next)) {
                    return buildPath(parentStart, parentGoal, currentStart, next);
                }
                if (!visitedStart.contains(next)) {
                    visitedStart.add(next);
                    parentStart.put(next, currentStart);
                    startQueue.add(next);
                }
            }

            State currentGoal = goalQueue.poll();
            for (State next : currentGoal.nextStates()) {
                if (visitedStart.contains(next)) {
                    return buildPath(parentStart, parentGoal, next, currentGoal);
                }
                if (!visitedGoal.contains(next)) {
                    visitedGoal.add(next);
                    parentGoal.put(next, currentGoal);
                    goalQueue.add(next);
                }
            }
        }

        /**
         * If this point is reached, it means no path has been found
         */
        return null;
    }

    /**
     * Build the path from start to goal using parent links in the HashMaps provided
     */
    private static LinkedList<State> buildPath(HashMap<State, State> parentStart, HashMap<State, State> parentGoal, State start, State goal) {
        LinkedList<State> path = new LinkedList<>();
        State currentStart = start, currentGoal = goal;
        while (currentStart != null || currentGoal != null) {
            if (currentStart != null) {
                path.addFirst(currentStart);
                currentStart = parentStart.get(currentStart);
            }
            if (currentGoal != null) {
                path.addLast(currentGoal);
                currentGoal = parentGoal.get(currentGoal);
            }
        }
        return path;
    }

    /**
     * Bidirectional BFS approach returning an ArrayList,
     * but it's extremely memory inefficient
     */
    public static ArrayList<int[]> searchArrayList(State start, State goal) {
        Queue<State> startQueue = new LinkedList<>();
        Queue<State> goalQueue = new LinkedList<>();
        HashSet<State> visitedStart = new HashSet<>();
        HashSet<State> visitedGoal = new HashSet<>();
        HashMap<State, int[]> parentStart = new HashMap<>();
        HashMap<State, int[]> parentGoal = new HashMap<>();
        ArrayList<int[]> path = new ArrayList<>();

        startQueue.add(start);
        visitedStart.add(start);

        goalQueue.add(goal);
        visitedGoal.add(goal);

        parentStart.put(start, null);
        parentGoal.put(goal, null);

        while (!startQueue.isEmpty() && !goalQueue.isEmpty()) {

            State current1 = startQueue.poll();
            for (State next : current1.nextStates()) {
                if (visitedGoal.contains(next)) {
                    return buildPathArrayList(parentStart, parentGoal, current1, next, path);
                }
                if (!visitedStart.contains(next)) {
                    visitedStart.add(next);
                    int[] coordinates = new int[]{next.blankX, next.blankY};
                    parentStart.put(next, coordinates);
                    startQueue.add(next);
                }
            }

            State current2 = goalQueue.poll();
            for (State next : current2.nextStates()) {
                if (visitedStart.contains(next)) {
                    return buildPathArrayList(parentStart, parentGoal, next, current2, path);
                }
                if (!visitedGoal.contains(next)) {
                    visitedGoal.add(next);
                    int[] coordinates = new int[]{next.blankX, next.blankY};
                    parentGoal.put(next, coordinates);
                    goalQueue.add(next);
                }
            }
        }
        return null;
    }

    private static ArrayList<int[]> buildPathArrayList(HashMap<State, int[]> parentStart, HashMap<State, int[]> parentGoal, State start, State goal, ArrayList<int[]> path) {
        State currentStart = start, currentGoal = goal;
        while (currentStart != null || currentGoal != null) {
            if (currentStart != null) {
                path.add(0, parentStart.get(currentStart));
                currentStart = parentStart.get(currentStart) != null ? currentStart : null;
            }
            if (currentGoal != null) {
                path.add(parentGoal.get(currentGoal));
                currentGoal = parentGoal.get(currentGoal) != null ? currentGoal : null;
            }
        }
        return path;
    }
}