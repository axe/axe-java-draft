package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.calc.Calculator;


public class ComboPath<T> extends AbstractPath<T>
{

	private Path<T>[] paths;
	private float[] times;

	public ComboPath( Path<T>[] paths, int points )
	{
		this( paths, getPathTimes( paths, points ) );
	}
	
	public ComboPath( Path<T>[] paths, float[] times )
	{
		super( paths[0].getCalculator() );
		
		this.paths = paths;
		this.times = times;
	}
	
	@Override
	public T set( T subject, float delta )
	{
		if (delta <= 0)
		{
			paths[0].set( subject, 0 );
		}
		else if (delta >= 1)
		{
			paths[ paths.length - 1 ].set( subject, 1 );
		}
		else
		{
			int i = paths.length - 1;
			while (times[i] > delta) --i;
			float q = (delta - times[i]) / (times[i + 1] - times[i]);
			paths[i].set( subject, q );
		}
		
		return subject;
	}

	public int getPointCount()
	{
		return -1;
	}
	
	public T getPoint(int index)
	{
		return null;
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc 
		paths = input.readModelArray( "path", "path-type" );
		times = new float[ paths.length + 1 ];
		
		int i = 0;
		
		times[0] = 0;
		for (InputModel c : input)
		{
			times[++i] = c.readFloat( "time" );
		}
	}

	@Override
	public void write( OutputModel output )
	{
		OutputModel[] models = output.writeModelArray( "path", paths, "path-type" );
		
		for (int i = 1; i <= paths.length; i++) 
		{
			models[i].write( "time", times[i] );
		}
	}

	public static <T> float[] getPathTimes(Path<T>[] paths, int points)
	{
		int n = paths.length;
		float[] times = new float[ n + 1 ];
		
		times[0] = 0;
		for (int k = 1; k <= n; k++)
		{
			times[k] = times[k - 1] + getPathLength( paths[k - 1], points ); 
		}
		
		float scale = 1.0f / times[n];
		
		times[n] = 1.0f;
		for (int k = 1; k < n; k++)
		{
			times[k] *= scale;
		}
		
		return times;
	}
	
	public static <T> float getPathLength(Path<T> path, int points)
	{
		Calculator<T> calc = path.getCalculator();
		T start = calc.create();
		T end = calc.create();
		float length = 0;
		
		path.set( start, 0 );
		
		for (int i = 1; i <= points; i++)
		{
			path.set( end, (float)i / points );
			length += calc.distance( end, start );
			start = end;
		}
		
		return length;
	}
	
}
