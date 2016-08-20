package Logic;

/**
 * Created by Maor Gershkovitch on 8/19/2016.
 */
public class MoveSet {
    private Integer m_StartSquareRowNumber;
    private Integer m_StartSquareColNumber;
    private Integer m_EndSquareRowNumber;
    private Integer m_EndSquareColNumber;
    private Square.eSquareSign m_SquareSign;
    private String m_Comment;

    public MoveSet(Integer i_StartSquareRowNumber, Integer i_StartSquareColNumber,
                   Integer i_EndSquareRowNumber, Integer i_EndSquareColNumber,
                   Square.eSquareSign i_Sign, String i_Comment){
        m_StartSquareRowNumber = i_StartSquareRowNumber;
        m_StartSquareColNumber = i_StartSquareColNumber;
        m_EndSquareRowNumber = i_EndSquareRowNumber;
        m_EndSquareColNumber = i_EndSquareColNumber;
        m_SquareSign = i_Sign;
        m_Comment = i_Comment;
    }

    public Square.eSquareSign getSquareSign() {
        return m_SquareSign;
    }

    public Integer getEndSquareColNumber() {
        return m_EndSquareColNumber;
    }

    public Integer getEndSquareRowNumber() {
        return m_EndSquareRowNumber;
    }

    public Integer getStartSquareColNumber() {
        return m_StartSquareColNumber;
    }

    public Integer getStartSquareRowNumber() {
        return m_StartSquareRowNumber;
    }

    public String getComment() {
        return m_Comment;
    }
}
