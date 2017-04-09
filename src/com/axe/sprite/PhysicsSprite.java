package com.axe.sprite;

import com.axe.View;
import com.axe.core.Factory;
import com.axe.game.GameState;
import com.axe.integrate.Integrator;
import com.axe.integrate.IntegratorEuler;
import com.axe.integrate.IntegratorVerlet;
import com.axe.math.Vec;

public class PhysicsSprite<V extends Vec<V>> extends Sprite<V> 
{

	public final V velocity;
	public final V acceleration;
	public Integrator<V> integrator;
	
	public PhysicsSprite(int id, Factory<V> factory) 
	{
		super(id, factory);
		
		this.velocity = factory.create();
		this.acceleration = factory.create();		
		this.setIntegratorEuler();
	}
	
	@Override
	public void update(GameState state, View<V> firstView)
	{
		integrator.update( state );
		
		super.update( state, firstView );
	}
	
	public PhysicsSprite<V> setVelocity(V velocity)
	{
		this.velocity.set( velocity );
		
		return this;
	}
	
	public PhysicsSprite<V> setAcceleration(V acceleration)
	{
		this.acceleration.set( acceleration );
		
		return this;
	}

	public PhysicsSprite<V> setIntegratorEuler()
	{
		this.integrator = new IntegratorEuler<V>( position, velocity, acceleration );
		
		return this;
	}
	
	public PhysicsSprite<V> setIntegratorVerlet()
	{
		this.integrator = new IntegratorVerlet<V>( position, acceleration );
		
		return this;
	}

}
