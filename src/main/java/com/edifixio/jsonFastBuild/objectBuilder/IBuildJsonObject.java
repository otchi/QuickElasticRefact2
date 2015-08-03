package com.edifixio.jsonFastBuild.objectBuilder;

import com.edifixio.jsonFastBuild.arrayBuilder.IStartBuildJsonArray;

public interface IBuildJsonObject<ParentType> {
	public IStartBuildJsonObject<IPutProprety<ParentType>> putObject();
	public IStartBuildJsonArray<IPutProprety<ParentType>> putArray();
	public IPutProprety<ParentType> putValue(String value);
	public IPutProprety<ParentType> putValue(Number value);
	public IPutProprety<ParentType> putValue(Character value);
	public IPutProprety<ParentType> putValue(Boolean value);

}
