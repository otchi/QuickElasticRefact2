package com.edifixio.amine.loadConfig;

import java.util.Map;

public  class  Mapping<Type>{

	protected Class<?> beanClass;
	protected Map<String, Type> mapping;
	
	
	

	public Class<?> getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
		
	}
	public Map<String, Type> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, Type>mapping) {
		this.mapping = mapping;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//return super.toString();
		return "class ::>>"+this.beanClass+"\n mapping ::>> "
				+this.mapping;
	}
	
	public static void main(String args[]) throws NoSuchFieldException, SecurityException{
		System.out.println(Mapping.class.getDeclaredField("beanClass").getType());
	}

}
