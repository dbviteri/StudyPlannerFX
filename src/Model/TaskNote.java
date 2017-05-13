package Model;

import java.util.Date;

/**
 * Created by Didac on 09/05/2017.
 */
public class TaskNote extends Note {

    private Integer taskId;

    public TaskNote(Integer taskId, String title, String text, Date date) {
        super(title, text, date);
        this.taskId = taskId;
    }

    public TaskNote(String title, String text, Date date) {
        super(title, text, date);
        taskId = null;
    }

    public void setTaskId(Integer taskId) { this.taskId = taskId; }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        if (taskId == null) return false;

        TaskNote note = (TaskNote) obj;
        return this.taskId.equals(note.taskId);
    }

    @Override
    public int hashCode() {
        if (taskId == null)
            return getTitle().hashCode();

        return taskId.hashCode();
    }
}
