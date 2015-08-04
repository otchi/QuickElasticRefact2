package com.edifixio.amine.mapping;

import java.util.Map;

public  class  Mapping<Type>{

	protected Class<?> BeanClass;
	protected Map<String, Type> mapping;
	
	
	

	public Class<?> getBeanClass() {
		return BeanClass;
	}
	public void setBeanClass(Class<?> beanClass) {
		BeanClass = beanClass;
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
		return "class ::>>"+this.BeanClass+"\n mapping ::>> "
				+this.mapping;
	}

}
