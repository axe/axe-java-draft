package com.axe.path;

import com.axe.math.calc.Calculator;

public class UniformPath<T> extends TimedPath<T>
{

	public UniformPath( Path<T> path )
	{
		this( path, path.getPointCount() );
	}
	
	public UniformPath( Path<T> path, int attributeCount )
	{
		setPoints( getPoints( path, attributeCount ) );
		
		setTimes( LinearPath.getTimes( points ) );
	}
	
	public static <T> T[] getPoints( Path<T> path, int attributeCount )
	{
		final Calculator<T> calc = path.getCalculator();
		T[] points = calc.createArray( attributeCount );
		
		if (path.getPointCount() == attributeCount)
		{
			for (int i = 0; i < attributeCount; i++)
			{
				points[i] = calc.clone( path.getPoint( i ) );
			}
		}
		else
		{
			points = CompiledPath.compile( path, points );
		}
		
		return points;
	}

}
