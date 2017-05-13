package Tests;

import Controller.SemesterController;
import Controller.UserController;
import Model.SemesterProfile;
import Model.User;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Didac on 09/05/2017.
 */
public class SemesterControllerTest {
    User user;

    @Before
    public void setUp() throws Exception {
        user = UserController.find("xdn15mcu", "test");
    }

    @Test
    public void find() throws Exception {
        SemesterProfile semesterProfile = SemesterController.find(user.getId());
        System.out.println("There are: " + semesterProfile.getModules().size() + " modules");

//        for (Map.Entry<Module, Module> moduleEntry : semesterProfile.getModules().entrySet()) {
//            System.out.println("*** MODULE " + moduleEntry.getKey().getTitle() + ": ");
//            System.out.println("There are: " + semesterProfile.getModules().get(moduleEntry.getKey()).getAssessments().size() + " assessments");
//            for (Map.Entry<Assessment, Assessment> assessmentEntry : moduleEntry.getKey().getAssessments().entrySet()){
//                System.out.println("\t--- Assessment " + assessmentEntry.getKey().getTitle() + ": ");
//                System.out.println("\t--- There are " + assessmentEntry.getKey().getTasks().size() + " tasks");
//                for (Map.Entry<Task, Task> taskEntry : assessmentEntry.getKey().getTasks().entrySet()){
//                    System.out.println("\t\t*** Task " + taskEntry.getKey().getTitle() + ": ");
//                    System.out.println("\t\t*** There are " + taskEntry.getKey().getDependencyTasks().size() + " dependencies");
//                    System.out.println("\t\t*** There are " + taskEntry.getKey().getNotes().size() + " notes");
//                    System.out.println("\t\t*** There are " + taskEntry.getKey().getActivities().size() + " activities: ");
//                    for (Map.Entry<Activity, Activity> activityEntry : taskEntry.getKey().getActivities().entrySet()){
//                        System.out.println("\t\t\t--- Activity " + activityEntry.getKey().getTitle() + ": ");
//                        System.out.println("\t\t\t--- There are " + activityEntry.getKey().getNotes().size() + " notes");
//                    }
//                }
//            }
//        }

        //System.out.println(semesterProfile.getModules().size());
    }

    @Test
    public void insertSemester() throws Exception {

    }

}