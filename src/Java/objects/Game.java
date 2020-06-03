package Java.objects;

import Java.Main;
import Java.enums.GameState;
import Java.enums.SeedType;

import static Java.Main.board;

public class Game {

    // all the game behaviour goes here
    private final int ROWSCOLS;

    public Game(int rowscols) {
        ROWSCOLS = rowscols;
    }

    /** Initialize the game-board contents and the current states */
    public void initGame(int firstPlayer) {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                board[row][col] = SeedType.EMPTY.ordinal();  // all cells empty
            }
        }
        Main.currentState = GameState.PLAYING.ordinal(); // ready to play
        Main.currentPlayer = firstPlayer;  // choosen Player plays first
    }

    /** Update the "currentState" after the player with "theSeed" has placed on
     (currentRow, currentCol). */
    public void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed, currentRow, currentCol, ROWSCOLS)) {  // check if winning move
            Main.currentState = (theSeed == SeedType.CROSS.ordinal()) ? GameState.CROSS_WON.ordinal() : GameState.NOUGHT_WON.ordinal();
        } else if (isTie()) {  // check for draw
            Main.currentState = GameState.TIE.ordinal();
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /** Return true if it is a draw (no more empty cell) */
    private boolean isTie() {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                if (board[row][col] == SeedType.EMPTY.ordinal()) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing at
     (currentRow, currentCol) */
    private boolean hasWon(int theSeed, int currentRow, int currentCol, int n) {
        boolean rows = true; // to store the row cells boolean results
        boolean cols = true; // to store all the column cells boolean results
        boolean diag = true; // to store diagonal cells boolean results
        boolean opdiag = true; // to store the opposite diagonal cells boolean results
        for (int i = 0; i < n; i++) {
            rows &= board[currentRow][i] == theSeed; // checking all the cells in the current Row
            cols &= board[i][currentCol] == theSeed; // checking all the cells in the current Column
            diag &= board[i][i] == theSeed; // checking all the cells in the diagonal Row
            opdiag &= board[i][n-i-1] == theSeed;  // checking all the cells in the opposite diagonal Row
        }
        return rows || cols || diag || opdiag; // checking if any returning true
    }

    /** Print the game board */
    public void printBoard() {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != ROWSCOLS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWSCOLS - 1) {
                // below line will print the horizontal lines with appropriate length needed
                String lines = new String(new char[ROWSCOLS]).replace("\0", "====");
                System.out.println(lines); // print horizontal partition
            }
        }
        System.out.println();
    }

    /** Print a cell with the specified "content" */
    public void printCell(int content) {
        // since switch case requires a compile-time constant, so we replace it with if else statement instead
        if(content == SeedType.NOUGHT.ordinal()) {
            System.out.print(" O ");
        } else if(content == SeedType.CROSS.ordinal()) {
            System.out.print(" X ");
        } else {
            System.out.print("   ");
        }
    }
}
