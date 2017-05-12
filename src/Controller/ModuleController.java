package Controller;

import Model.Module;
import Utils.SPException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 100125468 on 04/05/2017.
 */
public class ModuleController {
    private static final String QUERY_INSERT_MODULE =
            "INSERT INTO Module (title,code,Semester_ID) VALUES (?,?,?)";
    private static final String QUERY_FIND_MODULES =
            "SELECT * FROM Module WHERE Semester_ID = ?";
    private static final String QUERY_UPDATE_MODULE =
            "UPDATE Module SET title = ?, code = ?, Semester_ID = ? WHERE code = ?";
    //TODO : GOOD OR BAD STATIC ?
    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public ModuleController(){
    }
    //TODO : SHOULD IT BE STATIC OR NOT | MAYBE ANOTHER CLASS FOR DB QUERRY?

    /** Function inserts a module into the DB
     *  return true if action completed succesfuly ,
     *  false otherwise
     *
     * @param module
     * @return true/false
     */
    public static boolean insertModule(Module module){
        String name = module.getTitle();
        String code = module.getCode();
        int id = module.getSemesterID();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_MODULE,name,code,id)

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to add Modules. No rows affected");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Function used to return all modules
     *  matching the semesterID from the database
     *
     * @param semesterID
     * @return ArrayList
     */
    public static ArrayList<Module> findAll(int semesterID) {
        ArrayList<Module> modules = new ArrayList<>();
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_MODULES, semesterID);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                modules.add(formModule(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modules;
    }

    /**
     * Function used to create an object from
     * a resultSet received from the database
     *
     * @param resultSet
     * @return Module
     * @throws SQLException
     */
    private static Module formModule(ResultSet resultSet) throws SQLException {
        String title = resultSet.getString("title");
        String code = resultSet.getString("code");
        int semesterID = resultSet.getInt("Semester_ID");

        Module module = new Module(title, code, semesterID);
        return module;
    }

    /** Function used to update
     *
     * @param module
     * @return
     */
    public boolean updateModule(Module module){
        String title = module.getTitle();
        String code = module.getCode();
        int semesterID = module.getSemesterID();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_MODULE,true,title,code,semesterID)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to update Modules. No rows affected");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
