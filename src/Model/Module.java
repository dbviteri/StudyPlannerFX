package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Module {

    // Properties ------------------------------------------------------------------------------------------------------

    private String title;
    private String code;
    private ArrayList<Assessment> assessments = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();
    private int semesterID;

    // Constructors ----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    // Might be optional if we set everything by the setters
    public Module(String title, String code, int semesterID) {
        this.title = title;
        this.code = code;
        this.semesterID = semesterID;
    }
    public Module(String title, String code) {
        this.title = title;
        this.code = code;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getTitle() {
        return title;
    }
    public String getCode() { return code; }
    public int getSemesterID() { return semesterID;}

    public void setTitle(String title){
        this.title = title;
    }
    public void setCode(String code){
        this.code = code;
    }
    public void setSemesterID(int semesterID) {this.semesterID = semesterID;}

    public void addAssessment(Assessment assessment){
        assessments.add(assessment);
    }

    public ArrayList<Assessment> getAssessments(){return assessments;}

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String str = "";
        str += "MODULE\n" + title + "\n";
        str += code + "\n";
        return str;
    }
}
