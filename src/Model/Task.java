package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Didac on 02/05/2017.
 */
public class Task {

    // Properties ------------------------------------------------------------------------------------------------------

    public enum TaskType {PROGRAMMING, READING}

    private Integer id;
    private String title;
    private TaskType type;
    private int time;
    private String criterion;
    private int criterionValue;
    private int progress; // related to criterion value
    // TODO :'a task cannot be started before another has been completed'

    private Map<Task, Task> dependencies = new HashMap<>();
    private Map<TaskNote, TaskNote> notes = new HashMap<>();
    private Map<Activity, Activity> activities = new HashMap<>();

    // Constructor -----------------------------------------------------------------------------------------------------

    public Task() {}

    public Task(Integer id, String title, TaskType type, int time, String criterion,
                int criterionValue, int progress) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
    }

    // TODO: Decide whether we need constructors
    public Task(String title, TaskType type, int time, String criterion,
                int criterionValue, int progress) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    public void addNote(TaskNote note) {
        if (!notes.containsKey(note))
            notes.put(note, note);
    }

    // Getters and setters ---------------------------------------------------------------------------------------------


    public Integer getId() {return id;}

    public void setId(Integer id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public int getCriterionValue() {
        return criterionValue;
    }

    public void setCriterionValue(int criterionValue) {
        this.criterionValue = criterionValue;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    //public Map<Task, Task> getDependencyTasks() {return dependencies;}
    public Map<Task, Task> getDependencies() { return new HashMap<>(dependencies); }

    public void addActivity(Activity activity) {
        if (!activities.containsKey(activity))
            activities.put(activity, activity);
    }

    //public Map<Activity, Activity> getActivities() { return activities; }
    public Map<Activity, Activity> getActivities() { return new HashMap<>(activities); }

    public void addDependency(Task dependency) {
        if (!dependencies.containsKey(dependency))
            dependencies.put(dependency, dependency);
    }

//    public Map<TaskNote, TaskNote> getNotes() {
//        return notes;
//    }

    public List<TaskNote> getNotes() { return new ArrayList<>(notes.values()); }
    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {


        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(id).append("\n")
                .append(title).append("\n")
                .append(type.toString()).append("\n")
                .append(time).append("\n")
                .append(criterion).append("\n")
                .append(criterionValue).append("\n")
                .append(progress).append("\n");

        if (dependencies.size() == 0) {
            stringBuilder.append("No dependencies!").append("\n");
        } else {
            stringBuilder.append(title + " has " + dependencies.size() + " dependencies: ");
//            for (Task task : dependencies){
//                stringBuilder.append(task.title + ", ");
//            }
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        if (id == null) return false;


        Task task = (Task) obj;
        return this.id.equals(task.id);
    }

    @Override
    public int hashCode() {
        if (id == null)
            return title.hashCode();

        return id.hashCode();
    }
}
