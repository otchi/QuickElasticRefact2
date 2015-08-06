package test.com.edifixio.amine.mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import com.edifixio.amine.mapping.Mapping;
import com.edifixio.amine.mapping.RequestMappingResolver;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RequestMappingResolverTest {
	public static final String QUERY="_query";
	private RequestMappingResolver requestMappingResolver;
	
	public RequestMappingResolverTest(Mapping<List<String>> mapping,String path){
		try {
			this.requestMappingResolver=new RequestMappingResolver(mapping,
																	new JsonParser()
																	.parse(new FileReader(new File(path)))
																	.getAsJsonObject().get(QUERY)
																	.getAsJsonObject());
			
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testResolveMapping(){
		System.out.println("-------------> test ResolveMapping :");
		System.out.println(requestMappingResolver.resolveMapping()+"\n\n");
	}

}
