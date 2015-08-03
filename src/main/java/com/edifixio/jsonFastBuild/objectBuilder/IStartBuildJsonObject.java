package com.edifixio.jsonFastBuild.objectBuilder;


public interface IStartBuildJsonObject<ParentType> {
	public IPutProprety<ParentType> begin();
	public ParentType emptyObject();

}
