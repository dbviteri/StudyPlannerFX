package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Task {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer id;
    private String title;
    private TaskType type;
    private int time;
    private String criterion;
    private int criterionValue;
    private int progress; // related to criterion value
    // TODO :'a task cannot be started before another has been completed'
    private ArrayList<Task> dependencies;
    private ArrayList<Note> notes;

    // Foreign key to Assessment
    //private int assessmentId;

    // Constructor -----------------------------------------------------------------------------------------------------

    public Task(Integer id, String title, TaskType type, int time, String criterion,
                int criterionValue, int progress, Task dependencyTask) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
        this.dependencies = dependencies;
        //this.assessmentId = assessmentId;
    }

    // TODO: Decide whether we need constructors
    public Task(String title, TaskType type, int time, String criterion,
                int criterionValue, int progress, ArrayList<Task> dependencies) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
        this.dependencies = dependencies;
        //this.assessmentId = assessmentId;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

//    public boolean addNote(String title, String text) {
//        Note aNote = new Note(title, text);
//        if (notes.add(aNote)) {
//            return true;
//        }
//        return false;
//    }

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

    public ArrayList<Task> getDependencyTasks() {return dependencies;}

//    public int getAssessmentId(){
//        return assessmentId;
//    }

//    public void setAssessmentId(int assessmentId) {
//        this.assessmentId = assessmentId;
//    }

//    public ArrayList<Task> getDependencies() {
//        return dependencies;
//    }

//    public void setDependencies(ArrayList<Task> dependencies) {
//        this.dependencies = dependencies;
//    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    // TODO: Write toString
    @Override
    public String toString(){


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
            stringBuilder.append("DEPENDENCIES FOR " + title + ": ").append("\n")
            .append(dependencies.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    public enum TaskType {PROGRAMMING, READING}
}
