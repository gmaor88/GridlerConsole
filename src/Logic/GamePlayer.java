package Logic;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GamePlayer {
    private final String f_Name;
    private final Boolean f_IsHuman;
    private Integer m_MovesLimit;
    Double M_PlayerScore = (double)0;

    public GamePlayer(Boolean i_isHuman, String i_Name){
        f_IsHuman = i_isHuman;
        f_Name = i_Name;
    }

    public Boolean get_IsHuman() {
        return f_IsHuman;
    }

    public String getF_Name() {
        return f_Name;
    }

    public Double getM_PlayerScore() {
        return M_PlayerScore;
    }

    public void setM_PlayerScore(Double m_PlayerScore) {
        M_PlayerScore = m_PlayerScore;
    }
}
