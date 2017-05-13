package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Didac on 02/05/2017.
 */
public class SemesterProfile {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer semesterId;
    private Date startDate;
    private Date endDate;
    //private Integer userId;

    private Map<Module, Module> modules = new HashMap<>();
    //private ArrayList<Module> modules = new ArrayList<>();
    //private int semester_id;
    // Maybe modules should be a hashmap to be able to retrieve by module name.

    public SemesterProfile() {}

    public SemesterProfile(Integer semesterId, Date start, Date end) {
        this.semesterId = semesterId;
        this.startDate = start;
        this.endDate = end;
        //this.userId = userId;
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

    //public ArrayList<Module> getModules(){ return modules; }

//    public Integer getUserId() { return userId; }
//    public void setUserId(Integer userId) { this.userId = userId; }

//    public Module getModule() {
//        return modules.get(0);
//    }

    public Integer getSemesterId() { return semesterId; }

    public void setSemesterId(Integer semesterId) { this.semesterId = semesterId; }

    public void addModule(Module module) {
        if (!modules.containsKey(module))
            modules.put(module, module);
//        if (!modules.contains(module))
//            modules.add(module);
    }

    //public void addModules(Set<Module> modules) { this.modules = modules; }



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
