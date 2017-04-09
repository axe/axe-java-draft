package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.util.Array;


public class DurationPath<T> extends AbstractPath<T>
{

	private static final float[] NO_DURATIONS = {};
	
	protected float[] durations;
	protected T[] points;
	protected float durationTotal;
	
	public DurationPath()
	{
	}
	
	public DurationPath( T initialPoint )
	{
		this( toArray( initialPoint ), NO_DURATIONS );
	}
	
	public DurationPath( Alternative<T>[] points, float[] durations ) 
	{
		this( PathUtility.alternatives( points ), durations );
	}
	
	public DurationPath( T[] points, float[] durations )
	{
		super( points[0] );
		
		this.points = points;
		this.durations = durations;
		this.updateDuration();
	}
	
	public void setPoints( T[] points )
	{
		this.points = points;
	}
	
	public void setDurations( float[] durations )
	{
		this.durations = durations;
		this.updateDuration();
	}
	
	public DurationPath<T> withPoints( T ... points )
	{
		this.points = points;
		
		return this;
	}
	
	public DurationPath<T> withDurations( float ... durations )
	{
		this.durations = durations;
		this.updateDuration();
		
		return this;
	}
	
	public DurationPath<T> addPoint( T point, float duration )
	{
		points = Array.add( point, points );
		durations = Array.add( duration, durations );
		durationTotal += duration;
		
		return this;
	}
	
	public float updateDuration()
	{
		durationTotal = 0;
		
		for ( int i = 0; i < durations.length; i++ )
		{
			durationTotal += durations[ i ];
		}
		
		return durationTotal;
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
			int i = 0;
			float d = delta * durationTotal;
			
			while ( d > durations[i] ) {
				d -= durations[i++];
			}
			
			float q = d / durations[i];
			
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
	
	public float[] durations()
	{
		return durations;
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc
		
		points = input.readModelArray( "point", "point-type" );
		durations = new float[ points.length - 1 ];
		
		InputModel[] pointModels = input.readModelArray( "point" );
		
		for (int i = 0; i < pointModels.length - 1; i++ )
		{
			InputModel c = pointModels[ i ];
			
			durations[i] = c.readFloat( "duration" );
		}
		
		updateDuration();
	}

	@Override
	public void write( OutputModel output )
	{
		OutputModel[] models = output.writeModelArray( "point", points, "point-type", calc );
		
		for (int i = 0; i < durations.length; i++) 
		{
			models[i].write( "duration", durations[i] );
		}
	}
	
	private static <T> T[] toArray( T ... attributes )
	{
		return attributes;
	}

}
