package UI;

import Logic.GameBoard;
import Logic.GamePlayer;
import Logic.MoveSet;
import Logic.Square;
import Utils.GameLoadException;
import Utils.InputScanner;
import Utils.JaxBGridlerClassGenerator;
import jaxb.GameDescriptor;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameManager {
    private GameBoard m_GameBoard;
    private GamePlayer m_Player;
    private Boolean m_PlayerWantsToPlay = true;
    private Boolean m_InGame = false;
    private Boolean m_GameReady = false;
    private ArrayList<MoveSet> m_UndoList;
    private ArrayList<MoveSet> m_RedoList;

    public void Run() {
        while (m_PlayerWantsToPlay) {
            presentMainMenu();
            eGameOptions choice = getPlayersChoiceForMenu();
            switch (choice) {
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
                case QUIT:
                    if (!m_InGame) {
                        m_PlayerWantsToPlay = false;
                    } else {
                        m_InGame = false;
                    }
                    break;
            }
        }
    }

    private void presentMainMenu() {
        int i = 1;

        for (eGameOptions option : eGameOptions.values()) {
            if (m_InGame) {
                if (option != eGameOptions.LOAD_GAME && option != eGameOptions.START_GAME) {
                    System.out.print(i + "." + option.toString());
                    i++;
                } else {
                    System.out.print(i + "." + option.toString());
                    i++;
                }
            }
        }
    }

    private void loadNewGame() {
        String path = getFilePathFromUser();

        try {
            GameDescriptor gameDescriptor = JaxBGridlerClassGenerator.FromXmlFileToObject(path);
            m_GameReady = validateGameDescriptorInfo();
        } catch (JAXBException e) {
            System.out.println("Illegal file.");
        } catch (GameLoadException ex) {
            System.out.println(ex.getMessage());
        }
        if (m_GameReady) {
            initilizeGame();
        }
    }

    private Boolean validateGameDescriptorInfo() throws GameLoadException {
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

    private void startNewGame() {
        m_InGame = true;

        printBoard();
    }

    private void printBoard() {
        for (int i = 0; i < m_GameBoard.getBoardHeight(); i++) {
            for (int j = 0; j < m_GameBoard.getBoardWidth(); j++) {
                try {
                    printSign(m_GameBoard.getSquare(i, j).getCurrentSquareSign());
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    private void printSign(Square.eSquareSign i_Sgin) {
        if (i_Sgin == Square.eSquareSign.BLACKED) {
            System.out.print("|X");
        } else if (i_Sgin == Square.eSquareSign.CLEARED) {
            System.out.print("|O");
        } else {
            System.out.print("| ");
        }
    }

    private void preformPlayerMove() {
        try {
            parseAndMakeAMove();
        } catch (IllegalArgumentException ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void parseAndMakeAMove() throws IllegalArgumentException {
        Integer rowNumS = 0, colNumS = 0, rowNumE = 0, colNumE = 0;
        String userComment = null, changeTo = null;

        /*ToReadMe:
        * enter move in the following format:rowNumS colNumS rowNumE colNumE b/w/n comment
        * rowNumS,colNumS rowNumE,colNumE - enter the square number you want to start filling from
         *use "," between row and col. then enter the square number you want to finish in.
         * The start square must be smaller or equal to the ending square.
        * b/w/n - Turn Squares to black white or non.
        */

        if (parseToSquares(rowNumS, colNumS, rowNumE, colNumE)) {
            if (validateChangeTo(changeTo))
                getComment(userComment);
        } else {
            throw new IllegalArgumentException();
        }

        //ToDo: insert all parms to logic.

        printBoard();
    }

    private Boolean parseToSquares(Integer o_rowNumS, Integer o_colNumS,
                                   Integer o_rowNumE, Integer o_colNumE) {
        Boolean validInput = true;

        if (InputScanner.scanner.hasNextInt()) {
            o_rowNumS = InputScanner.scanner.nextInt();
            if (InputScanner.scanner.hasNextInt())
                o_colNumS = InputScanner.scanner.nextInt();
            if (InputScanner.scanner.hasNextInt())
                o_rowNumE = InputScanner.scanner.nextInt();
            if (InputScanner.scanner.hasNextInt())
                o_colNumE = InputScanner.scanner.nextInt();
        } else {
            validInput = false;
        }
        if (!checkIfFirstSquareIsSmaller(o_rowNumS, o_colNumS,
                o_rowNumE, o_colNumE)) {
            validInput = false;
        }
        return validInput;
    }

    private boolean checkIfFirstSquareIsSmaller(Integer i_rowNumS, Integer i_colNumS,
                                                Integer i_rowNumE, Integer i_colNumE) {
        Boolean startSquareIsSmaller = true;

        if (i_rowNumS > i_rowNumE || i_colNumS > i_colNumE) {
            startSquareIsSmaller = false;
        }

        return startSquareIsSmaller;
    }

    private Boolean validateChangeTo(String o_changeTo) {
        Boolean validInput = true;
        String inputChar;

        if (InputScanner.scanner.hasNext()) {
            inputChar = InputScanner.scanner.next();
            if (inputChar == "b" || inputChar == "w" || inputChar == "n")
                o_changeTo = inputChar;
        } else {
            validInput = false;
        }
        return validInput;
    }

    private void getComment(String o_userComment) {
        if (InputScanner.scanner.hasNext()) {
            o_userComment = InputScanner.scanner.nextLine();
        }
    }

    private void printPlayersMovesList() {

    }

    private void preformUndo() {
    }

    private void preformRedo() {

    }

    private void printStatistics() {

    }

    private eGameOptions getPlayersChoiceForMenu() {
        eGameOptions playersChoice = eGameOptions.START_GAME;
        String input = InputScanner.scanner.nextLine();
        Integer inputAsNum = 0, minVal = eGameOptions.LOAD_GAME.getOrdinalPosition(),
                maxVal = eGameOptions.QUIT.getOrdinalPosition();//Find A Way To Use with enum methods

        if (m_InGame) {
            minVal = eGameOptions.DISPLAY_BORD.getOrdinalPosition();
        } else if (m_GameReady) {
            minVal = eGameOptions.START_GAME.getOrdinalPosition();
        }

        while (inputAsNum < minVal || inputAsNum > maxVal) {
            input = InputScanner.scanner.nextLine();
            if (tryParseInt(input)) {
                inputAsNum = Integer.parseInt(input);
            } else {
                System.out.print("Please select once more.");
            }
        }
        for (eGameOptions options : eGameOptions.values()) {
            if (options.getOrdinalPosition() == inputAsNum)
                playersChoice = options;
        }

        return playersChoice;
    }


    //A parsing utill. Doesn't fit the class.
    Boolean tryParseInt(String i_Value) {
        try {
            Integer.parseInt(i_Value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private enum eGameOptions {

        LOAD_GAME("Load new game", 1),
        START_GAME("Start new game", 2),
        DISPLAY_BORD("Display board", 3),
        PERFORM_MOVE("Preform a new move", 4),
        DISPLAY_MOVES_LIST("Display all made move", 5),
        UNDO("Undo move", 6),
        REDO("Redo move", 7),
        STATISTICS("Display statistics", 8),
        QUIT("Quit", 9);

        private String m_Name;
        private int m_OrdinalPosition;

        private eGameOptions(String i_Name, int i_OrdinalPosition) {
            m_Name = i_Name;
            m_OrdinalPosition = i_OrdinalPosition;
        }

        @Override
        public String toString() {
            return (m_Name);
        }

        public int getOrdinalPosition() {
            return m_OrdinalPosition;
        }
    }
}
