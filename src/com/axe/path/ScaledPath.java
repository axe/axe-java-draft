package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class ScaledPath<T> extends AbstractPath<T>
{

	public T scale;
	public Path<T> path;
	
	public ScaledPath()
	{
	}
	
	public ScaledPath(T scale, Path<T> path)
	{
		super( scale );
		
		this.scale = scale;
		this.path = path;
	}
	
	@Override
	public void read( InputModel input )
	{
		path = input.readModel( "path", "path-type", true );
		scale = input.readModel( "scale", "scale-type", true );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModel( "path", path, "path-type" );
		output.writeModel( "scale", scale, "scale-type", calc );
	}

	@Override
	public T set( T subject, float delta )
	{
		subject = path.set( subject, delta );
		
		return calc.muli( subject, scale );
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

}
