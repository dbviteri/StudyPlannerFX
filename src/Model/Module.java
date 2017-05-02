package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Module {

    // Properties ------------------------------------------------------------------------------------------------------

    String name;
    String code;
    ArrayList<Assessment> assessments = new ArrayList<>();
    ArrayList<Activity> activities = new ArrayList<>();

    // Constructors ----------------------------------------------------------------------------------------------------

    // TODO: Decide whether we need constructors
    // Might be optional if we set everything by the setters
    public Module(String name, String code, Assessment assessment, Activity activity) {
        this.name = name;
        this.code = code;
        assessments.add(assessment);
        activities.add(activity);
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public String getName() {
        return name;
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
