package Controller;

import Model.Note;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 100125468 on 07/05/2017.
 */
public class NoteController implements DBQuery {

    private static final String QUERY_FIND_NOTES =
            "SELECT * FROM Note WHERE task_ID = ? OR activity_ID = ?";
    private static final String QUERY_INSERT_NOTE =
            "INSERT INTO Note (note_title, text, date, task_ID, activity_ID) VALUES (?,?,?,?,?)";
    private static final String QUERY_UPDATE_NOTE =
            "UPDATE Note SET text = ? WHERE task_ID = ? OR activity_ID = ?";

    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    /** Function used to retrieve all
     *  notes of some given properties
     *
     * @param sql
     * @param properties
     * @param <T>
     * @return
     */
    private static <T> ArrayList<T> findAll(String sql, Object... properties) {
        ArrayList<T> notes = new ArrayList<>();
        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties);
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

    /** Function used to update
     *  a note in the Database
     *
     * @param note
     * @param taskId
     * @param activityId
     */
    public void updateNote(Note note, Integer taskId, Integer activityId) {
        Object[] properties = {
                note.getTitle(),
                note.getText(),
                note.getDate(),
                taskId,
                activityId
        };

        Object[] updateProperties = {
                note.getText(),
                taskId,
                activityId
        };

        if (noteExists(taskId, activityId)) {
            updateNote(QUERY_UPDATE_NOTE, updateProperties);
        } else {
            updateNote(QUERY_INSERT_NOTE, properties);
        }
    }

    /** Function used to update
     *  a note in the database
     *
     * @param sql
     * @param properties
     */
    private void updateNote(String sql, Object... properties) {
        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql, false, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new note. No rows affected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Function checks if note is present in database
     *
     * @param taskId
     * @param activityId
     * @return true if present / false otherwise
     */
    private boolean noteExists(Integer taskId, Integer activityId) {
        Object[] properties = {
                taskId,
                activityId
        };

        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_NOTES, false, properties);
                ResultSet set = statement.executeQuery()
        ) {
            if (set.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Function used to create
     *  a note from a result set
     *
     * @param resultSet
     * @return a note
     * @throws SQLException
     */
    static Note formNote(ResultSet resultSet) throws SQLException {
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
