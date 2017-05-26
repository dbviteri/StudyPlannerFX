package Tests;

import Controller.TaskController;
import Model.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test
 */
public class TaskControllerTest {

    private TaskController taskController;

    @Before
    public void setUp() throws Exception {
        taskController = new TaskController();
    }

    @Test
    public void findAll() throws Exception {
        assert taskController.findAll(13).size() == 1;
    }

    @Test
    public void findAllDependencies() throws Exception {
        assert taskController.findAllDependencies(23).size() == 0;
    }

    @Test
    public void insertTask() throws Exception {
        Task task = new Task();
        task.setType(Task.TaskType.PROGRAMMING);
        taskController.insertTask(task, 13, null);
        assert taskController.findAll(13).size() == 2;
    }

    @Test
    public void deleteTask() throws Exception {
        Task task = new Task();
        task.setId(24);
        task.setType(Task.TaskType.PROGRAMMING);
        taskController.deleteTask(task);
        assert taskController.findAll(13).size() == 1;
    }

    @Test
    public void updateTask() throws Exception {
        ArrayList<Task> tasks = taskController.findAll(13);
        Task task = tasks.get(0);

        task.setTitle("ANOTHER TITLE");

        taskController.updateTask(task);

        ArrayList<Task> tasks2 = taskController.findAll(13);
        Task task2 = tasks2.get(0);

        assert task2.getTitle().equals("ANOTHER TITLE");
    }

    @Test
    public void formTask() throws Exception {
        // If above work, it works
    }

    @Test
    public void formDependency() throws Exception {
        // If above work, it works
    }

}