package Controller;

import Model.Note;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 100125468 on 07/05/2017.
 */
public class NoteController {
    private static final String QUERY_FIND_NOTES =
            "";

    private DatabaseHandler dbhandler = DatabaseHandler.getInstance();


    public ArrayList<Note> findAll(int id) {
        ArrayList<Note> notes= new ArrayList<>();
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_NOTES, id);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                notes.add(formNote(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Note formNote(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        String text = resultSet.getString("text");
        Date date = resultSet.getDate("date");
        Integer taskId = resultSet.getInt("task_ID");
        if (resultSet.wasNull()) taskId = null;

        Integer activityId = resultSet.getInt("activity_ID");
        if (resultSet.wasNull()) activityId = null;

        return new Note(title, text, date, taskId, activityId);
    }
}
