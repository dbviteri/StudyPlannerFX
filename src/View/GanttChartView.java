package View;

import Model.Activity;
import Model.Assessment;
import Model.Milestone;
import Model.Module;

import java.util.HashMap;

/**
 * Created by Didac on 14/05/2017.
 */
public class GanttChartView {
    private HashMap<Activity,Activity> activities;
    private HashMap<Assessment,Assessment> assessments;
    private HashMap<Milestone,Milestone> milestones;

    public GanttChartView(Module module) {
        assessments = module.getAssessments();
        for(HashMap.Entry entry : assessments.entrySet()){
            //Milestone aMilestone = ((Assessment)entry.getValue()).getMilestone();
            //milestones.put(aMilestone,aMilestone);
        }
    }

}
