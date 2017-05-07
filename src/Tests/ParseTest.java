package Tests;

import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import Utils.FileParser;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by 100125468 on 06/05/2017.
 */
public class ParseTest {
    @Test
    public void makeDateTest(){
        String sDate = "12/06/2000";
        Date date = FileParser.makeDate(sDate);
        System.out.println(date);
    }
    @Test
    public void parse() throws IOException {
        File file = new File("res/testFile.json");
        //FileParser parser = new FileParser();
        //JSONObject data = FileParser.parseFile(file);
        SemesterProfile semesterProfile = FileParser.parseFile(file);
        System.out.println(semesterProfile);
        for(Module mod : semesterProfile.getModules()){
            for(Assessment as : mod.getAssessments()){
                System.out.println(as.toString());
            }
        }
    }
}
