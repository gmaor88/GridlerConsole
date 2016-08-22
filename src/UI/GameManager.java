package UI;

import Logic.*;
import Utils.*;
import jaxb.GameDescriptor;
import javax.xml.bind.JAXBException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Maor Gershkovitch on 8/8/2016.
 */
public class GameManager {
    private GameBoard m_GameBoard;
    private GamePlayer m_Player;
    private LinkedList<MoveSet> m_UndoList = new LinkedList<>();
    private LinkedList<MoveSet> m_RedoList = new LinkedList<>();
    private Boolean m_PlayerWantsToPlay = true;
    private Boolean m_InGame = false;
    private Boolean m_GameReady = false;
    private Long m_GameDurationTimer;

    public GameManager() {
    }

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
            validateGameDescriptorInfo(gameDescriptor);
            loadPlayerData(gameDescriptor);
        } catch (JAXBException e) {
            System.out.println("Illegal file.");
        } catch (GameLoadException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void loadPlayerData(GameDescriptor i_GameDescriptor) throws GameLoadException {
        String userChoice;
        boolean validInput;

        do {
            System.out.print("Would you like to load player data from file? y/n");
            userChoice = InputScanner.scanner.nextLine();
            validInput = userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("n");
        }while (validInput);

        if(userChoice.equalsIgnoreCase("y")){
            GameLoader gameLoader = new GameLoader();
            m_Player = gameLoader.loadPlayer(i_GameDescriptor);
        }
        else{
            getPlayerDataFromUser();
        }
    }

    private void getPlayerDataFromUser() {
       String playerName, playerId, humanPLayer, userChoice;
        Boolean validInput;
        int movesLimit;

        System.out.print("Please enter player name");
        playerName = InputScanner.scanner.nextLine();
        do {
            System.out.print("Please enter player ID");
            playerId = InputScanner.scanner.nextLine();
        }while (Tools.tryParseInt(playerId));

        do {
            System.out.print("Human player? y/n");
            userChoice = InputScanner.scanner.nextLine();
            validInput = userChoice.equalsIgnoreCase("y") || userChoice.equalsIgnoreCase("n");
        }while (validInput);

        humanPLayer = userChoice;
        m_Player = new GamePlayer(humanPLayer.equalsIgnoreCase("y"), playerName, playerId);
        if(humanPLayer.equalsIgnoreCase("y")){
            do {
                System.out.print("Please Enter Pc Move Limit");
                userChoice = InputScanner.scanner.nextLine();
            }while (Tools.tryParseInt(userChoice));

            m_Player.setMoveLimit(Integer.parseInt(userChoice));
        }
    }

    private void validateGameDescriptorInfo(GameDescriptor i_GameDescriptor) throws GameLoadException {
        GameLoader gameloader = new GameLoader();

        m_GameBoard = gameloader.loadBoard(i_GameDescriptor);
    }

    private String getFilePathFromUser() {
        String requestedPath;

        System.out.println("Enter file path:");
        requestedPath = InputScanner.scanner.nextLine();

        return requestedPath;
    }

   /* private void initializeGame() {

    }*/

    private void startNewGame() {
        m_InGame = true;
        m_GameDurationTimer = System.currentTimeMillis();

        printBoard();
    }

    private void printBoard() {
        for (int i = 0; i < m_GameBoard.getBoardHeight(); i++) {
            for (int j = 0; j < m_GameBoard.getBoardWidth(); j++) {
                try {
                    printSign(m_GameBoard.getSquare(i, j).getCurrentSquareSign());
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    return;
                }
            }

            System.out.print("|");
            for(Integer j:m_GameBoard.getVerticalSlice(i)){
                System.out.print(j.toString() + " ");//todo new cast insted of Integer that will tell us if the block might be on the board and make it print in bold
            }

            System.out.println();
            PrintSeparator();
        }

        
    }

    private void PrintSeparator() {
    }

    private void printSign(Square.eSquareSign i_Sign) {
        if (i_Sign == Square.eSquareSign.BLACKED) {
            System.out.print("|X");
        } else if (i_Sign == Square.eSquareSign.CLEARED) {
            System.out.print("|O");
        } else {
            System.out.print("| ");
        }
    }

    private void preformPlayerMove() {
        printBoard();

        try {
            parseAndMakeAMove();
        } catch (IllegalArgumentException ex) {
            System.out.print(ex.getMessage());
        }
    }

    private void AiPlay(){
        Random rand = new Random();
        double percentage = 0;
        int startRow,startCol, endRow, endCol;
        Square.eSquareSign sign;

        while (m_Player.checkIfPlayerHasMovesLeft() && m_GameBoard.getBoardCompletionPercentage() != 100){
            sign = randSign(rand);
            startRow = rand.nextInt(m_GameBoard.getBoardHeight()) + 1;
            endRow = getRandomEndRowOrCol(startRow,m_GameBoard.getBoardHeight(),rand);
            startCol = rand.nextInt(m_GameBoard.getBoardWidth()) + 1;
            endCol = getRandomEndRowOrCol(startCol,m_GameBoard.getBoardWidth(),rand);
            try {
                m_UndoList.addFirst(m_GameBoard.insert(startRow,startCol,endRow,endCol,sign,"Pc"));
                m_RedoList.clear();
                m_Player.insertMoveToMoveList(startRow,startCol,endRow,endCol,sign,"Pc");
            }
            catch (Exception e){
                System.out.print(e.getMessage());
                return;
            }

            if(percentage <= m_GameBoard.getBoardCompletionPercentage()){
                preformUndo();
                m_Player.incrementNumOfUndos();
            }
            else{
                printBoard();
                percentage = m_GameBoard.getBoardCompletionPercentage();
            }
        }
    }

    private int getRandomEndRowOrCol(int i_Start, int i_Limit, Random i_Rand) {
        int end = i_Rand.nextInt(i_Limit) + 1;

        if(i_Start > end){
            end = i_Start;
        }

        return end;
    }

    private Square.eSquareSign randSign(Random i_Rand) {
        Square.eSquareSign sign;
        int numSign = i_Rand.nextInt(3) + 1;

        if(numSign == 1){
            sign = Square.eSquareSign.BLACKED;
        }
        else if(numSign == 2){
            sign = Square.eSquareSign.CLEARED;
        }
        else {
            sign = Square.eSquareSign.UNDEFINED;
        }

        return sign;
    }

    private void parseAndMakeAMove() throws IllegalArgumentException {
        Integer rowNumS = 0, colNumS = 0, rowNumE = 0, colNumE = 0;
        String userComment = null, changeTo = null;
        Square.eSquareSign requestedSign = Square.eSquareSign.UNDEFINED;

        /*ToReadMe:
        * enter move in the following format:rowNumS colNumS rowNumE colNumE b/c/u comment
        * rowNumS,colNumS rowNumE,colNumE - enter the square number you want to start filling from
         *use "," between row and col. then enter the square number you want to finish in.
         * The start square must be smaller or equal to the ending square.
        * b/c/u - Turn Squares to black, cleared or undefined.
        */

        if (!parseToSquares(rowNumS, colNumS, rowNumE, colNumE)) {
            throw new IllegalArgumentException();
        }

        if (!validateChangeTo(changeTo)) {
            throw new IllegalArgumentException();
        }

        if (changeTo.equalsIgnoreCase("b")) {
            requestedSign = Square.eSquareSign.BLACKED;
        }
        else if (changeTo.equalsIgnoreCase("c")) {
            requestedSign = Square.eSquareSign.CLEARED;
        }

        getComment(userComment);

        m_UndoList.add(m_GameBoard.insert(rowNumS, colNumS, rowNumE, colNumE, requestedSign, userComment));
        m_Player.insertMoveToMoveList(rowNumS, colNumS, rowNumE, colNumE, requestedSign, userComment);
        m_RedoList.clear();
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

        if ((i_rowNumS > i_rowNumE || i_colNumS > i_colNumE) &&
                (i_rowNumS < 1)) {
            startSquareIsSmaller = false;
        }

        return startSquareIsSmaller;
    }

    private Boolean validateChangeTo(String o_changeTo) {
        Boolean validInput = true;
        String inputChar;

        if (InputScanner.scanner.hasNext()) {
            inputChar = InputScanner.scanner.next();
            if (inputChar.equalsIgnoreCase("b") || inputChar.equalsIgnoreCase("c") || inputChar.equalsIgnoreCase("u")) {
                o_changeTo = inputChar;
            }
        }
        else {
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
        for(String move : m_Player.getMoveList()){
            System.out.print(move);
        }
    }

    private void preformUndo() {
        if(m_UndoList.isEmpty()){
            System.out.print("Undo unavailable");
            return;
        }

        try {
            m_RedoList.addFirst(undoRedoHandler(m_UndoList));
            m_Player.incrementNumOfUndos();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

        printBoard();
    }

    private void preformRedo() {
        if(m_RedoList.isEmpty()){
            System.out.print("Redo unavailable");
            return;
        }

        try {
            m_UndoList.addFirst(undoRedoHandler(m_RedoList));
            m_Player.incrementNumOfRedos();
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }

        printBoard();
    }

    private MoveSet undoRedoHandler(LinkedList<MoveSet> i_MoveSetList) throws Exception{
        MoveSet moveSet = new MoveSet(i_MoveSetList.getFirst().getComment());
        Square.eSquareSign sign;

        for(Point point: i_MoveSetList.getFirst().getPointsList()){
            sign = m_GameBoard.getSquare(point.getRowCord(), point.getColCord()).getCurrentSquareSign();
            moveSet.AddNewPoint(point.getRowCord(),point.getColCord(),sign);
            m_GameBoard.getSquare(point.getRowCord(), point.getColCord()).setCurrentSquareSign(point.getSign());
        }

        return moveSet;
    }

    private void printStatistics() {
        Long currentTime = System.currentTimeMillis();

        System.out.print("Number of Moves played: " + m_Player.getNumOfMovesMade());
        System.out.print("Number of Undo's made: " + m_Player.getNumOfUndoMade());
        System.out.print("Number of Redo's made: " + m_Player.getNumOfRedoMade());
        System.out.print("Play time: " + ((currentTime - m_GameDurationTimer) * 1000) + "seconds");
        System.out.print("Score: " + m_GameBoard.getBoardCompletionPercentage());
    }

    private eGameOptions getPlayersChoiceForMenu() {
        eGameOptions playersChoice = eGameOptions.START_GAME;
        String input;
        Integer inputAsNum = 0, minVal = eGameOptions.LOAD_GAME.getOrdinalPosition(),
                maxVal = eGameOptions.QUIT.getOrdinalPosition();

        if (m_InGame) {
            minVal = eGameOptions.DISPLAY_BORD.getOrdinalPosition();
        } else if (m_GameReady) {
            minVal = eGameOptions.START_GAME.getOrdinalPosition();
        }

        while (inputAsNum < minVal || inputAsNum > maxVal) {
            input = InputScanner.scanner.nextLine();
            if (Tools.tryParseInt(input)) {
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
