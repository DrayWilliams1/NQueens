package nqueens;

/**
 * @author Drayton Williams The purpose of this class is to create full
 * 2-dimensional boards out of provided coordinates from 1-dimensional array of
 * integer values. It contains constructors that are able to create
 * 2-dimensional boards from a int[] array and an ArrayList<Integer>
 */
import java.util.*;

public class QBoard {

    char[][] board;
    int[] coordinates;
    int numQueens;
    int size = 0;

    public QBoard(int[] resultIn) {
        coordinates = resultIn;
        size = coordinates.length;
        board = new char[size][size];
        numQueens = 0;

        fillBoard();
        placeQueens();
    }

    public QBoard(ArrayList<Integer> resultIn) {
        coordinates = new int[resultIn.size()];
        size = coordinates.length;

        for (int i = 0; i < size; i++) {
            coordinates[i] = resultIn.get(i);
        }

        board = new char[size][size];
        numQueens = 0;

        fillBoard();
        placeQueens();
    }

    /**
     * Initializes the board with x's in each spot where a Queen is not placed
     * (for better visibility)
    *
     */
    private void fillBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 'x';
            }
        }
    }

    /**
     * Uses provided coordinates to place a queen on that spot in the board
    *
     */
    private void placeQueens() {
        for (int i = 0; i < board.length; i++) {
            board[i][coordinates[i]] = 'Q';
        }
    }

    /**
     * Allows for the board to be printed
    *
     */
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
