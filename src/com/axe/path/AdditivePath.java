
package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class AdditivePath<T> extends AbstractPath<T>
{

	public Path<T> path0;
	public Path<T> path1;
	public T temp;

	public AdditivePath()
	{
	}

	public AdditivePath( Path<T> path0, Path<T> path1 )
	{
		super( path0.getCalculator() );
		
		this.path0 = path0;
		this.path1 = path1;
		this.temp = calc.create();
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		temp = input.readModel( "temp", "temp-type", true );
		path0 = input.readModel( "path0", "path0-type", true );
		path1 = input.readModel( "path1", "path1-type", true );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModel( "path0", path0, "path0-type" );
		output.writeModel( "path1", path1, "path1-type" );
		output.writeModel( "temp", temp, "temp-type", getCalculator() );
	}

	@Override
	public T set( T subject, float delta )
	{
		calc.copy( temp, path0.set( subject, delta ) );
		calc.addi( temp, path1.set( subject, delta ) );
		calc.copy( subject, temp );
		
		return subject;
	}

	@Override
	public int getPointCount()
	{
		return path0.getPointCount() + path1.getPointCount();
	}

	@Override
	public T getPoint( int index )
	{
		int offset = path0.getPointCount();

		if (index < offset)
		{
			return path0.getPoint( index );
		}
		
		return path1.getPoint( index - offset );
	}

}
