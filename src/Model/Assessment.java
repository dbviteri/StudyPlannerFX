package Model;

import java.util.Date;

/**
 * Created by Didac on 02/05/2017.
 */
public class Assessment {

    // Properties ------------------------------------------------------------------------------------------------------

    public enum Event {EXAM, ASSIGNMENT}
    private Event type;
    private String title;
    private int weight;
    private Date deadline;
    private int completion;

    public Assessment(String title, Event type, int weight, Date deadline ){
        this.title = title;
        this.type = type;
        this.weight = weight;
        this.deadline = deadline;
        this.completion = 0;
    }
    // Getters and setters ---------------------------------------------------------------------------------------------

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
        return title;
    }

}
