package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

/**
 * 
 * @author Philip Diffenderfer
 *
 * @param <T>
 */
public class QuadraticCornerPath<T> extends AbstractPath<T> 
{
	
	protected T[] points;
	protected T temp0;
	protected T temp1;
	protected boolean loops;
	protected float midpoint;
	
	public QuadraticCornerPath()
	{
	}
	
	public QuadraticCornerPath( float midpoint, boolean loops, Alternative<T> ... points ) 
	{
		this( midpoint, loops, PathUtility.alternatives( points ) );
	}
	
	public QuadraticCornerPath( float midpoint, boolean loops, T ... points ) 
	{
		super( points[0] );
		
		this.midpoint = midpoint;
		this.loops = loops;
		this.points = points;
		this.temp0 = calc.create();
		this.temp1 = calc.create();
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		final float negmidpoint = 1.0f - midpoint;
		final float halfmidpoint = midpoint * 0.5f;
		final int n = points.length - (loops ? 0 : 1);
		final float a = delta * n;
		final int i = Numbers.clamp( (int)a, 0, n - 1 );
		float d = a - i;
		
		T p0 = points[ getActualIndex( i - 1 ) ];
		T p1 = points[ getActualIndex( i ) ];
		T p2 = points[ getActualIndex( i + 1 ) ];
		T p3 = points[ getActualIndex( i + 2 ) ];
		
		if ( d < midpoint )
		{
			d = (d / midpoint);
			
			temp0 = calc.interpolate( temp0, p0, p1, d * halfmidpoint + negmidpoint + halfmidpoint );
			temp1 = calc.interpolate( temp1, p1, p2, d * halfmidpoint + halfmidpoint );
			
			p1 = temp0;
			p2 = temp1;
			d = d * 0.5f + 0.5f;
		}
		else if ( d > negmidpoint )
		{
			d = (d - negmidpoint) / midpoint;
			
			temp0 = calc.interpolate( temp0, p1, p2, d * halfmidpoint + negmidpoint );
			temp1 = calc.interpolate( temp1, p2, p3, d * halfmidpoint );
			
			p1 = temp0;
			p2 = temp1;
			d = d * 0.5f;
		}
		
		subject = calc.interpolate( subject, p1, p2, d );
		
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
		points = input.readModelArray( "point", "point-type" );
		temp0 = calc.create();
		temp1 = calc.create();
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModelArray( "point", points, "point-type", calc );
	}
	
}
