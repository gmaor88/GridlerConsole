package Utils;

import Logic.GameBoard;
import Logic.Square;
import jaxb.GameDescriptor;

import java.util.ArrayList;

/**
 * Created by dan on 8/19/2016.
 */

/*
* 4.	יש לבצע בדיקת תקינות לקלט ולהגיב בצורה נאותה לקלט שאינו תקין.
בדיקות התקינות שעליכם לבצע (אתם מוזמנים להגדיל ראש ולהגן מפני מקרים נוספים):
1.	הקובץ קיים, והוא מסוג XML.
2.	כשטוענים קובץ חדש, יש לוודא כי אין משחק שכבר מתנהל במע' (אפשרי גם ע"י חסימת פקודה מס' מלהתבצע במידה ומשחק פעיל כבר קיים במע').
3.	כמות שורות ו/או עמודות אינה תואמת את גודל הלוח המוצהר
4.	סך הבלוקים בשורה/עמודה גדול מגודל השורה/עמודה בהתאמה
5.	תיאור הפתרון מפנה לשורה/עמודה שהינם מעבר לגבולות הלוח
6.	תיאור הפתרון מכיל משבצת יותר מפעם אחת
7.	בדיקת תקינות קלט כחלק מהאינטרקציה עם המשתמש. (למשל אם אתם מצפים לקבל את מספר השורה/עמודה כחלק מפקודה, והמשתמש הכניס מחרוזת ולא מספר – עליכם לאתר זאת, להודיע זאת למשתמש ולאפשר את המשך מהלך המשחק תוךבחירת פקודה אחרת).
* */
public class GameLoader {
    public GameBoard load(GameDescriptor i_GameDescriptor) throws Exception{
        int columns, rows, blocks[], numberOfSlices, slicesId, numberOfBlackSquares;
        int columnIndex, rowIndex;
        GameBoard board;

        columns = i_GameDescriptor.getBoard().getDefinition().getColumns().intValue();
        rows = i_GameDescriptor.getBoard().getDefinition().getRows().intValue();
        numberOfSlices = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().size();
        numberOfBlackSquares = i_GameDescriptor.getBoard().getSolution().getSquare().size();
        if(rows + columns != numberOfSlices){
            throw new GameLoadException("Invalid number of slices");
        }
        else if(rows*columns < numberOfBlackSquares){
            throw new GameLoadException("Invalid number of black squares");
        }

        board = new GameBoard(rows,columns);

        for(int i = 0; i < numberOfSlices ; i++){
            blocks = getBlocks(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getBlocks());
            slicesId = i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getId().intValue();
            if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "rows"){
                if(getNumberOfBlackSquare(blocks) > columns){
                    throw new GameLoadException("Invalid number of blocks in column "+ slicesId);
                }

                board.setHorizontalSlice(slicesId,blocks);
            }
            else if(i_GameDescriptor.getBoard().getDefinition().getSlices().getSlice().get(i).getOrientation() == "columns"){
                if(getNumberOfBlackSquare(blocks) > rows){
                    throw new GameLoadException("Invalid number of blocks in row "+ slicesId);
                }

                board.setVerticalSlice(slicesId, blocks);
            }
            else{
                throw new GameLoadException("Invalid orientation");
            }
        }

        for(int i = 0; i < numberOfBlackSquares; i++) {
            rowIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            columnIndex = i_GameDescriptor.getBoard().getSolution().getSquare().get(i).getRow().intValue();
            try{
                board.getSquare(rowIndex,columnIndex).setTrueSquareSignValue(Square.eSquareSign.BLACKED);
            }
            catch (Exception e){
                System.out.print(e.getMessage());
            }

        }

        return board;
    }

    private int getNumberOfBlackSquare(int[] blocks) {
        int sum = 0;

        for(int i:blocks){
            sum += i + 1;
        }

        return  sum - 1;
    }

    private int[] getBlocks(String i_Blocks) {
        int blocks[];
        ArrayList<Integer> intermediate = new ArrayList<>(1);
        //first we trim any whitespace
        //then we split the string in to smaller ones with the , separator
        //then intermediate gets the numbers from the sub strings

        i_Blocks = i_Blocks.replaceAll(" ","");//need to make sure it works
        for(String str: i_Blocks.split(",")){
            intermediate.add(Integer.parseInt(str));
        }

        blocks = new int[intermediate.size()];
        for(int i = 0; i < intermediate.size(); i++){
            blocks[i] = intermediate.get(i);
        }

        return blocks;
    }
}
