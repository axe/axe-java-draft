package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;


public class Tween<T> extends AbstractPath<T>
{
	
	public T start;
	public T end;

	public Tween()
	{
	}
	
	public Tween(Alternative<T> start, Alternative<T> end)
	{
		this( start.alternative(), end.alternative() );
	}
	
	public Tween(T start, T end) 
	{
		super( start );
		
		this.start = start;
		this.end = end;
	}
	
	@Override
	public T set(T subject, float delta) 
	{
		return calc.interpolate( subject, start, end, delta );
	}

	@Override
	public int getPointCount()
	{
		return 2;
	}

	@Override
	public T getPoint(int index)
	{
		switch(index) {
		case 0: return start;
		case 1: return end;
		}
		return null;
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc
		// Attribute<T> factory = input.readInstance( "type" );

		input.readModel( "start", start = calc.create(), calc );
		input.readModel( "end", end = calc.create(), calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", start );
		
		output.writeModel( "start", start, calc );
		output.writeModel( "end", end, calc );
	}
	
}