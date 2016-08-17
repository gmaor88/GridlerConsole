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

    public int get_BoardHeight() {
        return f_BoardHeight;
    }

    public int getF_BoardWidth() {
        return f_BoardWidth;
    }
}
