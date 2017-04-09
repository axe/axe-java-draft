package com.axe.integrate;

import com.axe.core.Attribute;
import com.axe.game.GameState;


public class IntegratorVerlet<T extends Attribute<T>> implements Integrator<T>
{

	public T position;
	public T lastPosition;
	public T acceleration;
	private T temp;

	public IntegratorVerlet(T position, T acceleration)
	{		
		this.position = position;
		this.lastPosition = position.clone();
		this.acceleration = acceleration;
		this.temp = position.clone();
	}
	
	@Override
	public void update( GameState state )
	{
		temp.set( position );
		position.scale( 2.0f );
		position.sub( lastPosition );
		position.adds( acceleration, state.seconds * state.seconds );
		lastPosition.set( temp );
	}
	
	@Override
	public T position()
	{
		return position;
	}

	@Override
	public T acceleration()
	{
		return acceleration;
	}

}
