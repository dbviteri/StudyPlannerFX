package Tests;
import Model.Assessment;
import Model.Milestone;
import Model.Task;
import View.AssessmentsView;
import org.junit.Test;

import java.util.Date;

/**
 * Created by Dinara Adilova on 26/05/2017.
 */
public class AssessmentTest {
    Assessment assessment = new Assessment("Test Exam", Assessment.Type.EXAM,
            50,new Date(), 0.0);
    Task task = new Task("Test task",Task.TaskType.READING, "Chapters",
            5,50, new Date());
    Milestone milestone = new Milestone("Test milestone",new Date(), new Date());
    @Test
    public void taskTest(){
        System.out.println("Completion : " + assessment.getCompletion());
        assessment.addTask(task);
        System.out.println("Added a task :");

        System.out.println(assessment.getTasks().values().toArray()[0]);
        System.out.println("Completion after adding : " + assessment.getCompletion());

        System.out.println("Trying to add same task again");
        System.out.println(assessment.addTask(task));

        assessment.deleteTask(task);
        System.out.println("Now deleted it ");
        System.out.println("Completion after deleting : " + assessment.getCompletion());

    }
    @Test
    public void milestoneTest(){
        System.out.println(assessment.addMilestone(milestone));
        System.out.println(assessment.getMilestones().values().toArray()[0]);
        System.out.println("Trying to add the same milestone again : " + assessment.addMilestone(milestone));
    }
}
