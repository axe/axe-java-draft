package com.axe.spring;

import com.axe.View;
import com.axe.game.GameState;
import com.axe.io.InputModel;
import com.axe.io.OutputModel;
import com.axe.math.Scalarf;
import com.axe.math.Vec;
import com.axe.math.calc.Calculator;


public class DistanceSpring<V extends Vec<V>, T> extends AbstractSpring<V, T>
{

	protected Scalarf distance;
	protected Scalarf stiffness;
	protected Scalarf damping;
	
	protected T temp;
	
	public DistanceSpring()
	{
	}
	
	public DistanceSpring(T position, Scalarf distance, Scalarf damping, Scalarf stiffness)
	{
		this( position, Calculator.cloneLike( position ), distance, damping, stiffness );
	}
	
	public DistanceSpring(T position, T rest, Scalarf distance, Scalarf damping, Scalarf stiffness)
	{		
		super( rest, position, Calculator.createLike( position ) );
		
		this.distance = distance;
		this.damping = damping;
		this.stiffness = stiffness;
		this.temp = calc.create();
	}

	@Override
	public void update( GameState gameState, View<V> view )
	{
		// d = DISTANCE( position, rest )
		// velocity += ((position - rest) / d * stiffness * |distance - d| - (damping * velocity)) * elapsed.seconds;
		// position += velocity * elapsed.seconds;

		final float elapsed = gameState.seconds;
		float d = calc.distance( position, rest );
		
		temp = calc.sub( temp, position, rest );
		
		if ( d != 0 )
		{
			temp = calc.scalei( temp, 1f / d );
			temp = calc.scalei( temp, (d - distance.v) * stiffness.v );
		}
		
		temp = calc.addsi( temp, velocity, -damping.v );
		
		velocity = calc.addsi( velocity, temp, elapsed );
		position = calc.addsi( position, velocity, elapsed );
	}

	public Scalarf stiffness() 
	{
		return stiffness;
	}
	
	public Scalarf damping() 
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
		rest = calc.create();
		temp = calc.create();

		if ( stiffness == null )
		{
			stiffness = new Scalarf();
		}
		
		if ( damping == null )
		{
			damping = new Scalarf();
		}
		
		input.readModel( "stiffness", stiffness );
		input.readModel( "damping", damping );
		input.readModel( "rest", rest, calc );
		input.readModel( "velocity", velocity, calc );
		input.readModel( "position", position, calc );
	}

	@Override
	public void write( OutputModel output )
	{
		output.writeInstance( "type", position );
		
		output.writeModel( "stiffness",stiffness );
		output.writeModel( "damping", damping );
		output.writeModel( "rest", rest, calc );
		output.writeModel( "velocity", velocity, calc );
		output.writeModel( "position", position, calc );
	}

}
