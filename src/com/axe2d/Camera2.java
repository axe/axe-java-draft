package com.axe2d;

import com.axe.AbstractCamera;
import com.axe.core.Attribute;
import com.axe.game.GameState;
import com.axe.math.Bound2f;
import com.axe.math.Scalarf;
import com.axe.math.Vec2f;
import com.axe.math.calc.CalculatorCamera2;
import com.axe.node.Node;

public class Camera2 extends AbstractCamera<Vec2f> implements Attribute<Camera2>
{
	
	private static final Vec2f MIN = new Vec2f();
	private static final Vec2f MAX = new Vec2f();
	private static final Bound2f BOUNDS = new Bound2f();
	
	public static final int SIZE = 28;

	public final Scalarf roll = new Scalarf(0f);
	public final Vec2f center = new Vec2f();
	public final Vec2f scale = new Vec2f(1f, 1f);
	public final Vec2f size = new Vec2f();
	public final Scalarf near = new Scalarf(0);
	public final Scalarf far = new Scalarf(1);
	public final Vec2f halfSizeScaled = new Vec2f(); // <auto>
	public final Vec2f up = new Vec2f(); //<auto>
	public final Vec2f right = new Vec2f(); //<auto>
	public final Bound2f bounds = new Bound2f(); //<auto>
	
	// sceneBounds, scaleMax, scaleMin, sizeMax, sizeMin
	// offset (from center) - for shake spring
	// 
	
	@Override
	public void update()
	{
		float cosr = roll.cos();
		float sinr = roll.sin();

		right.set(cosr, sinr);
		up.set(-sinr, cosr);
		bounds.quad(center.x, center.y, size.x, size.y, cosr, sinr);
		
		halfSizeScaled.set( size );;
		halfSizeScaled.mul( scale );
		halfSizeScaled.scale( 0.5f );
	}
	
	@Override
	public boolean intersects(GameState state, Node<Vec2f> node) 
	{
		if (node.getBounds(state, MIN, MAX))
		{
			BOUNDS.set( MIN, MAX );
			
			if ( !BOUNDS.intersects( bounds ) )
			{
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public Camera2 get()
	{
		return this;
	}
	
	@Override
	public CalculatorCamera2 getCalculator()
	{
		return CalculatorCamera2.INSTANCE;
	}
	
	@Override
	public Camera2 clone()
	{
		return copy( create() );
	}
	
}
