package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Didac on 02/05/2017.
 */
public class Activity {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer activityId;
    private String title;
    private int quantity;
    private int time;
    private HashMap<Integer, Note> notes;

    // Constructor -----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    public Activity(Integer activityId, String title, int quantity, int time) {
        this.activityId = activityId;
        this.title = title;
        this.quantity = quantity;
        this.time = time;
    }

    public Activity(String title, int quantity, int time) {
        this.activityId = activityId;
        this.title = title;
        this.quantity = quantity;
        this.time = time;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    public boolean addNote(String title, String text) {
        Note note = new Note(title, text, null, activityId);
        notes.put(note.getId(),note);
        return true;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public HashMap<Integer,Note> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<Integer,Note> notes) {
        this.notes.putAll(notes);
    }

    // Overrides -------------------------------------------------------------------------------------------------------
    // TODO: Write toString
    @Override
    public String toString(){

        return null;
    }
}
