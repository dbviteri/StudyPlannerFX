package Controller;

import Model.ActivityNote;
import Model.Note;
import Model.TaskNote;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 100125468 on 07/05/2017.
 */
public class NoteController {

    private static final String QUERY_FIND_TASK_NOTES =
            "SELECT * FROM Note WHERE task_ID = ?";
    private static final String QUERY_FIND_ACTIVITY_NOTES =
            "SELECT * FROM Note WHERE activity_ID = ?";

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public static ArrayList<TaskNote> findTaskNotes(int taskId) {
        return findAll(QUERY_FIND_TASK_NOTES, taskId);
    }

    public static ArrayList<ActivityNote> findActivityNotes(int activityId) {
        return findAll(QUERY_FIND_ACTIVITY_NOTES, activityId);
    }

    private static <T> ArrayList<T> findAll(String sql, Object... properties) {
        ArrayList<T> notes = new ArrayList<>();
        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, properties);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                notes.add((T) formNote(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public static Note formNote(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("note_title");
        String text = resultSet.getString("text");
        //Integer taskId = resultSet.getInt("task_ID");
        Date date = resultSet.getDate("date");

        return new Note(title, text, date);
        // If task id is null in db, means it's activity note
//        if (!resultSet.wasNull()) {
//            return new TaskNote(taskId, title, text, date);
//        } else {
//            Integer activityId = resultSet.getInt("activity_ID");
//            return new ActivityNote(activityId, title, text, date);
//        }
    }
}
