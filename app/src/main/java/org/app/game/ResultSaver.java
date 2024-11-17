package org.app.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ResultSaver {

  private String filePath = "src/main/resources/gamestats/file.json";

  public void saveResult(int score) {

    JSONParser parser = new JSONParser();
    JSONObject jsonObject;

    try {
      File file = new File(filePath);
      if (file.exists() && file.isFile()) {
        FileReader reader = new FileReader(file);
        jsonObject = (JSONObject) parser.parse(reader);
        reader.close();
      } else {
        jsonObject = new JSONObject();
      }

      String newScore = "game" + (jsonObject.size() + 1);

      JSONObject newScoreObject = new JSONObject();
      newScoreObject.put("Date", LocalDate.now().toString());
      newScoreObject.put("Score", String.valueOf(score));

      JSONArray newScoreArray = new JSONArray();
      newScoreArray.add(newScoreObject);
      jsonObject.put(newScore, newScoreArray);

      try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(jsonObject.toJSONString());
      }
    } catch (Exception e) {
      System.err.println("Error in result read/write\n" + e.getMessage());
    }
  }
}
