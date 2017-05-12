package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Didac on 02/05/2017.
 */
public class Assessment {

    // Properties ------------------------------------------------------------------------------------------------------

    public enum Type {EXAM, ASSIGNMENT}
    private int id;
    private String title;
    private Type type;
    private int weight;
    private Date deadline;
    private int completion;
    private HashMap<Integer,Task> tasks;


    // Foreign key code module
    private String moduleCode;

    //TODO: Be able to define study milestones which must be attached to coursework or exams
    //TODO: Be able to define study tasks contributing towards specific coursework or exams

    // EMPTY CONSTRUCTOR FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!
    public Assessment(int id, String title, Type type, int weight, Date deadline, int completion, String moduleCode){
        this.id = id;
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.deadline = deadline;
        this.completion = completion;
        this.moduleCode = moduleCode;
        tasks = new HashMap<>();
    }
    public Assessment(String title, Type type, int weight, Date deadline, int completion, String moduleCode) {
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.deadline = deadline;
        this.completion = completion;
        this.moduleCode = moduleCode;
        tasks = new HashMap<>();
    }
    // Getters and setters ---------------------------------------------------------------------------------------------

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public HashMap<Integer, Task> getTasks() { return tasks; }

    //public void addAllTasks(ArrayList<Task> tasks) { this.tasks = tasks; }

    public void addTask(Task task) { tasks.put(task.getId(),task); }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDeadLine() {
        return deadline;
    }

    public int getCompletion() {
        return completion;
    }

    public String getModuleCode() { return moduleCode; }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    // TODO: Rewrite the tostring
    @Override
    public String toString() {
        String str="";
        str += title + "\n" + type + "\n" + "Weight :" + weight +"%";
        return str;
    }

}
