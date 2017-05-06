package Controller;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 100125468 on 04/05/2017.
 */
public class ModuleController {
    // Find a module based on semester id

    private DatabaseHandler dbhandler;

    public ModuleController(){
        dbhandler = DatabaseHandler.getDatabaseHandler();
    }

    public boolean addModule(String sql, Object... properties){
        try (
                PreparedStatement statement = dbhandler.prepareStatement(sql,true,properties);

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to add Data. No rows affected");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
