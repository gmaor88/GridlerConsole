package Utils;

/**
 * Created by Maor Gershkovitch on 8/17/2016.
 */
public class GameLoadException extends Exception {
    String m_ErrorMsg;

    public String get_ErorMsg() {
        return m_ErrorMsg;
    }

    public void set_ErorMsg(String i_ErrorMsg) {
        m_ErrorMsg = i_ErrorMsg;
    }
}
