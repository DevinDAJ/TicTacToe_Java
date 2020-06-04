package Java.objects;

import Java.enums.SeedType;

import java.util.LinkedList;
import java.util.List;

import static Java.Main.*;

public class Com extends Player {
    // this is an attempt to create an AI Player with simple randomized move
    // and can be  considered as the easiest AI difficulty to play with

    private final int ROWSCOLS;

    public Com(int rowscols) {
        super(rowscols);
        this.ROWSCOLS = rowscols;
    }

    @Override
    public void Move(int theSeed) {
        //super.Move(theSeed);
        String seedName = "'X'";
        if (theSeed == 2) {
            seedName = "'O'";
        }
        System.out.println("COM Player "+seedName+", is thinking ...");
        // finding all the empty cells first before the Com Player can make a move
        List<int[][]> emptyCells = new LinkedList<>();
        for (int row = 0; row < ROWSCOLS; ++row) {
            for (int col = 0; col < ROWSCOLS; ++col) {
                if (board[row][col] == SeedType.EMPTY.ordinal()) {
                    int[][] current = new int[2][1];
                    current[0][0] = row;
                    current[1][0] = col;
                    emptyCells.add(current);
                }
            }
        }
        // randomizing the move ((0.0 to 1.0) * (list.size - 1))
        int pick = (int) (Math.random() * (emptyCells.size() - 1));
        // applying Com Player's picked cell to the board
        System.out.println("COM Player "+seedName+", is picking row " + (emptyCells.get(pick)[0][0]+1) + ", column " + (emptyCells.get(pick)[1][0]+1));
        currentRow = emptyCells.get(pick)[0][0];
        currentCol = emptyCells.get(pick)[1][0];
        board[currentRow][currentCol] = theSeed;
    }
}
