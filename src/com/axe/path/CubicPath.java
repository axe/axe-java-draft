package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;

public class CubicPath<T> extends AbstractPath<T> 
{

	private T p0;
	private T p1;
	private T p2;
	private T p3;
	private T temp;

	public CubicPath()
	{
	}
	
	public CubicPath(Alternative<T> p0, Alternative<T> p1, Alternative<T> p2, Alternative<T> p3)
	{
		this( p0.alternative(), p1.alternative(), p2.alternative(), p3.alternative() );
	}
	
	public CubicPath(T p0, T p1, T p2, T p3)
	{
		super( p0 );
		
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.temp = calc.create();
	}
	 
	@Override
	public T set(T subject, float d1) 
	{
		float d2 = d1 * d1;
		float d3 = d1 * d2;
		float i1 = 1 - d1;
		float i2 = i1 * i1;
		float i3 = i1 * i2;
	
		calc.copy( temp, p0 );
		calc.scalei( temp, i3 );
		calc.addsi( temp, p1, 3 * i2 * d1 );
		calc.addsi( temp, p2, 3 * i1 * d2 );
		calc.addsi( temp, p3, d3 );	
		calc.copy( subject, temp );
		
		return subject;
	}

	public int getPointCount()
	{
		return 4;
	}
	
	public T getPoint(int index)
	{
		switch(index) {
		case 0: return p0;
		case 1: return p1;
		case 2: return p2;
		case 3: return p3;
		}
		return null;
	}
	
	@Override
	public void read( InputModel input )
	{
		// TODO calc
		// Attribute<T> factory = input.readInstance( "type" );
		
		p0 = calc.create();
		p1 = calc.create();
		p2 = calc.create();
		p3 = calc.create();
		temp = calc.create();
		
		input.readModel( "p0", p0, calc );
		input.readModel( "p1", p1, calc );
		input.readModel( "p2", p2, calc );
		input.readModel( "p3", p3, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", p0 );
		
		output.writeModel( "p0", p0, calc );
		output.writeModel( "p1", p1, calc );
		output.writeModel( "p2", p2, calc );
		output.writeModel( "p3", p3, calc );
	}

}
