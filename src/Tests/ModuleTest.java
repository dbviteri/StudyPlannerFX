package Tests;

import Model.Assessment;
import Model.Module;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Didac on 12/05/2017.
 */
public class ModuleTest {
    @Test
    public void addAssessment() throws Exception {
        Assessment assessment = new Assessment("test", Assessment.Type.EXAM, 2, new Date(), 2.0);
        Assessment assessment2 = new Assessment("test2", Assessment.Type.EXAM, 2, new Date(), 2.0);

        Module module = new Module();
        module.addAssessment(assessment);
        module.addAssessment(assessment2);

        System.out.println(module.getAssessments().size());
    }

    @Test
    public void getAssessments() throws Exception {

    }

}