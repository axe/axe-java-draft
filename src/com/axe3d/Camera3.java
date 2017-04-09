package com.axe3d;

import com.axe.AbstractCamera;
import com.axe.core.Attribute;
import com.axe.game.GameState;
import com.axe.math.Bound3f;
import com.axe.math.Numbers;
import com.axe.math.Quaternion3;
import com.axe.math.Scalarf;
import com.axe.math.Vec3f;
import com.axe.math.calc.CalculatorCamera3;
import com.axe.node.Node;
import com.axe3d.math.Frustum3;

public class Camera3 extends AbstractCamera<Vec3f> implements Attribute<Camera3> 
{
	
	private static final Bound3f BOUNDS = new Bound3f();
	private static final Vec3f CENTER = new Vec3f();
	private static final Scalarf RADIUS = new Scalarf();
	private static final Vec3f MIN = new Vec3f();
	private static final Vec3f MAX = new Vec3f();
	
	public static final int SIZE = 40;
	
	public final Scalarf yaw = new Scalarf();
	public final Scalarf pitch = new Scalarf();
	public final Scalarf roll = new Scalarf();
	public final Scalarf distance = new Scalarf();
	public final Vec3f focus = new Vec3f();
	public final Scalarf fov = new Scalarf(60f);
	public final Scalarf aspect = new Scalarf(); //<auto>
	public final Scalarf near = new Scalarf(0.01f);
	public final Scalarf far = new Scalarf(100.0f);
	public final Frustum3 frustum = new Frustum3();
	public final Vec3f direction = new Vec3f();  //<auto>
	public final Vec3f right = new Vec3f(); //<auto>
	public final Vec3f up = new Vec3f(); //<auto>
	public final Vec3f position = new Vec3f(); //<auto>
	public final Vec3f forward = new Vec3f(); //<auto>
	public final Quaternion3 rollq = new Quaternion3(); // <auto>

	@Override
	public void update()
	{
		// Wrap all angles between 0 and 2PI
		yaw.mod(Numbers.PI2);
		pitch.mod(Numbers.PI2);
		roll.mod(Numbers.PI2);

		// Use yaw and pitch to calculate the direction and right vector.
		// The right vector always lies on the x,z plane. All three vectors
		// are unit vectors.
		float cosy = yaw.cos();
		float siny = yaw.sin();
		float cosp = pitch.cos();
		float sinp = pitch.sin();

		direction.normal(siny * cosp, sinp, -cosy * cosp);
		right.set(cosy, 0f, siny);
		forward.set(siny, 0f, -cosy);
		up.cross(right, direction);

		// If there is roll, setup the quaternion using the direction and
		// the requested roll angle, and rotate up and right.
		if (roll.v != 0f) 
		{
			rollq.set(direction, -roll.v);
			rollq.rotate(up);
			rollq.rotate(right);
		}

		// Start at the focus and back out (or forward into) by the given
		// distance in the current looking direction.
		position.set(focus);
		position.adds(direction, -distance.v);
		
		// Update Planes
		frustum.update(position, direction, up, right, fov.v, near.v, far.v);
	}
	
	@Override
	public boolean intersects(GameState state, Node<Vec3f> node) 
	{		
		if (node.getSphere(state, CENTER, RADIUS))
		{
			float distance = frustum.distance( CENTER );
			
			if ( distance > RADIUS.v )
			{
				return false;
			}
		}
		
		if (node.getBounds(state, MIN, MAX))
		{
			BOUNDS.set( MIN, MAX );
			
			if ( frustum.sign( BOUNDS ) == Frustum3.Outside )
			{
				return false;
			}
		}
		
		return true;
	}
	
	public Vec3f getOrientation( Vec3f out )
	{
		out.x = pitch.v;
		out.y = yaw.v;
		out.z = roll.v;
		
		return out;
	}
	
	@Override
	public Camera3 clone() 
	{
		return copy( create() );
	}
	
	@Override
	public CalculatorCamera3 getCalculator()
	{
		return CalculatorCamera3.INSTANCE;
	}

	@Override
	public Camera3 get() 
	{
		return this;
	}

}
