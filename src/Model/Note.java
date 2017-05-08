package Model;

import java.util.Date;

/**
 * Created by Didac on 02/05/2017.
 */
public class Note {

    // Properties ------------------------------------------------------------------------------------------------------

    private String title;
    private String text;
    private Date date;
    private Integer taskId;
    private Integer activityId;

    // Constructor -----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    public Note(String title, String text, Date date, Integer taskId, Integer activityId) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.taskId = taskId;
        this.activityId = activityId;
    }

    public Note(String title, String text, Integer taskId, Integer activityId) {
        this.title = title;
        this.text = text;
        this.date = new Date();
        this.taskId = taskId;
        this.activityId = activityId;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date date() {
        return date;
    }

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String str = "";
        str += "\t " + title + "\n"
                + text + "\n" + date;
        return str;

    }

}
