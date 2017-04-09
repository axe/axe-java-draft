package com.axe.integrate;

import com.axe.core.Attribute;
import com.axe.game.GameState;


public class IntegratorRungeKutta<T extends Attribute<T>> implements Integrator<T>
{

	public static final int DEFAULT_ITERATIONS = 4;
	
	public T position;
	public T acceleration;
	public int iterations;
	public Derivative d0;
	public Derivative d1;
	public Derivative d2;
	public Derivative d3;

	public IntegratorRungeKutta(T position, T acceleration)
	{
		this( position, acceleration, DEFAULT_ITERATIONS );
	}
	
	public IntegratorRungeKutta(T position, T acceleration, int iterations)
	{		
		this.position = position;
		this.acceleration = acceleration;
		this.d0 = new Derivative( position );
		this.d1 = new Derivative( position );
		this.d2 = new Derivative( position );
		this.d3 = new Derivative( position );
	}
	
	@Override
	public void update( GameState state )
	{
		// float delta = 1f / iterations;
		
		for (int i = 0; i < iterations; i++)
		{
			/*
			a = evaluate( state, t, 0.0f, Derivative() );
		    b = evaluate( state, t, dt*0.5f, a );
		    c = evaluate( state, t, dt*0.5f, b );
		    d = evaluate( state, t, dt, c );

		    float dxdt = 1.0f / 6.0f * 
		        ( a.dx + 2.0f*(b.dx + c.dx) + d.dx );
		    
		    float dvdt = 1.0f / 6.0f * 
		        ( a.dv + 2.0f*(b.dv + c.dv) + d.dv );

		    state.x = state.x + dxdt * dt;
		    state.v = state.v + dvdt * dt;
		    */
		}
		
	}
	
	
	// TODO implementations specific.
	public T acceleration(T out, State state, float t)
	{
		// Spring Example
		int springConstant = 10;
		int springDamping = 1;
		
		out.set( state.x );
		out.scale( -springConstant );
		out.adds( state.v, -springDamping );
		
		return out;
	}
	
	public Derivative evaluate(Derivative out, State initial, float t, float dt, Derivative d)
	{
		State state = new State( initial.v );
		
		state.x.set( initial.x );
		state.x.adds( d.dx, dt );
		
		state.v.set( initial.v );
		state.v.adds( d.dv, dt );
		
		out.dx = state.v;
		out.dv = acceleration( initial.v.clone(), state, t + dt );
		
		return out;
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
	
	public class State
	{
		public T x, v;
		
		public State(T factory)
		{
			this.x = factory.clone();
			this.v = factory.clone();
		}
	}
	
	public class Derivative
	{
		public T dx, dv;
		
		public Derivative(T factory)
		{
			this.dx = factory.clone();
			this.dv = factory.clone();
		}
	}

}
