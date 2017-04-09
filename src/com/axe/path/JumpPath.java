package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

/**
 *                                     delta
 *                                       |
 * 0.0       0.25       0.5       0.75   V    1.0
 * |----------|----------|----------|----------	(4)
 * 0          1          2          3          
 * |________  |________  |________  |__________
 * 0 0 0 0 0  1 1 1 1 1  2 2 2 2 2  3 3 3 3 3 3 
 * 
 * @param <T>
 */
public class JumpPath<T> extends AbstractPath<T> 
{
	
	protected T[] points;
	
	public JumpPath()
	{
	}
	
	public JumpPath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public JumpPath( T ... jumps ) 
	{
		super( jumps[0] );
		
		this.points = jumps;
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		float a = delta * points.length;
		int index = Numbers.clamp( (int)a, 0, points.length - 1 );
		
		calc.copy( subject, points[ index ] );
		
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
