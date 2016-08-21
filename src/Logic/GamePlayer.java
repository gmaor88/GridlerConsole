package Logic;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GamePlayer {
    private final String f_Name;
    private  final String f_Id;
    private final Boolean f_IsHuman;
    private Integer m_MoveLimit;
    Double M_PlayerScore = (double)0;

    public GamePlayer(Boolean i_isHuman, String i_Name, String i_Id){
        f_IsHuman = i_isHuman;
        f_Name = i_Name;
        f_Id = i_Id;
    }

    public void setMoveLimit(Integer i_MoveLimit){
        m_MoveLimit = i_MoveLimit;
    }

    public Boolean getIsHuman() {
        return f_IsHuman;
    }

    public String getName() {
        return f_Name;
    }

    public Double getPlayerScore() {
        return M_PlayerScore;
    }

    public void setPlayerScore(Double m_PlayerScore) {
        M_PlayerScore = m_PlayerScore;
    }
}
