package Tests;

import Controller.TaskController;
import Model.Assessment;
import Model.SemesterProfile;
import Model.Task;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 *
 * Created by Didac on 06/05/2017.
 */
public class TaskControllerTest {
    TaskController taskController;
    Assessment assessment;
    Date date = new Date();

    @Before
    public void setUp() throws Exception {
        taskController = new TaskController();
        assessment = new Assessment(2, "test", Assessment.Event.ASSIGNMENT, 0, date);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void find() throws Exception {
        ArrayList<Task> tasks = taskController.findAll(assessment);

        for (Task task : tasks) {
            assessment.addTask(task);
            System.out.println(task.toString());
        }

        assert (assessment.getTasks().size() == 3);
    }

    @Test
    public void insertTask() throws Exception {
        //ArrayList<Task> tasks = taskController.findAll(assessment);
        Task task = new Task("test4", Task.TaskType.PROGRAMMING, 3, "test4", 4, 5, null, 2);

        taskController.insertTask(task);
    }

}