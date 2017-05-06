package Model;

import com.oracle.javafx.jmx.json.JSONDocument;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Task {
    // Properties ------------------------------------------------------------------------------------------------------

    private int id;
    private String title;
    private TaskType type;
    private int time;
    private String criterion;
    private int criterionValue;
    private int progress;
    // TODO: Instead of an array list, have another instance of a class?
    // 'a task cannot be started before another has been completed'
    // private Task dependencyTask;
    private Task dependencyTask;
    private ArrayList<Task> dependencies;
    private ArrayList<Note> notes;

    // Constructor -----------------------------------------------------------------------------------------------------

    public Task(int id, String title, TaskType type, int time, String criterion,
                int criterionValue, int progress, Task dependencyTask) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = progress;
        this.dependencyTask = dependencyTask;
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

    public boolean addNote(String title, String text) {
        Note aNote = new Note(title, text);
        if (notes.add(aNote)) {
            return true;
        }
        return false;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public int getId() {return id;}

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

    public Task getDependencyTask(){return dependencyTask;}

    public ArrayList<Task> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<Task> dependencies) {
        this.dependencies = dependencies;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public enum TaskType {PROGRAMMING, READING}

    // Overrides -------------------------------------------------------------------------------------------------------

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

        if (dependencyTask == null){
            stringBuilder.append("No dependencies!").append("\n");
        } else {
            stringBuilder.append("DEPENDENCIES FOR " + title + ": ").append("\n")
            .append(dependencyTask.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
