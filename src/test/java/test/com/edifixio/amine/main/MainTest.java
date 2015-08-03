package test.com.edifixio.amine.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.edifixio.amine.load.LoadJsonConfig;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import test.com.edifixio.amine.load.LoadJsonConfigTest;
import test.com.edifixio.amine.mapping.RequestMappingResolverTest;

public class MainTest {
	
	public static final String PATH_CONFIG="/home/amine/Bureau/confQuery/Voiture/query.json"; 
	
/******************************************************************************************************************************/	
	public static void callLoadJsonConfigTests(){
		LoadJsonConfigTest loadJsonConfigTest=new LoadJsonConfigTest(PATH_CONFIG);
		loadJsonConfigTest.testLoadFacets();
		loadJsonConfigTest.testLoadRequestMapping();
		loadJsonConfigTest.testLoadResponseMapping();
		loadJsonConfigTest.testLoadJsonConf();
		
	}
/************************************************************************************************************************************/	
	public static void callRequestMappingResolver(){
	
		try {
			LoadJsonConfig loadJsonConfig=new LoadJsonConfig(new JsonParser()
					.parse(new FileReader(new File(PATH_CONFIG)))
					.getAsJsonObject().get("_config")
					.getAsJsonObject());
			RequestMappingResolverTest requestMappingResolverTest=new RequestMappingResolverTest(loadJsonConfig.loadRequestMapping(), PATH_CONFIG);
			requestMappingResolverTest.testResolveMapping();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
/****************************************************************************************************************************************************/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		callLoadJsonConfigTests();
		callRequestMappingResolver();

	}

}
