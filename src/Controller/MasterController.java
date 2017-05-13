//package Controller;
//
//
//import Model.*;
//
//import java.util.ArrayList;
//
///**
// * Created by 100125468 on 05/05/2017.
// */
////TODO MASTER CONTROLLER TO LINK CONTROLLERS OR NOT ?
//
//public class MasterController {
//
//    public static SemesterProfile getSemester(int userId){
//        SemesterProfile semesterProfile = SemesterController.find(userId);
//
//        ArrayList<Module> modules = ModuleController.findAll(semesterProfile.getSemesterId());
//
//        for (Module module : modules){
//            ArrayList<Assessment> assessments = AssessmentController.findAll(module.getModuleId());
//            for (Assessment assessment : assessments){
//                ArrayList<Task> tasks = TaskController.findAll(assessment.getId());
//
//                for (Task task : tasks){
//                    ArrayList<Activity> activities = ActivityController.findAll(task.getId());
//                    for (Activity activity : activities){
//                        ArrayList<ActivityNote> activityNotes = NoteController.findActivityNotes(activity.getActivityId());
//                        activity.setNotes(activityNotes);
//                    }
//
//                    ArrayList<TaskNote> notes = NoteController.findTaskNotes(task.getId());
//                    task.setActivities(activities);
//                    task.setNotes(notes);
//                }
//                assessment.addTasks(tasks);
//            }
//
//            module.addAssessments(assessments);
//        }
//
//        semesterProfile.addModules(modules);
//
//        return semesterProfile;
//    }
//
//    public void updateSemester(){
//
//    }
//}
