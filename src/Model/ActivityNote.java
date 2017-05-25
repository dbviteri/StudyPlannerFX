package Model;

import java.util.Date;

/**
 * Implementation of an activity note.
 * It extends Note.
 * Created by Didac on 09/05/2017.
 */
public class ActivityNote extends Note {

    private Integer activityId;

    public ActivityNote(Integer activityId, String title, String text, Date date) {
        super(title, text, date);
        this.activityId = activityId;
    }

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
