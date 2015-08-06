package com.edifixio.amine.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.edifixio.amine.config.MainConfig;
import com.edifixio.amine.load.LoadJsonConfig;
import com.edifixio.amine.mapping.Mapping;
import com.edifixio.amine.mapping.RequestMappingResolver;
import com.edifixio.amine.utiles.ElasticClient;
import com.edifixio.amine.utiles.Utiles;
import com.edifixio.jsonFastBuild.objectBuilder.JsonObjectBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.Search.Builder;

public class ElasticController {
	
	private static final String JS_CONFIG="_config";
	private static final String JS_QUERY="_query";
	private static final String SELECT_ALL="match_all";
	private static final String ES_QUERY="query";
	
	
	private MainConfig config;
	private JsonObject query;

/************************************************************************************************************************************/	
	public ElasticController(JsonObject config){
		try {
			
			this.config=new LoadJsonConfig(	config.get(JS_CONFIG)
												  .getAsJsonObject())
											.loadJsonConf();
			
			this.query=config.getAsJsonObject(JS_QUERY);
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
/***************************************************************************************************************************************/
	
	public JsonObject processQuery(){
		JsonObject newQuery=new JsonParser().parse(query.getAsJsonObject().toString())
											.getAsJsonObject();
		newQuery.remove(ES_QUERY);
		newQuery.add(ES_QUERY, 
				JsonObjectBuilder.init()
								 .begin()
									.putEmptyObject(SELECT_ALL)
								 .end()
								 .getJsonElement());
		
		return newQuery;
	}
	
/**
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException 
 * @throws IllegalAccessException ********************************************************************************************************************************************/
// this methode hes same problem from type of recuperated field .............
	public JsonObject processQuery(Object request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		
		JsonParser jsonParser=new JsonParser();
		JsonObject newQuery=jsonParser	.parse(query.getAsJsonObject().toString())
										.getAsJsonObject();
		
		//--------------------------------------------------------------------------------------------------------------
		
			Mapping<List<String>> requestConf=new Mapping<List<String>>();
			requestConf.setBeanClass(config.getRequestMapping().getBeanClass());
			requestConf.setMapping(new RequestMappingResolver(
					config.getRequestMapping(), query).resolveMapping());
			
			System.out.println("####"+request.getClass()+"####"+requestConf.getBeanClass());
			//sSystem.out.println(requestConf.getBeanClass());
			if(request.getClass()!=requestConf.getBeanClass()){
				System.out.println("class de requete incompatible");
			}else{
				Iterator<Entry<String, List<String>>> IterRequestMap=requestConf.getMapping().entrySet().iterator();
				
				//--------------------------------------------------------------------------------------------------------
				while(IterRequestMap.hasNext()){
					
					Entry<String, List<String>> entry=IterRequestMap.next();
					
					Method m=request.getClass()
									.getMethod("get"
												+ entry.getKey().substring(0, 1).toUpperCase()
												+ entry.getKey().substring(1));
					Object var=m.invoke(request);
					
					Iterator<String> iterFieldToReplace=entry.getValue().iterator();
					//-----------------------------------------------------------------------------------------------------
					while(iterFieldToReplace.hasNext()){
						String selector=iterFieldToReplace.next();
						System.out.println(selector);
						System.out.println();
						int indexOfPrefix=selector.lastIndexOf("::");
						System.out.println(indexOfPrefix);
						if(indexOfPrefix<=0){
							indexOfPrefix=0;
						}
						JsonElement js=Utiles.seletor(selector.substring(0, indexOfPrefix), newQuery);
						//---------------------------------------------------------------------------------------------------
						if(js.isJsonArray()){
							int i=Integer.parseInt(selector.substring(indexOfPrefix+2));
							JsonArray ja=js.getAsJsonArray();
							ja.remove(i);
							ja.add(jsonParser.parse(var.toString()));
						}
						
						if(js.isJsonObject()){
							JsonObject jo=js.getAsJsonObject();
							String s=selector.substring(indexOfPrefix+2);
							jo.remove(s);
							jo.add(s, jsonParser.parse(var.toString()));
						}	
					}
				}
			}
		//--------------------------------------------------------------------------------------------------------------------
	
		return newQuery; 
	}
/****************************** use only the index for the moment ***********************************************/	
	
	public ProcessResult execute(){
		ProcessResult processResult=null;
		try {
			processResult=new ProcessResult(send(new Search.Builder(processQuery().toString())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processResult;
		
	}

/****************************************************************************************************************/
	public ProcessResult execute(Object request){
		ProcessResult processResult=null;
		try {
			processResult=new ProcessResult(send(new Search.Builder(processQuery(request).toString())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return processResult;
		
	}
/***************************************************************************************************************/
	private JestResult send(Builder builder) throws IOException{
	
		Iterator<Entry<String, List<String>>> iterIndex=config.getIndexes()
																.entrySet().iterator();
		
		while(iterIndex.hasNext()){
			Entry<String, List<String>> entry=iterIndex.next();
			builder.addIndex((String) entry.getKey());
			
		}
		return ElasticClient.getElasticClient(config.getHost())
							.getClient()
							.execute(builder.build());
		
	}


/**********************************************************************************************************************/
	public MainConfig getConfig() {
		return config;
	}
/***********************************************************************************************************************/
	public JsonObject getQuery() {
		return query;
	}



	

}
