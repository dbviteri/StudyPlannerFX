package Controller;
import Model.Assessment;
import Model.Module;
import Utils.SPException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
/**
 * Created by 100125468 on 04/05/2017.
 */
public class AssessmentController {

    private static final String QUERY_INSERT_ASSINGMENT =
            "INSERT INTO Assessment (title,isExam,deadline,weight,module_ID) VALUES (?,?,?,?,?)";
    private static DatabaseHandler dbhandler = DatabaseHandler.getInstance();

    public AssessmentController(DatabaseHandler db){
    }

    public boolean insertAssessment(Assessment assessment, Module module){
        String title = assessment.getTitle();
        String type = assessment.getType().toString();
        int weight = assessment.getWeight();
        Date deadline = assessment.getDeadLine();
        String code = module.getCode();
        try (
                PreparedStatement statement =
                        dbhandler.prepareStatement(QUERY_INSERT_ASSINGMENT,true,
                                title,type,weight,deadline,code)
        ) {
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0) throw new SPException("Failed to add Assessmentsd. No rows affected");
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
