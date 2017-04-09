package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Numbers;

public class BezierPath<T> extends AbstractPath<T> 
{
	
	private T[] points;
	private T temp;
	private int[] weights;
	private float[] inverses;

	public BezierPath()
	{
	}
	
	public BezierPath( Alternative<T> ... points ) 
	{
		this( PathUtility.alternatives( points ) );
	}
	
	public BezierPath( T ... points ) 
	{
		super( points[0] );
		
		this.points = points;
		this.weights = computeWeights( points.length );
		this.inverses = new float[ points.length ];
		this.temp = calc.create();
	}
	
	private int[] computeWeights( int n )
	{
		int[] w = new int[ n-- ];
		
		for (int i = 0; i <= n; i++) 
		{
			w[i] = Numbers.choose( n, i );
		}
		
		return w;
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		final int n = points.length;
		float x = 1;
		
		inverses[n - 1] = 1;
		
		for (int i = n - 2; i >= 0; i--) 
		{
			inverses[i] = inverses[i + 1] * (1 - delta);
		}
		
		calc.clear( temp, 0 );
		
		for (int i = 0; i < n; i++) 
		{
			calc.addsi( temp, points[i], weights[i] * inverses[i] * x );

			x *= delta;
		}
		
		calc.copy( subject, temp );
		
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
		weights = computeWeights( points.length );
		temp = calc.create();
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModelArray( "point", points, "point-type", calc );
	}


}
