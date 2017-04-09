package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

/**
 * Chaikin?s algorithm
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 */
public class ChaikinPath<T> extends AbstractPath<T> 
{
	
	protected T[] points;
	protected T temp0;
	protected T temp1;
	protected T temp2;
	protected T temp3;
	protected T temp4;
	protected int depth;
	protected boolean loops;
	protected float midpoint;
	
	public ChaikinPath()
	{
	}
	
	public ChaikinPath( int depth, Alternative<T> ... points ) 
	{
		this( depth, PathUtility.alternatives( points ) );
	}
	
	public ChaikinPath( int depth, T ... points ) 
	{
		super( points[0] );
		
		this.depth = depth;
		this.points = points;
		this.temp0 = calc.create();
		this.temp1 = calc.create();
		this.temp2 = calc.create();
		this.temp3 = calc.create();
		this.temp4 = calc.create();
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		final int n = points.length - 1;
		final float a = delta * n;
		final int i = Numbers.clamp( (int)a, 0, n - 1 );
		float d = a - i;
		final int s = (d < 0.5f ? i - 1 : i );
		
		calc.copy( temp0, points[ s ] );
		calc.copy( temp2, points[ s + 1 ] );
		calc.copy( temp4, points[ s + 2 ] );
		
		int k = depth;
		
		while (--k >= 0)
		{
			calc.interpolate( temp1, temp0, temp2, 0.5f );
			calc.interpolate( temp3, temp2, temp4, 0.5f );
		}
		
		calc.interpolate( subject, temp1, temp3, d );
		
		return subject;
	}
	
	public int getActualIndex( int index )
	{
		final int n = points.length;
		
		return ( loops ? (index + n) % n : Numbers.clamp( index, 0, n - 1 ) );
	}
	
	@Override
	public int getPointCount()
	{
		return points.length;
	}

	@Override
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
		depth = input.readInt( "depth" );
		points = input.readModelArray( "point", "point-type" );
		temp0 = calc.create();
		temp1 = calc.create();
		temp2 = calc.create();
		temp3 = calc.create();
		temp4 = calc.create();
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "depth", depth );
		output.writeModelArray( "point", points, "point-type", calc );
	}
	
}
