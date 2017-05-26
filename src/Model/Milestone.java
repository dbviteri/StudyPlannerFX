package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private HashMap<Task, Task> tasks = new HashMap<>();

    private ObservableList<Task> observableTaskList = FXCollections.observableArrayList();

    /** Constructor used when first
     *  creating a milestone into memory
     *
     * @param title
     * @param start
     * @param deadline
     */
    public Milestone(String title, Date start, Date deadline) {
        // IF FIRST TIME MILESTONE IS ADDED DATE = CURRENT DATE
        id = UUID.randomUUID().hashCode();
        this.title = title;
        this.start = start;
        this.deadline = deadline;
    }

    /** Constructor used when recreating
     *  a milestone from Database into memory
     *
     * @param id
     * @param title
     * @param progress
     * @param start
     * @param deadline
     */
    public Milestone(Integer id, String title, double progress, Date start, Date deadline) {
        this(title, start, deadline);
        this.progress.setValue(progress);
        this.id = id;
    }

    public Integer getId() { return this.id; }
    public String getTitle(){
        return title;
    }
    public double getProgress() { return progress.get(); }
    public Date getStart() { return start; }
    public Date getDeadline() { return deadline; }
    public HashMap<Task,Task> getTasks() { return new HashMap<>(tasks); }

    public ObservableList<Task> getObservableTaskList() {
        for (Task task : tasks.values()) {
            if (!observableTaskList.contains(task))
                observableTaskList.add(task);
        }
        return observableTaskList;
    }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title){
        this.title = title;
    }

    public void setProgress(double progress) {this.progress.set(progress);}

    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public boolean deleteTask(Task passedTask) {
        for (Task task : tasks.values()) {
            if (!task.equals(passedTask)) {
                if (task.getDependency() != null) {
                    if (task.getDependency().equals(passedTask)) return false;
                }
            }
        }

        //if (!task.getDependencies().isEmpty()) return false;
        tasks.remove(passedTask);
        updateProgress();
        return true;
    }

    public DoubleProperty progressProperty() { return progress; }

    /** Function used to check update milestone's progress
     * @return boolean
     */
    public double updateProgress(){
        double prog = 0;
        for(Task task : tasks.values()){
            prog += task.getProgress();
        }
        prog = prog / tasks.size();
        progress.setValue(prog);
        return prog;
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
    public boolean addTask(Task task){
        if(!tasks.containsKey(task)){
            tasks.put(task,task);
            updateProgress();
            return true;
        }
        return false;
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

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (getClass() != obj.getClass())
            return false;

        if (id == null) return false;


        Milestone milestone = (Milestone) obj;
        return this.id.equals(milestone.id);
    }

    @Override
    public int hashCode() {
        if (id == null)
            return title.hashCode();

        return id.hashCode();
    }
}