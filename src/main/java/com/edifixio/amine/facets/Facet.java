package com.edifixio.amine.facets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Facet {
	
	private String name;
	private List<FacetTerm> facetTerms;
	
/******************************************************************************************************************/	
	public Facet(String name, List<FacetTerm> facetTerms) {
		super();
		this.name = name;
		this.facetTerms = facetTerms;
	}
	
/******************************************************************************************************************/	
	
	public Facet updateFacet(Facet subFacet){
		//-------------------------------------------------------------------------------
		List<FacetTerm> facetTermsRollBack=new LinkedList<FacetTerm>();
		if(!this.name.equals(subFacet.getName())) {
			System.out.println("this methode is used with facet have same name");
			return null;
		}
		else{
			
			Map<String, FacetTerm> facetMap=new HashMap<String, FacetTerm>();
			
			for(FacetTerm facetTerm:facetTerms){
				
				facetMap.put(facetTerm.getTerm(), facetTerm);
				//-------------------------clone ----------------------------
				facetTermsRollBack.add(
						new FacetTerm(facetTerm.getTerm().toString(), 
										facetTerm.getCount().intValue(), 
										facetTerm.getIsChecked().booleanValue()));
				//---------------------------------------------------------------
				facetTerm.setCount(0);
			
			}
			
			Iterator<FacetTerm> subFacetIter=subFacet.facetTerms.iterator();
			//----------------------------------------------------------------------------
			while(subFacetIter.hasNext()){
				//-----------------------------------------------------------------------
				FacetTerm facetTermSub=subFacetIter.next();
				FacetTerm curent;
				//----------------------------------------------------------------------
				if((curent=facetMap.get(facetTermSub.getTerm()))==null){
					System.out.println("this methode is used with facet including in th facet called this methode");
					this.facetTerms=facetTermsRollBack;
					return null;
				}else{
					curent.setCount(facetTermSub.getCount());
				}
			}
		}
		
		return this;
	}
	
/**************************************************************************************************************************/
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
	

/*****************************************************************************************************************************/
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n"+name+"::>>"+facetTerms+"\n";
	}
	
	
}
