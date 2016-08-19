package Utils;

import Logic.GameBoard;
import Logic.Square;
import jaxb.GameDescriptor;

/**
 * Created by dan on 8/19/2016.
 */
public class GameLoader {
    public GameBoard load(GameDescriptor i_GameDescriptor){
        int columns, rows, blocks[], numberOfSlices, slicesId, numberOfBlackSquares;
        int columnIndex, rowIndex;
        GameBoard board;

        columns = i_GameDescriptor.getBoard().getDefinition().getColumns().intValue();
        rows = i_GameDescriptor.getBoard().getDefinition().getRows().intValue();
        numberOfSlices = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().size();
        numberOfBlackSquares = i_GameDescriptor.getBoard().getSolution().getSquare().size();
        if(rows + columns != numberOfSlices){
            throw ...
        }
        else if(rows*columns < numberOfBlackSquares){
            throw ...
        }

        board = new GameBoard(rows,columns);

        for(int i = 0; i < numberOfSlices ; i++){
            blocks = getBlocks(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getBlocks());
            slicesId = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getId().intValue();
            if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "rows"){
                board.setHorizontalSlice(slicesId,blocks);
            }
            else if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "columns"){
                board.setVerticalSlice(slicesId, blocks);
            }
            else{
                throw ...
            }
        }

        for(int i = 0; i < numberOfBlackSquares; i++) {
            rowIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            columnIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            try{
                board.getSquare(rowIndex,columnIndex).setCurrentSquareSign(Square.eSquareSign.BLACKED);
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }

        }
    }

    private int[] getBlocks(String i_Blocks) {
        int blocks[];

        return blocks;
    }
}
