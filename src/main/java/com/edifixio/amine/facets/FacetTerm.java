package com.edifixio.amine.facets;

public class FacetTerm {
	
	private String term;
	private Integer count;
	private Boolean isChecked;
	
	public FacetTerm(String term, Integer count, Boolean isChecked) {
		super();
		this.term = term;
		this.count = count;
		this.isChecked = isChecked;
	}
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n<<"+term+"--"+count+"--"+isChecked+">>\n";
	}

}
