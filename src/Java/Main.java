package Java;

import Java.enums.GameState;
import Java.enums.SeedType;
import Java.objects.Game;
import Java.objects.Player;

import java.util.Scanner;

public class Main {
    // The game board and the game status
    public static int ROWSCOLS; // number of rows and columns
    public static int[][] board; // game board in 2D array

    //  containing (EMPTY, CROSS, NOUGHT)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    public static int currentPlayer; // the current player (CROSS or NOUGHT)
    public static int currentRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {
        // Initialize the game-board and current status
        while (true) {
            System.out.println("Please input the size of the board ( 3-10 ): ");
            System.out.println("(ex : typing in 4 will generate a 4x4 board size)");
            String inp = in.nextLine();
            if (inp.matches("[0-9]+") && Integer.parseInt(inp) <= 10 && Integer.parseInt(inp) >= 3) {
                ROWSCOLS = Integer.parseInt(inp);
                board = new int[Integer.parseInt(inp)][Integer.parseInt(inp)];
                break;
            }
        }
        // initializing all the objects needed for the game to play
        Player player = new Player(ROWSCOLS);
        Game game = new Game(ROWSCOLS);
        // initializing the game and picking witch player goes first
        game.initGame(SeedType.NOUGHT.ordinal());
        // Play the game once
        do {
            player.Move(currentPlayer); // update currentRow and currentCol
            game.updateGame(currentPlayer, currentRow, currentCol); // update currentState
            game.printBoard();
            // Print message if game-over
            if (currentState == GameState.CROSS_WON.ordinal()) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == GameState.NOUGHT_WON.ordinal()) {
                System.out.println("'O' won! Bye!");
            } else if (currentState == GameState.TIE.ordinal()) {
                System.out.println("It's a Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == SeedType.CROSS.ordinal()) ? SeedType.NOUGHT.ordinal() : SeedType.CROSS.ordinal();
        } while (currentState == GameState.PLAYING.ordinal()); // repeat if not game-over
    }
}
