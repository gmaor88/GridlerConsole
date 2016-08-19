package Utils;

import Logic.GameBoard;
import jaxb.GameDescriptor;

import java.math.BigInteger;

/**
 * Created by dan on 8/19/2016.
 */
public class GameLoader {
    public GameBoard load(GameDescriptor i_GameDescriptor){
        int columns,rows,blocks[];
        GameBoard board;

        columns = i_GameDescriptor.getBoard().getDefinition().getColumns().intValue();
        rows = i_GameDescriptor.getBoard().getDefinition().getRows().intValue();
        board = new GameBoard(rows,columns);

        for(int i = 0; i < rows; i++){
            blocks = getBlocks(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getBlocks());
            board.setHorizontalSlice(i,blocks);
        }

        int i = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(1).getOrientation();
    }

    private int[] getBlocks(String i_Blocks) {
        int blocks[];

        return blocks;
    }
}
