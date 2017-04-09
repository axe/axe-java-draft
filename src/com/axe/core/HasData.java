package com.axe.core;

public interface HasData 
{

	public <T> T getData();
	
	default public <T> T getData(Class<T> ofType)
	{
		return ofType.isInstance( getData() ) ? getData() : null;
	}
	
	public void setData(Object data);
	
}
