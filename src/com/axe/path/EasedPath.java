package com.axe.path;

import com.axe.core.Alternative;
import com.axe.easing.Easing;
import com.axe.easing.EasingMethod;
import com.axe.easing.EasingType;
import com.axe.easing.Easings;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.util.Array;


public class EasedPath<T> extends AbstractPath<T>
{

	protected float[] durations;
	protected Easing[] easings;
	protected T[] points;
	protected float durationTotal;
	
	public EasedPath()
	{
	}

	public EasedPath( Alternative<T>[] points, float[] durations, Easing[] easings ) 
	{
		this( PathUtility.alternatives( points ), durations, easings );
	}
	
	public EasedPath( T[] points, float[] durations, Easing[] easings )
	{
		super( points[0] );
		
		this.points = points;
		this.durations = durations;
		this.easings = easings;
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
	
	public void setEasings( Easing[] easings )
	{
		this.easings = easings;
	}
	
	public EasedPath<T> withPoints( T ... points )
	{
		this.points = points;
		
		return this;
	}
	
	public EasedPath<T> withDurations( float ... durations )
	{
		this.durations = durations;
		this.updateDuration();
		
		return this;
	}
	
	public EasedPath<T> withEasings( Easing ... easings )
	{
		this.easings = easings;
		
		return this;
	}
	
	public EasedPath<T> addPoint( T point, float duration, EasingType type, EasingMethod method, float scale )
	{
		return addPoint( point, duration, new Easing( type, method, scale ) );
	}
	
	public EasedPath<T> addPoint( T point, float duration, EasingType type, EasingMethod method )
	{
		return addPoint( point, duration, new Easing( type, method ) );
	}
	
	public EasedPath<T> addPoint( T point, float duration, EasingMethod method )
	{
		return addPoint( point, duration, new Easing( Easings.In, method ) );
	}
	
	public EasedPath<T> addPoint( T point, float duration, Easing easing )
	{
		points = Array.add( point, points );
		durations = Array.add( duration, durations );
		easings = Array.add( easing, easings );
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
			
			float q = easings[i].delta( d / durations[i] );
			
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
	
	public float totalDuration()
	{
		float total = 0;
		
		for (float d : durations)
		{
			total+=d;
		}
		
		return total;
	}
	
	public float[] durations()
	{
		return durations;
	}
	
	public Easing[] easings()
	{
		return easings;
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc
		points = input.readModelArray( "point", "point-type" );
		durations = new float[ points.length - 1 ];
		easings = new Easing[ points.length - 1 ];
		
		InputModel[] pointModels = input.readModelArray( "point" );

		for (int i = 0; i < pointModels.length - 1; i++)
		{
			InputModel c = pointModels[ i ];
			
			durations[i] = c.readFloat( "duration" );
			
			EasingType type = c.readInstance( "easing-type" ); 
			EasingMethod method = c.readInstance( "easing-method"	 ); 
			float scale = c.readFloat( "easing-scale" ); 
			
			easings[i] = new Easing( type, method, scale );
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
			models[i].writeInstance( "easing-type", easings[i].type() );
			models[i].writeInstance( "easing-method", easings[i].method() );
			models[i].write( "easing-scale", easings[i].scale() );
		}
	}

}
