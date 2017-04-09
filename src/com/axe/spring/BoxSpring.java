package com.axe.spring;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;

public class BoxSpring<V extends Vec<V>, T> extends AbstractSpring<V, T>
{

	protected T constant;
	protected T acceleration;

	public BoxSpring()
	{
	}
	
	public BoxSpring(T position, T constant)
	{
		this( position, Calculator.cloneLike( position ), constant );
	}
	
	public BoxSpring(T position, T rest, T constant)
	{
		super( rest, position, Calculator.createLike( position ) );
		
		this.constant = constant;
		this.acceleration = calc.create();
	}
	
	@Override
	public void update( GameState state, View<V> view )
	{
		// acceleration = (position - rest) * -constant
		// position += velocity * dt
		// velocity += acceleration * dt
		final float elapsed = state.seconds;
		
		acceleration = calc.sub( acceleration, rest, position );
		acceleration = calc.muli( acceleration, constant );
		
		position = calc.addsi( position, velocity, elapsed );
		velocity = calc.addsi( velocity, acceleration, elapsed );
	}
	
	public T constant()
	{
		return constant;
	}
	
	public T acceleration()
	{
		return acceleration;
	}

	@Override
	public void read( InputModel input )
	{
		
	}

	@Override
	public void write( OutputModel output )
	{
		
	}

}
