package main;

import java.util.LinkedList;

public class EightPuzzle {

    /**
     * Representation of the puzzle state
     */
    static class State {
        int[][] board;
        int blankX, blankY;

        State(int[][] b, int x, int y) {
            board = b;
            blankX = x;
            blankY = y;
        }

        /**
         * Generate the next states from the current state
         */
        LinkedList<State> nextStates() {
            LinkedList<State> nextStates = new LinkedList<State>();
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int[] d : directions) {
                int nextX = blankX + d[0];
                int nextY = blankY + d[1];
                if (nextX < 0 || nextX >= 3 || nextY < 0 || nextY >= 3) continue;
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        newBoard[i][j] = board[i][j];
                newBoard[blankX][blankY] = newBoard[nextX][nextY];
                newBoard[nextX][nextY] = 0;
                nextStates.add(new State(newBoard, nextX, nextY));
            }
            return nextStates;
        }

        /**
         * Check if two states are the same
         */
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State s = (State) o;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (s.board[i][j] != this.board[i][j]) return false;
            return true;
        }

        /**
         * Generate a hash code for the state
         */
        public int hashCode() {
            int code = 0;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    code = code * 10 + board[i][j];
            return code;
        }

        /**
         * Override toString() method to produce readable output
         */
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    result.append(board[i][j] + " ");
                }
                result.append("\n");
            }
            return result.toString();
        }
    }
}