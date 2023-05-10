package Chess;//package Chess;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class CreateJsonFile {
    public static void main(String[] args) {
        JSONObject UsersData = new JSONObject();
        JSONArray names_of_user = new JSONArray();
       UsersData.put("name","mohamed");




        try (FileWriter fileWriter = new FileWriter("playerData.json")) {
            fileWriter.write(UsersData.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
