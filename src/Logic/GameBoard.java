package Logic;

import java.util.ArrayList;
import Logic.Square;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameBoard {

    private Square m_board[][];
    private final int f_BoardHeight;
    private final int f_BoardWidth;
    private ArrayList<Integer> m_VerticalSlices[];
    private ArrayList<Integer> m_HorizontalSlices[];
    private int m_MaxHorizontalSlicesLength = 0;
    private int m_MaxVerticalSlicesLength = 0;

    public GameBoard(int i_BoardHeight, int i_BoardWidth){
        f_BoardHeight = i_BoardHeight;
        f_BoardWidth = i_BoardWidth;
        m_VerticalSlices = new ArrayList[i_BoardWidth];
        m_HorizontalSlices = new ArrayList[i_BoardHeight];
        m_board = new Square[f_BoardHeight][f_BoardWidth];
    }

    public int getBoardHeight() {
        return f_BoardHeight;
    }

    public int getBoardWidth() {
        return f_BoardWidth;
    }

    public  int getMaxHorizontalSlicesLength(){return m_MaxHorizontalSlicesLength;}

    public int getMaxVerticalSlicesLength(){return  m_MaxVerticalSlicesLength;}

    public void setVerticalSlice(int i_Width, int [] i_Blocks){
        for(int num: i_Blocks){
            m_VerticalSlices[i_Width].add(num);
        }

        if(m_MaxVerticalSlicesLength < i_Blocks.length){
            m_MaxVerticalSlicesLength = i_Blocks.length;
        }
    }

    public  ArrayList<Integer> getVerticalSlice(int i_Width){
        return  m_VerticalSlices[i_Width];
    }

    public void setHorizontalSlice(int i_Height, int [] i_Blocks){
        for (int num: i_Blocks){
            m_HorizontalSlices[i_Height].add(num);
        }

        if(m_MaxHorizontalSlicesLength < i_Blocks.length){
            m_MaxHorizontalSlicesLength = i_Blocks.length;
        }
    }

    public  ArrayList<Integer> getHorizontalSlice(int i_Height){
        return  m_VerticalSlices[i_Height];
    }

    public Square getSquare(int i_Height, int i_Width) throws ArrayIndexOutOfBoundsException{
        if(0 < i_Height || i_Height > f_BoardHeight){
            throw new ArrayIndexOutOfBoundsException ("Height Out of Bounds");
        }

        if(0 < i_Width || i_Width > f_BoardWidth){
            throw new ArrayIndexOutOfBoundsException ("Width Out of Bounds");
        }

        return  m_board[i_Height][i_Width];
    }

    public MoveSet insert(int i_StartRow, int i_StartColumn, int i_EndRow, int i_EndColumn, Square.eSquareSign i_Sign, String i_Comment) throws  ArrayIndexOutOfBoundsException{
        MoveSet moveset = new MoveSet(i_Comment);

        if(i_EndRow > f_BoardHeight){
            throw new ArrayIndexOutOfBoundsException("End row is out of bounds");
        }
        else if(i_EndColumn > f_BoardWidth){
            throw new ArrayIndexOutOfBoundsException("End column is out of bounds");
        }

        for(int i = i_StartRow - 1; i < i_EndRow; i++){
            for(int j = i_StartColumn - 1; j < i_EndColumn; j++){
                moveset.AddNewPoint(i, j, m_board[i][j].getCurrentSquareSign());
                m_board[i][j].setCurrentSquareSign(i_Sign);
            }
        }

        return moveset;
    }

    public double getBoardCompletionPercentage() {
        double completionPercentage = 0;

        for (int i = 0; i < f_BoardHeight; i++) {
            for (int j = 0; j < f_BoardWidth; j++) {
                if (m_board[i][j].CheckIfCellMarkedCorrectly()) {
                    completionPercentage++;
                }
            }
        }

        completionPercentage /= (f_BoardHeight * f_BoardHeight);

        return  completionPercentage*100;
    }
}
