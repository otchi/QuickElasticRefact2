package test.com.edifixio.amine.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.controller.ElasticController;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ElasticFirtControllerTest {

	private ElasticController elasticController;
	public ElasticFirtControllerTest(String config){
		try {
			elasticController=
					new ElasticController(
							new JsonParser().parse(
									new FileReader(
											new File(config)))
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
	
	public void processQueryTest(){
		System.out.println("\n\n--------------> processQueryFirstIteation Test :");
		System.out.println(this.elasticController.processQuery());
	}
	

	
	public void executeTest(){
		System.out.println("\n\n--------------> execute Test :");
		System.out.println(elasticController.execute().getJestResult());
		
		
	}

	//---------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	/************************** processResult Test **********************************************************************/
	//---------------------------------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------------
	public void processResultTest(){
		try {
			System.out.println("\n\n--------------> processResultFist Test :");
			System.out.println(
					elasticController.execute()
									.processResult(elasticController
														.getConfig()
														.getResponseMapping()));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void facetsProcessTest(){
		System.out.println("\n\n-------------> facetsProcessFirst Test :");
		System.out.println(
				this.elasticController.execute()
									.facetsProcess(elasticController.getConfig()
																	.getFacets()));
		
	}

	
	
	

}
