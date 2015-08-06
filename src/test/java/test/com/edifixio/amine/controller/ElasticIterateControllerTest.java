package test.com.edifixio.amine.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

import com.edifixio.amine.beans.RequestBean;
import com.edifixio.amine.controller.ElasticController;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ElasticIterateControllerTest {
	
	private String config;
	private ElasticController elasticController;
	
	public ElasticIterateControllerTest(String config){
		try {
			this.config=config;
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
		System.out.println("\n\n--------------> processQuery Test :");
		try {
			System.out.println(
					this.elasticController
						.processQuery(new RequestBean()));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
	
	
	public void executeTest() {
		System.out.println("\n\n--------------> execute Test :");
		
			System.out.println(elasticController.execute(new RequestBean()).getJestResult());
	}
	
	
	public void processResultTest(){
		
		System.out.println("\n\n--------------> processResult Test :");
		try {
			System.out.println("-->>"+
			elasticController.execute(new RequestBean()).processResult(elasticController.getConfig().getResponseMapping()));
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
		System.out.println("\n\n-------------> facetsProcess Test :");
		ElasticController e=new ElasticIterateControllerTest(config).elasticController;
		System.out.println("-->>"+
				this.elasticController
									.execute(new RequestBean())
									.facetsProcess(elasticController.getConfig()
																	.getFacets(),
													e.execute().facetsProcess(e.getConfig().getFacets())));
	}

}
