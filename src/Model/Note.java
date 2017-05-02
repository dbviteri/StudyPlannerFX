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

    // Constructor -----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = new Date();
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
