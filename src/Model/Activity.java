package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Model representation of an activity.
 * It holds notes.
 * Created by Didac on 02/05/2017.
 */
public class Activity {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer activityId;
    private String title;
    private int quantity;
    private int time;
    private Date date;
    private Map<ActivityNote, ActivityNote> notes = new HashMap<>();

    // Constructor -----------------------------------------------------------------------------------------------------

    public Activity() {}

    public Activity(String title, int quantity, int time) {
        this.title = title;
        this.quantity = quantity;
        this.time = time;
        this.date = new Date(); // current date
    }
    // TODO: Decide whether we need constructors
    public Activity(Integer activityId, String title, int quantity, int time) {
        this(title,quantity,time);
        this.activityId = activityId;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

//    public boolean addNote(String title, String text) {
//        ActivityNote note = new ActivityNote(activityId, title, text, new Date());
//        return notes.add(note);
//    }

    public void addNote(ActivityNote note) {
        notes.put(note, note);
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public Date getDate() { return date; }

    public Integer getActivityId() { return activityId; }

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTime() {
        return time;
    }

    public Map<ActivityNote, ActivityNote> getNotes() {
        return notes;
    }


    public void setDate(Date date) { this.date = date; }

    public void setActivityId(Integer activityId) { this.activityId = activityId; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTime(int time) {
        this.time = time;
    }


//    public void setNotes(ArrayList<ActivityNote> notes) {
//        this.notes = notes;
//    }

    // Overrides -------------------------------------------------------------------------------------------------------
    // TODO: Write toString
    @Override
    public String toString(){
        return title + "\n" + quantity + "\n" + time + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        if (activityId == null) return false;


        Activity activity = (Activity) obj;
        return this.activityId.equals(activity.activityId);
    }

    @Override
    public int hashCode() {
        if (activityId == null)
            return title.hashCode();

        return activityId.hashCode();
    }

}
