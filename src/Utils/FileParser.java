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

    public SemesterProfile parse(JSONObject json){
        // Get data for a Semester profile
        Date sem_start = (Date)json.get("start_date");
        Date sem_end = (Date)json.get("end_date");
        // Create the semester profile
        SemesterProfile semester = new SemesterProfile(sem_start,sem_end);
        JSONArray modules = (JSONArray)json.get("modules");
        for(int i=0; i<modules.size(); i++) {
            JSONObject jsonModule = (JSONObject)modules.get(i);
            String name = (String)jsonModule.get("name");
            String code = (String)jsonModule.get("code");
            JSONArray assingments = (JSONArray)json.get("assessments");
            Module module = new Module(name,code);
            for(int j=0; j<assingments.size(); j++) {
                JSONObject jsonAssessment = (JSONObject)assingments.get(i);
                String title = (String)jsonAssessment.get("title");
                String t = (String)jsonAssessment.get("type");
                Assessment.Event type = Assessment.Event.valueOf(t);
                int weight = (int)jsonAssessment.get("weight");
                Date deadline = (Date)jsonAssessment.get("deadline");
                Assessment assessment = new Assessment(title,type,weight,deadline);
                module.addAssessment(assessment);
            }
            semester.addModule(module);
        }
        return semester;
    }
    /** Helper Function takes a string representation of a date
     *  and turns it into Date object (DD,MM,YYYY) format
     *
     * @param date
     * @return date
     */
    public static Date makeDate(String date) {
        DateFormat format = new SimpleDateFormat("DD,MM,YYYY", Locale.UK);
        Date aDate = new Date();
        try {
            aDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return aDate;
    }
    public static JSONObject parseFile(File file) throws IOException {
        if (!file.getName().contains(".json")) {
            throw new IOException("Invalid File");
        }
        JSONParser parser = new JSONParser();
        try {
            FileReader rd = new FileReader(file);
            Object obj = parser.parse(rd);
            JSONObject json = (JSONObject)obj;
            return json;
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
