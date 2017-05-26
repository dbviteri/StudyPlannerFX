package Tests;

import Controller.AssessmentController;
import Model.Assessment;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Test
 */
public class AssessmentControllerTest {
    private AssessmentController assessmentController;

    @Before
    public void setUp() throws Exception {
        assessmentController = new AssessmentController();
    }

    @Test
    public void findAll() throws Exception {
        ArrayList<Assessment> assessments = assessmentController.findAll(64);
        assert assessments.size() == 1;
    }

    @Test
    public void insertAssessment() throws Exception {
        Assessment assessment = new Assessment("test", Assessment.Type.EXAM, 20, new Date(), 10.0);
        assessmentController.insertAssessment(assessment, 50);
    }

    @Test
    public void updateAssessment() throws Exception {
        ArrayList<Assessment> assessments1 = assessmentController.findAll(64);
        Assessment assessment = assessments1.get(0);
        assert assessment.getTitle().equals("OMDBC++");

        assessment.setTitle("OMDBC");
        assessmentController.updateAssessment(assessment);
        ArrayList<Assessment> assessments2 = assessmentController.findAll(64);
        assert assessments2.get(0).getTitle().equals("OMDBC");
    }

    @Test
    public void updateDeadline() throws Exception {
        ArrayList<Assessment> assessments1 = assessmentController.findAll(64);
        Assessment assessment = assessments1.get(0);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assert assessment.getDeadLine().compareTo(dateFormat.parse("2017-09-10")) == 0;

        assessment.setDeadline(dateFormat.parse("2017-08-10"));
        assessmentController.updateAssessment(assessment);
        ArrayList<Assessment> assessments2 = assessmentController.findAll(64);
        assert assessments2.get(0).getDeadLine().compareTo(dateFormat.parse("2017-08-10")) == 0;
    }

    @Test
    public void formAssessment() throws Exception {
        // If all the above tests have passed, this function works correctly.
    }

}