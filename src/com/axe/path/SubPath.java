package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Scalarf;


public class SubPath<T> extends AbstractPath<T>
{

	public final Scalarf start = new Scalarf();
	public final Scalarf end = new Scalarf();
	public Path<T> path;
	
	public SubPath( float start, float end, Path<T> path )
	{
		super( path.getCalculator() );
		
		this.start.set( start );
		this.end.set( end );
		this.path = path;
	}

	@Override
	public T set( T subject, float delta )
	{
		subject = path.set( subject, (end.v - start.v) * delta + start.v );
		
		return subject;
	}
	
	@Override
	public int getPointCount()
	{
		return path.getPointCount();
	}

	@Override
	public T getPoint( int index )
	{
		return path.getPoint( index );
	}

	@Override
	public void read( InputModel input )
	{
		start.v = input.readFloat( "start" );
		end.v = input.readFloat( "end" );
		path = input.readModel( "path", "path-class" );
	}

	@Override
	public void write( OutputModel output )
	{
		output.write( "start", start.v );
		output.write( "end", end.v );
		output.writeModel( "path", "path-class", path );
	}
	
}
