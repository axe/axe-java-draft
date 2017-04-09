package com.axe.path;

import com.axe.core.Alternative;
import com.axe.math.calc.Calculator;
import com.axe.math.calc.CalculatorRegistry;

public class LinearPath<T> extends TimedPath<T> 
{
	
	public LinearPath()
	{
	}
	
	public LinearPath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public LinearPath( T ... points) 
	{
		super( points, getTimes( points ) );
	}
	
	public static <T> float[] getTimes(T[] points)
	{
		final Calculator<T> calc = CalculatorRegistry.getFor( points[0] );
		int n = points.length;
		float[] distances = new float[n--];
		
		distances[0] = 0;
		for (int i = 1; i <= n; i++) 
		{
			distances[i] = distances[i - 1] + calc.distance( points[i - 1], points[i] );
		}
		
		float length = 1f / distances[ n ];
		
		for (int i = 1; i < n; i++) 
		{
			distances[i] *= length;
		}
		
		distances[n] = 1f;
		
		return distances;
	}
	
}
