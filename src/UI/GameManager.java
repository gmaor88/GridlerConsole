package UI;

import Logic.GameBoard;
import Logic.GamePlayer;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameManager {
    private GameBoard m_GameBoard;
    private GamePlayer m_Player;
    private Boolean m_PlayerWantsToPlay = true;
    private Boolean m_InGame = false;

    public void Run(){
        while (m_PlayerWantsToPlay){
            presentMainMenu();
            eGameOptions choice = getPlayersChoice();
            switch (choice){
                case LOAD_GAME:
                    loadNewGame();
                    break;
                case START_GAME:
                    startNewGame();
                    break;
                case DISPLAY_BORD:
                    printBoard();
                    break;
                case PERFORM_MOVE:
                    preformPlayerMove();
                    break;
                case DISPLAY_MOVES_LIST:
                    printPlayersMovesList();
                    break;
                case UNDO:
                    preformUndo();
                    break;
                case REDO:
                    preformRedo();
                    break;
                case STATISTICS:
                    printStatistics();
                    break;
                case  QUIT:
                    if(!m_InGame) {
                        m_PlayerWantsToPlay = false;
                    }
                    else{
                        m_InGame = false;
                    }
                    break;
            }
        }
    }

    private void presentMainMenu() {
        int i = 1;

        for (eGameOptions option : eGameOptions.values()){
            if(m_InGame) {
                if(option!=eGameOptions.LOAD_GAME && option!= eGameOptions.START_GAME){
                    System.out.print(i +"." + option.toString());
                    i++;
            }
            else{
                    System.out.print(i +"." + option.toString());
                    i++;
                }
            }
        }
    }

    private void loadNewGame(){
        String path = getFileNameFromUser();
        try{
            openFile(path);
            ValidateFilesData();
        }
    }

    private void startNewGame(){
        m_InGame = true;

        printBoard();
    }

    private void printBoard() {

    }

    private void preformPlayerMove(){

    }

    private void printPlayersMovesList(){

    }

    private void preformUndo() {
    }

    private void preformRedo(){

    }

    private void printStatistics() {

    }

    private eGameOptions getPlayersChoice(){
        eGameOptions playersCoice;

        return playersCoice;
    }

    public enum eGameOptions {
        LOAD_GAME,
        SET_PLAYER_TYPE,
        START_GAME,
        DISPLAY_BORD,
        PERFORM_MOVE,
        DISPLAY_MOVES_LIST,
        UNDO,
        REDO,
        STATISTICS,
        QUIT
    }

}
