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
	
	private MainConfig config;
	private JsonObject query;
	private ProcessResult processResult;
/************************************************************************************************************************************/	
	public ElasticController(JsonObject config){
		try {
			
			this.config=new LoadJsonConfig(	config.get("_config")
												  .getAsJsonObject())
											.loadJsonConf();
			
			this.query=config.getAsJsonObject("_query");
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
/***************************************************************************************************************************************/
	
	public JsonObject processQuery(){
		JsonObject newQuery=new JsonParser().parse(query.getAsJsonObject().toString())
											.getAsJsonObject();
		newQuery.remove("query");
		newQuery.add("query", 
				JsonObjectBuilder.init()
								 .begin()
									.putEmptyObject("match_all")
								 .end()
								 .getJsonElement());
		
		return newQuery;
	}
	
/**********************************************************************************************************************************************/
// this methode hes same problem from type of recuperated field .............
	public JsonObject processQuery(Object request){
		
		
		JsonParser jsonParser=new JsonParser();
		JsonObject newQuery=jsonParser.parse(query.getAsJsonObject().toString())
				.getAsJsonObject();
		//--------------------------------------------------------------------------------------------------------------
		try {
			Mapping<List<String>> requestConf=config.getRequestMapping();
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
						int indexOfPrefix=selector.lastIndexOf("::");
						
						JsonElement js=Utiles.seletor(selector.substring(0, indexOfPrefix), newQuery)
											 .getAsJsonPrimitive();
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
		}  catch (NoSuchMethodException e) {
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
		return newQuery; 
	}
/****************************** use only the index for the moment ***********************************************/	
	
	public void execute(){
		try {
			this.processResult=new ProcessResult(send(new Search.Builder(processQuery().toString())),
												config.getResponseMapping());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
/****************************************************************************************************************/
	public void execute(Object request){
		try {
			this.processResult=new ProcessResult(send(new Search.Builder(processQuery(request).toString())),
												config.getResponseMapping());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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


/****************************************************************************************************************/

	public ProcessResult getProcessResult() {
		return processResult;
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
