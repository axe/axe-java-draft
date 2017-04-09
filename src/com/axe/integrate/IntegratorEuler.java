package com.axe.integrate;

import com.axe.core.Attribute;
import com.axe.game.GameState;


public class IntegratorEuler<T extends Attribute<T>> implements Integrator<T>
{

	public T position;
	public T velocity;
	public T acceleration;

	public IntegratorEuler(T position, T velocity, T acceleration)
	{		
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
	}
	
	@Override
	public void update( GameState state )
	{
		velocity.adds( acceleration, state.seconds );
		position.adds( velocity, state.seconds );
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
