package Utils;

import Logic.GameBoard;
import Logic.Square;
import jaxb.GameDescriptor;

/**
 * Created by dan on 8/19/2016.
 */
public class GameLoader {
    public GameBoard load(GameDescriptor i_GameDescriptor) throws Exception{
        int columns, rows, blocks[], numberOfSlices, slicesId, numberOfBlackSquares;
        int columnIndex, rowIndex;
        GameBoard board;

        columns = i_GameDescriptor.getBoard().getDefinition().getColumns().intValue();
        rows = i_GameDescriptor.getBoard().getDefinition().getRows().intValue();
        numberOfSlices = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().size();
        numberOfBlackSquares = i_GameDescriptor.getBoard().getSolution().getSquare().size();
        if(rows + columns != numberOfSlices){
            throw new GameLoadException("Invalid number of slices");
        }
        else if(rows*columns < numberOfBlackSquares){
            throw new GameLoadException("Invalid number of black squares");
        }

        board = new GameBoard(rows,columns);

        for(int i = 0; i < numberOfSlices ; i++){
            blocks = getBlocks(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getBlocks());
            slicesId = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getId().intValue();
            if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "rows"){
                if(getNumberOfBlackSquare(blocks) > columns){
                    throw new GameLoadException("Invalid number of blocks in column "+ slicesId);
                }

                board.setHorizontalSlice(slicesId,blocks);
            }
            else if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "columns"){
                if(getNumberOfBlackSquare(blocks) > rows){
                    throw new GameLoadException("Invalid number of blocks in row "+ slicesId);
                }

                board.setVerticalSlice(slicesId, blocks);
            }
            else{
                throw new GameLoadException("Invalid orientation");
            }
        }

        for(int i = 0; i < numberOfBlackSquares; i++) {
            rowIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            columnIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            try{
                board.getSquare(rowIndex,columnIndex).setTrueSquareSignValue(Square.eSquareSign.BLACKED);
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }

        }
    }

    private int getNumberOfBlackSquare(int[] blocks) {
        int sum = 0;

        for(int i:blocks){
            sum += i + 1;
        }

        return  sum - 1;
    }

    private int[] getBlocks(String i_Blocks) {
        int blocks[], i = 0;
        char intermidate[];

        i_Blocks = i_Blocks.trim();
        intermidate = i_Blocks.toCharArray();
        for (char ch:intermidate){
            if
        }
        return blocks;
    }
}
