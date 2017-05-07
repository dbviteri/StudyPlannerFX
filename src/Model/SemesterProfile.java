package Model;

import Controller.DatabaseHandler;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Didac on 02/05/2017.
 */
public class SemesterProfile {

    // Properties ------------------------------------------------------------------------------------------------------

    private Integer semesterId;
    private Date startDate;
    private Date endDate;
    private Integer userId;

    private ArrayList<Module> modules = new ArrayList<>();
    // Maybe modules should be a hashmap to be able to retrieve by module name.

    public SemesterProfile(Integer semesterId, Date start, Date end, Integer userId) {
        this.semesterId = semesterId;
        this.startDate = start;
        this.endDate = end;
        this.userId = userId;
    }

    public SemesterProfile(Date start, Date end) {
        this.startDate = start;
        this.endDate = end;
    }
    // Getters and setters----------------------------------------------------------------------------------------------
    public Date getStartDate(){ return startDate; }
    public Date getEndDate(){ return endDate; }
    public ArrayList<Module> getModules(){ return modules; }
    public Module getModule() {
        return modules.get(0);
    }

    public Integer getSemesterId() { return semesterId; }
    public void setSemesterId(Integer semesterId) { this.semesterId = semesterId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public void addModule(Module module) { modules.add(module); }
    public void addModules(Module[] modules) { }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }



    // Overrides--------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        //StringBuilder sb = new StringBuilder();
        //return modules.get(0).toString();
        String astring = "";
        for (Module amodule : modules){
            astring += amodule;
        }
        return "SemesterProfile: " + startDate + ", " + endDate + " \n" + astring;
    }
}
