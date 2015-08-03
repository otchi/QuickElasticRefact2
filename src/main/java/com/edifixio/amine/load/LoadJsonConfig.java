package com.edifixio.amine.load;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.config.MainConfig;
import com.edifixio.amine.mapping.Mapping;
import com.edifixio.amine.utiles.Utiles;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LoadJsonConfig {
	public static final String II="::";
	public static final String MAPPING="mapping";
	public static final String CLASS="class";
	public static final String HOST="_host";
	public static final String INDEXES="_indexes";
	public static final String REQUEST="_request";
	public static final String RESPONSE="_response";
	public static final String FACETS="_facets";
	private JsonObject jsonObject;	
	
	public LoadJsonConfig(JsonObject jsonObject ){
		this.jsonObject=jsonObject;
	}
	
	public  MainConfig loadJsonConf() throws ClassNotFoundException{
		MainConfig mainConfig=new MainConfig();
		mainConfig.setHost(Utiles.seletor(HOST, jsonObject).getAsString());
		mainConfig.setIndexes(this.loadIndexes());
		mainConfig.setRequestMapping(this.loadRequestMapping());
		mainConfig.setResponseMapping(this.loadResponseMapping());
		mainConfig.setFacets(this.loadFacets());
	
		return mainConfig;
	}
	
	
/*************************** loading the indexex and types*************************************/
	public Map<String,List<String>> loadIndexes(){
		return loadMappingList(INDEXES);
	}

/*************************** loanding mapping formed at Map<String,List<String>> like request and indexes mapping************************************/
	private  Map<String,List<String>> loadMappingList(String path){
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
	private  Map<String,String> loadMapping(String path){
		Map<String,String> responseMapping=new HashMap<String, String>();
		Iterator<Entry<String, JsonElement>> iter=Utiles.seletor(path, jsonObject).getAsJsonObject().entrySet().iterator();
		
		while(iter.hasNext()){
			Entry<String, JsonElement> entry=iter.next();
			responseMapping.put(entry.getKey(), entry.getValue().getAsString());
		}
		
		return responseMapping;
	}
/************************************************************************************************************************************************/
	
	public Mapping<List<String>> loadRequestMapping() throws ClassNotFoundException{
		Mapping<List<String>> requestMapping=new Mapping<List<String>>();
		requestMapping.setBeanClass(Class.forName(Utiles.seletor(REQUEST+II+CLASS, jsonObject).getAsString()));
		requestMapping.setMapping(loadMappingList(REQUEST+II+MAPPING));	
		return requestMapping;
	}
/************************************************************************************************************************************************/
	public Mapping<String> loadResponseMapping() throws ClassNotFoundException{
		Mapping<String> responseMapping=new Mapping<String>();
		responseMapping.setBeanClass(Class.forName(Utiles.seletor(RESPONSE+II+CLASS, jsonObject).getAsString()));
		responseMapping.setMapping(loadMapping(RESPONSE+II+MAPPING));
		return responseMapping;
	}
/*************************************************************************************************************************************************/
	public List<String> loadFacets(){
		List<String> facets=new LinkedList<String>();
		Iterator<JsonElement> facetsIter=Utiles.seletor(FACETS,jsonObject).getAsJsonArray().iterator();
		
		while(facetsIter.hasNext()){
			facets.add(facetsIter.next().getAsString());
		}
		return facets;
	}
/**************************************** test*********************************************************************************************************/	

	

}
