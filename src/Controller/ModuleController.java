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
public class ModuleController implements DBQuery {
    private static final String QUERY_INSERT_MODULE =
            "INSERT INTO Module (module_title, code, Semester_ID) VALUES (?, ?, ?)";
    private static final String QUERY_FIND_MODULES =
            "SELECT * FROM Module WHERE semester_id = ?";
    private static final String QUERY_UPDATE_MODULE =
            "UPDATE Module SET module_title = ?, code = ? WHERE module_id = ?";
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
    public void insertModule(Module module, int semesterId) {

        Object[] properties = {
                module.getTitle(),
                module.getCode(),
                semesterId
        };
        //int id = module.getSemesterID();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_MODULE, true, properties)

        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to create new module. No rows affected");

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    module.setModuleId(set.getInt(1));
                } else {
                    throw new SPException("Failed to create new module. No key obtained");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Function used to return all modules
     *  matching the semesterID from the database
     *
     * @param semesterID
     * @return ArrayList
     */
    public ArrayList<Module> findAll(int semesterID) {
        ArrayList<Module> modules = new ArrayList<>();
        try (
                PreparedStatement statement = dbhandler.prepareStatement(QUERY_FIND_MODULES, false, semesterID);
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
    static Module formModule(ResultSet resultSet) throws SQLException {
        int moduleId = resultSet.getInt("module_id");
        String title = resultSet.getString("module_title");
        String code = resultSet.getString("code");
        return new Module(moduleId, title, code);
    }

    /** Function used to update a
     *  module in the database
     *
     * @param module
     * @return true if updated / false otherwise
     */
    public static boolean updateModule(Module module){

        Object[] properties = {
                module.getTitle(),
                module.getCode(),
                module.getId()
        };
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_UPDATE_MODULE, false, properties)
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
