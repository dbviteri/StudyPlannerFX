package View;

import Controller.MilestoneController;
import Controller.NoteController;
import Controller.TaskController;
import Model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Created by Didac on 17/05/2017.
 */
public class MilestonesView {

    // Task fields --------------------------------------------
    @FXML
    private TextField taskTitleField;
    @FXML
    private ComboBox<Task.TaskType> taskTypeBox;
    @FXML
    private TextField criteriaField;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<Task> dependencyBox;
    @FXML
    private TextArea notesField;
    // --------------------------------------------

    @FXML
    private VBox milestoneFormBox;
    @FXML
    private VBox taskFormBox;
    @FXML
    private TableView<Task> tasksTable;
    @FXML
    private ProgressBar milestoneProgress;
    @FXML
    private GridPane assessmentGrid;
    @FXML
    ComboBox<Module> moduleSelect;
    @FXML
    ComboBox<Assessment> assessmentSelect;
    @FXML
    private TableView<Milestone> milestonesTable;
    @FXML
    private TextField milestoneTitleField;
    @FXML
    private DatePicker deadlinePicker;

    private ObservableList<Milestone> milestones = FXCollections.observableArrayList();
    private ObservableList<Task> tasks = FXCollections.observableArrayList();

    private MilestoneController milestoneController;

    private ListChangeListener<Milestone> milestonesListener;
    private ListChangeListener<Task> tasksListener;
    private TaskController taskController;
    private NoteController noteController;

    /**
     * Initializes the items and adds listeners.
     */
    public void initialize() {
        noteController = new NoteController();
        taskController = new TaskController();
        milestoneController = new MilestoneController();

        taskTypeBox.getItems().setAll(Task.TaskType.values());

        tasksListener = changedValue -> {
            changedValue.next();

            Milestone selectedMilestone = milestonesTable.getSelectionModel().getSelectedItem();
            if (changedValue.wasAdded()) {
                selectedMilestone.addTask(changedValue.getAddedSubList().get(
                        changedValue.getAddedSubList().size() - 1));
            } else {
                selectedMilestone.deleteTask(changedValue.getRemoved().get(0));
            }
        };

        milestonesListener = changedValue -> {
            changedValue.next();
            if (changedValue.wasAdded()) {
                milestoneController.insertMilestone(
                        changedValue.getAddedSubList().get(changedValue.getAddedSubList().size() - 1),
                        assessmentSelect.getValue().getId()
                );
                assessmentSelect.getValue().addMilestone(
                        changedValue.getAddedSubList().get(changedValue.getAddedSubList().size() - 1)
                );
            } else {
                assessmentSelect.getValue().deleteMilestone(changedValue.getRemoved().get(0));
            }
        };

        assessmentSelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                reset();
                return;
            }

            refreshMilestoneTable();
            //refreshTasksTable();
        });

        milestonesTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                reset();
                return;
            }

            milestoneProgress.progressProperty().bind(newValue.progressProperty().divide(100));
            refreshMilestoneTable();
            refreshTasksTable();
        }));

        tasksTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.getTaskNote() == null) {
                notesField.clear();
                return;
            }

            notesField.setText(newValue.getTaskNote().getText());
        }));

        deadlinePicker.valueProperty().addListener((Ob, oldV, newV) -> {
            if (newV == null) return;
            Date date = Date.from(newV.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date newD = new Date();
            if (date.compareTo(newD) < 0)
                new AlertDialog(Alert.AlertType.INFORMATION, "Wrong deadline");
        });

        // Listener for the amount text field to only allow numbers
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    /**
     *
     */
    public void configureListeners() {

    }

    /**
     * Clears the tables
     */
    private void clear(TableView tableView) {
        tableView.getColumns().clear();
        tableView.getItems().clear();
    }

    private void reset() {
        clear(tasksTable);
        clear(milestonesTable);
        //assessmentSelect.getItems().clear();
        milestoneProgress.progressProperty().unbind();
        milestoneProgress.setProgress(0);
        notesField.clear();
        for (Node node : milestoneFormBox.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ComboBox) {
                ((ComboBox) node).getItems().clear();
            }
        }
    }

    /**
     * Clears the table, loads the new elements and binds them to the table
     */
    private void refreshTasksTable() {
        clear(tasksTable);

        Milestone selectedMilestone = milestonesTable.getSelectionModel().getSelectedItem();

        tasks.removeListener(tasksListener);

        tasks = selectedMilestone.getObservableTaskList();

        tasks.addListener(tasksListener);

        addTasksColumns();
        Bindings.bindContent(tasksTable.getItems(), tasks);
        Bindings.bindContent(dependencyBox.getItems(), tasks);
    }

    /**
     * Clears the table, loads the new elements and binds them to the table
     */
    private void refreshMilestoneTable() {
        clear(milestonesTable);

        milestones.removeListener(milestonesListener);
        milestones = assessmentSelect.getValue().getObservableMilestoneList();
        milestones.addListener(milestonesListener);

        addMilestoneColumns();
        Bindings.bindContent(milestonesTable.getItems(), milestones);
    }

    /**
     * Helper function to add columns to the milestones table
     */
    private void addMilestoneColumns() {
        TableColumn<Milestone, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Milestone, Date> startCol = new TableColumn<>("Start date");
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));

        TableColumn<Milestone, Date> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        milestonesTable.getColumns().addAll(titleCol, startCol, deadlineCol);
    }

    /**
     * Helper function to add columns to the tasks table
     */
    private void addTasksColumns() {
        TableColumn<Task, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Task, Task.TaskType> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Task, Integer> weightCol = new TableColumn<>("Time spent");
        weightCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Task, Integer> criterionCol = new TableColumn<>("Criterion");
        criterionCol.setCellValueFactory(new PropertyValueFactory<>("criterion"));

        TableColumn<Task, String> dependencyCol = new TableColumn<>("Depends on");
        dependencyCol.setCellValueFactory(dep -> new SimpleStringProperty(dep.getValue().getDependency().toString()));

        tasksTable.getColumns().addAll(titleCol, typeCol, weightCol, criterionCol);
    }

    /**
     * Adds a milestone to the observable list. The addition of an element in this list will trigger the listener
     * and it will add it to the actual reference in semester profile
     */
    public void addMilestone() {
        if (milestoneTitleField.getText().isEmpty()) return;
        if (deadlinePicker.getValue() == null) return;

        String title = milestoneTitleField.getText();
        LocalDate localDate = deadlinePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date deadLineDate = Date.from(instant);

        milestones.add(new Milestone(title, new Date(), deadLineDate));
    }

    /**
     * Deletes a milestone from the observable list. The removal of an element in this list will trigger the listener
     * and it will remove it from the actual reference in semester profile
     */
    public void deleteMilestone() {
        Milestone selectedMilestone = milestonesTable.getSelectionModel().getSelectedItem();
        if (selectedMilestone == null) return;

        AlertDialog alertDialog = new AlertDialog();
        boolean confirmed = alertDialog.getConfirmation("Are you sure you want to delete " + selectedMilestone.getTitle() + "?");

        if (confirmed) {
            milestoneProgress.progressProperty().unbind();
            milestones.remove(selectedMilestone);
        }
    }

    /**
     * Called by the button event handler
     */
    public void addTask() {
        if (taskTitleField.getText().isEmpty()) return;
        if (taskTypeBox.getValue() == null) return;
        if (criteriaField.getText().isEmpty()) return;
        if (amountField.getText().isEmpty()) return;

        String taskTitle = taskTitleField.getText();
        String criteria = criteriaField.getText();
        Task.TaskType taskType = taskTypeBox.getValue();
        int critValue = Integer.parseInt(amountField.getText());

        Task task = new Task(taskTitle, taskType, criteria, critValue, 0, new Date());

        if (dependencyBox.getValue() != null) {
            task.addDependency(dependencyBox.getValue());
        }

        tasks.add(task);
        taskController.insertTask(task, null, milestonesTable.getSelectionModel().getSelectedItem().getId());
    }

    public void deleteTask() {
        if (tasksTable.getSelectionModel().getSelectedItem() == null) return;

        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();

        AlertDialog alertDialog = new AlertDialog();
        boolean confirmed = alertDialog.getConfirmation("Are you sure you want to delete " + selectedTask.getTitle() + "?");

        // Try to delete task from the original reference
        Predicate<Task> acceptedTask = task -> task.equals(selectedTask) &&
                milestonesTable.getSelectionModel().getSelectedItem().deleteTask(selectedTask);

        if (confirmed) {
            if (!tasks.removeIf(acceptedTask)) {
                new AlertDialog(Alert.AlertType.ERROR, "Can't delete task. It has dependencies.");
            } else {
                taskController.deleteTask(selectedTask);
            }

        }
    }

    public void updateNotes(ActionEvent actionEvent) {
        if (notesField.getText().isEmpty()) return;
        if (tasksTable.getSelectionModel().getSelectedItem() == null) return;

        Task selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        TaskNote taskNote = new TaskNote("test", notesField.getText(), new Date());

        selectedTask.setTaskNote(taskNote);

        noteController.updateNote(taskNote, selectedTask.getId(), null);
        //System.out.println("notes " + tasksTable.getSelectionModel().getSelectedItem().getNotes().size());
    }
}
