package Model;

import java.util.ArrayList;

/**
 * Created by Tiberiu Voicu-100125468 on 07/05/2017.
 */
public class Milestone {
    private String title;
    private boolean isComplete;
    private ArrayList<Task> requirements;

    public String getTitle(){
        return title;
    }
    public ArrayList<Task> getRequirements(){
        return requirements;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setComplete(boolean isComplete){
        this.isComplete = isComplete;
    }
    public void addRequirement(Task task){
        this.requirements.add(task);
    }
    public void addRequirements(Task[] tasks){
        for(Task task : tasks){
            addRequirement(task);
        }
    }
    public boolean isCompleted(){
        return isComplete;
    }
}
