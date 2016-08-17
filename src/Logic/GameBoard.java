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

    public GameBoard(int i_BoardHeight, int i_BoardWidth){
        f_BoardHeight = i_BoardHeight;
        f_BoardWidth = i_BoardWidth;
    }

    public int getBoardHeight() {
        return f_BoardHeight;
    }

    public int getBoardWidth() {
        return f_BoardWidth;
    }

    public Square getSquare(int i_Height, int i_Width) throws Exception{
        if(0 < i_Height || i_Height > f_BoardHeight){
            throw new Exception("Height Out of Bounds");
        }

        if(0 < i_Width || i_Width > f_BoardWidth){
            throw new Exception("Width Out of Bounds");
        }

        return  m_board[i_Height][i_Width];
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
