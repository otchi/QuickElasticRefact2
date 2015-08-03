package com.edifixio.amine.mapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class RequestMappingResolver {
	private Mapping<List<String>> requestMapping;
	private JsonObject query;
	
	public RequestMappingResolver(Mapping<List<String>> requestMapping,JsonObject query){
		this.requestMapping=requestMapping;
		this.query=query;
		
	}

	public Map<String, List<String>> resolveMapping() {
		// TODO Auto-generated method stub
		Map<String, List<String>> result=new HashMap<String, List<String>>();
		Map<String, String>  corresp=new HashMap<String, String>();
		DetectVariable(this.query, corresp, "");
		
		Iterator<Entry<String, List<String>>> iterMapping=this.requestMapping.getMapping().entrySet().iterator();
	
		while(iterMapping.hasNext()){
			Entry<String, List<String>> entry=iterMapping.next();
			Iterator<String> IterOfVariable=entry.getValue().iterator();
			List<String> liste=new LinkedList<String>();
			while(IterOfVariable.hasNext()){
				String var=IterOfVariable.next();
				String path=corresp.get(var);
				if(path!=null){
					liste.add(path);
					corresp.remove(var);
				}else {
					System.out.println(var+" : cette variable est dubliqué ou inexistante");
				}
			}
			result.put(entry.getKey(), liste);
		}
		
		return result;
	}


	private static void DetectVariableInArray(JsonElement jsonElement,Map<String,String> result,String path){
			if(jsonElement==null||path==null) {
				System.out.println("DetectVariableInArray : valeur null detecter en argument"); 
				return ;
			}
			if(path.equals("")){
				System.out.println("DetectVariableInArray : la détéction des variable se fait sur un Query json");
				return ;
			}
			
			JsonArray jsonArray=jsonElement.getAsJsonArray();
			
			for(int i=0;i<jsonArray.size();i++){
				
				JsonElement je=jsonArray.get(i);
				
				if(je.isJsonArray()||je.isJsonObject()) 
					if(path.equals("")) 
						System.out.println("is not object");
					else 
						DetectVariable(je,result,path+"::"+i);
				
				else 
					if(!je.isJsonNull()&&je.getAsJsonPrimitive().isString()){
						
						String js=je.getAsString();
						if(js.substring(0, 2).equals("$$")){
							
							if(path.equals(""))
								System.out.println("is not JsonObject");
							else 
								result.put(js.substring(2),  path+"::"+i);
					}	
				}
			}
		}


	private static void DetectVariableInObject(JsonElement jsonElement,Map<String,String> result,String path){
		if(jsonElement==null||path==null) {
			System.out.println("DetectVariableInObject : valeur null detecter en argument"); 
			return ;
		}
		
		JsonObject jsonObject=jsonElement.getAsJsonObject();
		Iterator<Entry<String,JsonElement>> jsonElementSet=jsonObject.entrySet().iterator();
		
		while(jsonElementSet.hasNext()){
			Entry<String,JsonElement> entry=jsonElementSet.next();
			JsonElement je=entry.getValue();
			
			if(je.isJsonArray()||je.isJsonObject())
				
				if(path.equals(""))
				DetectVariable(je,result,entry.getKey());
				else 
					DetectVariable(je, result, path+"::"+entry.getKey());
			else if(!je.isJsonNull()&&je.getAsJsonPrimitive().isString()){
				String js=je.getAsString();
				if(js.substring(0, 2).equals("$$")){
					String pth=(!path.equals(""))?
						path+"::"+entry.getKey():entry.getKey(); 
					result.put(js.substring(2), pth);
				}	
			}
		}
	}
		
	
	
	private static void DetectVariable(JsonElement jsonElement,Map<String,String> result,String path){
		if(jsonElement.isJsonArray()){
			DetectVariableInArray(jsonElement, result,path);
			return;
		}
		
		if(jsonElement.isJsonObject()){
			DetectVariableInObject(jsonElement, result,path);
		}
	
	}
	



}
