package com.edifixio.amine.config;

import java.util.List;
import java.util.Map;

import com.edifixio.amine.mapping.Mapping;



public class MainConfig {


	private String host;
	private Map<String, List<String>> indexes;
	private List<String> facets;
	private Mapping<List<String>> requestMapping;
	private Mapping<String>  responseMapping;
	
	
	public Map<String, List<String>> getIndexes() {
		return indexes;
	}

	public void setIndexes(Map<String, List<String>> indexes) {
		this.indexes = indexes;
	}
	

	public Mapping<String> getResponseMapping() {
		return responseMapping;
	}

	public void setResponseMapping(Mapping<String> responseMapping) {
		this.responseMapping = responseMapping;
	}

	public Mapping<List<String>> getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(Mapping<List<String>>  requestMapping) {
		this.requestMapping = requestMapping;
	}

	

	public List<String> getFacets() {
		return facets;
	}

	public void setFacets(List<String> facets) {
		this.facets = facets;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return super.toString();
		return "*************************************************"+
				"------------------------------------------------"+"\n host ::>>"+
				this.host+"\n"+
				"------------------------------------------------"+"\n indexes ::>>"+
				this.indexes+"\n"+
				"------------------------------------------------"+"\n facets ::>>"+
				this.facets+"\n"+
				"------------------------------------------------"+"\n responseMapping ::>>"+
				this.responseMapping+"\n"+
				"------------------------------------------------"+"\n requestMapping ::>>"+
				this.requestMapping+"\n"
				+"------------------------------------------------"+"\n"+
				"*************************************************";
		
		
	}
}
