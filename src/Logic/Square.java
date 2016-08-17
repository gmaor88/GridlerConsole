package Logic;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class Square {
    private eSquareSign m_CurrentSquareSign=eSquareSign.UNDEFINED;
    private eSquareSign m_TrueSquareSignValue=eSquareSign.CLEARED;

    public eSquareSign getM_CurrentSquareSign() {
        return m_CurrentSquareSign;
    }

    public eSquareSign getM_TrueSquareSignValue() {
        return m_TrueSquareSignValue;
    }

    public void setCurrentSquareSign(eSquareSign i_GivenSign){
        m_CurrentSquareSign = i_GivenSign;
    }

    public void setM_TrueSquareSignValue(eSquareSign i_GivenSign){
        m_CurrentSquareSign = i_GivenSign;
    }

    public Boolean CheckIfCellMarkedCorrectly(){
        return m_CurrentSquareSign == m_TrueSquareSignValue;
    }

    public enum eSquareSign{
        BLACKED,
        CLEARED,
        UNDEFINED
    }
}
