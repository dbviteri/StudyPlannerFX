package Tests;

import Model.Activity;
import Model.Task;
import Model.TaskNote;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * Created by Didac on 13/05/2017.
 */
public class TaskTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void addNote() throws Exception {
        TaskNote note = new TaskNote("test", "test", new Date());
        TaskNote note2 = new TaskNote("test", "test", new Date());

//        Task task = new Task();
//        task.addNote(note);
//        task.addNote(note2);
//
//        //System.out.println(task.getNotes().get(note).getTitle());
//        //assert !task.getNotes().get(note).equals(task.getNotes().get(note2));
//        assert task.getNotes().size() == 2;
    }

    @Test
    public void addActivity() throws Exception {
        Activity activity = new Activity("test", 2, 2, new Date());
        Activity activity2 = new Activity("test", 2, 2, new Date());
        Activity activity3 = new Activity("test", 2, 2, new Date());

        Task task = new Task();
        task.addActivity(activity);
        task.addActivity(activity2);
        task.addActivity(activity3);


        for (Map.Entry<Activity, Activity> activities : task.getActivities().entrySet()) {
            System.out.println(activities.getValue().getTitle());
        }
    }

    @Test
    public void addDependency() throws Exception {

    }

    @Test
    public void getDependencies() throws Exception {

    }

    @Test
    public void getActivities() throws Exception {

    }
}