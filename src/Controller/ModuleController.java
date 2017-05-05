package Controller;
import Model.Assessment;
import Model.Module;
import Model.Semester;
import Model.User;
import Utils.ControlledScene;
import Utils.SPException;
import Utils.StageHandler;
import com.sun.xml.internal.bind.v2.TODO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by 100125468 on 04/05/2017.
 */
public class ModuleController {
    private DatabaseHandler dbhandler;

    public ModuleController(DatabaseHandler db){
        this.dbhandler = db;
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
