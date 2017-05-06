package Controller;
import Model.Module;
import Model.SemesterProfile;
import Utils.SPException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 100125468 on 04/05/2017.
 */
public class ModuleController {
    private static final String QUERY_INSERT_MODULES =
            "INSERT INTO Module (name,code,Semester_ID) VALUES (?,?,?)";
    //TODO : GOOD OR BAD STATIC ?
    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public ModuleController(){
    }
    //TODO : SHOULD IT BE STATIC OR NOT | MAYBE ANOTHER CLASS FOR DB QUERRY?
    public static boolean insertModule(Module module, SemesterProfile associatedSemesterProfile){
        String name = module.getName();
        String code = module.getCode();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_MODULES,true,name,code,1);

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
}
