package com.axe.path;

import java.util.Arrays;

import com.axe.core.Alternative;


public class PathUtility
{
	

	public static <T> T[] alternatives(Alternative<T>[] alts, T ... empty)
	{
		T[] attrs = Arrays.copyOf( empty, alts.length );
		
		for (int i = 0; i < alts.length; i++)
		{
			attrs[ i ] = alts[ i ].alternative();
		}
		
		return attrs;
	}
	
}
