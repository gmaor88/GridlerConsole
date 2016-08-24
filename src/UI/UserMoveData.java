package UI;

import Logic.Square;

/**
 * Created by Maor Gershkovitch on 8/24/2016.
 */
public class UserMoveData {

    private Integer m_StartSquareRowNum = 0;
    private Integer m_StartSquareColNum = 0;
    private Integer m_EndSquareRowNum = 0;
    private Integer m_EndSquareColNum = 0;
    private Square.eSquareSign m_Sign = Square.eSquareSign.UNDEFINED;
    private String m_Comment = null;

    public Square.eSquareSign getSign() {
        return m_Sign;
    }

    public String getComment() {
        return m_Comment;
    }

    public Integer getStartSquareRowNum() {
        return m_StartSquareRowNum;
    }

    public Integer getStartSquareColNum() {
        return m_StartSquareColNum;
    }

    public Integer getEndSquareRowNum() {
        return m_EndSquareRowNum;
    }

    public Integer getEndSquareColNum() {
        return m_EndSquareColNum;
    }

    public void setSign(Square.eSquareSign i_Sign) {
        m_Sign = i_Sign;
    }

    public void setComment(String i_Comment) {
        m_Comment = i_Comment;
    }

    public void setStartSquareRowNum(Integer i_StartSquareRowNum) {
        m_StartSquareRowNum = i_StartSquareRowNum;
    }

    public void setStartSquareColNum(Integer i_StartSquareColNum) {
        m_StartSquareColNum = i_StartSquareColNum;
    }

    public void setEndSquareRowNum(Integer i_EndSquareRowNum) {
        m_EndSquareRowNum = i_EndSquareRowNum;
    }

    public void setEndSquareColNum(Integer i_EndSquareColNum) {
        m_EndSquareColNum = i_EndSquareColNum;
    }
}
