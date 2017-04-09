package com.axe.util;


public class EnumIntMap<E extends Enum<E>> 
{

	private int[] ints;
	
	public EnumIntMap(Class<E> enumClass)
	{
		ints = new int[ enumClass.getEnumConstants().length ];
	}
	
	public void add(E enumeration, int integer)
	{
		ints[ enumeration.ordinal() ] = integer;
	}
	
	public int get(E enumeration)
	{
		return ints[ enumeration.ordinal() ];
	}
	
	public boolean has(E enumeration)
	{
		return ints[ enumeration.ordinal() ] != 0;
	}
	
}
