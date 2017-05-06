package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Module {

    // Properties ------------------------------------------------------------------------------------------------------

    private String name;
    private String code;
    private ArrayList<Assessment> assessments = new ArrayList<>();
    private ArrayList<Activity> activities = new ArrayList<>();

    // Constructors ----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    // Might be optional if we set everything by the setters
    public Module(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getName() {
        return name;
    }
    public String getCode() { return code; }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void addAssessment(Assessment assessment){
        assessments.add(assessment);
    }


    // Overrides -------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MODULE\n").append(name).append("\n");
        sb.append(code).append("\n");
        return sb.toString();
    }
}
