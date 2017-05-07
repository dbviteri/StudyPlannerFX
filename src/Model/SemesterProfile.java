package Model;

import Controller.DatabaseHandler;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Didac on 02/05/2017.
 */
public class SemesterProfile {

    // Properties ------------------------------------------------------------------------------------------------------

    private Date startDate;
    private Date endDate;
    private ArrayList<Module> modules = new ArrayList<>();
    private int semester_id;
    // Maybe modules should be a hashmap to be able to retrieve by module name.

    public SemesterProfile(){ }
    public SemesterProfile(Date start, Date end) {
        this.startDate = start;
        this.endDate = end;
    }
    public SemesterProfile(Date start, Date end,int semester_id) {
        this.startDate = start;
        this.endDate = end;
        this.semester_id = semester_id;
    }
    // Getters and setters----------------------------------------------------------------------------------------------
    public Date getStartDate(){ return startDate; }
    public Date getEndDate(){ return endDate; }
    public int getSemester_id() { return semester_id;
    }
    public ArrayList<Module> getModules(){ return modules; }
    public Module getModule() {
        return modules.get(0);
    }

    public void addModule(Module module) { modules.add(module); }
    public void addModules(Module[] modules) { }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public void setSemester_id(int semester_id) { this.semester_id = semester_id;}



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
