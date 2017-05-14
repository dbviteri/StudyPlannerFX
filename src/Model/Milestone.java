package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tiberiu Voicu-100125468 on 07/05/2017.
 */
public class Milestone {
    private String title;
    private boolean isComplete;
    private Map<Task, Task> requirements = new HashMap<>();

    public String getTitle(){
        return title;
    }

    public HashMap<Task, Task> getRequirements() {
        return new HashMap<>(requirements);
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setComplete(boolean isComplete){
        this.isComplete = isComplete;
    }
    public void addRequirement(Task task){
        this.requirements.put(task, task);
    }

    public boolean isCompleted(){
        return isComplete;
    }
}
