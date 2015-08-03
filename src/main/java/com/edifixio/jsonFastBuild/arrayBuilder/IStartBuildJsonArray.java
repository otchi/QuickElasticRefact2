package com.edifixio.jsonFastBuild.arrayBuilder;

public interface IStartBuildJsonArray <ParentType>{
	
	public IBuildJsonArray<ParentType> begin();
	public ParentType emptyArray();
	

}
