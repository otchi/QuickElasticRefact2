package com.edifixio.amine.facets;

import java.util.List;

public class Facet {
	
	public Facet(String name, List<FacetTerm> facetTerms) {
		super();
		this.name = name;
		this.facetTerms = facetTerms;
	}
	private String name;
	private List<FacetTerm> facetTerms;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FacetTerm> getFacetTerms() {
		return facetTerms;
	}
	public void setFacetTerms(List<FacetTerm> facetTerms) {
		this.facetTerms = facetTerms;
	}
}
