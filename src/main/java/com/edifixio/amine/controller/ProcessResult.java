package com.edifixio.amine.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.amine.facets.Facet;
import com.edifixio.amine.mapping.Mapping;
import com.edifixio.amine.utiles.Utiles;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import io.searchbox.client.JestResult;

public class ProcessResult {
//--------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------
	private static final String RESULT_ARRAY="hits::hits";
	
	private static final String SOURCE="_source";
	public static final String AGG="aggregations";// to put private 
	public static final String BUCKETS="buckets"; // to put private
	
	private JestResult jestResult;
	
	private Mapping<String>  mapping;
	
/********************************************************************************************************************/	
	public ProcessResult(JestResult jestResult,Mapping<String> mapping) {
		super();
		
		this.jestResult = jestResult;
		
		this.mapping=mapping;
	}
	

/**
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException ****************************************************************************************************************/
	public List<Object> processResult() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
												IllegalArgumentException, InvocationTargetException{
		List<Object> result=new LinkedList<Object>();
		
		Map<String, Method> setMethodes=new HashMap<String, Method>();
		
		Iterator<JsonElement> resultIter=Utiles.seletor(RESULT_ARRAY,jestResult.getJsonObject())
												.getAsJsonArray().iterator();
		
		while(resultIter.hasNext()){
			Object o=mapping.getBeanClass().newInstance();
			JsonElement source=resultIter.next().getAsJsonObject().get(SOURCE);
			System.out.println(source);
			Iterator<Entry<String, JsonElement>> sourceIter=source.getAsJsonObject().entrySet().iterator();
			
		
			while( sourceIter.hasNext()){
				Entry<String, JsonElement> entry= sourceIter.next();
				String key=entry.getKey();
				/***************** type complex non suporter ***********************************/
				/**********************************************************************************/
				JsonPrimitive value=entry.getValue().getAsJsonPrimitive();
				System.out.println(entry);
				String settersName="set"
										+	key.substring(0, 1).toUpperCase()
										+	key.substring(1);
				Method m=setMethodes.get(settersName);
				if(value.isString()){
					if(m==null)
						setMethodes.put(settersName, 
										m=mapping.getBeanClass()
												 .getMethod(settersName, String.class));
					
						m.invoke(o, value.getAsString());
				}

				if(value.isNumber()){
					if(m==null)
						setMethodes.put(settersName, 
										m=mapping.getBeanClass()
												 .getMethod(settersName, Number.class));
					
						m.invoke(o, value.getAsNumber());	
				}
						
				if(value.isBoolean()){
					if(m==null)
						setMethodes.put(settersName, 
										m=mapping.getBeanClass()
												 .getMethod(settersName, Number.class));
					
						m.invoke(o, value.getAsNumber());	
				}
			}

			result.add(o);
		}
		return result;
	}

/******************************************************************************************************************/
	public List<Facet> facetsProcess(){
		return null;
		
	} 
/******************************************************************************************************************/
	public List<Facet> facetsProcess(List<Facet> facets){
		return null;
		
	}
/******************************************************************************************************************/
	public JsonObject getJestResult() {
		return jestResult.getJsonObject();
	}


}
