package Tests;

import Model.Module;
import Model.SemesterProfile;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Didac on 12/05/2017.
 */
public class SemesterProfileTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addModule() throws Exception {
        Module module = new Module("test", "test");
        Module module2 = new Module("test1", "test1");

        SemesterProfile semesterProfile = new SemesterProfile();
        semesterProfile.addModule(module);
        semesterProfile.addModule(module2);

        System.out.println(semesterProfile.getModules().size());
    }

}