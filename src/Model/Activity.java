package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Activity {

    // Properties ------------------------------------------------------------------------------------------------------

    private String title;
    //private ArrayList<Task> contributions;
    private int quantity;
    private int time;
    private ArrayList<Note> notes;

    // Constructor -----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    public Activity(String title, ArrayList<Task> contributions, int quantiy, int time) {
        this.title = title;
        this.contributions = contributions;
        this.quantity = quantity;
        this.time = time;
    }

    // Methods ---------------------------------------------------------------------------------------------------------

    public boolean addNote(String title, String text) {
        Note aNote = new Note(title, text);
        return notes.add(aNote);
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getContributions() {
        return contributions;
    }

    public void setContributions(ArrayList<Task> contributions) {
        this.contributions = contributions;
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

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    // Overrides -------------------------------------------------------------------------------------------------------
    // TODO: Write toString
    @Override
    public String toString(){

        return null;
    }
}
