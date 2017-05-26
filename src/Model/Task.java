package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Model representation for a task
 * It holds dependencies, task notes and activities
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
    private int criterionSoFar;
    private double progress; // related to criterion value
    private Date date;
    // TODO :'a task cannot be started before another has been completed'
    private Task dependency;
    //private Map<TaskNote, TaskNote> notes = new HashMap<>();
    private TaskNote taskNote;
    private Map<Activity, Activity> activities = new HashMap<>();
    private ObservableList<Activity> activityObservableList = FXCollections.observableArrayList();

    // Constructor -----------------------------------------------------------------------------------------------------

    public Task() {}

    /** Constructor for use when
     *  first creating a task in memory
     *
     * @param title
     * @param type
     * @param criterion
     * @param criterionValue
     * @param progress
     * @param date
     */
    public Task(String title, TaskType type, String criterion,
                int criterionValue, int progress, Date date) {
        // IF FIRST ADDED TO PROFILE DATE = CURRENT DATE
        this.id = UUID.randomUUID().hashCode();
        this.title = title;
        this.type = type;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
        this.date = date;
        this.time = 0;
    }

    /** Constructor for use when
     *  recreating a task from database
     *
     * @param id
     * @param title
     * @param type
     * @param criterion
     * @param criterionValue
     * @param progress
     * @param date
     */
    public Task(Integer id, String title, TaskType type, String criterion,
                int criterionValue, int progress, Date date) {
        this(title,type,criterion,criterionValue,progress,date);
        this.id = id;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

//    public void addNote(TaskNote note) {
//        if (!notes.containsKey(note))
//            notes.put(note, note);
//    }

    public void setTaskNote(TaskNote taskNote) {
        this.taskNote = taskNote;
    }

    public TaskNote getTaskNote() {
        return taskNote;
    }
    // Getters and setters ---------------------------------------------------------------------------------------------

    public ObservableList<Activity> getObservableActivityList() {
        for (Activity activity : activities.values()) {
            if (!activityObservableList.contains(activity)) {
                activityObservableList.add(activity);
            }
        }
        return activityObservableList;
    }

    public Integer getId() {return id;}

    public Date getDate() { return date; }

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

    public int getCriterionSoFar() {
        return criterionSoFar;
    }

    public void setCriterionValue(int criterionValue) {
        this.criterionValue = criterionValue;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    /** Function used to delete an activity from this task
     *  updates criterion,progress and time accordingly
     *
     * @param activity
     * @return true if deleted / false otherwise
     */
    public boolean deleteActivity(Activity activity) {
        if (!activities.containsKey(activity)) return false;
        this.criterionSoFar -= activity.getQuantity();
        this.time -= activity.getTime();
        this.progress = (criterionSoFar / criterionValue) * 100;
        activities.remove(activity);
        return true;
    }

    public Task getDependency() { return dependency; }

    /** Function used to add an activity
     *  that works towards completing the task.
     *  It also updates the progress and time take by this task
     *
     * @param activity
     * @return true if added / false otherwise
     */
    public boolean addActivity(Activity activity) {
        if (!activities.containsKey(activity) && criterionSoFar < criterionValue) {
            activities.put(activity, activity);
            criterionSoFar += activity.getQuantity();
            progress = ((double) criterionSoFar / criterionValue) * 100;
            System.out.println(progress);
            this.time += activity.getTime();
            return true;
        }
        return false;
    }

    /** Function used to return a HashMap containing
     *  all the activities related to this task
     *
     * @return new HashMap
     */
    public Map<Activity, Activity> getActivities() { return new HashMap<>(activities); }

    public void addDependency(Task dependency) {
        this.dependency = dependency;
    }

    /** Function checks if task is completed based on all the related activities
     *  if complete , it returns true
     *  if not , it returns false and
     *  updates progress to level of completion
     *
     * @return
     */
    public boolean isComplete(){
        updateProgress();
        return progress == 100;

    }

    /** Function used to update the progress
     *  based on related activities
     *
     */
    public void updateProgress() {
        double count = 0;
        for (Activity activity : activities.values()) {
            count += activity.getQuantity();
        }

        if (progress == criterionValue) progress = 100;
        else {
            if (count != 0) progress = (criterionValue / count) * 100;
            else progress = 0;
        }
    }

//    public Map<TaskNote, TaskNote> getNotes() {
//        return notes;
//    }

    //public List<TaskNote> getNotes() { return new ArrayList<>(notes.values()); }
    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(title).append("\n")
                .append(type.toString()).append("\n")
                .append(time).append("\n")
                .append(criterion).append("\n")
                .append(criterionValue).append("\n")
                .append(progress).append("\n");

//        if (dependency.title != null) {
//            stringBuilder.append(title).append(" depends on ").append(dependency.title);
//        }

        return stringBuilder.toString();
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

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
