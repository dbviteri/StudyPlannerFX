package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Model representation for a milestone
 * Created by Tiberiu Voicu-100125468 on 07/05/2017.
 */
public class Milestone {
    private Integer id;
    private String title;
    private DoubleProperty progress = new SimpleDoubleProperty();
    private Date start;
    private Date deadline;
    private HashMap<Task,Task> tasks;

    public Milestone(String title, Date start, Date deadline) {
        // IF FIRST TIME MILESTONE IS ADDED DATE = CURRENT DATE
        id = UUID.randomUUID().hashCode();
        this.title = title;
        this.start = start;
        this.deadline = deadline;
    }

    public Milestone(Integer id, String title, double progress, Date start, Date deadline) {
        this(title, start, deadline);
        this.progress.setValue(progress);
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public double getProgress() { return progress.get(); }

    public Date getDeadline() { return deadline; }
    public HashMap<Task,Task> getTasks() { return new HashMap<>(tasks); }

    public void setTitle(String title){
        this.title = title;
    }

    public void setProgress(double progress) {this.progress.set(progress);}

    public void setDeadline(Date deadline) { this.deadline = deadline; }

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
        sb.append(title).append("\n").append("Completion : ").append(progress.get());
        sb.append("\n Unfinished Tasks : ");
        for(HashMap.Entry entry : tasks.entrySet()){
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
