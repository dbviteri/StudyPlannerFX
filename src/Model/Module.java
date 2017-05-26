package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Model representation for a module
 * Created by Didac on 02/05/2017.
 */
public class Module {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer moduleId;
    private String title;
    private String code;
    private HashMap<Assessment, Assessment> assessments = new HashMap<>();

    // Constructors ----------------------------------------------------------------------------------------------------

    public Module() {}

    /** Constructor used when first
     *  creating a module into memory
     *
     * @param title
     * @param code
     */
    public Module(String title, String code) {
        this.moduleId = null;
        this.title = title;
        this.code = code;
    }

    /** Constructor used when recreating a module
     *  from Database into memory
     *
     * @param moduleId
     * @param title
     * @param code
     */
    public Module(int moduleId, String title, String code) {
        this(title,code);
        this.moduleId = moduleId;
    }

    // Getters and setters ---------------------------------------------------------------------------------------------

    public int getId() { return moduleId; }
    public String getTitle() {
        return title;
    }
    public String getCode() { return code; }

    public void setModuleId(int moduleId) { this.moduleId = moduleId; }
    public void setTitle(String title){
        this.title = title;
    }
    public void setCode(String code){
        this.code = code;
    }

    /** Function adds a new assessment to
     *  this module if not already present
     *
     * @param assessment
     */
    public void addAssessment(Assessment assessment){
        if (!assessments.containsKey(assessment))
            assessments.put(assessment, assessment);
    }

    public HashMap<Assessment, Assessment> getAssessments() {return assessments;}
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
