package com.edifixio.amine.utiles;

import java.io.FileNotFoundException;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public abstract class Utiles {

	public static JsonElement seletor(String selecor, JsonElement jse) {
		if(selecor==null) return jse;
		String[] ArraySelecor=selecor.split("::");
		if (!ArraySelecor[0].equals("")) {
			JsonElement inter = null;
			if (jse.isJsonObject())
				inter = jse.getAsJsonObject();
			if (jse.isJsonArray())
				inter = jse.getAsJsonArray();
			if (jse.isJsonPrimitive())
				return null;

			int i = 0;

			while ((inter != null) && i < ArraySelecor.length) {
				if (inter.isJsonObject())
					inter = inter.getAsJsonObject().get(ArraySelecor[i]);
				else if (inter.isJsonArray())
					inter = inter.getAsJsonArray().get(Integer.parseInt(ArraySelecor[i]));
				else if (inter.isJsonPrimitive())
					return null;
				i++;
			}

			return inter;
		} else
			return jse;

	}
	
	

	
	
	
	
	

	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		// TODO Auto-generated method stub
		//JsonParser jsonParser = new JsonParser();
	/*	JsonObject jo = (JsonObject) jsonParser.parse("{select:{cc:{dc:[{zz:{fg:\"nn\"}},{vf:\"eez\"}]}}}");

		System.out.println(Utiles.seletor("", jo));*/
		
		/*JsonObject jsonObject=(JsonObject) jsonParser.parse("{ teste:\"$$tb\",bb:[\"$$ts\"],\"tk\":{tm:{tt:\"$$dfe\"}}}");
		Map<String,Entry<JsonElement,String>> vMap=new HashMap<String, Map.Entry<JsonElement,String>>();
		DetectVariable(jsonObject, vMap);
		System.out.println(vMap);*/
		
	}

}
