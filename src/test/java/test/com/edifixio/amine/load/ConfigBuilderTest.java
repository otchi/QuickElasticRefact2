package test.com.edifixio.amine.load;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.edifixio.amine.loadConfig.ConfigBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ConfigBuilderTest {
	
	private ConfigBuilder loadJsonConfig;
	private static final String CONFIG="_config";
/******************************************************************************************************************/	
	public ConfigBuilderTest(String path)  {
		// TODO Auto-generated constructor stub
		try {
			this.loadJsonConfig=new ConfigBuilder(new JsonParser()
														.parse(new FileReader(new File(path)))
														.getAsJsonObject().get(CONFIG)
														.getAsJsonObject());
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/******************************************************************************************************************/	
	public void testLoadFacets(){
		System.out.println("-------> test LoadFacets :");
		System.out.println(loadJsonConfig.loadFacetsMapping()+"\n\n");
	}
/******************************************************************************************************************/	
	
	public void testLoadRequestMapping(){
		System.out.println("-------> test loadRequestMapping :");
		try {
			System.out.println(loadJsonConfig.loadRequestMapping()+"\n\n");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/******************************************************************************************************************/	
	public void testLoadResponseMapping(){
		System.out.println("-------> test loadResponseMapping :");
		try {
			System.out.println(loadJsonConfig.loadResponseMapping()+"\n\n");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("\n\n");
			
		}
	}
/******************************************************************************************************************/
	
	public void testBuildConf(){
		System.out.println("-------> test loadJsonConf :");
		try {
			System.out.println(loadJsonConfig.buildConf()+"\n\n");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/******************************************************************************************************************/	

}
