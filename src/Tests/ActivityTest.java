package Tests;
import Model.Activity;
import Model.ActivityNote;
import Model.Note;
import org.junit.Test;

import java.util.Date;

/**
 * Created by 100125468 on 26/05/2017.
 */
public class ActivityTest {
    @Test
    public void addNote(){
        Activity activity = new Activity("Programming",5,2,new Date());
        ActivityNote note = new ActivityNote("Note test","about activity",new Date());
        activity.addNote(note);
        System.out.println(activity.getNotes().values().toArray()[0]);
    }
}
