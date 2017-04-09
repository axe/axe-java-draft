package com.axe.util;

public class Ref<T> 
{

	public T value;
	
	public Ref()
	{
	}
	
	public Ref(T value)
	{
		this.value = value;
	}
	
	public Ref<T> set(T value)
	{
		this.value = value;
		
		return this;
	}
	
}
