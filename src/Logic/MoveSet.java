package Logic;

/**
 * Created by Maor Gershkovitch on 8/19/2016.
 */
public class MoveSet {
    private Integer m_StartSquareRowNumber;
    private Integer m_StartSquareColNumber;
    private Integer m_EndSquareRowNumber;
    private Integer m_EndSquareColNumber;
    private Square.eSquareSign m_squareSign;
    private String m_comment;

    public MoveSet(Integer i_StartSquareRowNumber, Integer i_StartSquareColNumber,
                   Integer i_EndSquareRowNumber, Integer i_EndSquareColNumber,
                   Square.eSquareSign i_sign, String i_comment){
        m_StartSquareRowNumber = i_StartSquareRowNumber;
        m_StartSquareColNumber = i_StartSquareColNumber;
        m_EndSquareRowNumber = i_EndSquareRowNumber;
        m_EndSquareColNumber = i_EndSquareColNumber;
        m_squareSign = i_sign;
        m_comment = i_comment;
    }

    
}
