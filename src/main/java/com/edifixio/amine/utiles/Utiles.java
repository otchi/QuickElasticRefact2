package com.edifixio.amine.utiles;

import com.google.gson.JsonElement;

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
	
	

}
