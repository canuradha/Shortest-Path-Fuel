package lk.ac.pdn.mt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configuration {
	
	public static String START;
	public static String END;
	public static Integer FUEL_LIMIT;
	public static List<String> CENTERS = new ArrayList<>();
	public static List<String> INCLUDES = new ArrayList<>();
	public static List<String> EXCLUDES = new ArrayList<>();
	
	public Configuration(Properties p) {
		START = p.getProperty("start").trim();
		END = p.getProperty("end").trim();
		FUEL_LIMIT = Integer.valueOf(p.getProperty("fuel_limit").trim());
		
		CENTERS = Arrays.asList(p.getProperty("centers").trim().split("@"));
		INCLUDES = Arrays.asList(p.getProperty("includes").trim().split("@"));
		EXCLUDES = Arrays.asList(p.getProperty("excludes").trim().split("@"));
		//System.out.println(EXCLUDES.toString());
	}

}
