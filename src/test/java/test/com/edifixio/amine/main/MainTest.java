package test.com.edifixio.amine.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.edifixio.amine.loadConfig.ConfigBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import test.com.edifixio.amine.controller.ElasticFirtControllerTest;
import test.com.edifixio.amine.controller.ElasticIterateControllerTest;
import test.com.edifixio.amine.load.ConfigBuilderTest;
import test.com.edifixio.amine.load.RequestMappingResolverTest;

public class MainTest {
	
	public static final String PATH_CONFIG="/home/amine/Bureau/confQuery/Voiture/query.json"; 
	public static final String CONFIG="_config";
/******************************************************************************************************************************/	
	public static void callLoadJsonConfigTests(){
		ConfigBuilderTest loadJsonConfigTest =
				new ConfigBuilderTest(PATH_CONFIG);
		
		loadJsonConfigTest.testLoadFacets();
		loadJsonConfigTest.testLoadRequestMapping();
		loadJsonConfigTest.testLoadResponseMapping();
		loadJsonConfigTest.testBuildConf();
		
	}
/************************************************************************************************************************************/	
	public static void callRequestMappingResolverTests(){
	
		try {
			ConfigBuilder loadJsonConfig=new ConfigBuilder(
																new JsonParser().parse(new FileReader(new File(PATH_CONFIG)))
																			 	.getAsJsonObject().get(CONFIG)
																			 	.getAsJsonObject());
			
			RequestMappingResolverTest requestMappingResolverTest =
					new RequestMappingResolverTest(loadJsonConfig.loadRequestMapping(), PATH_CONFIG);
			
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
	
	public static void callElasticFirstControllerTests(){
		ElasticFirtControllerTest elastiControllerTest =
				new ElasticFirtControllerTest(PATH_CONFIG);
		
		
		elastiControllerTest.executeTest();
		elastiControllerTest.processResultTest();
		elastiControllerTest.facetsProcessTest();
	}
	public static void callElasticIteratedControllerTests(){
		ElasticIterateControllerTest elastiControllerTest =
				new ElasticIterateControllerTest(PATH_CONFIG);
		
	
		elastiControllerTest.executeTest(); // 
		elastiControllerTest.processResultTest();
		elastiControllerTest.facetsProcessTest();
	}
	
	
	
	
/****************************************************************************************************************************************************/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("//////////////////////////////////////////////////////////////////////// LoadJsonConfigTests :");
		callLoadJsonConfigTests();
		System.out.println("//////////////////////////////////////////////////////////////////////// RequestMappingResolver :");
		callRequestMappingResolverTests();
		System.out.println("//////////////////////////////////////////////////////////////////////// ElastiControllerTest for first iteration:");
		callElasticFirstControllerTests();
		System.out.println("//////////////////////////////////////////////////////////////////////// ElastiControllerTest :");
		callElasticIteratedControllerTests();
		
		

	}

}
