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
import com.edifixio.amine.facets.FacetTerm;
import com.edifixio.amine.loadConfig.Mapping;
import com.edifixio.amine.utiles.MyEntry;
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
	private static final String AGG="aggregations";
	private static final String BUCKETS="buckets"; 
	private static final String KEY="key";
	private static final String DOC_COUNT="doc_count";
	private static final String SETTER_PREFIX="set";
	private JsonObject jestResult;
	
	
/********************************************************************************************************************/	
	public ProcessResult(JestResult jestResult) {
		super();
		this.jestResult = jestResult.getJsonObject();
	}
/***************************************************************************************************************/	
	public Entry<Object, Map<String, Method>>
								initProcessResult(JsonObject firstResults,Mapping<String> mapping)
												throws InstantiationException, IllegalAccessException, 
												NoSuchMethodException, SecurityException,
												IllegalArgumentException, InvocationTargetException{
		
		
		Iterator<Entry<String, JsonElement>> firstResultsIter=
										firstResults.get(SOURCE)
													.getAsJsonObject()
													.entrySet().iterator();
		System.out.println("---"+firstResults);
		Map<String, Method> map=new HashMap<String,Method>();
		
		Object object=mapping.getBeanClass().newInstance();
		
		while(firstResultsIter.hasNext()){
			
			Entry<String, JsonElement> result=firstResultsIter.next();
			String key=result.getKey();
		
			JsonElement elementValue=result.getValue();
			
			
			if(!elementValue.isJsonPrimitive())
				System.out.println("not support a complex type");
			
			else{
				JsonPrimitive value=elementValue.getAsJsonPrimitive();
				String beanAttName=mapping.getMapping().get(key);
				
				if(value.isString()||value.isNumber()||value.isBoolean()){
					Method m = null;
					
					if(value.isString()){
						m = object.getClass().getMethod(SETTER_PREFIX 
								                      + beanAttName.substring(0, 1)
								                      			   .toUpperCase()
								                      + beanAttName.substring(1), String.class);
						map.put(key, m);
						m.invoke(object, value.getAsString());
						
					}
					
					if(value.isNumber()){
						m = object.getClass().getMethod(SETTER_PREFIX 
								                      + beanAttName.substring(0, 1)
								                      			   .toUpperCase()
								                      + beanAttName.substring(1), Number.class);
						map.put(key, m);
						m.invoke(object, value.getAsNumber());
						
					}
					
					if(value.isBoolean()){
						m = object.getClass().getMethod(SETTER_PREFIX 
								                      + beanAttName.substring(0, 1)
								                      			   .toUpperCase()
								                      + beanAttName.substring(1), Boolean.class);
						map.put(key, m);
						m.invoke(object, value.getAsBoolean());
						
					}
					
				}
				
			}
			
			
		}
		
		
		
		return new  MyEntry<Object, Map<String, Method>>(object, map);
		
	}
/**
 * @throws SecurityException 
 * @throws NoSuchMethodException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws InvocationTargetException 
 * @throws IllegalArgumentException ****************************************************************************************************************/
	public List<Object> processResult(Mapping<String> mapping) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
												IllegalArgumentException, InvocationTargetException{
		List<Object> result=new LinkedList<Object>();
		
		
		Iterator<JsonElement> resultIter=Utiles.seletor(RESULT_ARRAY,jestResult)
												.getAsJsonArray().iterator();
		
		
		Map<String, Method> mapOfSetters=null;
		if(resultIter.hasNext()){
			Entry<Object, Map<String, Method>> initResult=
					initProcessResult(resultIter.next().getAsJsonObject(), mapping);
			 mapOfSetters=initResult.getValue();
			 result.add(initResult.getKey());
		}
		System.out.println(mapOfSetters);
		
		while(resultIter.hasNext()){
			
			Object o=mapping.getBeanClass().newInstance();
			JsonElement source=resultIter.next().getAsJsonObject().get(SOURCE);
			//System.out.println(source);
			Iterator<Entry<String, JsonElement>> sourceIter=source.getAsJsonObject()
																.entrySet().iterator();
			
		
			while( sourceIter.hasNext()){
				
				Entry<String, JsonElement> entry= sourceIter.next();
				String key=entry.getKey();
				/***************** type complex non suporter ***********************************/
				/**********************************************************************************/
				JsonPrimitive value=entry.getValue().getAsJsonPrimitive();
				
				if(value.isString()){
					mapOfSetters.get(key).invoke(o, value.getAsString());
						
				}

				if(value.isNumber()){
					mapOfSetters.get(key).invoke(o, value.getAsNumber());
				}
						
				if(value.isBoolean()){
					mapOfSetters.get(key).invoke(o, value.getAsBoolean());
						
				}
				
			}

			result.add(o);
		}
		return result;
	}

/******************************************************************************************************************/
	public List<Facet> facetsProcess(List<String> facetsName){
		
		List<Facet> facets=new LinkedList<Facet>();
		
		Iterator<String> facetsNameIter=facetsName.iterator();
		System.out.println("++++"+jestResult.get(AGG));
		while(facetsNameIter.hasNext()){
			
			String facet=facetsNameIter.next();
			Iterator<JsonElement> facetResultIter=Utiles.seletor(AGG+"::"+facet+"::"+BUCKETS, jestResult)
														.getAsJsonArray().iterator();
			
			List<FacetTerm> facetTerms=new LinkedList<FacetTerm>();
			
			while(facetResultIter.hasNext()){
				
				JsonObject facetTerm=facetResultIter.next().getAsJsonObject();
				facetTerms.add(
						new FacetTerm(facetTerm.get(KEY).getAsString(), 
										facetTerm.get(DOC_COUNT).getAsInt(), false));	
			}
			
			facets.add(new Facet(facet, facetTerms));
			
			
		}
		
		return facets;
		
	} 
/******************************************************************************************************************/
	public List<Facet> facetsProcess(List<String> facetName,List<Facet> facetsModel){
		
		Iterator<Facet> facetsIter=this.facetsProcess(facetName).iterator();
		Map<String,Facet> mapping=new HashMap<String, Facet>();
		
		for(Facet facet:facetsModel)
			mapping.put(facet.getName(), facet);
			
		Facet facetToUpdate;
		
		while (facetsIter.hasNext()){
			Facet facet=facetsIter.next();
			
			if((facetToUpdate=mapping.get(facet.getName()))==null){
				System.out.println("facet incompatible");
				return null;
			}
			
			facetToUpdate.updateFacet(facet);
		}
		
		return facetsModel;	
	}
/******************************************************************************************************************/
	public JsonObject getJestResult() {
		return jestResult;
	}


}
