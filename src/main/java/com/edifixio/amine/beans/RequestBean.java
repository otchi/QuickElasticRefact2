package com.edifixio.amine.beans;

public class RequestBean {
	private String mainSearch="chevrolet";
	public RequestBean() {
		super();
	}
	
	public RequestBean(String mainSearch) {
		super();
		this.mainSearch = mainSearch;
	}


	public String getMainSearch() {
		return mainSearch;
	}

	public void setMainSearch(String mainSearch) {
		this.mainSearch = mainSearch;
	}



}
