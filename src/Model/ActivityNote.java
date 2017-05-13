package Model;

import java.util.Date;

/**
 * Created by Didac on 09/05/2017.
 */
public class ActivityNote extends Note {

    private Integer activityId;

    public ActivityNote(Integer activityId, String title, String text, Date date) {
        super(title, text, date);
        this.activityId = activityId;
    }

    public void setActivityId(Integer activityId) { this.activityId = activityId; }

    @Override
    public String toString() {
        return super.toString();
    }
}
