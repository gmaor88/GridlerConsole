package UI;

import Logic.GameBoard;
import Logic.GamePlayer;
import Logic.Square;
import Utils.GameLoadException;
import Utils.InputScanner;
import Utils.JaxBGridlerClassGenerator;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jaxb.GameDescriptor;

import javax.xml.bind.JAXBException;
import java.util.Scanner;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameManager{
    private GameBoard m_GameBoard;
    private GamePlayer m_Player;
    private Boolean m_PlayerWantsToPlay = true;
    private Boolean m_InGame = false;
    private Boolean m_GameReady = false;

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
        String path = getFilePathFromUser();

        try{
            GameDescriptor gameDescriptor = JaxBGridlerClassGenerator.FromXmlFileToObject(path);
            m_GameReady = validateGameDescriptorInfo();
        }
        catch (JAXBException e){
            System.out.println("Illegal file.");
        }
        catch (GameLoadException ex){
            System.out.println(ex.getMessage());
        }
        if(m_GameReady){
            initilizeGame();
        }
    }

    private Boolean validateGameDescriptorInfo() throws GameLoadException{
        Boolean dataIsValid = true;

        return dataIsValid;
    }

    private String getFilePathFromUser() {
        String requestedPath;

        System.out.println("Enter file path:");
        requestedPath = InputScanner.scanner.nextLine();

        return requestedPath;
    }

    private void initilizeGame() {

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
        try{
            parseAndMakeAMove();
        }
        catch (IllegalArgumentException ex){
            System.out.print(ex.getMessage());
        }
   }

    private void parseAndMakeAMove() throws IllegalArgumentException{
        Integer rowNumS=0, colNumS=0, rowNumE=0, colNumE=0;
        String userComment= null, changeTo = null;

        /*ToReadMe:
        * enter move in the following format:rowNumS colNumS rowNumE colNumE b/w/n comment
        * rowNumS,colNumS rowNumE,colNumE - enter the square number you want to start filling from
         *use "," between row and col. then enter the square number you want to finish in.
         * The start square must be smaller or equal to the ending square.
        * b/w/n - Turn Squares to black white or non.
        */

        if(parseToSquares(rowNumS, colNumS, rowNumE, colNumE)){
            if(validateChangeTo(changeTo))
                getComment(userComment);
            }
        else{
            throw new IllegalArgumentException();
        }

        //ToDo: insert all parms to logic.

        printBoard();
    }

    private Boolean parseToSquares(Integer o_rowNumS, Integer o_colNumS,
                                   Integer o_rowNumE, Integer o_colNumE){
        Boolean validInput = true;

        if(InputScanner.scanner.hasNextInt()){
            o_rowNumS = InputScanner.scanner.nextInt();
            if (InputScanner.scanner.hasNextInt())
                o_colNumS = InputScanner.scanner.nextInt();
                if (InputScanner.scanner.hasNextInt())
                    o_rowNumE = InputScanner.scanner.nextInt();
                    if (InputScanner.scanner.hasNextInt())
                        o_colNumE = InputScanner.scanner.nextInt();
        }
        else{
            validInput = false;
        }
        return validInput;
    }

    private Boolean validateChangeTo(String o_changeTo){
        Boolean validInput = true;
        String inputChar;

        if(InputScanner.scanner.hasNext()){
            inputChar = InputScanner.scanner.next();
            if(inputChar == "b" || inputChar == "w" || inputChar == "n")
                o_changeTo = inputChar;
        }
        else {
            validInput = false;
        }
        return validInput;
    }

    private void getComment(String o_userComment) {
        if (InputScanner.scanner.hasNext()){
            o_userComment = InputScanner.scanner.nextLine();
        }
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