package Tests;

import Controller.TaskController;
import Model.Assessment;
import Model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

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
        assessment = new Assessment(1, "test", Assessment.Type.ASSIGNMENT, 0, date, 2, "CMP");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void find() throws Exception {
        ArrayList<Task> tasks = taskController.findAll(assessment.getId());

        for (Task task : tasks) {
            assessment.addTask(task);
            System.out.println(task.toString());
        }

        assert (assessment.getTasks().size() == 3);
    }

    @Test
    public void insertTask() throws Exception {
        //ArrayList<Task> tasks = taskController.findAll(assessment);
        //Task task = new Task("test4", Task.TaskType.PROGRAMMING, 3, "test4", 4, 5, null, 2);

        //taskController.insertTask(task);
    }

    @Test
    public void deleteTask() throws Exception {
        ArrayList<Task> allTasks = taskController.findAll(assessment.getId());
        Task toBeDeleted = allTasks.get(0);
        taskController.deleteTask(toBeDeleted);

        ArrayList<Task> oneLessTask = taskController.findAll(assessment.getId());
        Task differentTask = oneLessTask.get(0);

        assert (!toBeDeleted.equals(differentTask));
    }

}