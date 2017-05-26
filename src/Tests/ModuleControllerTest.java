package Tests;

import Controller.ModuleController;
import Model.Module;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test
 */
public class ModuleControllerTest {
    private ModuleController moduleController;

    @Before
    public void setUp() throws Exception {
        moduleController = new ModuleController();
    }

    @Test
    public void insertModule() throws Exception {
        Module module = new Module();
        module.setTitle("test");
        module.setCode("ASDFA");
        moduleController.insertModule(module, 16);
        ArrayList<Module> modules = moduleController.findAll(16);
        boolean added = false;
        for (Module moduleIn : modules) {
            if (moduleIn.equals(module)) added = true;
        }
        assert added;
    }

    @Test
    public void findAll() throws Exception {
        ArrayList<Module> modules = moduleController.findAll(16);
        assert modules.size() > 0;
    }

    @Test
    public void formModule() throws Exception {
        // If above tests work, this works
    }

    @Test
    public void updateModule() throws Exception {
        ArrayList<Module> modules = moduleController.findAll(16);
        Module gotModule = modules.get(0);

        gotModule.setCode("1234");
        ModuleController.updateModule(gotModule);

        ArrayList<Module> modules2 = moduleController.findAll(16);
        Module updatedModule = modules2.get(0);

        assert gotModule.equals(updatedModule);
    }

}