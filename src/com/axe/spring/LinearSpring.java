package com.axe.spring;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;
import com.axe.math.calc.CalculatorRegistry;


public class LinearSpring<V extends Vec<V>, T> extends AbstractSpring<V, T>
{

	protected T stiffness;
	protected T damping;
	
	protected T temp0;
	protected T temp1;
	
	public LinearSpring()
	{
	}
	
	public LinearSpring(T position, T damping, T stiffness)
	{
		this( position, Calculator.cloneLike( position ), damping, stiffness );
	}
	
	public LinearSpring(T position, T rest, T damping, T stiffness)
	{
		super( rest, position, Calculator.createLike( position ) );
		
		this.damping = damping;
		this.stiffness = stiffness;
		this.temp0 = calc.create();
		this.temp1 = calc.create();
	}

	@Override
	public void update( GameState gameState, View<V> view )
	{
		// velocity += ((stiffness * (position - rest)) - (damping * velocity)) * elapsed.seconds;
		// position += velocity * elapsed.seconds;
		final float elapsed = gameState.seconds;
		
		temp0 = calc.sub( temp0, position, rest );
		temp0 = calc.muli( temp0, stiffness );
		
		temp1 = calc.mul( temp1, damping, velocity );
		temp0 = calc.subi( temp0, temp1 );

		velocity = calc.addsi( velocity, temp0, elapsed );
		position = calc.addsi( position, velocity, elapsed );
	}

	public T stiffness() 
	{
		return stiffness;
	}
	
	public T damping() 
	{
		return damping;
	}

	@Override
	public void read( InputModel input )
	{
		// TODO calc
		// Attribute<T> factory = input.readInstance( "type" );
		
		position = calc.create();
		velocity = calc.create();
		stiffness = calc.create();
		damping = calc.create();
		rest = calc.create();
		temp0 = calc.create();
		temp1 = calc.create();
		
		input.readModel( "stiffness", stiffness, calc );
		input.readModel( "damping", damping, calc );
		input.readModel( "rest", rest, calc );
		input.readModel( "velocity", velocity, calc );
		input.readModel( "position", position, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", position );
		
		output.writeModel( "stiffness",stiffness, calc );
		output.writeModel( "damping", damping, calc );
		output.writeModel( "rest", rest, calc );
		output.writeModel( "velocity", velocity, calc );
		output.writeModel( "position", position, calc );
	}
	
	protected static <T> T clone(T a)
	{
		return CalculatorRegistry.getFor( a ).clone( a );
	}
	
	protected static <T> T create(T a)
	{
		return CalculatorRegistry.getFor( a ).create();
	}

}
