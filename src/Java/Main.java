package Java;

import Java.enums.GameState;
import Java.enums.SeedType;
import Java.objects.Com;
import Java.objects.Game;
import Java.objects.Player;

import java.util.Scanner;

public class Main {
    // The game board and the game status
    public static int ROWSCOLS; // number of rows and columns
    public static int[][] board; // game board in 2D array

    // the maximum dimension of the board allowed
    // 10 = 10x10 the maximum dimension that can be picked by the Player
    private final static int MAX_SIZE = 10;

    // selected gameMode
    private static int GAME_MODE = 0;

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
            System.out.println("Please input the size of the board ( 3-"+MAX_SIZE+" ): ");
            System.out.println("(ex : typing in 4 will generate a 4x4 board size)");
            String inp = in.nextLine();
            if (inp.matches("[0-9]+") && Integer.parseInt(inp) <= MAX_SIZE && Integer.parseInt(inp) >= 3) {
                ROWSCOLS = Integer.parseInt(inp);
                board = new int[Integer.parseInt(inp)][Integer.parseInt(inp)];
                break;
            }
        }

        while(true) {
            System.out.println("Please select the game mode from the list below : ");
            System.out.println("1. Player vs Player");
            System.out.println("2. Player vs COM");
            System.out.println("3. COM vs COM");
            String inp = in.nextLine();
            if (inp.matches("[0-9]+") && Integer.parseInt(inp) <= 3 && Integer.parseInt(inp) >= 1) {
                GAME_MODE = Integer.parseInt(inp);
                break;
            }
        }
        // initializing all the objects needed for the game to play
        Player player = new Player(ROWSCOLS);
        Com comPlayer = new Com(ROWSCOLS);
        Game game = new Game(ROWSCOLS);
        // initializing the game and picking witch player goes first
        game.initGame(SeedType.CROSS.ordinal());
        // switching the round between Player & Com Player
        boolean isPlayer = true;
        // Play the game once
        do {
            switch (GAME_MODE) {
                case 1:
                    player.Move(currentPlayer);
                    break;
                case 2:
                    if (isPlayer) {
                        player.Move(currentPlayer);
                        isPlayer = false;
                    }
                    else {
                        comPlayer.Move(currentPlayer);
                        isPlayer = true;
                    }
                    break;
                case 3:
                    comPlayer.Move(currentPlayer);
                    break;
            }
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
