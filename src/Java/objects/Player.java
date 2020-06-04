package Java.objects;

import Java.Main;
import Java.enums.SeedType;
import static Java.Main.*;

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
            int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
            int col = in.nextInt() - 1;
            if (row >= 0 && row < ROWSCOLS && col >= 0 && col < ROWSCOLS && board[row][col] == SeedType.EMPTY.ordinal()) {
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

}
