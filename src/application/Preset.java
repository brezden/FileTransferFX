package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Preset {
	
	Directories DirectoryClass = new Directories();
	
	//Creates a JSON file for the preset
	public void PresetInitializer(String fileName) throws IOException {
		FileWriter file = new FileWriter("preset/" + fileName + ".json");
	}
	
	// Inserts all the directories into the preset value
	public void PresetInsertion(String fileName, ArrayList<String> filePaths,  ArrayList<String> folderPaths) throws IOException {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < filePaths.size(); i++) {
			array.add(filePaths.get(i));
		}
		
		for (int i = 0; i < folderPaths.size(); i++) {
			array.add(folderPaths.get(i));
		}

		object.put(fileName, array);
		
		FileWriter reader = new FileWriter("preset/" + fileName + ".json");
		reader.write(object.toJSONString());
		reader.flush();
		reader.close();
	}
	
	// Returns the preset paths in the JSON file
	public List<String> PresetGetter(String fileName) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		FileReader file = new FileReader("preset/" + fileName + ".json");
		Object object = parser.parse(file);
		JSONObject tempObject  = (JSONObject)object;
		Object fileObject  = tempObject.get(fileName);
		
		ObjectMapper mapper = new ObjectMapper();
		List<String> presetList = mapper.readValue(fileObject.toString(), new TypeReference<List<String>>(){});
		return presetList;
	}
}