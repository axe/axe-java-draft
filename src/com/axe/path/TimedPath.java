package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class TimedPath<T> extends AbstractPath<T>
{

	protected float[] times;
	protected T[] points;
	
	public TimedPath()
	{
	}
	
	public TimedPath( Alternative<T>[] points, float[] times )
	{
		this( PathUtility.alternatives( points ), times );
	}
	
	public TimedPath( T[] points, float[] times )
	{
		super( points[0] );
		
		this.points = points;
		this.times = times;
	}
	
	protected void setPoints( T[] points )
	{
		this.points = points;
	}
	
	protected void setTimes( float[] times )
	{
		this.times = times;
	}
	
	@Override
	public T set( T subject, float delta )
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
			int i = points.length - 1;
			while (times[i] > delta) --i;
			float q = (delta - times[i]) / (times[i + 1] - times[i]);
			
			subject = calc.interpolate( subject, points[i], points[i + 1], q );
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
		times = new float[ points.length ];
		
		int i = 0;
		
		for (InputModel c : input)
		{
			times[i++] = c.readFloat( "time" );
		}
	}

	@Override
	public void write( OutputModel output )
	{
		OutputModel[] models = output.writeModelArray( "point", points, "point-type", calc );
		
		for (int i = 0; i < points.length; i++) 
		{
			models[i].write( "time", times[i] );
		}
	}

}
