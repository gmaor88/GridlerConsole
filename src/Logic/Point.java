package Logic;

/**
 * Created by Maor Gershkovitch on 8/21/2016.
 */
public class Point {
    Integer m_RowCord;
    Integer m_ColCord;
    Square.eSquareSign m_Sign;

    public Point( Integer i_RowCord, Integer i_ColCord, Square.eSquareSign i_Sign){
        m_RowCord = i_RowCord;
        m_ColCord = i_ColCord;
        m_Sign = i_Sign;
    }

    public Square.eSquareSign getSign() {
        return m_Sign;
    }

    public Integer getColCord() {
        return m_ColCord;
    }

    public Integer getRowCord() {
        return m_RowCord;
    }
}
