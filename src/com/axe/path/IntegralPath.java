package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

/**
 *                                     delta
 *                                       |
 * 0.0       0.25       0.5       0.75   V    1.0
 * |----------|----------|----------|----------|		(5)
 * 0          1          2          3          4
 * 
 * @param <T>
 */
public class IntegralPath<T> extends AbstractPath<T> 
{
	
	private T[] points;
	
	public IntegralPath()
	{
	}
	
	public IntegralPath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public IntegralPath(T ... points ) 
	{
		super( points[0] );
		
		this.points = points;
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		if (delta <= 0) 
		{
			subject = calc.copy( subject, points[0] );
		}
		else if (delta >= 1) 
		{
			subject = calc.copy( subject, points[points.length - 1] );
		}
		else 
		{
			float a = delta * (points.length - 1);
			int index = Numbers.clamp( (int)a, 0, points.length - 2 );
			
			subject = calc.interpolate( subject, points[index], points[index + 1], a - index );	
		}
		
		return subject;
	}

	public int getPointCount()
	{
		return points.length;
	}
	
	public T getPoint(int index)
	{
		return points[ index ];
	}
		
	public T[] points() 
	{
		return points;
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		points = input.readModelArray( "point", "point-type" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModelArray( "point", points, "point-type", calc );
	}

	
}
