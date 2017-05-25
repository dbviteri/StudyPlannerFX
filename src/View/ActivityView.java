package View;

import Controller.ActivityController;
import Controller.NoteController;
import Model.Activity;
import Model.ActivityNote;
import Model.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Didac on 22/05/2017.
 */
class ActivityView {
    protected boolean addActivity(Task task, Activity activity) {
        ActivityController activityController = new ActivityController();
        // update the dependency before checking

        // Update dependencies

//         Task updatedDependency = tasks.stream().filter(
//                 observedTask -> task.getDependency() != null &&
//                                 observedTask.equals(task.getDependency()))
//                 .collect(Collectors.toList()).get(0);

//         if (updatedDependency != null)
//         if (task.getDependency() != null){
//             task.addDependency(updatedDependency);
//         }

        if (task.getDependency() != null) {
            if (task.getDependency().isComplete()) {
                task.addActivity(activity);
                activityController.insertActivity(activity, task.getId());
                return true;
            } else {
                new AlertDialog(Alert.AlertType.ERROR, "Can't start this task until dependencies are completed.");
                return false;
            }
        } else {
            task.addActivity(activity);
            activityController.insertActivity(activity, task.getId());
            return true;
        }


    }

    protected void addActivitiesColumns(TableView<Activity> tableView) {
        TableColumn<Activity, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Activity, Integer> quantityCol = new TableColumn<>("Amount");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Activity, Integer> timeCol = new TableColumn<>("Time spent");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.getColumns().addAll(titleCol, quantityCol, timeCol);
    }

    protected void updateNotes(Activity activity, ActivityNote activityNote) {
        NoteController noteController = new NoteController();
        activity.addNote(activityNote);

        noteController.updateNote(activityNote, activity.getActivityId(), null);
    }
}
