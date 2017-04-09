package com.axe.util;

import java.lang.reflect.Array;

public class EnumIntBiMap<E extends Enum<E>> 
{

	private E[] enums;
	private int[] ints;
	
	public EnumIntBiMap(Class<E> enumClass, int maxInteger)
	{
		enums = (E[])Array.newInstance( enumClass, maxInteger + 1 );
		ints = new int[ enumClass.getEnumConstants().length ];
	}
	
	public void add(E enumeration, int integer)
	{
		enums[ integer ] = enumeration;
		ints[ enumeration.ordinal() ] = integer;
	}
	
	public int get(E enumeration)
	{
		return ints[ enumeration.ordinal() ];
	}
	
	public E get(int integer)
	{
		return enums[ integer ];
	}

	public boolean has(E enumeration)
	{
		return ints[ enumeration.ordinal() ] != 0;
	}

	public boolean has(int integer)
	{
		return enums[ integer ] != null;
	}
	
}
