package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Model representation for a semester profile
 * It holds modules.
 * Created by Didac on 02/05/2017 .
 */
public class SemesterProfile {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer semesterId;
    private Date startDate;
    private Date endDate;

    private Map<Module, Module> modules = new HashMap<>();

    public SemesterProfile() {}

    public SemesterProfile(Integer semesterId, Date start, Date end) {
        this.semesterId = semesterId;
        this.startDate = start;
        this.endDate = end;
    }

    public SemesterProfile(Date start, Date end) {
        this.startDate = start;
        this.endDate = end;
    }
    // Getters and setters----------------------------------------------------------------------------------------------
    public Date getStartDate(){ return startDate; }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate(){ return endDate; }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<Module, Module> getModules() { return modules; }

    public Integer getSemesterId() { return semesterId; }

    public void setSemesterId(Integer semesterId) { this.semesterId = semesterId; }

    public void addModule(Module module) {
        if (!modules.containsKey(module))
            modules.put(module, module);
    }

    // Overrides--------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SemesterProfile: ").append(semesterId).append("\n ");
        sb.append(startDate).append(" - ").append(endDate);
        for (HashMap.Entry module : modules.entrySet()) {
            sb.append(module.getValue());
        }
        return "SemesterProfile: " + startDate + ", " + endDate + " \n" + sb;
    }
}
