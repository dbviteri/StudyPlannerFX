package Tests;

import Controller.ActivityController;
import Model.Activity;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test
 */
public class ActivityControllerTest {


    @Test
    public void findAll() throws Exception {
        ActivityController controller = new ActivityController();
        ArrayList<Activity> activities = controller.findAll(19);
        System.out.println(activities.get(0).toString());
    }

}