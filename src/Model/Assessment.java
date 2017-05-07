package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Didac on 02/05/2017.
 */
public class Assessment {

    // Properties ------------------------------------------------------------------------------------------------------

    public enum Event {EXAM, ASSIGNMENT}
    private int id;
    private Event type;
    private String title;
    private int weight;
    private Date deadline;
    private int completion;
    private ArrayList<Task> tasks;
    //TODO: Be able to define study milestones which must be attached to coursework or exams
    //TODO: Be able to define study tasks contributing towards specific coursework or exams

    // EMPTY CONSTRUCTOR FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!
    public Assessment(int id, String title, Event type, int weight, Date deadline){
        this.id = id;
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.deadline = deadline;
        this.completion = 0;
        tasks = new ArrayList<>();
    }
    public Assessment(String title, Event type, int weight, Date deadline) {
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.deadline = deadline;
    }
    // Getters and setters ---------------------------------------------------------------------------------------------

    public int getId() { return id; }

    public void setId(int id) { this.id = id;}

    public Event getType() {
        return type;
    }

    public void setType(Event type) {
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

    public ArrayList<Task> getTasks() { return tasks; }

    //public void addAllTasks(ArrayList<Task> tasks) { this.tasks = tasks; }

    public void addTask(Task task) { tasks.add(task); }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDeadLine() {
        return deadline;
    }

    public int completion() {
        return completion;
    }

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
