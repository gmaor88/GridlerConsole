package UI;

import Logic.GameBoard;
import Logic.GamePlayer;
import Logic.Square;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameManager{
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
        for(int i = 0; i < m_GameBoard.getBoardHeight(); i++){
            for(int j = 0; j < m_GameBoard.getBoardWidth(); j++){
                try{
                    printSign(m_GameBoard.getSquare(i,j).getCurrentSquareSign());
                }
                catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    private  void printSign(Square.eSquareSign i_Sgin){
        if(i_Sgin == Square.eSquareSign.BLACKED){
            System.out.print("|X");
        }
        else if(i_Sgin == Square.eSquareSign.CLEARED){
            System.out.print("|O");
        }
        else{
            System.out.print("| ");
        }
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
        LOAD_GAME{
            @Override
            public String toString() {
                return ("Load new game");
            }
        },
        START_GAME{
            @Override
            public String toString() {
                return ("Start game");
            }
        },
        DISPLAY_BORD{
        @Override
        public String toString() {
            return ("Display board");
            }
        },
        PERFORM_MOVE{
            @Override
            public String toString() {
                return ("Preform new move");
            }
        },
        DISPLAY_MOVES_LIST{
            @Override
            public String toString() {
                return ("Display moves list");
            }
        },
        UNDO{
            @Override
            public String toString() {
                return ("Undo last move");
            }
        },
        REDO{
            @Override
            public String toString() {
                return ("Redo move");
            }
        },
        STATISTICS{
            @Override
            public String toString() {
                return ("Display statistics");
            }
        },
        QUIT{
            @Override
            public String toString() {
                return ("Quit");
            }
        };
        }
    }