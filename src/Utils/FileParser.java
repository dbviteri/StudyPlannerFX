package Utils;

import Controller.ModuleController;
import Model.Assessment;
import Model.Module;
import Model.SemesterProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 100125468 on 06/05/2017.
 */
public class FileParser {

    private static SemesterProfile parseJson(JSONObject json){
        // Get data for a Semester profile
        Date sem_start = makeDate((String)json.get("start_date"));
        Date sem_end = makeDate((String)json.get("end_date"));
        // Create the semester profile
        SemesterProfile semester = new SemesterProfile(sem_start,sem_end);
        JSONArray modules = (JSONArray)json.get("modules");
        for(int i=0; i<modules.size(); i++) {
            // Get a module
            JSONObject jsonModule = (JSONObject)modules.get(i);
            String name = (String)jsonModule.get("title");
            String code = (String)jsonModule.get("code");
            Module module = new Module(name,code);
            // Get all Assessments from module
            JSONArray assessments = (JSONArray)jsonModule.get("assessments");
            if(assessments != null) {
                for (int j = 0; j < assessments.size(); j++) {
                    // Get Assessment
                    JSONObject jsonAssessment = (JSONObject)assessments.get(j);
                    String title = (String) jsonAssessment.get("title");
                    String t = (String) jsonAssessment.get("type");
                    Assessment.Type type = Assessment.Type.valueOf(t.toUpperCase());
                    int weight = (int)(long) jsonAssessment.get("weight");
                    Date deadline = makeDate((String) jsonAssessment.get("deadline"));
                    // Create and add assessment to module
                    Assessment assessment = new Assessment(title, type, weight, deadline, 0, module.getCode());
                    module.addAssessment(assessment);
                }
            }
            semester.addModule(module);
        }
        return semester;
    }
    /** Helper Function takes a string representation of a date
     *  and turns it into Date object (DD,MM,YYYY) format
     *
     * @param sDate
     * @return date
     */
    public static Date makeDate(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = format.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static boolean validate(File file){
        return file.getName().contains(".json");
    }

    /** Function takes a file, reads it and returns
     *  a JSON object containing the data
     *
     * @param file
     * @return SemesterProfile
     * @throws IOException
     */
    public static SemesterProfile parseFile(File file) {
        validate(file);
        JSONParser parser = new JSONParser();
        try {
            FileReader rd = new FileReader(file);
            Object obj = parser.parse(rd);
            JSONObject json = (JSONObject)obj;
            return parseJson(json);
        } catch (org.json.simple.parser.ParseException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** Function takes a file and returns a file reader
     *
     * @param file
     * @return BufferedReader
     * @throws IOException
     */
    private static BufferedReader openFile(File file){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
