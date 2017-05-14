package Model;

import java.util.HashMap;

/**
 * Model representation for a milestone
 * Created by Tiberiu Voicu-100125468 on 07/05/2017.
 */
public class Milestone {
    private String title;
    private boolean isComplete;
    private HashMap<Task,Task> tasks;

    public Milestone(String title){
        this.title = title;
    }
    public Milestone(String title, HashMap<Task,Task> tasks){
        this(title);
        this.tasks = tasks;
    }

    public String getTitle(){
        return title;
    }
    public boolean getIsComplete(){
        return isComplete;
    }
    public HashMap<Task,Task> getTasks() { return new HashMap<>(tasks); }

    public void setTitle(String title){
        this.title = title;
    }
    public void setComplete(boolean isComplete){
        this.isComplete = isComplete;
    }

    /** Function used to check if milestone is complete
     *  by checking if every depending task is complete
     *  it also updates isComplete variable
     *
     * @return boolean
     */
    public boolean checkCompletion(){
        for(HashMap.Entry entry : tasks.entrySet()){
            if(!((Task)entry.getValue()).isComplete()){
                return false;
            }
        }
        isComplete = true;
        return true;
    }

    /** Function used to return a HashMap
     *  containing unfinished tasks,
     *  related to this milestone
     *
     * @return HashMap<Task,Task>
     */
    public HashMap<Task,Task> getIncompletes(){
        HashMap<Task,Task> incomplete = new HashMap<>();
        for(HashMap.Entry entry : tasks.entrySet()){
            if(!((Task)entry.getValue()).isComplete()){
                incomplete.put((Task)entry.getKey(),(Task)entry.getValue());
            }
        }
        return incomplete;
    }
    /** Function adds a task requirement
     *  to the Milestone
     *
     * @param task
     */
    public void addTask(Task task){
        tasks.put(task,task);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(title).append("\n").append("Completion : ").append(isComplete);
        sb.append("\n Unfinished Tasks : ");
        for(HashMap.Entry entry : tasks.entrySet()){
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
