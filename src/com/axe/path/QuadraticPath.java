package com.axe.path;

import com.axe.core.Alternative;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;

public class QuadraticPath<T> extends AbstractPath<T> 
{

	public T p0;
	public T p1;
	public T p2;
	private T temp;

	public QuadraticPath()
	{
	}
	
	public QuadraticPath( Alternative<T> p0, Alternative<T> p1, Alternative<T> p2 ) 
	{
		this( p0.alternative(), p1.alternative(), p2.alternative() );
	}
	
	public QuadraticPath(T p0, T p1, T p2)
	{
		super( p0 );
		
		this.p0 = p0;
		this.p1 = p1;
		this.p2 = p2;
		this.temp = calc.create();
	}
	 
	
	@Override
	public T set(T subject, float d1) 
	{
		float d2 = d1 * d1;
		float i1 = 1 - d1;
		float i2 = i1 * i1;
	
		temp = calc.scale( temp, p0, i2 );
		temp = calc.addsi( temp, p1, 2 * i1 * d1 );
		temp = calc.addsi( temp, p2, d2 );
		
		subject = calc.copy(subject, temp );
		
		return subject;
	}

	public int getPointCount()
	{
		return 3;
	}
	
	public T getPoint(int index)
	{
		switch(index) {
		case 0: return p0;
		case 1: return p1;
		case 2: return p2;
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
		temp = calc.create();
		
		input.readModel( "p0", p0, calc );
		input.readModel( "p1", p1, calc );
		input.readModel( "p2", p2, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", p0 );
		
		output.writeModel( "p0", p0, calc );
		output.writeModel( "p1", p1, calc );
		output.writeModel( "p2", p2, calc );
	}

}
