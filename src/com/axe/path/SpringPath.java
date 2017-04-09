package com.axe.path;

import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;
import com.axe.spring.LinearSpring;


public class SpringPath<V extends Vec<V>, T> extends LinearSpring<V, T> implements Path<T>
{

	private Path<T> path;

	public SpringPath()
	{
	}
	
	public SpringPath(Path<T> path, T position, T damping, T stiffness)
	{
		super( position, damping, stiffness );
		
		this.path = path;
	}
	
	public SpringPath(Path<T> path, T position, T rest, T damping, T stiffness)
	{
		super( position, rest, damping, stiffness );
		
		this.path = path;
	}
	
	@Override
	public T set( T subject, float delta )
	{
		final Calculator<T> calc = path.getCalculator();
		
		subject = path.set( subject, delta );
		subject = calc.addi( subject, position() );
		
		return subject;
	}

	@Override
	public T get( float delta )
	{
		return set( getCalculator().create(), delta ); 
	}

	@Override
	public int getPointCount()
	{
		return path.getPointCount();
	}

	@Override
	public T getPoint(int index)
	{
		return path.getPoint( index );
	}

	@Override
	public Calculator<T> getCalculator()
	{
		return path.getCalculator();
	}
	
	@Override
	public void read( InputModel input )
	{
		super.read( input );
		
		path = input.readModel( "path", "path-type", true );
	}

	@Override
	public void write( OutputModel output )
	{
		super.write( output );
		
		output.writeModel( "path", path, "path-type" );
	}

}
