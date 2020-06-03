package Java.objects;

import Java.Main;
import Java.enums.SeedType;

public class Player {

    // all the player behaviour goes here

    private final int ROWSCOLS;

    public Player(int rowscols) {
        ROWSCOLS = rowscols;
    }
    /** Java.objects.Player with the "theSeed" makes one move, with input validation.
     Update global variables "currentRow" and "currentCol". */
    public void Move(int theSeed) {
        boolean validInput = false;  // for input validation
        do {
            if (theSeed == SeedType.CROSS.ordinal()) {
                System.out.print("Player 'X', enter your move (row[1-"+ ROWSCOLS+"] column[1-"+ ROWSCOLS+"]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-"+ ROWSCOLS+"] column[1-"+ ROWSCOLS+"]): ");
            }
            int row = Main.in.nextInt() - 1;  // array index starts at 0 instead of 1
            int col = Main.in.nextInt() - 1;
            if (row >= 0 && row < ROWSCOLS && col >= 0 && col < ROWSCOLS && Main.board[row][col] == SeedType.EMPTY.ordinal()) {
                Main.currentRow = row;
                Main.currentCol = col;
                Main.board[Main.currentRow][Main.currentCol] = theSeed;  // update game-board content
                validInput = true;  // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);  // repeat until input is valid
    }

}
