import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 0;
    public static final int TIE = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;

    // The game board and the game status
    private int ROWSCOLS; // number of rows and columns
    private int[][] board; // game board in 2D array

    public void setROWSCOLS(int ROWSCOLS) {
        this.ROWSCOLS = ROWSCOLS;
        board = new int[ROWSCOLS][ROWSCOLS];
    }
    //  containing (EMPTY, CROSS, NOUGHT)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    public static int currentPlayer; // the current player (CROSS or NOUGHT)
    public static int currentRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {
        // Initialize the game-board and current status
        TicTacToe ttt = new TicTacToe();
        while (true) {
            System.out.println("Please input the size of the board ( 3-10 ): ");
            System.out.println("(ex : typing in 4 will generate a 4x4 board size)");
            String inp = in.nextLine();
            if (inp.matches("[0-9]+") && Integer.parseInt(inp) <= 10 && Integer.parseInt(inp) >= 3) {
                ttt.setROWSCOLS(Integer.parseInt(inp));
                break;
            }
        }
        ttt.initGame();
        // Play the game once
        do {
            ttt.playerMove(currentPlayer); // update currentRow and currentCol
            ttt.updateGame(currentPlayer, currentRow, currentCol); // update currentState
            ttt.printBoard();
            // Print message if game-over
            if (currentState == CROSS_WON) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == NOUGHT_WON) {
                System.out.println("'O' won! Bye!");
            } else if (currentState == TIE) {
                System.out.println("It's a Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
        } while (currentState == PLAYING); // repeat if not game-over
    }

    /** Initialize the game-board contents and the current states */
    void initGame() {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
    }

    /** Player with the "theSeed" makes one move, with input validation.
     Update global variables "currentRow" and "currentCol". */
    void playerMove(int theSeed) {
        boolean validInput = false;  // for input validation
        do {
            if (theSeed == CROSS) {
                System.out.print("Player 'X', enter your move (row[1-"+ROWSCOLS+"] column[1-"+ROWSCOLS+"]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-"+ROWSCOLS+"] column[1-"+ROWSCOLS+"]): ");
            }
            int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
            int col = in.nextInt() - 1;
            if (row >= 0 && row < ROWSCOLS && col >= 0 && col < ROWSCOLS && board[row][col] == EMPTY) {
                currentRow = row;
                currentCol = col;
                board[currentRow][currentCol] = theSeed;  // update game-board content
                validInput = true;  // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);  // repeat until input is valid
    }

    /** Update the "currentState" after the player with "theSeed" has placed on
     (currentRow, currentCol). */
    void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed, currentRow, currentCol, ROWSCOLS)) {  // check if winning move
            currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else if (isTie()) {  // check for draw
            currentState = TIE;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /** Return true if it is a draw (no more empty cell) */
    boolean isTie() {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                if (board[row][col] == EMPTY) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing at
     (currentRow, currentCol) */
    boolean hasWon(int theSeed, int currentRow, int currentCol, int n) {
        boolean rows = true;
        boolean cols = true;
        boolean diag = true;
        boolean opdiag = true;
        for (int i = 0; i < n; i++) {
            rows &= board[currentRow][i] == theSeed;
            cols &= board[currentCol][i] == theSeed;
            diag &= board[i][i] == theSeed;
            opdiag &= board[i][n-i-1] == theSeed;
        }
        return rows || cols || diag || opdiag;
    }

    /** Print the game board */
    void printBoard() {
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != ROWSCOLS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWSCOLS - 1) {
                String lines = new String(new char[ROWSCOLS]).replace("\0", "====");
                System.out.println(lines); // print horizontal partition
            }
        }
        System.out.println();
    }

    /** Print a cell with the specified "content" */
    void printCell(int content) {
        switch (content) {
            case EMPTY:  System.out.print("   "); break;
            case NOUGHT: System.out.print(" O "); break;
            case CROSS:  System.out.print(" X "); break;
        }
    }
}
