package Model;

import java.util.ArrayList;

/**
 * Created by Didac on 02/05/2017.
 */
public class Semester {

    // Properties ------------------------------------------------------------------------------------------------------

    String startDate;
    String endDate;
    ArrayList<Module> modules = new ArrayList<>();
    // Maybe modules should be a hashmap to be able to retrieve by module name.

    // Getters and setters----------------------------------------------------------------------------------------------

    public void addModule(Module module) {
        modules.add(module);
    }

    public void addModules(Module[] modules) {

    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public Module getModule() {
        return modules.get(0);
    }

    // Overrides--------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        //StringBuilder sb = new StringBuilder();
        return modules.get(0).toString();
    }
}
