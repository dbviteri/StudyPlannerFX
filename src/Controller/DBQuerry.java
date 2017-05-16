package Controller;

import Model.Module;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 100125468 on 16/05/2017.
 */
public interface DBQuerry {
    DatabaseHandler db = new DatabaseHandler();
    default boolean update(String sql, Object... properties){
        try (
                PreparedStatement statement =
                        db.prepareStatement(sql, false, properties)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to update Modules. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    default Integer insert(String sql, Object... properties) {
        Integer UID = null;
        try (
                PreparedStatement statement =
                        db.prepareStatement(sql, true, properties)

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new Semester. No rows affected");

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    UID = set.getInt(1);
                } else {
                    throw new SPException("Failed to create new Semester. No key obtained");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return UID;
    }
}
