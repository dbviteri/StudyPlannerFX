package Tests;

import Controller.MilestoneController;
import Model.Milestone;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test
 */
public class MilestoneControllerTest {

    private MilestoneController milestoneController;

    @Before
    public void setUp() throws Exception {
        milestoneController = new MilestoneController();
    }

    @Test
    public void insertMilestone() throws Exception {
        Milestone milestone = new Milestone("test", new Date(), new Date());
        milestoneController.insertMilestone(milestone, 13);

        //assert milestone.equals(addedMilestone);
    }

    @Test
    public void updateMilestone() throws Exception {
    }

}