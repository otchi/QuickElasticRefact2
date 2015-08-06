package com.edifixio.amine.loadConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.utiles.Utiles;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigBuilder {

	private static final String II="::";
	private static final String MAPPING="mapping";
	private static final String CLASS="class";
	private static final String HOST="_host";
	private static final String INDEXES="_indexes";
	private static final String REQUEST="_request";
	private static final String RESPONSE="_response";
	private static final String FACETS="_facets";
	private JsonObject jsonObject;	
	
	public ConfigBuilder(JsonObject jsonObject ){
		this.jsonObject=jsonObject;
	}
	
	public  Config buildConf() throws ClassNotFoundException{
		Config mainConfig=new Config();
		System.out.println("---->:::"+jsonObject);
		mainConfig.setHost(Utiles.seletor(HOST, jsonObject).getAsString());
		mainConfig.setIndexes(this.loadIndexes());
		mainConfig.setRequestMapping(this.loadRequestMapping());
		mainConfig.setResponseMapping(this.loadResponseMapping());
		mainConfig.setFacets(this.loadFacetsMapping());
	
		return mainConfig;
	}
	
	
/*************************** loading the indexex and types*************************************/
	public  Map<String,List<String>> loadIndexes(){
		return loadMappingList(INDEXES);
	}

/*************************** loanding mapping formed at Map<String,List<String>> like request and indexes mapping************************************/
	public   Map<String,List<String>> loadMappingList(String path){
		Map<String, List<String>> indexes=new HashMap<String, List<String>>();
		Iterator<Entry<String, JsonElement>> iter=Utiles.seletor(path, jsonObject).getAsJsonObject().entrySet().iterator();
					
		while(iter.hasNext()){
			Entry<String, JsonElement> entry=iter.next();
			List<String> Types=new LinkedList<String>();
			Iterator<JsonElement> TypeIter=entry.getValue().getAsJsonArray().iterator();
			while(TypeIter.hasNext()){
				Types.add(TypeIter.next().getAsString());
			}
			indexes.put(entry.getKey(), Types);
		}
		
		return indexes;
	}
/*********************************************** laoding a simple maping like response mapping *******************************************************/
	public   Map<String,String> loadMapping(String path){
		Map<String,String> responseMapping=new HashMap<String, String>();
		Iterator<Entry<String, JsonElement>> iter=Utiles.seletor(path, jsonObject).getAsJsonObject().entrySet().iterator();
		
		while(iter.hasNext()){
			Entry<String, JsonElement> entry=iter.next();
			responseMapping.put(entry.getKey(), entry.getValue().getAsString());
		}
		
		return responseMapping;
	}
/************************************************************************************************************************************************/
	
	public  Mapping<List<String>> loadRequestMapping() throws ClassNotFoundException{
		Mapping<List<String>> requestMapping=new Mapping<List<String>>();
		requestMapping.setBeanClass(Class.forName(Utiles.seletor(REQUEST+II+CLASS, jsonObject).getAsString()));
		requestMapping.setMapping(loadMappingList(REQUEST+II+MAPPING));	
		return requestMapping;
	}
/************************************************************************************************************************************************/
	public  Mapping<String> loadResponseMapping() throws ClassNotFoundException{
		Mapping<String> responseMapping=new Mapping<String>();
		responseMapping.setBeanClass(Class.forName(Utiles.seletor(RESPONSE+II+CLASS, jsonObject).getAsString()));
		responseMapping.setMapping(loadMapping(RESPONSE+II+MAPPING));
		return responseMapping;
	}
/*************************************************************************************************************************************************/
	public  List<String> loadFacetsMapping(){
		List<String> facets=new LinkedList<String>();
		Iterator<JsonElement> facetsIter=Utiles.seletor(FACETS,jsonObject).getAsJsonArray().iterator();
		
		while(facetsIter.hasNext()){
			facets.add(facetsIter.next().getAsString());
		}
		return facets;
	}
/**************************************** test*********************************************************************************************************/	

	

}
