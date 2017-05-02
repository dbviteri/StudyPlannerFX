package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Task {

    // Properties ------------------------------------------------------------------------------------------------------

    private String title;
    private TaskType type;
    private int time;
    private String criterion;
    private int criterionValue;
    private int progress;
    private ArrayList<Task> dependencies;
    private ArrayList<Note> notes;

    // Constructor -----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    public Task(String title, TaskType type, int time, String criterion,
                int criterionValue, ArrayList<Task> dependencies) {
        this.title = title;
        this.type = type;
        this.time = time;
        this.criterion = criterion;
        this.criterionValue = criterionValue;
        this.progress = 0;
        this.dependencies = dependencies;
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
}
