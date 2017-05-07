package Controller;

import Model.Note;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 100125468 on 07/05/2017.
 */
public class NoteController {
    private DatabaseHandler dbhandler;
    private static final String QUERRY_FIND_NOTES =
            "";

    public NoteController(){
        dbhandler = DatabaseHandler.getInstance();
    }
    public ArrayList<Note> findAll(){
        Note note = null;
        ArrayList<Note> notes= new ArrayList<>();
//        try (
//                PreparedStatement statement = dbhandler.prepareStatement(, false, assessment.getId());
//                ResultSet resultSet = statement.executeQuery()
//        ) {
//            while (resultSet.next()) {
//                task = formTask(resultSet);
//                tasks.add(task);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
