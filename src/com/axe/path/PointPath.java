package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class PointPath<T> extends AbstractPath<T>
{
	
	public T value;
	
	public PointPath(T value)
	{
		super( value );
		
		this.value = value;
	}

	@Override
	public T set( T subject, float delta )
	{
		return calc.copy( subject, value );
	}

	public int getPointCount()
	{
		return 1;
	}
	
	public T getPoint(int index)
	{
		return (index == 0 ? value : null);
	}
	
	public T get(int index)
	{
		return (index == 0 ? value : null );
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		input.readModel( "value", value, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeModel( "value", value, calc );
	}

}
