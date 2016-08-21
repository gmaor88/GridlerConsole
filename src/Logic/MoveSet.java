package Logic;

import java.util.LinkedList;

/**
 * Created by Maor Gershkovitch on 8/19/2016.
 */
public class MoveSet {
    LinkedList<Point> m_PointsList = new LinkedList<>();
    String m_Comment = null;

    public MoveSet(String i_Comment){
        m_Comment = i_Comment;
    }

    public void AddNewPoint(Integer i_RowCord, Integer i_ColCord, Square.eSquareSign i_Sign){
        Point pointToAdd = new Point(i_RowCord, i_ColCord, i_Sign);
        m_PointsList.addFirst(pointToAdd);
    }

    public void AddNewPoint(Point i_Point){
        m_PointsList.addFirst(i_Point);
    }

    public LinkedList<Point> getPointsList(){
        return  m_PointsList;
    }

    public String getComment (){
        return m_Comment;
    }
}
