package Tests;
import Model.Milestone;
import Model.Task;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 100125468 on 26/05/2017.
 */
public class MilestoneTest {
    Milestone milestone = new Milestone("Test milestone",new Date(), new Date());
    Task task = new Task("Test task",Task.TaskType.READING, "Chapters",
            5,50, new Date());

    @Test
    public void testTasks(){
        System.out.println("Current milestone progress : " + milestone.getProgress());

        System.out.println("Adding task to milestone");
        milestone.addTask(task);
        System.out.println("New completion :" + milestone.getProgress());

        System.out.println("Trying to add it again : " + milestone.addTask(task));

        milestone.deleteTask(task);
        System.out.println("Completion after deleting task : " + milestone.getProgress());

    }
}
