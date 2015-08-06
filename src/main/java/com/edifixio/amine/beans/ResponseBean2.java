package com.edifixio.amine.beans;

public class ResponseBean2 {
	private String adrss;
	private String t;
	private Number superf;
	
	
	public String getAdrss() {
		return adrss;
	}


	public void setAdrss(String adrss) {
		this.adrss = adrss;
	}


	public String getT() {
		return t;
	}


	public void setT(String t) {
		this.t = t;
	}


	public Number getSuperf() {
		return superf;
	}


	public void setSuperf(Number superf) {
		this.superf = superf;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n(("+this.adrss+"#"+this.t+"#"+this.superf+"))\n";
	}
	

}
