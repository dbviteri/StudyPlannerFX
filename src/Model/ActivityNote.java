package Model;

import java.util.Date;

/**
 * Implementation of an activity note.
 * It extends Note.
 * Created by Didac on 09/05/2017.
 */
public class ActivityNote extends Note {

    private Integer activityId;

    /** Constructor used when creating
     *  a note from Database into memory
     *
     * @param activityId
     * @param title
     * @param text
     * @param date
     */
    public ActivityNote(Integer activityId, String title, String text, Date date) {
        super(title, text, date);
        this.activityId = activityId;
    }
    /** Constructor used when first
     *  creating a note in memory
     *
     * @param title
     * @param text
     * @param date
     */
    public ActivityNote(String title, String text, Date date) {
        super(title, text, date);
        activityId = null;
    }

    public void setActivityId(Integer activityId) { this.activityId = activityId; }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        if (activityId == null) return false;

        ActivityNote note = (ActivityNote) obj;
        return this.activityId.equals(note.activityId);
    }

    @Override
    public int hashCode() {
        if (activityId == null)
            return getTitle().hashCode();

        return activityId.hashCode();
    }
}
