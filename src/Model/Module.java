package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Didac on 02/05/2017.
 */
public class Module {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer moduleId;
    private String title;
    private String code;
    private Map<Assessment, Assessment> assessments = new HashMap<>();
    private Map<Activity, Activity> activities = new HashMap<>();
    //private int semesterID;

    // Constructors ----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    // Might be optional if we set everything by the setters
    public Module() {}

    public Module(int moduleId, String title, String code) {
        this.moduleId = moduleId;
        this.title = title;
        this.code = code;
    }
    public Module(String title, String code) {
        this.moduleId = null;
        this.title = title;
        this.code = code;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public int getId() { return moduleId; }
    public String getTitle() {
        return title;
    }
    public String getCode() { return code; }
    //public int getSemesterID() { return semesterID;}

    public void setModuleId(int moduleId) { this.moduleId = moduleId; }
    public void setTitle(String title){
        this.title = title;
    }
    public void setCode(String code){
        this.code = code;
    }
    //public void setSemesterID(int semesterID) {this.semesterID = semesterID;}

    public void addAssessment(Assessment assessment){
        if (!assessments.containsKey(assessment))
            assessments.put(assessment, assessment);
    }
//    public void addAssessments(Set<Assessment> assessments){
//        this.assessments = assessments;
//    }

    public Map<Assessment, Assessment> getAssessments() {return assessments;}

    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String str = "";
        str += title + "\n";
        str += code + "\n";
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (getClass() != obj.getClass())
            return false;

        if (moduleId == null) return false;

        Module module = (Module) obj;
        return this.moduleId.equals(module.moduleId);
    }

    @Override
    public int hashCode() {
        if (moduleId == null)
            return code.hashCode();

        return moduleId.hashCode();
    }
}
