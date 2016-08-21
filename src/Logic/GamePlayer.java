package Logic;

import java.util.LinkedList;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GamePlayer {
    private final String f_Name;
    private  final String f_Id;
    private final Boolean f_IsHuman;
    private Integer m_MoveLimit;
    private Double m_BoardFillPercentage = 0.0;
    private LinkedList<String> m_MoveList = new LinkedList();

    public GamePlayer(Boolean i_isHuman, String i_Name, String i_Id){
        f_IsHuman = i_isHuman;
        f_Name = i_Name;
        f_Id = i_Id;
    }

    public void setMoveLimit(Integer i_MoveLimit){
        m_MoveLimit = i_MoveLimit;
    }

    public LinkedList<String> getMoveList(){
        return m_MoveList;
    }

    public  void insertMoveToMoveList(int i_StartRow, int i_StartColumn, int i_EndRow, int i_EndColumn, Square.eSquareSign i_Sign, String i_Comment){
        m_MoveList.addFirst(i_StartRow + "," + i_StartColumn + " " + i_EndRow + "," + i_EndColumn + " " + i_Sign + " " + i_Comment);
    }

    public Boolean getIsHuman() {
        return f_IsHuman;
    }

    public String getName() {
        return f_Name;
    }

    public Double getBoardFillPracentage() {
        return m_BoardFillPercentage;
    }

    public void updateBoardFillPracentage(Double i_BoardFillPercentage) {
        m_BoardFillPercentage = i_BoardFillPercentage;
    }
}
