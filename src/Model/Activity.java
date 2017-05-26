package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Model representation of an activity.
 * It holds notes.
 * Created by Didac on 02/05/2017.
 */
public class Activity {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer activityId;
    private String title;
    private int quantity; // Contributes to criterion in task
    private int time;
    private Date date;
    private Map<ActivityNote, ActivityNote> notes = new HashMap<>();

    // Constructor -----------------------------------------------------------------------------------------------------

    public Activity() {}

    /** Constructor used when first
     *  creating an activity into memory
     *
     * @param title
     * @param quantity
     * @param time
     * @param date
     */
    public Activity(String title, int quantity, int time, Date date) {
        // IF FIRST ADDED TO PROFILE DATE = CURRENT DATE
        if (activityId == null) {
            this.activityId = UUID.randomUUID().hashCode();
        }
        this.title = title;
        this.quantity = quantity;
        this.time = time;
    }

    /** Constructor used when recreating an
     *  activity from database into memory
     *
     * @param activityId
     * @param title
     * @param quantity
     * @param time
     * @param date
     */
    // TODO: Decide whether we need constructors
    public Activity(Integer activityId, String title, int quantity, int time, Date date) {
        this(title,quantity,time,date);
        this.activityId = activityId;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

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
        if (obj == null) return false;

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
