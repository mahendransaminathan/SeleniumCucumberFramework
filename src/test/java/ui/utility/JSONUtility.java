package ui.utility;

import com.google.gson.Gson;
import ui.constants.Env;
import ui.pojos.Config;
import ui.pojos.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONUtility {
	
	public static String readJSON(Env env)  {
		
		Gson gson = new Gson();
		File jsonFile = new File(System.getProperty("user.dir") + "//config//config.json");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(jsonFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Config config = gson.fromJson(fileReader, Config.class);
		Environment environment = config.getEnvironments().get(env.toString().toUpperCase());
		//System.out.println(environment.getUrl());
		return environment.getUrl();
	}

}
